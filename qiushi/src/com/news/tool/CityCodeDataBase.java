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
		
		if(checkDbfile()){
			return true;
		}
		
		int i = 0;
		int j = 0;
		String fileName = null;
		initDir();
		for(i=1,j=5;i<=3;++i,j+=2){
			fileName = "code" + i + ".txt";
			loadCodeFile(fileName,j);
		}
		return true;
	}
	
	protected boolean checkDbfile()
	{
		String path = CityCodeDataBase.databaseDir+DatabaseUtil.database_name;
		File dirFile = new File(path);
		return dirFile.exists();
	}
	
	protected boolean checkDbfile()
	{
		String path = CityCodeDataBase.databaseDir+DatabaseUtil.database_name;
		File dirFile = new File(path);
		if(dirFile.exists()){
			DatabaseUtil.setDatabasePath(path);
			return true;
		}
		return false;
	}
	
	protected void loadCodeFile(String fielName,int j)
	{
		AssetManager asset_manager=this.m_context.getAssets();
		InputStream input_stream;
		List<String> level=new ArrayList<String>();
		String path = CityCodeDataBase.databaseDir+DatabaseUtil.database_name;
		
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
			database.setDatabaseData(level, j);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DatabaseUtil.setDatabasePath(path);
	
	}
	
}
