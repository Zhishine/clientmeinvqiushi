package com.news.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.news.modal.MAd;
import com.news.modal.MAppData;
import com.news.modal.MImage;
import com.news.modal.MNews;
import com.news.modal.MProduct;
import com.news.modal.MSystem;


public class AppDataClient {
	 private static final String BASE_URL = "http://news.hontek.com.cn/news";
	 private  AsyncHttpClient m_client = new AsyncHttpClient();
	 AppDataObserver m_observer=null;
	 final String TAG="AppDataClient";
	 final String APPID="1";
	 final String CATEGORYID="1";
	 public AppDataClient(AppDataObserver observer)
	 {
		 this.m_observer=observer;
	 }
	 //获取系统参数，进行初始化逻辑，例如系统有下发数据更新
	 public  void getSystem()
	 {
		 String fullUrl=BASE_URL+"GetSystem?appId="+APPID;
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	            	Log.i(TAG, response);
		            Gson gson = new Gson();
		            MSystem sysData=gson.fromJson(response,new TypeToken<MSystem>(){}.getType());
					if(m_observer!=null)
					{
						m_observer.getSystemResponse(sysData);
					}
			
	            }
	        });
	 }
	 
	 public  void getSystem(AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetSystem?appId="+APPID;
		 m_client.get(fullUrl,responseHandler);
	 }
	 
	 //获取与APP相关的参数，是否插入广告，APP UI并跳转参数
	 public void getAppData()
	 {
		 String fullUrl=BASE_URL+"GetAppData?id="+APPID+"&type=1";
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               Gson gson = new Gson();
	               MAppData appData=gson.fromJson(response,new TypeToken<MAppData>(){}.getType());
					if(m_observer!=null)
					{
						m_observer.getAppDataResponse(appData);
					}
				
	            }
	        }); 
	 }
	 
	 public  void getAppData(AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetAppData?id="+APPID+"&type=1";
		 m_client.get(fullUrl,responseHandler);
	 }
	 
	 //获取图片列表
	 public void getImage(final int pageNO,final int pageSize)
	 {
		 String fullUrl=BASE_URL+"GetImage?categoryId="+CATEGORYID+"&type=0&pageNO="+pageNO+"&pageSize="+pageSize;
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               Gson gson = new Gson();
	               List<MImage> list=gson.fromJson(response,new TypeToken<List<MImage>>(){}.getType());
					if(m_observer!=null)
					{
						m_observer.getImageResponse(list,pageNO);
					}
	            }
	        }); 
	 }
	 
	 public  void getImage(final int pageIndex,final int pageSize,AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetImage?categoryId="+CATEGORYID+"&type=0&pageIndex="+pageIndex+"&pageSize="+pageSize;
		 m_client.get(fullUrl,responseHandler);
	 }
	 
	//获取单个图片实例
	 public void getImage(int id)
	 {
		 String fullUrl=BASE_URL+"GetImage?id="+id+"&type=1";
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               Gson gson = new Gson();
	               MImage image=gson.fromJson(response,new TypeToken<MImage>(){}.getType());
					if(m_observer!=null)
					{
						m_observer.getImageResponse(image);
					}
	            }
	        }); 
	 }
	 
	 public  void getImage(int id,AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetImage?id="+id+"&type=1";
		 m_client.get(fullUrl,responseHandler);
	 }
	 
	 
	//获取新闻列表
	 public void getNews(final int pageNO,final int pageSize)
	 {
		 String fullUrl=BASE_URL+"GetNews?categoryId="+CATEGORYID+"&type=0&pageNO="+pageNO+"&pageSize="+pageSize;
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               URLDecoder ud = new URLDecoder();
	               Gson gson = new Gson();
	               List<MNews> list=gson.fromJson(response,new TypeToken<List<MNews>>(){}.getType());
	                 if(list!=null){
					for(MNews news:list){
						try {
							news.mTitle=ud.decode(news.mTitle, "utf-8");
							news.mDescription=ud.decode(news.mDescription, "utf-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
	               }
	               if(m_observer!=null)
					{
						m_observer.getNewsResponse(list,pageNO);
					}
	            }
	        }); 
	 }
	 
	 public  void getNews(final int pageIndex,final int pageSize,AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetNews?categoryId="+CATEGORYID+"&type=0&pageIndex="+pageIndex+"&pageSize="+pageSize;
		 m_client.get(fullUrl,responseHandler);
	 }
	 
	//获取单个新闻实例
	 public void getNews(int id)
	 {
		 String fullUrl=BASE_URL+"GetNews?id="+id+"&type=1";
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               Gson gson = new Gson();
	               MNews news=gson.fromJson(response,new TypeToken<MNews>(){}.getType());
					if(m_observer!=null)
					{
						m_observer.getNewsResponse(news);
					}
	            }
	        }); 
	 }
	 
	 public  void getNews(int id,AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetNews?id="+id+"&type=1";
		 m_client.get(fullUrl,responseHandler);
	 }
	 
	 
	//获取商品列表实例
	 public void getProduct(final int pageNO,final int pageSize)
	 {
		 String fullUrl=BASE_URL+"GetProduct?categoryId="+CATEGORYID+"&type=0&pageNO="+pageNO+"&pageSize="+pageSize;
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               Gson gson = new Gson();
	               List<MProduct> productList=gson.fromJson(response,new TypeToken<List<MProduct>>(){}.getType());
					if(m_observer!=null)
					{
						m_observer.getProductResponse(productList,pageNO);
					}
	            }
	        }); 
	 }
	 
	 public  void getProduct(final int pageNO,final int pageSize,AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetProduct?categoryId="+CATEGORYID+"&type=0&pageNO="+pageNO+"&pageSize="+pageSize;
		 m_client.get(fullUrl,responseHandler);
	 }
		 
	//获取随机商品列表实例
	 public void getProduct(int count)
	 {
		 String fullUrl=BASE_URL+"GetProduct?categoryId="+CATEGORYID+"&type=2&count="+count;
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               Gson gson = new Gson();
	               List<MProduct> productList=gson.fromJson(response,new TypeToken<List<MProduct>>(){}.getType());
					if(m_observer!=null)
					{
						m_observer.getProductResponse(productList);
					}
	            }
	        }); 
	 }
	 
	 public  void getProduct(int count,AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetProduct?categoryId="+CATEGORYID+"&type=2&count="+count;
		 m_client.get(fullUrl,responseHandler);
	 }
	//获取单个商品实例
	 public void getProduct(long numId)
	 {
		 String fullUrl=BASE_URL+"GetProduct?numId="+numId+"&type=1";
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               Gson gson = new Gson();
	               MProduct product=gson.fromJson(response,new TypeToken<MProduct>(){}.getType());
					if(m_observer!=null)
					{
						m_observer.getProductResponse(product);
					}
	            }
	        }); 
	 }
	 
	 public  void getProduct(long numId,AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetProduct?numId="+numId+"&type=1";
		 m_client.get(fullUrl,responseHandler);
	 }		 
	 
	//获取Banner集合实例
	 public void getAd()
	 {
		 String fullUrl=BASE_URL+"GetAd?appId="+APPID;
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               Gson gson = new Gson();
	               List<MAd> adList=gson.fromJson(response,new TypeToken<List<MAd>>(){}.getType());
					if(m_observer!=null)
					{
						m_observer.getAdResponse(adList);
					}
	            }
	        }); 
	 }
	 
	 public  void getAd(AsyncHttpResponseHandler responseHandler)
	 {
		 String fullUrl=BASE_URL+"GetAd?appId="+APPID;
		 m_client.get(fullUrl,responseHandler);
	 }
}
