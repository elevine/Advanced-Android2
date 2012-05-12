package com.elevine.aww;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class RedditResponse {
	public ResponseData data;
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class ResponseData{
		private List<PostWrapper> children;

		public List<PostWrapper> getChildren() {
			return children;
		}

		public void setChildren(List<PostWrapper> children) {
			this.children = children;
		}
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class PostWrapper{
		Post data;

		public Post getData() {
			return data;
		}

		public void setData(Post data) {
			this.data = data;
		}
		
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Post{
		private String title;
		private String thumbnail;
		private String url;
		
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getThumbnail() {
			return thumbnail;
		}
		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
		
	}

	public ResponseData getData() {
		return data;
	}

	public void setData(ResponseData data) {
		this.data = data;
	}
	
	
}
