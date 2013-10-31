package com.news.tool;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.news.modal.MAddressComponet;
import com.news.modal.MCity;


public class AppCityClient {
	
	private  AsyncHttpClient m_client = new AsyncHttpClient();
	private MAddressComponet m_address_detail = new MAddressComponet();
	
	public MAddressComponet getCityInfo()
	{
		/*location */
		//http://api.map.baidu.com/geocoder/v2/?ak=129c55029c32cd9ea2d15f0fb1d4cb2f&location=39.983424,116.322987&output=json
		
		/*ip*/
		String url = "http://api.map.baidu.com/location/ip?ak=129c55029c32cd9ea2d15f0fb1d4cb2f";
		
		m_client.get(url, new AsyncHttpResponseHandler() {
	           @Override
	           public void onSuccess(String response) {
	              Gson gson = new Gson();
	              //MWeatherInfo appData=gson.fromJson(response,new TypeToken<MWeatherInfo>(){}.getType());
	              //m_observer.getAppWeatherResponse(appData);
	              URLDecoder ud = new URLDecoder();
	              MCity appData = gson.fromJson(response,new TypeToken<MCity>(){}.getType());
	              try {
	            	  	m_address_detail.city = ud.decode(appData.content.address_detail.city,"utf-8");
	            	  	m_address_detail.district = ud.decode(appData.content.address_detail.district,"utf-8");
	            	  	m_address_detail.province = ud.decode(appData.content.address_detail.province,"utf-8");
	            	  	m_address_detail.street = ud.decode(appData.content.address_detail.street,"utf-8");
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          }
	    });
		
		return m_address_detail;
	}
	
}
