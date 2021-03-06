package com.news.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "meinvqiushi.db";
	private static final int DATABASE_VERSION = 1;
	public static final String FAVOURITE_TABLE_NAME = "favourite";
	public static final String NEWS_FAVOURITE_ID = "_id";
	public static final String NEWS_FAVOURITE_NEWS_ID = "news_id";
	public static final String NEWS_FAVOURITE_NEWS_TITLE = "news_title";
	public static final String NEWS_FAVOURITE_NEWS_DESCRIPTION = "news_description";
	public static final String NEWS_FAVOURITE_NEWS_URL = "news_url";
	public static final String NEWS_FAVOURITE_NEWS_TITLE_URL = "news_title_url";
	public NewsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="CREATE TABLE " + FAVOURITE_TABLE_NAME + " ("
	             + NEWS_FAVOURITE_ID+ " INTEGER PRIMARY KEY,"
	             + NEWS_FAVOURITE_NEWS_ID+ " INTEGER,"
	             + NEWS_FAVOURITE_NEWS_TITLE+ " TEXT,"
	             + NEWS_FAVOURITE_NEWS_DESCRIPTION  + " TEXT,"
	             + NEWS_FAVOURITE_NEWS_URL+ " TEXT,"
	             + NEWS_FAVOURITE_NEWS_TITLE_URL+ " TEXT);";
		db.execSQL(sql);
	}

	public void insert(String newsId,String title,String description,String url){
	
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + FAVOURITE_TABLE_NAME);
        onCreate(db);
	}

}
