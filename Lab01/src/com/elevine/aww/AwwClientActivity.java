package com.elevine.aww;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import com.fasterxml.jackson.databind.ObjectMapper;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

public class AwwClientActivity extends ListActivity {
	private static final String AWW_URL = "http://www.reddit.com/r/aww.json";
	private List<RedditResponse.PostWrapper> posts = new ArrayList<RedditResponse.PostWrapper>();
	private ProgressDialog pd = null;
	private ObjectMapper mapper = new ObjectMapper();
	
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
				
				RedditResponse jsonResponse = mapper.readValue(responseBody.toString(), RedditResponse.class);
				posts = jsonResponse.data.getChildren();
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> getData(Map<String,Object> jsonResponse){
			Map<String,Object> data = mapper.convertValue(jsonResponse.get("data"), Map.class);
			return mapper.convertValue(data.get("children"), List.class);
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