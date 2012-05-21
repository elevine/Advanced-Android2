package com.elevine.aww;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;

public class FavoritesFragment extends SherlockListFragment{
	private ContentResolver contentResolver = null;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		this.contentResolver = getActivity().getContentResolver();
		
		
		Cursor cursor = contentResolver.query(AwwProvider.CONTENT_URI, null, null, null, null);
		setListAdapter(new FavoritesAdapter(getActivity(), cursor));
	}
		
	
}
