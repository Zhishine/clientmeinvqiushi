package com.news.tool;

import java.util.List;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.news.modal.MWeatherInfo;

public class AppWeatherClient {
	
	private  AsyncHttpClient m_client = new AsyncHttpClient();
	//private Context m_context = null;
	private AppDataObserver m_observer = null;
	
	public AppWeatherClient(AppDataObserver observer)
	{
		this.m_observer = observer;
		//this.m_context = context;
	}
	
	public void getWeatherInfo(String city)
	{
		DatabaseUtil db = new DatabaseUtil((Context)this.m_observer);
		String cityCode = db.getCode(city);
		String url = "http://m.weather.com.cn/data/" + cityCode + ".html";
		
		m_client.get(url, new AsyncHttpResponseHandler() {
	           @Override
	           public void onSuccess(String response) {
	              Gson gson = new Gson();
	              List<MWeatherInfo> appData=gson.fromJson(response,new TypeToken<List<MWeatherInfo>>(){}.getType());
	              m_observer.getAppWeatherResponse(appData.get(0));
	          }
	    });
	}
}
