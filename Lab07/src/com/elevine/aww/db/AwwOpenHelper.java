package com.elevine.aww.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AwwOpenHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "aww.db";
	private static final int DATABASE_VERSION = 1;
	public static final String FAVORITES_TABLE_NAME = "favorite_posts";
	public static final String COL_ID = "_id";
	public static final String COL_URL = "url";
	public static final String COL_TITLE = "title";
	public static final String COL_THUMBNAIL = "thumbnail";
	public static final String TABLE_CREATE = "CREATE TABLE "
			+ FAVORITES_TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY,"
			+ COL_URL + " TEXT," + COL_THUMBNAIL + " TEXT," + COL_TITLE
			+ " TEXT);";

	public AwwOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
