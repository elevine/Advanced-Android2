package com.elevine.aww;

import com.elevine.aww.db.AwwOpenHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class AwwProvider extends ContentProvider {
	private AwwOpenHelper openHelper = null;
	public static final String AUTHORITY = "com.elevine.aww.provider.AwwProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/posts");
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = this.openHelper.getWritableDatabase();
		int rows = db.delete(AwwOpenHelper.FAVORITES_TABLE_NAME, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return rows;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = this.openHelper.getWritableDatabase();
		long rowId = db.insert(AwwOpenHelper.FAVORITES_TABLE_NAME, null, values);
		
		if(rowId > 0){
			Uri postUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(postUri, null);
	        return postUri;
		}
		 throw new IllegalArgumentException("Failed to insert row into " + uri);
	}
	
	
	
	@Override
	public boolean onCreate() {
		this.openHelper = new AwwOpenHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = this.openHelper.getReadableDatabase();
		
		Cursor c = db.query(AwwOpenHelper.FAVORITES_TABLE_NAME, projection, selection, selectionArgs, null,
				null, sortOrder);

		// Tell the cursor what uri to watch, so it knows when its source data
		// changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
