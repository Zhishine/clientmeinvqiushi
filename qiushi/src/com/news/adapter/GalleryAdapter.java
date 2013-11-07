package com.news.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.news.modal.MGallery;
import com.news.qiushi.R;
import com.news.tool.DensityUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GalleryAdapter extends BaseAdapter {

	List<MGallery> m_galleryList=null;
	Context m_context;
	DisplayImageOptions options=null;
	 float m_rate=(float)480/(float)480;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	public GalleryAdapter(Context context,List<MGallery> galleryList){
		this.m_galleryList=galleryList;
		this.m_context=context;
		options = new DisplayImageOptions.Builder()
	      
		.showImageForEmptyUri(R.drawable.image_click)
		.showImageOnFail(R.drawable.image_click)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.build();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return m_galleryList.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return m_galleryList.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		 ImageLoader imageLoader = ImageLoader.getInstance();
		 MGallery entity=m_galleryList.get(index);
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			ImageView imageView = new ImageView(m_context);
		
		    imageView.setAdjustViewBounds(true);
		   // imageView.setBackgroundColor(Color.BLACK);
		    imageView.setScaleType(ScaleType.FIT_XY );
		    int height=(int) (DensityUtil.getLogicalWidth()*m_rate+0.5);
		    Gallery.LayoutParams lp=new Gallery.LayoutParams(
		    		LayoutParams.MATCH_PARENT ,LayoutParams.MATCH_PARENT);
		    
		  
		    LinearLayout container=new LinearLayout(m_context);
		    container.setGravity(Gravity.CENTER);
		    LinearLayout.LayoutParams containerParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		    containerParams.gravity=Gravity.CENTER;
		    container.setLayoutParams(lp);
		    container.addView(imageView);
		    imageView.setLayoutParams(containerParams);
		    convertView = container;
		    viewHolder.imageView = (ImageView)imageView; 
		    convertView.setTag(viewHolder);
			
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		imageLoader.displayImage(entity.mImageUrl,viewHolder.imageView, options, animateFirstListener);
	    return convertView;
	}
	private static class ViewHolder
	{
		LinearLayout linear;
		ImageView imageView;
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				imageView.setScaleType(ScaleType.FIT_XY);
				int height=loadedImage.getHeight();
				int width=loadedImage.getWidth();
				float rate=(float)height/(float)width;
				width=DensityUtil.getLogicalWidth();
				height=(int) (rate*width);
				LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width, height);
				lp.gravity=Gravity.CENTER;
				imageView.setLayoutParams(lp);
				imageView.invalidate();
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
