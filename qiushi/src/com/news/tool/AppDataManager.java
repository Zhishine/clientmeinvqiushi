package com.news.tool;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View.OnClickListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.news.modal.MAd;
import com.news.modal.MAppData;
import com.news.modal.MImage;
import com.news.modal.MNews;
import com.news.modal.MProduct;
import com.news.modal.MSystem;
import com.news.qiushi.R;

public class AppDataManager implements AppDataObserver {
    private static final AppDataManager m_instance=new AppDataManager();
    private MAppData m_appData=null;
    private AppDataClient m_client=null;
    private Context m_context;
    private SharedPreferences m_setting=null;
    private Drawable m_leftUpIcon=null;
    private String m_leftUpRedirectUrl=null;
    private Drawable m_rightUpIcon=null;
    private String m_rightUpRedirectUrl=null;
    private Drawable m_leftDowmIcon=null;
    private String m_leftDownRedirectUrl=null;
    private Drawable m_rightDownIcon=null;
    private String m_rightDownRedirectUrl=null;
    private String systemDir="/sdcard/meinvqiushi";
    private String systemImgDir="/sdcard/meinvqiushi/img";
    private boolean m_bannerIsShow=true;
    private boolean m_adIsShow=true;
    private boolean m_taobaokeIsShow=true;
    private long m_lastUpdateTime=0;
    private AppDataManager(){
    	
    }
    public static AppDataManager getInstance(){
    	return m_instance;
    }
    public void init(Context context) throws IOException{
    	this.m_context=context;
    	m_client=new AppDataClient(this);
    	m_setting=m_context.getSharedPreferences("app_data", 0);
    	SharedPreferences.Editor localEditor = m_setting.edit();
    	//localEditor.clear();
    	BitmapFactory.Options options = new BitmapFactory.Options();  
 	   options.inSampleSize=1;
    	String leftUpIconUrl=m_setting.getString("leftUpIcon",null);
    	if(leftUpIconUrl==null||leftUpIconUrl.equalsIgnoreCase("")){
    		InputStream is = m_context.getResources().openRawResource(R.drawable.game);
    		String fileName="left_up_icon.png";
    		String filePath=systemImgDir+"/"+fileName;
    		localEditor.putString("leftUpIcon",filePath);
    		if(saveImg(is,fileName)){
    		
    		 Bitmap bm = BitmapFactory.decodeStream(is);
     		 BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
             this.m_leftUpIcon=bd;
    		}
    		
    	}else{
    		File file = new File(leftUpIconUrl);  
    	       if(file.exists()){        //判断文件是否存在  
    	    	   
    	              Bitmap bm = BitmapFactory.decodeFile(leftUpIconUrl,options);
    	              BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
    	              this.m_leftUpIcon=bd;
    	       }
    	}
    	
    	String rightUpIconUrl=m_setting.getString("rightUpIcon",null);
    	if(rightUpIconUrl==null||rightUpIconUrl.equalsIgnoreCase("")){
    		InputStream is = m_context.getResources().openRawResource(R.drawable.navigation);
    		String fileName="right_up_icon.png";
    		String filePath=systemImgDir+"/"+fileName;
    		 localEditor.putString("rightUpIcon",filePath);
    		if(saveImg(is,fileName)){
    	
    		 Bitmap bm = BitmapFactory.decodeStream(is);
     		 BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
             this.m_rightUpIcon=bd;
    		}
    		
    	}else{
    		File file = new File(rightUpIconUrl);  
    	       if(file.exists()){        //判断文件是否存在  
    	              Bitmap bm = BitmapFactory.decodeFile(rightUpIconUrl,options);
    	              BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
    	              this.m_rightUpIcon=bd;
    	       }
    	}
    	
    	String leftDownIconUrl=m_setting.getString("leftDownIcon",null);
    	if(leftDownIconUrl!=null&&leftDownIconUrl.equalsIgnoreCase("")){
    		File file = new File(leftDownIconUrl);  
 	       if(file.exists()){        //判断文件是否存在  
 	              Bitmap bm = BitmapFactory.decodeFile(leftDownIconUrl,options);
 	              BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
 	              this.m_leftDowmIcon=bd;
 	       }
    	}
    	if(leftDownIconUrl==null){
    		String fileName="left_down_icon.png";
          localEditor.putString("leftDownIconUrl", fileName);
    	}
    	String rightDownIconUrl=m_setting.getString("rightDownIcon",null);
    	if(rightDownIconUrl!=null&&rightDownIconUrl.equalsIgnoreCase("")){
    		File file = new File(rightDownIconUrl);  
 	       if(file.exists()){        //判断文件是否存在  
 	              Bitmap bm = BitmapFactory.decodeFile(rightDownIconUrl,options);
 	              BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
 	              this.m_rightDownIcon=bd;
 	       }
    	}
    	if(rightDownIconUrl==null){
    		String fileName="right_down_icon.png";
            localEditor.putString("rightDownIconUrl", fileName);
    	}
    	this.m_leftUpRedirectUrl=m_setting.getString("leftUpRedirectUrl","");
    	this.m_rightUpRedirectUrl=m_setting.getString("rightUpRedirectUrl","");
    	this.m_leftDownRedirectUrl=m_setting.getString("leftDownRedirectUrl","");
    	this.m_rightDownRedirectUrl=m_setting.getString("rightDownRedirectUrl","");

    	this.m_adIsShow=m_setting.getBoolean("adIsShow", this.m_adIsShow);
    	this.m_bannerIsShow=m_setting.getBoolean("bannerIsShow", this.m_bannerIsShow);
    	this.m_taobaokeIsShow=m_setting.getBoolean("taobaokeIsShow", this.m_taobaokeIsShow);
    	
    	this.m_lastUpdateTime=m_setting.getLong("lastUpdateTime", this.m_lastUpdateTime);
    	localEditor.commit();
    	requestSystem();
    }
   
    public MAppData getAppData(){
    	return m_appData;
    }
    public Drawable getLeftUpIcon(){
    	return this.m_leftUpIcon;
    }
    public String getLeftRedirectUrl(){
    	return this.m_leftUpRedirectUrl;
    }
    public Drawable getRightUpIcon(){
    	return this.m_rightUpIcon;
    }
    public String getRightUpRedirectUrl(){
    	return this.m_rightUpRedirectUrl;
    }
    public Drawable getLeftDownIcon(){
    	return this.m_leftDowmIcon;
    }
    public String getLeftDownRedirectUrl(){
    	return this.m_leftDownRedirectUrl;
    }
    public Drawable getRightDownIcon(){
    	return this.m_rightDownIcon;
    }
    public String getRightDownRedirectUrl(){
    	return this.m_rightDownRedirectUrl;
    }
    public boolean getAdIsShow(){
    	return this.m_adIsShow;
    }
    public boolean getBannerIsShow(){
    	return this.m_bannerIsShow;
    }
    public boolean getTaobaokeIsShow(){
    	return this.m_taobaokeIsShow;
    }
    public void requestSystem(){
    	m_client.getSystem();
    }
    boolean saveImg(InputStream is, String fileName)  {  
    	
    	try {
    		Bitmap bm = BitmapFactory.decodeStream(is);
			saveImg(bm,fileName);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }   
    void saveImg(Bitmap bm, String fileName) throws IOException {  
    	if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {   
    		// sd card 可用                          
    		 File dirFile = new File(systemImgDir+"/");  
    		 boolean result=false;
    	        if(!dirFile.exists()){ 
    	        	
    	          result=dirFile.mkdirs()   ;
    	        }   
    	        File myFile = new File(systemImgDir +"/"+ fileName);   
    	        if(myFile.exists())
    	        	myFile.delete();
    	        myFile.createNewFile();
    	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myFile));   
    	        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);   
    	        bos.flush();   
    	        bos.close(); 
    		}else {   
    		// 当前不可用   
    		} 
         
    }   
	@Override
	public void getSystemResponse(MSystem system) {
		// TODO Auto-generated method stub
		if(system.mAppUpdateLastTime>this.m_lastUpdateTime){
			m_client.getAppData();
		}
	}
	@Override
	public void getAppDataResponse(MAppData appData) {
		// TODO Auto-generated method stub
		m_setting=m_context.getSharedPreferences("app_data", 0);
    	SharedPreferences.Editor localEditor = m_setting.edit();
    	localEditor.putString("leftUpRedirectUrl",appData.mLeftUpRedirectUrl);
    	localEditor.putString("rightUpRedirectUrl",appData.mRightUpRedirectUrl);
    	localEditor.putString("leftDownRedirectUrl",appData.mLeftDownRedirectUrl);
    	localEditor.putString("rightDownRedirectUrl",appData.mRightDownRedirectUrl);
    	localEditor.putBoolean("adIsShow",appData.mAdIsShow);
    	localEditor.putBoolean("bannerIsShow",appData.mBannerIsShow);
    	localEditor.putBoolean("taobaokeIsShow",appData.mTaobaokeIsShow);
    	localEditor.commit();
    	
    	AsyncHttpClient m_client = new AsyncHttpClient();
    	if(!appData.mLeftUpIconUrl.equalsIgnoreCase("")){
    		m_client.get(appData.mLeftUpIconUrl, new BinaryHttpResponseHandler(){
    			public void onSuccess(byte[] data){
    				String fileName="left_up_icon.png";
    				InputStream is = new ByteArrayInputStream(data); 
    				AppDataManager.this.saveImg(is, fileName);
    			}
    		});
    	}
    	
    	if(!appData.mRightUpIconUrl.equalsIgnoreCase("")){
    		m_client.get(appData.mRightUpIconUrl, new BinaryHttpResponseHandler(){
    			public void onSuccess(byte[] data){
    				String fileName="right_up_icon.png";
    				InputStream is = new ByteArrayInputStream(data); 
    				AppDataManager.this.saveImg(is, fileName);
    			}
    		});
    	}
    	
    	if(!appData.mLeftDownIconUrl.equalsIgnoreCase("")){
    		m_client.get(appData.mLeftDownIconUrl, new BinaryHttpResponseHandler(){
    			public void onSuccess(byte[] data){
    				String fileName="left_down_icon.png";
    				InputStream is = new ByteArrayInputStream(data); 
    				AppDataManager.this.saveImg(is, fileName);
    			}
    		});
    	}
    	
    	if(!appData.mRightDownIconUrl.equalsIgnoreCase("")){
    		m_client.get(appData.mRightDownIconUrl, new BinaryHttpResponseHandler(){
    			public void onSuccess(byte[] data){
    				String fileName="right_down_icon.png";
    				InputStream is = new ByteArrayInputStream(data); 
    				AppDataManager.this.saveImg(is, fileName);
    			}
    		});
    	}
	}
	@Override
	public void getImageResponse(List<MImage> imageList, int pageIndex) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getImageResponse(MImage image) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getNewsResponse(List<MNews> newsList, int pageIndex) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getNewsResponse(MNews news) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getProductResponse(List<MProduct> productList, int pageIndex) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getProductResponse(List<MProduct> productList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getProductResponse(MProduct product) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getAdResponse(List<MAd> adList) {
		// TODO Auto-generated method stub
		
	}
}
