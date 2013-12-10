
package com.bahrunnur.monocle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.bahrunnur.monocle.models.Post;
import com.bahrunnur.monocle.models.PostList;
import com.bahrunnur.monocle.rest.RestClient;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;

@EActivity(R.layout.activity_main)
public class MainActivity extends SherlockActivity implements TabListener {

	private static final int POPULAR_TAB = 0x00000001;
    private static final int NEWEST_TAB = 0x00000002;
    
    private static final String LIST_STATE = "listState"; 
    
    private String[] locations;
    private PostList mPostList;
    private PostAdapter mPostAdapter;
    private Parcelable mPostListState = null;
    private ActionBar mActionBar;
    private MenuItem mRefreshMenu;
    private int mCurrentActiveTab;
	
	@ViewById(R.id.post_list_view)
	ListView mPostListView;
	
    @RestService
    RestClient restClient;

    private OnItemClickListener mPostListClickHandler = new OnItemClickListener() {
    	@Override
    	public void onItemClick(AdapterView parent, View v, int position, long id) {
    		Intent outIntent = new Intent(MainActivity.this, DetailActivity_.class);
    		Post postHolder = mPostAdapter.getItem(position);
    		
    		outIntent.putExtra("POST_TITLE", postHolder.getTitle());
    		outIntent.putExtra("POST_VOTES", postHolder.getVotes());
    		outIntent.putExtra("POST_DOMAIN", postHolder.getDomain());
    		outIntent.putExtra("POST_USER", postHolder.getUserHandle());
    		outIntent.putExtra("POST_SUMMARY", postHolder.getSummary());
    		outIntent.putExtra("POST_URL", postHolder.getUrl());
    		outIntent.putExtra("POST_PHOTO_URL", postHolder.getPreviewUrl());
    		
    		startActivity(outIntent);
    	}
	};
    
    @AfterViews
    public void afterViews() {
        locations = getResources().getStringArray(R.array.locations);
        configureActionBar();
        
        if (mPostListView == null) {
            mRefreshMenu.setActionView(R.layout.progressbar);
            mRefreshMenu.expandActionView();
        }
        
        mPostListView.setOnItemClickListener(mPostListClickHandler);
    }

    @UiThread
    void updateAdapters(PostList list) {
    	mPostAdapter = new PostAdapter(this, list);
        mPostListView.setAdapter(mPostAdapter);
        
        mRefreshMenu.collapseActionView();
        mRefreshMenu.setActionView(null);
    }

    @Background
    void getMonoclePopularPosts() {
        mPostList = restClient.getPopularPost();
        updateAdapters(mPostList);
    }
    
    @Background
    void getMonocleNewestPosts() {
    	mPostList = restClient.getNewestPost();
    	updateAdapters(mPostList);
    }

    private void configureActionBar() {
        mActionBar = getSupportActionBar();
        
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
        		| ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE);
        
        for (String location: locations) {
            Tab tab = getSupportActionBar().newTab();
            tab.setText(location);
            tab.setTabListener(this);
            getSupportActionBar().addTab(tab);
        }
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
    	if (tab.getText().equals("Newest")) {
    		mCurrentActiveTab = NEWEST_TAB;
    		getMonocleNewestPosts();
    	} else {
    		mCurrentActiveTab = POPULAR_TAB;
    		getMonoclePopularPosts();
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        mRefreshMenu = menu.findItem(R.id.menu_refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	case R.id.menu_refresh:
        		mRefreshMenu.setActionView(R.layout.progressbar);
        		mRefreshMenu.expandActionView();
        		
        		if (mCurrentActiveTab == POPULAR_TAB)
        			getMonoclePopularPosts();
        		else
        			getMonocleNewestPosts();
        		
            case R.id.menu_settings:
                // open settings activity
            	Log.i("TEST", "Halo");
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	if (mPostListState != null)
    		mPostListView.onRestoreInstanceState(mPostListState);
    	mPostListState = null;
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle state) {
    	super.onRestoreInstanceState(state);
    	mPostListState = state.getParcelable(LIST_STATE);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle state) {
    	super.onSaveInstanceState(state);
    	mPostListState = mPostListView.onSaveInstanceState();
        state.putParcelable(LIST_STATE, mPostListState);
    }
    
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {}
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {}
}
