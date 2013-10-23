package com.news.qiushi;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class ViewImageActivity extends Activity{
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ScrollView scrollview =  new ScrollView(this);
		scrollview.setFillViewport(true);
		ImageView img = new ImageView(this);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		scrollview.setLayoutParams(lp);
		scrollview.setBackgroundColor(Color.WHITE);
		setContentView(scrollview);
		scrollview.addView(img);
		String imageurl  =  getIntent().getStringExtra("imgurl");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(imageurl, img , null,animateFirstListener);
		
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
