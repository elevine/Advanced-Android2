package com.elevine.aww;

import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;

public class AwwClientActivity extends SherlockFragmentActivity {

	private PostsFragment postsFragment = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// This has to be called before setContentView and you must use the
		// class in com.actionbarsherlock.view and NOT android.view
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.main);
		this.postsFragment = (PostsFragment)getSupportFragmentManager().findFragmentById(R.id.posts_fragment);
	}
	
	public void onThumbnailClick(View v){
		postsFragment.onThumbnailClick(v);
	}
	
	public void onTitleClick(View v){
		postsFragment.onTitleClick(v);
	}
	
	
	public void onFavoriteClick(View v){
		postsFragment.onFavoriteClick(v);
	}

}