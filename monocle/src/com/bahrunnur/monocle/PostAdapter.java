package com.bahrunnur.monocle;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bahrunnur.monocle.models.Post;
import com.bahrunnur.monocle.models.PostList;

public class PostAdapter extends ArrayAdapter<Post> {

	private final Context mContext;
	private final List<Post> mPostList;
	
	private LayoutInflater mInflater;
	
	public PostAdapter(Context context, PostList postList) {
		super(context, R.layout.post_item, postList);
		this.mContext = context;
		this.mPostList = postList;
	}
	
	@Override
	public int getCount() {
		return mPostList.size();
	}

	@Override
	public Post getItem(int position) {
		return mPostList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// this is bullshit
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = (LinearLayout) mInflater.inflate(R.layout.post_item, null);
			
			PostViewHolder holder = new PostViewHolder();
			holder.postTitle = (TextView) convertView.findViewById(R.id.post_title);
			holder.postVotes = (TextView) convertView.findViewById(R.id.post_votes);
			holder.postUrl = (TextView) convertView.findViewById(R.id.post_url);
			holder.postUserHandle = (TextView) convertView.findViewById(R.id.post_user_handle);
			
			convertView.setTag(holder);
		}
		
		Post currentItem = getItem(position);
		PostViewHolder holder = (PostViewHolder) convertView.getTag();
		holder.postTitle.setText(currentItem.getTitle());
		holder.postVotes.setText(currentItem.getVotes() + "");
		holder.postUrl.setText(currentItem.getDomain());
		holder.postUserHandle.setText("by " + currentItem.getUserHandle());
		
		return convertView;
	}
	
	static class PostViewHolder {
		TextView postTitle;
		TextView postVotes;
		TextView postUrl;
		TextView postUserHandle;
	}

}
