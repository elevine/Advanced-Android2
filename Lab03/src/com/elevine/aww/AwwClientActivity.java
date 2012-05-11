package com.elevine.aww;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

public class AwwClientActivity extends ListActivity {
	private static final String AWW_URL = "http://www.reddit.com/r/aww.json";
	private List<RedditResponse.PostWrapper> posts = new ArrayList<RedditResponse.PostWrapper>();
	private ProgressDialog pd = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new FetchDataTask().execute();
	}

	private class FetchDataTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = ProgressDialog.show(AwwClientActivity.this, "Working..",
					"Fetching new posts!", false);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			RestTemplate template = new RestTemplate();
			RedditResponse jsonResponse =  template.getForObject(AWW_URL, RedditResponse.class);
			posts = jsonResponse.data.getChildren();
						
			return null;
		}
		
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			if (values[0] == 0) {
				pd.hide();
				pd = new ProgressDialog(AwwClientActivity.this);
				pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pd.setMessage("Processing posts...");
				pd.setCancelable(false);
				pd.setMax(values[1]);
				pd.show();
			}

			pd.setProgress(values[0]);

		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			pd.dismiss();
			PostAdapter adapter = new PostAdapter(AwwClientActivity.this, posts);
			setListAdapter(adapter);
		}
		

	}

}