package com.news.qiushi;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.news.tool.DensityUtil;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class ViewImageActivity extends Activity{
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader =null;
	HorizontalScrollView horizontalScrollView=null;
	ScrollView scrollview=null;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    scrollview =  new ScrollView(this);

		scrollview.setFillViewport(true);
		scrollview.setHorizontalScrollBarEnabled(true);
	    horizontalScrollView=new HorizontalScrollView(this);
		RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		horizontalScrollView.setLayoutParams(lp2);
		ImageView img = new ImageView(this);
		img.setScaleType(ScaleType.FIT_XY);
		RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		img.setLayoutParams(lp1);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		scrollview.setLayoutParams(lp);
		scrollview.setBackgroundColor(Color.WHITE);
		setContentView(scrollview);
		scrollview.addView(horizontalScrollView);
		LinearLayout imgContainer=new LinearLayout(this);
	    LinearLayout.LayoutParams imgContainerParam=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		imgContainer.setGravity(Gravity.CENTER);
		imgContainer.addView(img);
	    imgContainer.setLayoutParams(imgContainerParam);
	    horizontalScrollView.addView(imgContainer);
		
		String imageurl  =  getIntent().getStringExtra("imgurl");
	    imageLoader = ImageLoader.getInstance();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
	      
		.showImageForEmptyUri(R.drawable.image_click)
		.showImageOnFail(R.drawable.image_click)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.build();
		imageLoader.displayImage(imageurl, img , options,animateFirstListener);
		
	}
	
 class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		 List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					
					ImageView imageView = (ImageView) view;
					
					int height=(int) (loadedImage.getHeight()*1.0);
					int width=(int) (loadedImage.getWidth()*1.0);
					float rate=(float)height/(float)width;
					int extraWidth=width-DensityUtil.getLogicalWidth();
					
					width=Math.max(DensityUtil.getLogicalWidth(),width);
					height=(int) (rate*width);
					
					LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(width,height);
					if(DensityUtil.getLogicalHeight()>height)
					lp1.topMargin=(DensityUtil.getLogicalHeight()-height-DensityUtil.dip2px(38))/2;
					imageView.setLayoutParams(lp1);
					
					
					boolean firstDisplay = !displayedImages.contains(imageUri);
					if (firstDisplay) {
						FadeInBitmapDisplayer.animate(imageView, 500);
						displayedImages.add(imageUri);
					}
//					if(extraWidth>0)
//						horizontalScrollView.scrollTo(500,500);
					//scrollview.scrollTo(500, 500);
					
				}
			}
	}
}
