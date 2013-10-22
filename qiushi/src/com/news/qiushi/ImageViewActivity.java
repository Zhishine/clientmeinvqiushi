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
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ImageViewActivity extends Activity implements OnClickListener{
	
	private final String mtitle = "ͼƬ";
	private final int TOP_HEIGHT = 50;
	private final int RIGHT_WIDTH = 70;
	private final int TOP_BACKBTN_WIDTH = 70;
	private final int RIGHT_BIN_PADDING = 50;
	
	
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
	ImageView img =null;
	protected void createImageView()
	{
		ImageLoader imageLoader = ImageLoader.getInstance();
		int screenWidth=DensityUtil.getActualWidth();
		int screenHeight = DensityUtil.getActualHeight();
		
		int width=DensityUtil.dip2px(screenWidth);
		int height = DensityUtil.dip2px(screenHeight);
		int topHeight=DensityUtil.dip2px(TOP_HEIGHT);
		int rightWidth = DensityUtil.dip2px(RIGHT_WIDTH);
		int rightBtnPadding =  DensityUtil.dip2px(RIGHT_BIN_PADDING);
		int leftWidth = width - rightWidth;
		
		int topBackBtnWidth = DensityUtil.dip2px(TOP_BACKBTN_WIDTH);
		int topTitleWidth = width - topBackBtnWidth;
		
		int pad10 = DensityUtil.dip2px(10);
		int pad3 = DensityUtil.dip2px(3);
		int pad2 = DensityUtil.dip2px(2);
		
		RelativeLayout container=new RelativeLayout(this);
		container.setBackgroundResource(R.drawable.bg);
		container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		//container.setBackgroundColor(Color.RED);
		setContentView(container);
		
		/* top */
		RelativeLayout topContianer  = new RelativeLayout(this);
		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,topHeight);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		topContianer.setBackgroundResource(R.drawable.top_bar_bg);
		topContianer.setLayoutParams(lp1);
		
		
		//title
		RelativeLayout.LayoutParams lp12=new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		//lp12.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//lp12.setMargins(topBackBtnWidth, 0, 0, 0);
		
		TextView lbtitle = new TextView(this);
		lbtitle.setLayoutParams(lp12);
		lbtitle.setGravity(Gravity.CENTER);
	    lp12.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		//lbtitle.setPadding(pad10, pad3, pad3, pad10);
		lbtitle.setTextSize(20.0f);
		lbtitle.setText(mtitle);
		lbtitle.setTextColor(Color.BLACK);
		
		//lbtitle.setBackgroundColor(Color.BLUE);
		topContianer.addView(lbtitle);
		
		//backBtn
		RelativeLayout.LayoutParams lp13=new RelativeLayout.LayoutParams( topBackBtnWidth,LayoutParams.MATCH_PARENT);
		//lp13.setMargins(0, 3, 3, 3);
		lp13.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		
		ImageView topBackBtn = new ImageView(this);
		topBackBtn.setOnClickListener(this);
		topBackBtn.setBackgroundResource(R.drawable.back);
		topBackBtn.setId(TOP_BACK_BTN_ID);
		//topBackBtn.setBackgroundColor(Color.GRAY);
		topBackBtn.setLayoutParams(lp13);
		topContianer.addView(topBackBtn);
		container.addView(topContianer);
		
		/*left */
		RelativeLayout leftContianer = new RelativeLayout(this);
		RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(leftWidth,height-topHeight);
		
	
		lp2.setMargins(0, topHeight, 0, 0);
		leftContianer.setLayoutParams(lp2);
		
		ScrollView scrollview = new ScrollView(this);
		scrollview.setFillViewport(true);
		//scrollview.setBackgroundColor(Color.BLUE);
		RelativeLayout.LayoutParams lp21 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		scrollview.setLayoutParams(lp21);
		leftContianer.addView(scrollview);
		
		 img = new ImageView(this);
		
		//img.setBackgroundColor(Color.YELLOW);
		Intent intent = getIntent();
		String imageurl  = intent.getStringExtra("imgurl");
		//leftWidth=920;
		int imgHeight = (int)(intent.getFloatExtra("scale",1.2f)*leftWidth);
		RelativeLayout.LayoutParams lp22 = new RelativeLayout.LayoutParams(leftWidth,imgHeight);
		
		//img.setMaxHeight(3000);
		img.setScaleType(ScaleType.FIT_CENTER);   
		img.setAdjustViewBounds(true);
	
		
		img.setLayoutParams(lp22);
		imageLoader.displayImage(imageurl, img,null,animateFirstListener);
		scrollview.addView(img);
		//img.setImageResource(R.drawable.download);
		container.addView(leftContianer);
		//leftContianer.addView(img);
		
		//*right
		RelativeLayout rightContianer = new RelativeLayout(this);
		RelativeLayout.LayoutParams lp3=new RelativeLayout.LayoutParams(rightWidth,height-topHeight);
		lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rightContianer.setLayoutParams(lp3);
		
		//downLoad btn
		ImageView dlimgBtn = new ImageView(this);
		dlimgBtn.setOnClickListener(this);
	
		dlimgBtn.setBackgroundResource(R.drawable.download);
		
		dlimgBtn.setId(DONW_IMG_BTN_ID);
		RelativeLayout.LayoutParams lp31=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,rightWidth);
		
		int pp = topHeight+rightBtnPadding;
		int pt = DensityUtil.dip2px(20);
   	lp31.setMargins(pad3, pp, pad3, 0);
		dlimgBtn.setLayoutParams(lp31);
		rightContianer.addView(dlimgBtn);
		
		//view btn
		ImageView viewimgBtn = new ImageView(this);
		viewimgBtn.setOnClickListener(this);
		viewimgBtn.setBackgroundResource(R.drawable.preview);
		viewimgBtn.setId(VIEW_IMG_BTN_ID);
		RelativeLayout.LayoutParams lp32=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,rightWidth);
		lp32.setMargins(pad3,pp+pt+topHeight,pad3,0);
		viewimgBtn.setLayoutParams(lp32);
		rightContianer.addView(viewimgBtn);
		
		//share btn
		ImageView shareimgBtn = new ImageView(this);
		shareimgBtn.setOnClickListener(this);
		shareimgBtn.setBackgroundResource(R.drawable.share);
		shareimgBtn.setId(SHARE_IMG_BTN_ID);
		RelativeLayout.LayoutParams lp33=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,rightWidth);
		lp33.setMargins(pad3,pp+2*(pt+topHeight),pad3,0);
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
				int height=img.getHeight();
				break;
			case VIEW_IMG_BTN_ID:
			//	scrollview;
				break;
			case SHARE_IMG_BTN_ID:
				
				break;
			case TOP_BACK_BTN_ID:
				this.finish();
				break;
			default:
				break;
		}
		
	}

}
