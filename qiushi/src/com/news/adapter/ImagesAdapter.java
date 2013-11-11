package com.news.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.news.modal.MImage;
import com.news.modal.MNews;
import com.news.qiushi.R;
import com.news.tool.DensityUtil;
import com.news.tool.image.ImageFetcher;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImagesAdapter extends BaseAdapter{
	private ImageFetcher mImageFetcher;
	private List<MImage> m_imgsList=null;
	public static Context m_context=null;
	LayoutInflater m_layoutInflater=null;
	//DisplayImageOptions options=null;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private final int IMAGE_PADDING =10;
    public ImagesAdapter(Context context,List<MImage> imgsList){
    	
    	this.m_context=context;
    	this.m_imgsList=imgsList;
    	this.m_layoutInflater=LayoutInflater.from(m_context);
//    	options = new DisplayImageOptions.Builder()
//		.showImageForEmptyUri(R.drawable.empty_photo)
//		.showImageOnFail(R.drawable.empty_photo)
//		.cacheInMemory(true)
//		.cacheOnDisc(true)
//		.build();
    	 mImageFetcher = new ImageFetcher(m_context, 300);
        // mImageFetcher.setLoadingImage(R.drawable.empty_photo);
    }

    public void addImages(List<MImage> images){
    	m_imgsList.addAll(images);
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(this.m_imgsList==null){
			return 0;
		}
		return this.m_imgsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if(this.m_imgsList==null){
			return null;
		}
		return this.m_imgsList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//MImage Item =  this.m_imgsList.get(position);
		MImage item = (MImage)this.getItem(position);
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		ImageViewHold holder = null;
		
		 if (convertView == null) {
	            LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
	            convertView = layoutInflator.inflate(R.layout.infos_list, null);
	            holder = new ImageViewHold();
	            holder.imageView = (ImageView) convertView.findViewById(R.id.news_pic);
	            holder.contentView = (TextView) convertView.findViewById(R.id.news_title);
	            convertView.setTag(holder);
	        }
	        holder = (ImageViewHold) convertView.getTag();
	        float scale = ((float)item.mHeight)/((float)item.mWidth);
	        int imgWidth = DensityUtil.getLogicalWidth()/2-DensityUtil.dip2px(2);
			int imgHeight = (int)(imgWidth*scale);
//	        float iHeight = ((float) 200 / 183 * duitangInfo.getHeight());
	        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, imgHeight));
	        holder.imageView.setImageDrawable(null);
	      //holder.imageView.setBackgroundResource(R.drawable.test);
	          mImageFetcher.loadImage(item.mImageUrl, holder.imageView);
	       // imageLoader.displayImage(item.mImageUrl, holder.imageView,options,animateFirstListener);
	        if(!item.mDescription.equalsIgnoreCase("")){
	        	holder.contentView.setText(item.mDescription);
	        	holder.contentView.setTextSize(14);
	    		}
	    		else{
	    			holder.contentView.setText(null);
	    			holder.contentView.setTextSize(0);
	    		}
	        //mImageFetcher.loadImage(duitangInfo.getIsrc(), holder.imageView);
	        return convertView;
		

	}
	
	private static class ImageViewHold
	{
		public ImageView imageView;
		public TextView contentView;
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					ImageView imageView = (ImageView) view;
				
					boolean firstDisplay = !displayedImages.contains(imageUri);
					if (firstDisplay) {
						FadeInBitmapDisplayer.animate(imageView, 500);
						displayedImages.add(imageUri);
					}
				}
			}
		}
}
