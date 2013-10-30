package com.news.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class DatabaseUtil extends SQLiteOpenHelper{
	
	public static final String database_name="weather_city.db";
	private static String database_path=null;
	public static final int CODE_PROVINCE = 5;
	public static final int CODE_CITY = 7;
	public static final int CODE_COUNTY = 9;
	
	public DatabaseUtil(Context context) {
		super(new DatabaseContext(context), getDatabasePath(), null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql_1="create table first_level(code char(5) primary key,value varchar(40) not null)";
		String sql_2="create table second_level(code char(7) primary key,value varchar(40) not null)";
		String sql_3="create table third_level(code char(9) primary key,value varchar(40) not null)";
		try{
			db.execSQL(sql_1);
			db.execSQL(sql_2);
			db.execSQL(sql_3);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public static String getDatabasePath()
	{
		return database_path;
	}
	
	public static void setDatabasePath(String path)
	{
		if(path!=null&&path.length()>0)
		{
			database_path=path;
		}
	}
	
	public static void closeDatabase(SQLiteDatabase db)
	{
		if(db!=null&&db.isOpen()==true)
		{
			db.close();
		}
	}
	
	public static void closeCursor(Cursor c)
	{
		if(c!=null&&c.isClosed()==false)
		{
			c.close();
		}
	}
	
	public static boolean checkDatabase()
    {
    	if(database_path==null||database_path.length()<=0)
    	{
    		return false;
    	}else
    	{
    		File f=new File(database_path);
    		return f.exists();
    	}
    }
	
	/*sub = {5,7,9}*/
	public boolean setDatabaseData(List<String> level,int code)
	{
		boolean ret  = false;
		if(level ==null){
			ret = false;
		}else{
			
			SQLiteDatabase db=getWritableDatabase();
			String queryStr = null;
			String sql=null;
			String temp=null;
			Log.i("cityCode", "setDatabaseData");
			
			switch(code){
				case DatabaseUtil.CODE_PROVINCE:
					queryStr = "insert into first_level(code,value) values('";
					break;
				case DatabaseUtil.CODE_CITY:
					queryStr = "insert into second_level(code,value) values('";
					break;
				case DatabaseUtil.CODE_COUNTY:
					queryStr = "insert into third_level(code,value) values('";
					break;
				default:
					queryStr = "insert into second_level(code,value) values('";
					break;
			}
			Log.i("cityCode", "setDatabaseData"+queryStr);
			
			try{
				int size=level.size();
				for(int i=0;i<size;i++)
				{
					temp=level.get(i);
					sql=queryStr + temp.substring(0, code)+"','"+temp.substring(code + 1).trim()+"')";
					db.execSQL(sql);
				}
			}catch(Exception e){
				ret = false;
			}finally{
				closeDatabase(db);
			}
			
			ret = true;
		}
		
		return ret;
	}
	
	
	protected String getCode(String location,int code)
	{
		String location_code=null;
		
		
		String queryStr = "first_level";
		SQLiteDatabase db=getReadableDatabase();
		Cursor c=null;
		
		switch(code){
			case DatabaseUtil.CODE_PROVINCE:
				queryStr = "first_level";
				break;
			case DatabaseUtil.CODE_CITY:
				queryStr = "second_level";
				break;
			case DatabaseUtil.CODE_COUNTY:
				queryStr = "third_level";
				break;
			default:
				queryStr = "second_level";
				break;
		}
		
		try{
			
		    c=db.query(queryStr, null, "value=?", new String[]{location}, null, null, null);
		    int size=c.getCount();
			if(size>0)
			{
				c.move(1);
				location_code=c.getString(0);
			}
			
		}catch(Exception e){
			return null;
		}finally{
			closeCursor(c);
			closeDatabase(db);
		}
		
		return location_code;
	}
	
	/* 2013/10/30  调用接口得到城市编码*/
	public String getCode(String location)
	{
		String location_code=null;
		
		if(checkDatabase()==false){
			
			return null;
		}
		
		if( (location_code=getCode(location, DatabaseUtil.CODE_CITY) )==null ){
			
			location_code = getCode(location, DatabaseUtil.CODE_COUNTY);
		}
		
		return location_code;
	}
	
	
	
	public String setDatabaseData(List<String> first_level,List<String> second_level,List<String> third_level)
	{
		if(first_level==null||second_level==null||third_level==null)
		{
			return "待插入数据有误";
		}
		SQLiteDatabase db=getWritableDatabase();
		String sql=null;
		String temp=null;
		try{
			int size=first_level.size();
			for(int i=0;i<size;i++)
			{
				temp=first_level.get(i);
				sql="insert into first_level(code,value) values('"+temp.substring(0, 5)+"','"+temp.substring(6).trim()+"')";
				db.execSQL(sql);
			}
			
			size=second_level.size();
			for(int i=0;i<size;i++)
			{
				temp=second_level.get(i);
				sql="insert into second_level(code,value) values('"+temp.substring(0, 7)+"','"+temp.substring(8).trim()+"')";
				db.execSQL(sql);
			}
			
			size=third_level.size();
			for(int i=0;i<size;i++)
			{
				temp=third_level.get(i);
				sql="insert into third_level(code,value) values('"+temp.substring(0, 9)+"','"+temp.substring(10).trim()+"')";
				db.execSQL(sql);
			}
		}catch(Exception e)
		{
			return e==null?"不明原因":e.getMessage();
		}finally{
			closeDatabase(db);
		}
		return "success";
	}
	public Bundle getSubLocations(String location)
	{
		Bundle bundle=new Bundle();
		if(checkDatabase()==false)
		{
			bundle.putString("error", "未找到数据库");
			return bundle;
		}
		ArrayList<String> codes_list=new ArrayList<String>();
		ArrayList<String> values_list=new ArrayList<String>();
		String location_code="";
		SQLiteDatabase db=getReadableDatabase();
		Cursor c=null;
		try{
		    c=db.query("first_level", null, "value=?", new String[]{location}, null, null, null);
		    int size=c.getCount();
			if(size>0)
			{
				c.move(1);
				location_code=c.getString(0);
				c=db.query("second_level", null, "code like '"+location_code+"__'", null, null, null, null);
				size=c.getCount();
				for(int i=0;i<size;i++)
				{
					c.move(1);
					codes_list.add(c.getString(0));
					values_list.add(c.getString(1));
				}
				closeCursor(c);
				closeDatabase(db);
				bundle.putStringArrayList("codes_list", codes_list);
				bundle.putStringArrayList("values_list", values_list);
				bundle.putString("error", "success");
				return bundle;
			}
			
			c=db.query("second_level", null, "value=?", new String[]{location}, null, null, null);
		    size=c.getCount();
			if(size>0)
			{
				c.move(1);
				location_code=c.getString(0);
				c=db.query("third_level", null, "code like '"+location_code+"__'", null, null, null, null);
				size=c.getCount();
				for(int i=0;i<size;i++)
				{
					c.move(1);
					codes_list.add(c.getString(0));
					values_list.add(c.getString(1));
				}
				closeCursor(c);
				closeDatabase(db);
				bundle.putStringArrayList("codes_list", codes_list);
				bundle.putStringArrayList("values_list", values_list);
				bundle.putString("error", "success");
				return bundle;
			}
			
			c=db.query("third_level", null, "value=?", new String[]{location}, null, null, null);
		    size=c.getCount();
			if(size>0)
			{
				for(int i=0;i<size;i++)
				{
					c.move(1);
					codes_list.add(c.getString(0));
					values_list.add(c.getString(1));
				}
				closeCursor(c);
				closeDatabase(db);
				bundle.putStringArrayList("codes_list", codes_list);
				bundle.putStringArrayList("values_list", values_list);
				bundle.putString("error", "success");
				return bundle;
			}
			
		}catch(Exception e)
		{
			bundle.putString("error", e==null?"不明原因":e.getMessage());
			return bundle;
		}finally{
			closeCursor(c);
			closeDatabase(db);
		}
		bundle.putString("error", "不明错误");
		return bundle;
	}
}

class DatabaseContext extends ContextWrapper {
	public DatabaseContext(Context base) {
		super(base);
	}
	
	@Override
	public File getDatabasePath(String name) {
		File file = new File(name);
		return file;
	}

	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode,SQLiteDatabase.CursorFactory factory) {
		SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
		return result;
	}
}
