package com.news.tool;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.news.modal.MWeatherInfo;
import com.news.modal.MWeatherInfo1;

public class AppWeatherClient {
	
	private  AsyncHttpClient m_client = new AsyncHttpClient();
	private Context m_context = null;
	//private AppDataObserver m_observer = null;
	public AppWeatherClient(Context context){
		m_context=context;
	}
	public AppWeatherClient(AppDataObserver observer)
	{
		//this.m_observer = observer;
		//this.m_context = context;
	}
	public void getWeatherInfo(String city,AsyncHttpResponseHandler handler){
		city=city.replace("ÊÐ","");
		DatabaseUtil db = new DatabaseUtil(m_context);
		String cityCode = db.getCode(city);
		String url = "http://m.weather.com.cn/data/" + cityCode + ".html";
		m_client.get(url,handler);
	}
}
