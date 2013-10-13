package com.news.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



import com.news.modal.MImage;
import com.news.modal.MNews;
import com.news.qiushi.R;
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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImagesAdapter extends BaseAdapter{
	
	private List<MImage> m_imgsList=null;
	private Context m_context=null;
	LayoutInflater m_layoutInflater=null;
	DisplayImageOptions options=null;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
    public ImagesAdapter(Context context,List<MImage> imgsList){
    	
    	this.m_context=context;
    	this.m_imgsList=imgsList;
    	this.m_layoutInflater=LayoutInflater.from(m_context);
    	options = new DisplayImageOptions.Builder()
      
		.showImageForEmptyUri(R.drawable.image_click)
		.showImageOnFail(R.drawable.image_click)
		.cacheInMemory(true)
		.cacheOnDisc(true)

		.displayer(new RoundedBitmapDisplayer(20))
		.build();

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
		MImage Item = (MImage)this.getItem(position);
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		ImageViewHold viewHold = null;
		
		if(convertView==null){
			
	//		 convertView = this.m_layoutInflater.inflate(R.layout.news_item, null);
			 convertView = this.m_layoutInflater.inflate(R.layout.images_item, null); 
			 convertView.setBackgroundColor(Color.WHITE);
			 viewHold = new ImageViewHold();
			 //(ImageView) convertView.findViewById(R.id.title_img);
			 viewHold.img = (ImageView)convertView.findViewById(R.id.image_img);
			// viewHold.img.setBackgroundColor(Color.WHITE);
			 convertView.setTag(viewHold);
			 
		}else{
			
			viewHold = (ImageViewHold)convertView.getTag();
		}
		
		//imageLoader.displayImage(entity.mTitleImageUrl,viewHolder.mTitleImg, options, animateFirstListener);
		imageLoader.displayImage(Item.mImageUrl, viewHold.img,options,animateFirstListener);
		
		return convertView;
	}
	
	private static class ImageViewHold
	{
		public ImageView img;
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