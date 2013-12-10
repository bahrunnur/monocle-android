package com.bahrunnur.monocle;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.post_detail)
public class DetailActivity extends SherlockActivity {
	
	@ViewById(R.id.post_preview_image)
	ImageView mPostPreviewImage;
	
	@ViewById(R.id.post_title)
	TextView mPostTitle;
	
	@ViewById(R.id.post_votes)
	TextView mPostVotes;
	
	@ViewById(R.id.post_domain)
	TextView mPostDomain;
	
	@ViewById(R.id.post_stale_time)
	TextView mPostStaleTime;
	
	@ViewById(R.id.post_user_handle)
	TextView mPostUserHandle;
	
	@ViewById(R.id.post_summary)
	TextView mPostSummary;
	
	@ViewById(R.id.post_read_more)
	Button mPostReadMore;
	
	private String mPostUrl;
	private Drawable mPreviewImage;
	
	@AfterViews
	public void afterViews() {
		Bundle extras = getIntent().getExtras();
		
		mPostTitle.setText(extras.getString("POST_TITLE"));
		mPostVotes.setText(extras.getInt("POST_VOTES") + "");
		mPostDomain.setText(extras.getString("POST_DOMAIN"));
		mPostUserHandle.setText("by " + extras.getString("POST_USER"));
		mPostSummary.setText(extras.getString("POST_SUMMARY"));
		
		// url to open in the browser
		mPostUrl = extras.getString("POST_URL");
		
		setActionBarTitle(extras.getString("POST_TITLE"));
		getPreviewImage(extras.getString("POST_PHOTO_URL"));
	}
	
	@Click(R.id.post_read_more)
	void readMoreClicked() {
		Intent outIntent = new Intent(Intent.ACTION_VIEW);
		outIntent.setData(Uri.parse(mPostUrl));
		startActivity(outIntent);
	}
	
	@UiThread
	void updatePreviewImage() {
		// set the preview image
		mPostPreviewImage.setImageDrawable(mPreviewImage);
	}
	
	@Background
	void getPreviewImage(String address) {
		mPreviewImage = loadImage(address);
		updatePreviewImage();
	}
	
	private void setActionBarTitle(String title) {
		ActionBar bar = getSupportActionBar();
		bar.setTitle(title);
		bar.setDisplayHomeAsUpEnabled(true);
	}
	
	private Drawable loadImage(String address) {
		Drawable imageDraw = null;
		try {
			URL url = new URL(address);
			InputStream content = (InputStream) url.getContent();
			imageDraw = Drawable.createFromStream(content, "src");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageDraw;
	}
	
}
