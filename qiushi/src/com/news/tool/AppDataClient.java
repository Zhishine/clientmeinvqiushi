package com.news.tool;

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
interface AppDataObserver
{
	public void getSystemResponse(MSystem system);
	public void getAppDataResponse(MAppData appData);
	public void getImageResponse(List<MImage> imageList,int pageIndex);
	public void getImageResponse(MImage image);
	public void getNewsResponse(List<MNews> newsList,int pageIndex);
	public void getNewsResponse(MNews news);
	public void getProductResponse(List<MProduct> productList,int pageIndex);
	public void getProductResponse(List<MProduct> productList);
	public void getProductResponse(MProduct product);
	public void getAdResponse(List<MAd> adList);
}
public class AppDataClient {
	 private static final String BASE_URL = "http://192.168.1.115:8080/news/";
	 private  AsyncHttpClient m_client = new AsyncHttpClient();
	 AppDataObserver m_observer=null;
	 final String TAG="AppDataClient";
	 final String APPID="1";
	 final String CATEGORYID="1";
	 public AppDataClient(AppDataObserver observer)
	 {
		 this.m_observer=observer;
	 }
	 //��ȡϵͳ���������г�ʼ���߼�������ϵͳ���·����ݸ���
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
	 
	 //��ȡ��APP��صĲ������Ƿ�����棬APP UI����ת����
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
	 
	 //��ȡͼƬ�б�
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
	 
	//��ȡ����ͼƬʵ��
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
	 
	 
	//��ȡ�����б�
	 public void getNews(final int pageNO,final int pageSize)
	 {
		 String fullUrl=BASE_URL+"GetNews?categoryId="+CATEGORYID+"&type=0&pageNO="+pageNO+"&pageSize="+pageSize;
		 m_client.get(fullUrl, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
	               Log.i(TAG, response);
	               Gson gson = new Gson();
	               List<MNews> list=gson.fromJson(response,new TypeToken<List<MNews>>(){}.getType());
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
	 
	//��ȡ��������ʵ��
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
	 
	 
	//��ȡ��Ʒ�б�ʵ��
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
		 
	//��ȡ�����Ʒ�б�ʵ��
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
	//��ȡ������Ʒʵ��
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
	 
	//��ȡBanner����ʵ��
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