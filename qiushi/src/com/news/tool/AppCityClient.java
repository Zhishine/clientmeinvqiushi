package com.news.tool;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.news.modal.MAddressComponet;
import com.news.modal.MCity;
import com.news.modal.MTitude;
import com.news.modal.MWeatherInfo;

public class AppCityClient {
	
	
	private  AsyncHttpClient m_client = new AsyncHttpClient();
	//private Context m_context = null;
	private AppDataObserver m_observer = null;
	private MAddressComponet m_addressCom = new MAddressComponet();
	
	public AppCityClient(AppDataObserver observer)
	{
		this.m_observer = observer;
	}
	
	public MAddressComponet getCityInfo()
	{
		TitudeDataClient titude = TitudeDataClient.getInstance();
		titude.Init((Context)this.m_observer);
		
		MTitude mtitude = titude.getLongitudeAndLatitude();
		//http://api.map.baidu.com/geocoder/v2/?ak=129c55029c32cd9ea2d15f0fb1d4cb2f&location=39.983424,116.322987&output=json
		String url = "http://api.map.baidu.com/geocoder/v2/?ak=129c55029c32cd9ea2d15f0fb1d4cb2f&location=" + 
				mtitude.latitude + "," + mtitude.longitude + "&output=json";
		
		m_client.get(url, new AsyncHttpResponseHandler() {
	           @Override
	           public void onSuccess(String response) {
	              Gson gson = new Gson();
	              //MWeatherInfo appData=gson.fromJson(response,new TypeToken<MWeatherInfo>(){}.getType());
	              //m_observer.getAppWeatherResponse(appData);
	              
	              MCity appData = gson.fromJson(response,new TypeToken<MCity>(){}.getType());
	              m_addressCom = appData.addressComponent;
	              //m_observer.getAppCityResponse(appData.addressComponent);
	          }
	    });
		
		return m_addressCom;
	}
	
}
