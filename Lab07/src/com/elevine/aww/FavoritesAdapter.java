package com.elevine.aww;

import com.elevine.aww.db.AwwOpenHelper;
import com.github.ignition.support.images.remote.RemoteImageLoader;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoritesAdapter extends CursorAdapter {
	private LayoutInflater inflater = null;
	private int titleIndex = -1;
	private int thumbnailIndex = -1;
	private int urlIndex = -1;
	private RemoteImageLoader imageLoader = null;
	private Drawable defaultDrawable = null;

	public FavoritesAdapter(Context context, Cursor cursor) {
		super(context, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		titleIndex = cursor.getColumnIndex(AwwOpenHelper.COL_TITLE);
		thumbnailIndex = cursor.getColumnIndex(AwwOpenHelper.COL_THUMBNAIL);
		urlIndex = cursor.getColumnIndex(AwwOpenHelper.COL_URL);
		this.imageLoader = new RemoteImageLoader(context);
		this.imageLoader.setDownloadFailedDrawable(context.getResources()
				.getDrawable(R.drawable.picture));
		this.defaultDrawable = context.getResources().getDrawable(
				R.drawable.picture);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		String title = cursor.getString(titleIndex);
		String thumbnail = cursor.getString(thumbnailIndex);
		String url = cursor.getString(urlIndex);
		
		ViewHolder holder = (ViewHolder)view.getTag(R.id.holder);
		holder.titleView.setText(title);
        holder.titleView.setTag(R.id.url, url);
        holder.thumbnailView.setTag(R.id.url, url);
        imageLoader.loadImage(thumbnail, holder.thumbnailView, defaultDrawable);
   
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflater.inflate(R.layout.post_row, null);
		ViewHolder holder = new ViewHolder();
		holder.thumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
		holder.titleView = (TextView) view.findViewById(R.id.title);
		
		view.findViewById(R.id.check_box_fav).setVisibility(View.GONE);
		view.setTag(R.id.holder, holder);
		return view;
	}

	private static class ViewHolder {
		ImageView thumbnailView = null;
		TextView titleView = null;
	}
}
