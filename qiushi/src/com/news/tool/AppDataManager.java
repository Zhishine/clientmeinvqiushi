package com.news.tool;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;
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
     AppDataClient m_client=null;
     AsyncHttpClient m_clientHttp = new AsyncHttpClient();
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
    private String systemImgDir="/sdcard/meinvqiushi/img/";
    private String systemAdDir="/sdcard/meinvqiushi/ad/";
    
    private String systemImageDir = "/sdcard/meinvqiushi/image/";
    
    private boolean m_bannerIsShow=false;
    private boolean m_adIsShow=true;
    private boolean m_taobaokeIsShow=true;
    private long m_lastUpdateTime=0;
    private long m_lastAddateTime=0;
    private long m_lastAdDateTimeTemp=0;
    List<Drawable> m_adDrawable=null;
    List<MAd> m_adList=null; 
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
   
    	String leftUpIconUrl=m_setting.getString("leftUpIcon",null);
    	if(leftUpIconUrl==null||leftUpIconUrl.equalsIgnoreCase("")){
    		InputStream is = m_context.getResources().openRawResource(R.drawable.game);
    		String fileName="left_up_icon.png";
    		String filePath=systemImgDir+fileName;
    		localEditor.putString("leftUpIcon",filePath);
    		if(saveImg(is,systemImgDir,fileName)){
    		
    		 Bitmap bm = BitmapFactory.decodeStream(is);
     		 BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
             this.m_leftUpIcon=bd;
    		}
    		
    	}else{
    		File file = new File(leftUpIconUrl);  
    	       if(file.exists()){        //判断文件是否存在  
    	    	   
    	              Bitmap bm = BitmapFactory.decodeFile(leftUpIconUrl);
    	              BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
    	              this.m_leftUpIcon=bd;
    	       }
    	}
    	
    	String rightUpIconUrl=m_setting.getString("rightUpIcon",null);
    	if(rightUpIconUrl==null||rightUpIconUrl.equalsIgnoreCase("")){
    		InputStream is = m_context.getResources().openRawResource(R.drawable.navigation);
    		String fileName="right_up_icon.png";
    		String filePath=systemImgDir+fileName;
    		 localEditor.putString("rightUpIcon",filePath);
    		if(saveImg(is,systemImgDir,fileName)){
    	
    		 Bitmap bm = BitmapFactory.decodeStream(is);
     		 BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
             this.m_rightUpIcon=bd;
    		}
    		
    	}else{
    		File file = new File(rightUpIconUrl);  
    	       if(file.exists()){        //判断文件是否存在  
    	              Bitmap bm = BitmapFactory.decodeFile(rightUpIconUrl);
    	              BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
    	              this.m_rightUpIcon=bd;
    	       }
    	}
    	
    	String leftDownIconUrl=m_setting.getString("leftDownIcon",null);
    	if(leftDownIconUrl!=null&&!leftDownIconUrl.equalsIgnoreCase("")){
    		File file = new File(leftDownIconUrl);  
 	       if(file.exists()){        //判断文件是否存在  
 	              Bitmap bm = BitmapFactory.decodeFile(leftDownIconUrl);
 	              BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
 	              this.m_leftDowmIcon=bd;
 	       }
    	}
  
    	String rightDownIconUrl=m_setting.getString("rightDownIcon",null);
    	if(rightDownIconUrl!=null&&!rightDownIconUrl.equalsIgnoreCase("")){
    		File file = new File(rightDownIconUrl);  
 	       if(file.exists()){        //判断文件是否存在  
 	              Bitmap bm = BitmapFactory.decodeFile(rightDownIconUrl);
 	              BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
 	              this.m_rightDownIcon=bd;
 	       }
    	}

    	this.m_leftUpRedirectUrl=m_setting.getString("leftUpRedirectUrl","");
    	this.m_rightUpRedirectUrl=m_setting.getString("rightUpRedirectUrl","");
    	this.m_leftDownRedirectUrl=m_setting.getString("leftDownRedirectUrl","");
    	this.m_rightDownRedirectUrl=m_setting.getString("rightDownRedirectUrl","");

    	this.m_adIsShow=m_setting.getBoolean("adIsShow", this.m_adIsShow);
    	this.m_bannerIsShow=m_setting.getBoolean("bannerIsShow", this.m_bannerIsShow);
    	this.m_taobaokeIsShow=m_setting.getBoolean("taobaokeIsShow", this.m_taobaokeIsShow);
    	
    	this.m_lastUpdateTime=m_setting.getLong("lastUpdateTime", this.m_lastUpdateTime);
    	this.m_lastAddateTime=m_setting.getLong("lastAdUpdateTime", this.m_lastAddateTime);
    	localEditor.commit();
    	if(this.m_bannerIsShow)
    		loadAd();
    		
    	requestSystem();
    
    }
     void loadAd(){
    	m_adDrawable=new ArrayList<Drawable>();
    	SharedPreferences adSetting=m_context.getSharedPreferences("ad", 0);
    	SharedPreferences.Editor editor = adSetting.edit();
    	//editor.clear();
    	//editor.commit();
     	String adListStr=adSetting.getString("adList",null);
     	if(adListStr==null){
     		List<MAd> list=new ArrayList<MAd>();
     	  MAd ad1=new MAd();
     	  ad1.mInfo="http://www.baidu.com";
     	  ad1.mOrder=1;
     	  ad1.mType="0";
     	  list.add(ad1);
     	  
     	  MAd ad2=new MAd();
    	  ad2.mInfo="http://www.baidu.com";
    	  ad2.mOrder=2;
    	  ad2.mType="0";
    	  list.add(ad2);
    	  
    	  MAd ad3=new MAd();
     	  ad3.mInfo="http://www.baidu.com";
     	  ad3.mOrder=3;
     	  ad3.mType="0";
     	  list.add(ad3);
     	  
     	  MAd ad4=new MAd();
    	  ad4.mInfo="http://www.baidu.com";
    	  ad4.mOrder=4;
    	  ad4.mType="0";
    	  list.add(ad4);
    	  this.m_adList=list;
    	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  		 ObjectOutputStream oos;
  		 try {
  			oos = new ObjectOutputStream(baos);
  			oos.writeObject(list);
  			String adListBase64 = new String(Base64.encode(baos.toByteArray(),0));
  		
  			editor.putString("adList", adListBase64);
  			editor.commit();
  		  } catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		  }
  		String fileName="1.png";
		InputStream is1 = m_context.getResources().openRawResource(R.drawable.img1); 
		m_adDrawable.add(m_context.getResources().getDrawable(R.drawable.img1));
		AppDataManager.this.saveImg(is1,systemAdDir, fileName);
	    fileName="2.png";
		InputStream is2 = m_context.getResources().openRawResource(R.drawable.img2);
		m_adDrawable.add(m_context.getResources().getDrawable(R.drawable.img2));
		AppDataManager.this.saveImg(is2,systemAdDir, fileName);
		fileName="3.png";
		InputStream is3 = m_context.getResources().openRawResource(R.drawable.img3); 
		m_adDrawable.add(m_context.getResources().getDrawable(R.drawable.img3));
		AppDataManager.this.saveImg(is3,systemAdDir, fileName);
	    fileName="4.png";
		InputStream is4 = m_context.getResources().openRawResource(R.drawable.img4); 
		m_adDrawable.add(m_context.getResources().getDrawable(R.drawable.img4));
		AppDataManager.this.saveImg(is4,systemAdDir, fileName);
     	}
     	else{
     		// 从ObjectInputStream中读取Product对象
     		try {
     			byte[] base64Bytes = Base64.decode(adListStr,0);
         		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
         		ObjectInputStream ois = new ObjectInputStream(bais);
				m_adList= (List<MAd>) ois.readObject();
				for(MAd ad:m_adList){
				File file = new File(systemAdDir+ad.mOrder+".png");  
		 	       if(file.exists()){        //判断文件是否存在  
		 	              Bitmap bm = BitmapFactory.decodeFile(systemAdDir+ad.mOrder+".png");
		 	              BitmapDrawable bd= new BitmapDrawable(m_context.getResources(), bm);   
		 	              this.m_adDrawable.add(bd);
		 	       }
				}
			} catch (OptionalDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
     		
     	}
     
     }
    
    public boolean SaveImage(Bitmap bm,String fileName){
    	
    	boolean ret  = false;
    	try {
    		ret  = AppDataManager.this.saveImg(bm, this.systemImageDir, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return ret;
    }
    public MAppData getAppData(){
    	return m_appData;
    }
    public List<Drawable> getDrawableList(){
    	return this.m_adDrawable;
    }
    public MAd getAd(int index){
    	if(m_adList==null||m_adList.size()<=index)
    		return null;
    	return m_adList.get(index);
    }
    public Drawable getLeftUpIcon(){
    	return this.m_leftUpIcon;
    }
    public String getLeftUpRedirectUrl(){
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
    boolean saveImg(InputStream is,String fileDir, String fileName)  {  
    	
    	try {
    		Bitmap bm = BitmapFactory.decodeStream(is);
			saveImg(bm,fileDir,fileName);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    boolean saveImg(Bitmap bm,String fileDir, String fileName) throws IOException {  
    	if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {   
    		// sd card 可用                          
    		 File dirFile = new File(fileDir);  
    		 boolean result=false;
    	        if(!dirFile.exists()){ 
    	        	
    	          result=dirFile.mkdirs()   ;
    	        }   
    	        File myFile = new File(fileDir+ fileName);   
    	        if(myFile.exists())
    	        	myFile.delete();
    	        myFile.createNewFile();
    	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myFile));   
    	        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);   
    	        bos.flush();   
    	        bos.close(); 
    	        return true;
    		}else {   
    		// 当前不可用   
    			return false;
    		} 
    }   
	@Override
	public void getSystemResponse(MSystem system) {
		boolean appIsUpdate=false;
		// TODO Auto-generated method stub
		if(system.mAppUpdateLastTime>this.m_lastUpdateTime){
			appIsUpdate=true;
			this.m_lastAdDateTimeTemp=system.mPublicAdLastTime;
			m_client.getAppData();
			m_setting=m_context.getSharedPreferences("app_data", 0);
	    	SharedPreferences.Editor localEditor = m_setting.edit();
			this.m_lastUpdateTime=system.mAppUpdateLastTime;
			localEditor.putLong("lastUpdateTime", m_lastUpdateTime);
			localEditor.commit();
		}
		if(system.mPublicAdLastTime>this.m_lastAddateTime&&this.m_bannerIsShow&&!appIsUpdate){
			m_client.getAd();
			m_setting=m_context.getSharedPreferences("app_data", 0);
	    	SharedPreferences.Editor localEditor = m_setting.edit();
			this.m_lastAddateTime=system.mPublicAdLastTime;
			localEditor.putLong("lastAdUpdateTime", m_lastAddateTime);
			localEditor.commit();
		}

	}
	@Override
	public void getAppDataResponse(MAppData appData) {
		// TODO Auto-generated method stub
		m_setting=m_context.getSharedPreferences("app_data", 0);
    	final SharedPreferences.Editor localEditor = m_setting.edit();
    	localEditor.putString("leftUpRedirectUrl",appData.mLeftUpRedirectUrl);
    	localEditor.putString("rightUpRedirectUrl",appData.mRightUpRedirectUrl);
    	localEditor.putString("leftDownRedirectUrl",appData.mLeftDownRedirectUrl);
    	localEditor.putString("rightDownRedirectUrl",appData.mRightDownRedirectUrl);
    	localEditor.putBoolean("adIsShow",appData.mAdIsShow);
    	localEditor.putBoolean("bannerIsShow",appData.mBannerIsShow);
    	localEditor.putBoolean("taobaokeIsShow",appData.mTaobaokeIsShow);
    	
    	this.m_adIsShow=appData.mAdIsShow;
    	this.m_bannerIsShow=appData.mBannerIsShow;
    	this.m_taobaokeIsShow=appData.mTaobaokeIsShow;
    	this.m_leftUpRedirectUrl=appData.mLeftUpRedirectUrl;
    	this.m_rightUpRedirectUrl=appData.mRightUpRedirectUrl;
    	this.m_leftDownRedirectUrl=appData.mLeftDownRedirectUrl;
    	this.m_rightDownRedirectUrl=appData.mRightDownRedirectUrl;
    	
    	if(this.m_lastAdDateTimeTemp>this.m_lastAddateTime&&this.m_bannerIsShow){
			m_client.getAd();
			//m_setting=m_context.getSharedPreferences("app_data", 0);
			this.m_lastAddateTime=m_lastAdDateTimeTemp;
			localEditor.putLong("lastAdUpdateTime", m_lastAddateTime);
		}
    	
    	if(!appData.mLeftUpIconUrl.equalsIgnoreCase("")){
    		m_clientHttp.get(appData.mLeftUpIconUrl, new BinaryHttpResponseHandler(){
    			public void onSuccess(byte[] data){
    				if(data==null)
    					return;
    				String fileName="left_up_icon.png";
    				String filePath=systemImgDir+fileName;
    	            localEditor.putString("leftUpIcon", filePath);
    				InputStream is = new ByteArrayInputStream(data); 
    				AppDataManager.this.saveImg(is,systemImgDir, fileName);
    			}
    		});
    	}
    	else
         localEditor.putString("leftUpIcon",appData.mLeftUpIconUrl);

    	if(!appData.mRightUpIconUrl.equalsIgnoreCase("")){
    		m_clientHttp.get(appData.mRightUpIconUrl, new BinaryHttpResponseHandler(){
    			public void onSuccess(byte[] data){
    				if(data==null)
    					return;
    				String fileName="right_up_icon.png";
    				String filePath=systemImgDir+fileName;
    	            localEditor.putString("rightUpIcon", filePath);
    				InputStream is = new ByteArrayInputStream(data); 
    				AppDataManager.this.saveImg(is,systemAdDir, fileName);
    			}
    		});
    	}
    	else
            localEditor.putString("rightUpIcon",appData.mRightUpIconUrl);
    	
    	if(!appData.mLeftDownIconUrl.equalsIgnoreCase("")){
    		m_clientHttp.get(appData.mLeftDownIconUrl, new BinaryHttpResponseHandler(){
    			public void onSuccess(byte[] data){
    				if(data==null)
    					return;
    			
    				String fileName="left_down_icon.png";
    	    		String filePath=systemImgDir+fileName;
    	            localEditor.putString("leftDownIcon", filePath);
    				InputStream is = new ByteArrayInputStream(data); 
    				AppDataManager.this.saveImg(is,systemAdDir,fileName);
    			}
    		});
    	}
    	else
            localEditor.putString("leftDownIcon",appData.mLeftDownIconUrl);
    	
    	if(!appData.mRightDownIconUrl.equalsIgnoreCase("")){
    		m_clientHttp.get(appData.mRightDownIconUrl, new BinaryHttpResponseHandler(){
    			public void onSuccess(byte[] data){
    				if(data==null)
    					return;
    				String fileName="right_down_icon.png";
    				String filePath=systemImgDir+fileName;
    	            localEditor.putString("rightDownIcon", filePath);
    				InputStream is = new ByteArrayInputStream(data); 
    				AppDataManager.this.saveImg(is,systemImgDir, fileName);
    			}
    		});
    	}
    	else
            localEditor.putString("rightDownIcon",appData.mRightDownIconUrl);
    	localEditor.commit();
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
		SharedPreferences adSetting=m_context.getSharedPreferences("ad", 0);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(adList);
			String adListBase64 = new String(Base64.encode(baos.toByteArray(),0));
			SharedPreferences.Editor editor = adSetting.edit();
			editor.putString("adList", adListBase64);
			editor.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(MAd ad:adList){
		    final MAd param=ad;
			m_clientHttp.get(ad.mImageUrl, new BinaryHttpResponseHandler(){
    			public void onSuccess(byte[] data){
    				if(data==null)
    					return;
    				String fileName=param.mOrder+".png";
    				String filePath=systemAdDir+fileName;
    	            //localEditor.putString("rightDownIcon", filePath);
    				InputStream is = new ByteArrayInputStream(data); 
    				AppDataManager.this.saveImg(is,systemAdDir, fileName);
    			}
    		});
		}
	}
}
