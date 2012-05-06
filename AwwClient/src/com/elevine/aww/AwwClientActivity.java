package com.elevine.aww;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

public class AwwClientActivity extends ListActivity {
	private static final String AWW_URL = "http://www.reddit.com/r/aww.json";
	private List<Post> posts = new ArrayList<Post>();
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
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(AWW_URL);
			HttpResponse response;
			try {
				response = client.execute(request);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));

				StringBuilder responseBody = new StringBuilder();
				String nextLine = null;
				while ((nextLine = reader.readLine()) != null) {
					responseBody.append(nextLine);

				}
				JSONObject jsonResponse = new JSONObject(responseBody.toString());
				JSONArray children = jsonResponse.getJSONObject("data").getJSONArray("children");
				
				for (int index = 0; index < children.length(); index++) {
					JSONObject data = children.getJSONObject(index).getJSONObject("data");
					
					String title  = data.getString("title");
					String imageUrl = data.getString("thumbnail");
					
					Post post = new Post(title, imageUrl);
					posts.add(post);

					try {
						get(100, TimeUnit.MILLISECONDS);
					} catch (ExecutionException e) {
						e.printStackTrace();
					} catch (TimeoutException e) {
						e.printStackTrace();
					}

					publishProgress(index, jsonResponse.length());
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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