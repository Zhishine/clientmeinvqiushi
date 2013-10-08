package com.news.imageitem;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.news.modal.MImage;

public class ImageItem extends ImageView{
	
	public ImageItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public MImage getImage() {
		return image;
	}

	public void setImage(MImage image) {
		this.image = image;
	}

	
	public void createImageItem(){
		AsyncHttpClient m_client = new AsyncHttpClient();
		 
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(this.w,this.h);
		//lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		ImageView img = this;
		this.setLayoutParams(lp);
		m_client.get(this.image.mImageUrl, new BinaryHttpResponseHandler() {
	            @Override
	            public void onSuccess(byte[] arg0) {
	               //Log.i(TAG, arg0);
	            	
	            }
	    }); 
	}
	
	
	
	
	private int w;
	private int h;
	private MImage image = null;
	final String TAG="ImageItem";
	
	
	
	
	
	
}
