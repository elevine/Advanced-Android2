package com.elevine.aww;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends ArrayAdapter<Post> {
	LayoutInflater inflater = null;
	public PostAdapter(Context context, List<Post> objects) {
		super(context, R.layout.post_row, objects);
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

        if (convertView == null) {
            view = this.inflater.inflate(R.layout.post_row, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.thumbnailView = (ImageView)view.findViewById(R.id.thumbnail);
            holder.titleView = (TextView)view.findViewById(R.id.title);
            view.setTag(holder);
        } else {
            view = convertView;
        }
        Post post = getItem(position);
        ViewHolder holder = (ViewHolder)view.getTag();
        holder.titleView.setText(post.getTitle());
        
       
		try {
			InputStream is = (InputStream) new URL(post.getThumbnailUrl()).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        holder.thumbnailView.setImageDrawable(d);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        
        return view;
	}
	
	private static class ViewHolder{
		ImageView thumbnailView = null;
		TextView titleView = null;
	}
	
	
}
