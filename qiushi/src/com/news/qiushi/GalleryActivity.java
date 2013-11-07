package com.news.qiushi;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import com.news.adapter.GalleryAdapter;
import com.news.modal.MGallery;
import com.news.tool.AppDataManager;
import com.news.tool.AppShareManager;
import com.news.view.PhotoGallery;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends Activity implements OnClickListener {

	 List<MGallery> m_galleryList=null;
	 ImageView m_view=null;
	 int m_currentIndex=0;
	 LinearLayout m_popupView=null;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		ImageView back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		LinearLayout download=(LinearLayout) findViewById(R.id.download_container);
		download.setOnClickListener(this);
		LinearLayout share=(LinearLayout) findViewById(R.id.share_container);
		share.setOnClickListener(this);
		LinearLayout comment=(LinearLayout) findViewById(R.id.comment_container);
		comment.setOnClickListener(this);
		m_galleryList=(List<MGallery>)getIntent().getSerializableExtra("galleryList");
		PhotoGallery gallery=(PhotoGallery) findViewById(R.id.banner_gallery);
		
		GalleryAdapter adapter=new GalleryAdapter(this,m_galleryList);
		gallery.setAdapter(adapter);
		//FrameLayout container=(FrameLayout) findViewById(R.id.gallery_container);
		//container.setBackgroundResource(R.drawable.bg);
		final TextView description=(TextView) findViewById(R.id.description);
		description.setText(m_galleryList.get(0).mDescription);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> adpter, View view,
					int index, long arg3) {
				// TODO Auto-generated method stub
				description.setText(m_galleryList.get(index).mDescription);
				m_currentIndex=index;
				LinearLayout linear=(LinearLayout)view;
				m_view=(ImageView)linear.getChildAt(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		m_popupView=(LinearLayout) findViewById(R.id.popup);
		m_popupView.setOnClickListener(this);
		m_popupView.setVisibility(View.INVISIBLE);
		ImageView expand=(ImageView) findViewById(R.id.expand);
		expand.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				updatePopup();
			}}); 
	}
    void updatePopup(){
    	  if(m_popupView.getVisibility()==View.INVISIBLE)
    	  {
		    	m_popupView.setVisibility(View.VISIBLE);
		    	fadeIn(m_popupView, 500);
    	  }
		    else {
		    	//m_popupView.setVisibility(View.INVISIBLE);
		    	fadeOut(m_popupView, 500);
		    }
    }
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		switch(view.getId()){
		case R.id.back:
			this.finish();
			break;
		case R.id.download_container:
			updatePopup();
			String systemImageDir = "/sdcard/meinvqiushi/image/";
			m_view.setDrawingCacheEnabled(true);
			Bitmap  bitmap = Bitmap.createBitmap(m_view.getDrawingCache());
			m_view.setDrawingCacheEnabled(false);
			SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			long ms = System.currentTimeMillis();
			String dt = formatter.format(ms);
			String fileName = dt + "_" + ms + ".png";
			if( AppDataManager.getInstance().SaveImage(bitmap, fileName)){
				//success
				//Log.i("downLoadImage", "downLoadImage click"+fileName);
				String msg = this.getString(R.string.msgSuccess) + ":" + systemImageDir;
				Toast toast=Toast.makeText(this, msg, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}else{
				//fail
				Toast.makeText(this, this.getString(R.string.msgFail), Toast.LENGTH_SHORT).show();
			}
			break;	
		case R.id.comment_container:
			updatePopup();
			 UMSocialService controller = UMServiceFactory.getUMSocialService("meinvqiushi.gallery."+m_galleryList.get(this.m_currentIndex).mId, RequestType.SOCIAL);
			  controller.openComment(this, false);
			break;
		case R.id.share_container:
			updatePopup();
			String description=m_galleryList.get(this.m_currentIndex).mDescription;
			String titleImageUrl=m_galleryList.get(this.m_currentIndex).mImageUrl;
			
			AppShareManager.getInstance().share(this, description, titleImageUrl, titleImageUrl);
			break;	
         case R.id.popup:
        	 fadeOut(m_popupView, 500);
			break;
		}
		
	}

	public static void fadeIn(View view, int durationMillis) {
		AlphaAnimation fadeImage = new AlphaAnimation(0, 1);
		fadeImage.setDuration(durationMillis);
		fadeImage.setInterpolator(new DecelerateInterpolator());
		view.startAnimation(fadeImage);
	}
	public static void fadeOut(final View view, int durationMillis) {
		AlphaAnimation fadeImage = new AlphaAnimation(1, 0);
		fadeImage.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				view.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		fadeImage.setDuration(durationMillis);
		fadeImage.setInterpolator(new DecelerateInterpolator());
		view.startAnimation(fadeImage);
	}
}
