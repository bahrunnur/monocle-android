package com.bahrunnur.monocle.models;

public class Post {
	
	private String id;
	private int votes;
	private boolean voted;
	private boolean created;
	private float score;
	private String title;
	private String url;
	private String slug;
	private String domain;
	private String summary;
	private String preview_url;
	private String link_icon_url;
	private String comments_count;
	private String user_id;
	private String user_handle;
	private String created_at;
	
	public Post(String id, int votes) {
		this.id = id;
		this.votes = votes;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public int getVotes() {
		return this.votes;
	}
	
	public String getUserHandle() {
		return this.user_handle;
	}
	
	public String getDomain() {
		return this.domain;
	}
	
	public String getSummary() {
		return this.summary;
	}
	
	public String getPreviewUrl() {
		return this.preview_url;
	}
	
}
