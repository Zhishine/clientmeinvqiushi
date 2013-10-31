package com.news.tool;

import com.news.modal.MTitude;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;

public class TitudeDataClient {

	private Context m_context;
	private static final TitudeDataClient m_instance = new TitudeDataClient();
	

	public static TitudeDataClient getInstance()
	{
		return m_instance;
	}
	public void Init(Context context)
	{
		this.m_context = context;
	}
	
	/**
	 * 设置GPS开启或关闭，入口参数为true时开启GPS，为false时关闭GPS
	 * */
	public void setGPS(boolean on_off) {
    	boolean gpsEnabled = android.provider.Settings.Secure.isLocationProviderEnabled( m_context.getContentResolver(), LocationManager.GPS_PROVIDER);
    	Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
    	
        if(on_off == true)
        {
	    	if(!gpsEnabled) {	    		
	            gpsIntent.setData(Uri.parse("custom:3"));
	            try {
	                    PendingIntent.getBroadcast(m_context, 0, gpsIntent, 0).send();
	                    
	            } catch (CanceledException e) {
	                    e.printStackTrace();
	            }
	    	}
        }
        else {
        	if(gpsEnabled) {	    		
	            gpsIntent.setData(Uri.parse("custom:3"));
	            try {
	                    PendingIntent.getBroadcast(m_context, 0, gpsIntent, 0).send();
	            } catch (CanceledException e) {
	                    e.printStackTrace();
	            }
	    	}
        }
    }
	
	/**
	 * 获得经纬度函数
	 * */
	public MTitude getLongitudeAndLatitude() {
		
		MTitude mtitude = new MTitude();
		
		setGPS(true);
		
		LocationManager loctionManager;
		String contextService=Context.LOCATION_SERVICE;
		//通过系统服务，取得LocationManager对象
		loctionManager=(LocationManager) m_context.getSystemService(contextService);
		String provider=LocationManager.GPS_PROVIDER;
		Location location = loctionManager.getLastKnownLocation(provider);
		if(location != null) {
			//longitude = location.getLongitude();				 
			//latitude = location.getLatitude();	
			//Toast.makeText(m_context.getApplicationContext(), String.valueOf(longitude)+ "," + String.valueOf(latitude),Toast.LENGTH_LONG).show();
			mtitude.latitude = String.valueOf(location.getLatitude());
			mtitude.longitude = String.valueOf(location.getLongitude());
		}
		
		return mtitude;
	}
}
