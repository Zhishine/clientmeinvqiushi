package com.news.qiushi;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


import com.news.tool.DensityUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageViewActivity extends Activity implements OnClickListener{
	
	private final String mtitle = "ͼƬ";
	private final int TOPHEIGHT = 50;
	private final int RIGHTWIDTH = 50;
	
	private final int DONW_IMG_BTN_ID = 1;
	private final int VIEW_IMG_BTN_ID = 2;
	private final int SHARE_IMG_BTN_ID = 3;
	private final int TOP_BACK_BTN_ID = 4;
	
	//private LayoutInflater m_layoutInflater=null;
	//private DisplayImageOptions options=null;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.image_view);
		
		createImageView();
		
		
	}
	
	protected void createImageView()
	{
		ImageLoader imageLoader = ImageLoader.getInstance();
		int screenWidth=DensityUtil.getActualWidth();
		int screenHeight = DensityUtil.getActualHeight();
		
		int width=DensityUtil.dip2px(screenWidth);
		int height = DensityUtil.dip2px(screenHeight);
		int topHeight=DensityUtil.dip2px(TOPHEIGHT);
		int rightWidth = DensityUtil.dip2px(RIGHTWIDTH);
		int leftWidth = width - rightWidth;
		
		int toptitleWidth = (3*width)/4;
		int topbackWidth = width - toptitleWidth;
		
		
		RelativeLayout container=new RelativeLayout(this);
		container.setBackgroundResource(R.drawable.bg);
		container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		setContentView(container);
		
		/* top */
		RelativeLayout topContianer  = new RelativeLayout(this);
		RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,topHeight);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		topContianer.setLayoutParams(lp1);
		//title
		RelativeLayout.LayoutParams lp12=new RelativeLayout.LayoutParams( toptitleWidth,LayoutParams.MATCH_PARENT);
		lp12.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		TextView lbtitle = new TextView(this);
		lbtitle.setLayoutParams(lp12);
		lbtitle.setText(mtitle);
		topContianer.addView(lbtitle);
		//backBtn
		RelativeLayout.LayoutParams lp13=new RelativeLayout.LayoutParams( topbackWidth,LayoutParams.MATCH_PARENT);
		lp13.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		ImageView topBackBtn = new ImageView(this);
		topBackBtn.setOnClickListener(this);
		topBackBtn.setBackgroundResource(R.drawable.news_no_click);
		topBackBtn.setId(TOP_BACK_BTN_ID);
		topBackBtn.setLayoutParams(lp13);
		topContianer.addView(topBackBtn);
		container.addView(topContianer);
		
		
		/*left */
		RelativeLayout leftContianer = new RelativeLayout(this);
		RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(leftWidth,height-topHeight);
		lp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		leftContianer.setLayoutParams(lp2);
		ImageView img = new ImageView(this);
		
		
		Intent intent = getIntent();
		String imageurl  = intent.getStringExtra("imgurl");
		
		imageLoader.displayImage(imageurl, img,null,animateFirstListener);
		//img.setAdjustViewBounds(true);
		//img.setMaxWidth(maxWidth);
		leftContianer.addView(img);
		container.addView(leftContianer);
		
		/* right */
		RelativeLayout rightContianer = new RelativeLayout(this);
		RelativeLayout.LayoutParams lp3=new RelativeLayout.LayoutParams(rightWidth,height-topHeight);
		lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rightContianer.setLayoutParams(lp3);
		//downLoad btn
		ImageView dlimgBtn = new ImageView(this);
		dlimgBtn.setOnClickListener(this);
		dlimgBtn.setBackgroundResource(R.drawable.news_no_click);
		dlimgBtn.setId(DONW_IMG_BTN_ID);
		RelativeLayout.LayoutParams lp31=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,topHeight);
		lp31.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		dlimgBtn.setLayoutParams(lp31);
		rightContianer.addView(dlimgBtn);
		
		//view btn
		ImageView viewimgBtn = new ImageView(this);
		viewimgBtn.setOnClickListener(this);
		viewimgBtn.setBackgroundResource(R.drawable.news_no_click);
		viewimgBtn.setId(VIEW_IMG_BTN_ID);
		RelativeLayout.LayoutParams lp32=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,topHeight);
		//lp32.addRule(RelativeLayout.);
		//dlimgBtn.setLayoutParams(lp31);
		//rightContianer.addView(dlimgBtn);
		
		//share btn
		ImageView shareimgBtn = new ImageView(this);
		shareimgBtn.setOnClickListener(this);
		shareimgBtn.setBackgroundResource(R.drawable.news_no_click);
		shareimgBtn.setId(SHARE_IMG_BTN_ID);
		RelativeLayout.LayoutParams lp33=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,topHeight);
		lp33.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		shareimgBtn.setLayoutParams(lp33);
		rightContianer.addView(shareimgBtn);
		container.addView(rightContianer);
		
		
		
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
			case DONW_IMG_BTN_ID:
				
				break;
			case VIEW_IMG_BTN_ID:
				break;
			case SHARE_IMG_BTN_ID:
				break;
			case TOP_BACK_BTN_ID:
				break;
			default:
				break;
		}
		
	}

}
