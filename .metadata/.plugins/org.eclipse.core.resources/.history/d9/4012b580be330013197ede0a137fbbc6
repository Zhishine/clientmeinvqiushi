package com.news.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

public class ImageAdapter extends BaseAdapter{
	private Context _context;
    private List<Drawable> m_imgList;
	public ImageAdapter(Context context) {
	    _context = context;
	}

	public int getCount() {
	    return Integer.MAX_VALUE;
	}

	public Object getItem(int position) {

	    return position;
	}

	public long getItemId(int position) {
	    return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			ImageView imageView = new ImageView(_context);
		    imageView.setAdjustViewBounds(true);
		    imageView.setScaleType(ScaleType.FIT_XY);
		    imageView.setLayoutParams(new Gallery.LayoutParams(
			    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		    convertView = imageView;
		    viewHolder.imageView = (ImageView)convertView; 
		    convertView.setTag(viewHolder);
			
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
	    viewHolder.imageView.setImageDrawable(m_imgList.get(position%m_imgList.size()));
	    
	    return convertView;

    }
    
    private static class ViewHolder
	{
		ImageView imageView;
	}

}
