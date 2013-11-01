package com.news.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

public class CityCodeDataBase {
	private static final CityCodeDataBase m_instance = new CityCodeDataBase();
	private Context m_context;
	public static final String databaseDir = "/sdcard/meinvqiushi/db/";
	public static CityCodeDataBase getInstance()
	{
		return m_instance;
	}
	
	public boolean initDataBase(Context context)
	{
		Log.i("cityCode", "InitDataBase2");
		this.m_context = context;
		String path = CityCodeDataBase.databaseDir+DatabaseUtil.database_name;
		DatabaseUtil.setDatabasePath(path);
		
		if(checkDbfile()){
			return true;
		}
	
		String fileName = "code3.txt";
		
		//initDir();
		
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {   
    		// sd card ø…”√                          
    		 File dirFile = new File(CityCodeDataBase.databaseDir);  
    	     if(!dirFile.exists()){        	
    	         dirFile.mkdirs();
    	     }
		}
		
		loadCodeFile(fileName);
		
		return true;
	}

	protected boolean checkDbfile()
	{
		String path = CityCodeDataBase.databaseDir+DatabaseUtil.database_name;
		File dirFile = new File(path);
		if(dirFile.exists()){
			//DatabaseUtil.setDatabasePath(path);
			return true;
		}
		return false;
	}
	
	protected void loadCodeFile(String fielName)
	{
		AssetManager asset_manager=this.m_context.getAssets();
		InputStream input_stream;
		List<String> level=new ArrayList<String>();
	
		
		try {
			
			input_stream = asset_manager.open(fielName);
			BufferedReader reader=new BufferedReader(new InputStreamReader(input_stream,"gb2312"),512);
			String read_out_line=reader.readLine();
			
			while(read_out_line!=null){
				
				level.add(read_out_line);
				read_out_line=reader.readLine();
				
				
			}
			
			reader.close();
			DatabaseUtil database=new DatabaseUtil(this.m_context);
			database.setDatabaseData(level, DatabaseUtil.CODE_COUNTY);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
