package com.news.tool;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.news.modal.MAddressComponet;
import com.news.modal.MCity;


public class AppCityClient {
	
	private  AsyncHttpClient m_client = new AsyncHttpClient();
	Context m_context;
	public AppCityClient(Context context)
	{
		this.m_context = context;
		
	}
	
	public void getCityInfo(AsyncHttpResponseHandler responseHandler){
	String url = "http://api.map.baidu.com/location/ip?ak=129c55029c32cd9ea2d15f0fb1d4cb2f";
		m_client.get(url,responseHandler);
	}
	
}
