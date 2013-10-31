package com.news.tool;

import java.util.HashMap;

import com.news.tool.NewsProviderInfo.Favourite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class NewsProvider extends ContentProvider {
	private SQLiteDatabase db;
	private static final int LOCATIONS = 1;
	
	private static HashMap<String, String> sfavouriteProjectionMap;
	private static final UriMatcher sUriMatcher;
	static{
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI("com.news.qiushi", "favourite", LOCATIONS);
		
		sfavouriteProjectionMap = new HashMap<String, String>();
		sfavouriteProjectionMap.put(Favourite.NEWS_FAVOURITE_ID,Favourite.NEWS_FAVOURITE_ID);
		sfavouriteProjectionMap.put(Favourite.NEWS_FAVOURITE_NEWS_ID,Favourite.NEWS_FAVOURITE_NEWS_ID);
		sfavouriteProjectionMap.put(Favourite.NEWS_FAVOURITE_NEWS_TITLE,Favourite.NEWS_FAVOURITE_NEWS_TITLE);
		sfavouriteProjectionMap.put(Favourite.NEWS_FAVOURITE_NEWS_DESCRIPTION,Favourite.NEWS_FAVOURITE_NEWS_DESCRIPTION);
		sfavouriteProjectionMap.put(Favourite.NEWS_FAVOURITE_NEWS_URL,Favourite.NEWS_FAVOURITE_NEWS_URL);
		
		
	}
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues content) {
		String newsId=content.getAsString(Favourite.NEWS_FAVOURITE_NEWS_ID);
		String title=content.getAsString(Favourite.NEWS_FAVOURITE_NEWS_TITLE);
		String description=content.getAsString(Favourite.NEWS_FAVOURITE_NEWS_DESCRIPTION);
		String url=content.getAsString(Favourite.NEWS_FAVOURITE_NEWS_URL);
		String sql="insert into favourite(news_id,news_title,news_description,news_url) values("+newsId+",'"+title+"','"+description+"','"+url+"');";
		db.execSQL(sql);
		return uri;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		NewsDatabase newsDatabase = new NewsDatabase(getContext());
		db = newsDatabase.openDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
	
			SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
			String orderBy = null;
			Cursor c = null;
			switch (sUriMatcher.match(uri)) {
			case LOCATIONS:
				qb.setTables(NewsDatabaseHelper.FAVOURITE_TABLE_NAME);
				qb.setProjectionMap(sfavouriteProjectionMap);			
				if (TextUtils.isEmpty(sortOrder)) {
					orderBy = NewsProviderInfo.DEFAULT_SORT_ORDER;
				} else {
					orderBy = sortOrder;
				}
				c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
			}
			
			
			c.setNotificationUri(getContext().getContentResolver(), uri);
			return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
