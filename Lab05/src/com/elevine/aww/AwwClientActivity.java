package com.elevine.aww;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.elevine.aww.RedditResponse.Post;
import com.elevine.aww.db.AwwOpenHelper;

public class AwwClientActivity extends SherlockListActivity {
	private static final String AWW_URL = "http://www.reddit.com/r/aww.json";
	private List<RedditResponse.PostWrapper> posts = new ArrayList<RedditResponse.PostWrapper>();
	private AwwOpenHelper openHelper = null;
	private SQLiteDatabase database = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// This has to be called before setContentView and you must use the
		// class in com.actionbarsherlock.view and NOT android.view
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.main);
		
		openHelper = new AwwOpenHelper(this);
		this.database = openHelper.getWritableDatabase();
		
		new FetchDataTask().execute();

	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Post post = posts.get(position).getData();
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(post.getUrl()));
		startActivity(i);
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Refresh")
			.setIcon(R.drawable.ic_action_refresh)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Refresh")){
			new FetchDataTask().execute();
			return true;
		}
		else{
			return super.onOptionsItemSelected(item);
		}
	}

	private class FetchDataTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			setSupportProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Void doInBackground(Void... params) {
			RestTemplate template = new RestTemplate();
			RedditResponse jsonResponse = template.getForObject(AWW_URL,
					RedditResponse.class);
			posts = jsonResponse.data.getChildren();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			PostAdapter adapter = new PostAdapter(AwwClientActivity.this, posts);
			setListAdapter(adapter);
			setSupportProgressBarIndeterminateVisibility(false);
		}

	}
	private final String deleteWhere = AwwOpenHelper.COL_ID+"=?";
	
	public void onThumbnailClick(View v){
		onPostItemClick(v);
	}
	
	public void onTitleClick(View v){
		onPostItemClick(v);
	}
	
	
	private void onPostItemClick(View v){
		int position = (Integer)v.getTag(R.id.pos);
		Post post = posts.get(position).getData();
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(post.getUrl()));
		startActivity(i);
	}
	
	public void onFavoriteClick(View v){
		int position = (Integer)v.getTag(R.id.pos);
		Post post = posts.get(position).getData();
		
		CheckBox checkBox = (CheckBox)v;
		if(checkBox.isChecked()){
			
			
			ContentValues cv = new ContentValues();
			cv.put(AwwOpenHelper.COL_THUMBNAIL, post.getThumbnail());
			cv.put(AwwOpenHelper.COL_TITLE, post.getTitle());
			cv.put(AwwOpenHelper.COL_URL, post.getUrl());
			
			long id = this.database.insert(AwwOpenHelper.FAVORITES_TABLE_NAME, null, cv);
			post.setId(id);
			
			checkBox.setButtonDrawable(android.R.drawable.btn_star_big_on);
		}
		else{
			Long id = post.getId();
			this.database.delete(AwwOpenHelper.FAVORITES_TABLE_NAME, deleteWhere, new String[]{id.toString()});
			
			checkBox.setButtonDrawable(android.R.drawable.btn_star_big_off);
		}
	}

}