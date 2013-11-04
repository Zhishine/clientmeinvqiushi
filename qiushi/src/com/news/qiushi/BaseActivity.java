package com.news.qiushi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import net.loonggg.fragment.RightFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.news.modal.MAddressComponet;
import com.news.modal.MCity;
import com.news.modal.MWeatherInfo1;
import com.news.tool.AppCityClient;
import com.news.tool.AppWeatherClient;
import com.news.tool.CityCodeDataBase;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.view.Menu;

public class BaseActivity extends Activity {
	boolean isActive=false;
	 private CityCodeDataBase cityCode = CityCodeDataBase.getInstance();
	 private AppCityClient m_cityClient = null;
	    private AppWeatherClient m_weatherClient = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_cityClient = new AppCityClient(this);
		m_weatherClient=new AppWeatherClient(this);
	}

	@Override
	public void onStop() {
		 super.onStop();  
		   
         if (!isAppOnForeground()) {  
                 //app 进入后台  
                    
               isActive = false; //记录当前已经进入后台  
         }  
	}
	@Override  
    protected void onResume() {  
            // TODO Auto-generated method stub  
            super.onResume();  

               
            if (!isActive) {  
                   //app 从后台唤醒，进入前台  
                       
                    isActive = true; 
                    try {
						cityCode.initDataBase(this);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            		m_cityClient.getCityInfo(new AsyncHttpResponseHandler() {
         	           @Override
        	           public void onSuccess(String response) {
        	              Gson gson = new Gson();
        	              MAddressComponet m_address_detail = new MAddressComponet();
        	              URLDecoder ud = new URLDecoder();
        	              MCity appData = gson.fromJson(response,new TypeToken<MCity>(){}.getType());
        	              try {
        	            	  m_address_detail.city = ud.decode(appData.content.address_detail.city,"utf-8");
        	            	  m_address_detail.district = ud.decode(appData.content.address_detail.district,"utf-8");
        	            	  m_address_detail.province = ud.decode(appData.content.address_detail.province,"utf-8");
        	            	  m_address_detail.street = ud.decode(appData.content.address_detail.street,"utf-8");

        	            	  String city = m_address_detail.city;
        	      			String district =  m_address_detail.district;
        	      			AsyncHttpResponseHandler handler=new AsyncHttpResponseHandler() {
        	     	           @Override
        	     	           public void onSuccess(String response) {
        	     	              Gson gson = new Gson();
//        	     	            
        	     	              MWeatherInfo1 appData=gson.fromJson(response,new TypeToken<MWeatherInfo1>(){}.getType());
        	     	              RightFragment.mWeather=appData.weatherinfo;
        	     	          }
        	     	    };
        	      			if(district!=null && !district.equalsIgnoreCase("")){
        	      				m_weatherClient.getWeatherInfo(district,handler);
        	      			}else{
        	      				m_weatherClient.getWeatherInfo(city,handler);
        	      			}
        				} catch (UnsupportedEncodingException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
        	          }
        	    });
            }  
    }  
	 public boolean isAppOnForeground() {  
         // Returns a list of application processes that are running on the  
         // device  
            
         ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);  
         String packageName = getApplicationContext().getPackageName();  

         List<RunningAppProcessInfo> appProcesses = activityManager  
                         .getRunningAppProcesses();  
         if (appProcesses == null)  
                 return false;  

         for (RunningAppProcessInfo appProcess : appProcesses) {  
                 // The name of the process that this object is associated with.  
                 if (appProcess.processName.equals(packageName)  
                                 && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {  
                         return true;  
                 }  
         }  

         return false;  
       }  
}
