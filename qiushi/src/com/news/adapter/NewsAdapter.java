package com.news.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
	private List<MNews> m_newsList=null;
	private Context m_context=null;
	LayoutInflater m_layoutInflater=null;
	DisplayImageOptions options=null;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private LinearLayout m_otherView;
    public NewsAdapter(Context context,List<MNews> newsList){
    	this.m_context=context;
    	this.m_newsList=newsList;
    	this.m_layoutInflater=LayoutInflater.from(m_context);
    	options = new DisplayImageOptions.Builder()
      
		.showImageForEmptyUri(R.drawable.image_click)
		.showImageOnFail(R.drawable.image_click)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.build();

    }
    public void replace(List<MNews> news){
    	m_newsList=news;
    }
    public void setTopView(LinearLayout view){
    	m_otherView=view;
    }
    public void addNews(List<MNews> news){
    	m_newsList.addAll(news);
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(this.m_newsList==null)
			return 0;
		return this.m_newsList.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
	   return this.m_newsList.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}
     int time=0;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MNews entity = this.m_newsList.get(position);
	    ImageLoader imageLoader = ImageLoader.getInstance();
    	ViewHolder viewHolder = null;	
//    	if(position==0){
//    		//m_otherView.invalidate();
//    	return this.m_otherView;	
//    	}
	    if (convertView!= null &&convertView.getTag()!=null)
	    {
	    	 viewHolder = (ViewHolder) convertView.getTag();
			 
	    }else{
	    	 convertView = this.m_layoutInflater.inflate(R.layout.news_item, null);
			 // convertView.setBackgroundColor(Color.WHITE);
	    	  viewHolder = new ViewHolder();
			  viewHolder.mTitleImg = (ImageView) convertView.findViewById(R.id.title_img);
			  viewHolder.mTitleImg.setScaleType(ScaleType.FIT_XY);
			  viewHolder.mTitleImg.setClickable(false);
			  //viewHolder.mTitleImg.setBackgroundColor(Color.RED);
			  
			  viewHolder.mTitleTxt = (TextView) convertView.findViewById(R.id.title);
			  viewHolder.mTitleTxt.setTextColor(Color.BLACK);
			  viewHolder.mTitleTxt.setTextSize(17);
			  viewHolder.mTitleTxt.setMaxLines(1);
			  viewHolder.mDescriptionTxt = (TextView) convertView.findViewById(R.id.description);
			  viewHolder.mDescriptionTxt.setTextColor(Color.GRAY);
			  viewHolder.mDescriptionTxt.setTextSize(14);
			  viewHolder.mDescriptionTxt.setMaxLines(2);
			  convertView.setTag(viewHolder);
	    }
	
		imageLoader.displayImage(entity.mTitleImageUrl,viewHolder.mTitleImg, options, animateFirstListener);
	    viewHolder.mTitleTxt.setText(entity.mTitle);
	    viewHolder.mDescriptionTxt.setText(entity.mDescription);
	    return convertView;
	}

    static class ViewHolder { 
        public ImageView mTitleImg;
        public TextView mTitleTxt;
        public TextView mDescriptionTxt;
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
