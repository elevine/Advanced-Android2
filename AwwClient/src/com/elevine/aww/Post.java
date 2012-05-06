package com.elevine.aww;

public class Post {
	String title = null;
	String thumbnailUrl = null;
	
	
	
	public Post(String title, String thumbnailUrl) {
		super();
		this.title = title;
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	
}
