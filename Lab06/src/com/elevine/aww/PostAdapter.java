package com.elevine.aww;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.elevine.aww.RedditResponse.Post;
import com.elevine.aww.RedditResponse.PostWrapper;
import com.github.ignition.support.images.remote.RemoteImageLoader;

public class PostAdapter extends ArrayAdapter<PostWrapper> {
	LayoutInflater inflater = null;
	private RemoteImageLoader imageLoader = null;
	private Drawable defaultDrawable = null;
	
	public PostAdapter(Context context, List<PostWrapper> objects) {
		super(context, R.layout.post_row, objects);
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = new RemoteImageLoader(context);
		this.imageLoader.setDownloadFailedDrawable(context.getResources().getDrawable(R.drawable.error));
		this.defaultDrawable = context.getResources().getDrawable(R.drawable.picture);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

        if (convertView == null) {
            view = this.inflater.inflate(R.layout.post_row, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.thumbnailView = (ImageView)view.findViewById(R.id.thumbnail);
            holder.titleView = (TextView)view.findViewById(R.id.title);
            holder.favoriteCheckbox = (CheckBox)view.findViewById(R.id.check_box_fav);
            view.setTag(R.id.holder, holder);
        } else {
            view = convertView;
        }
        PostWrapper postWrapper = getItem(position);
        Post post = postWrapper.getData();
        ViewHolder holder = (ViewHolder)view.getTag(R.id.holder);
        holder.titleView.setText(post.getTitle());
        holder.titleView.setTag(R.id.pos, position);
        holder.thumbnailView.setTag(R.id.pos, position);
        imageLoader.loadImage(post.getThumbnail(), holder.thumbnailView, defaultDrawable);
        holder.favoriteCheckbox.setTag(R.id.pos, position);
        return view;
	}
	
	private static class ViewHolder{
		ImageView thumbnailView = null;
		TextView titleView = null;
		CheckBox favoriteCheckbox = null;
	}
	
	
}
