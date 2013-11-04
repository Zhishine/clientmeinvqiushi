package com.news.tool;

import android.net.Uri;
import android.provider.BaseColumns;

public class NewsProviderInfo {
	public static final String AUTHORITY = "com.news.qiushi/favourite";
	  public static final String DEFAULT_SORT_ORDER = "_id DESC";
	/**
	 * Table Location
	 * */
   public static final class Favourite implements BaseColumns{
	   public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
	   private Favourite() {}
	   /**
        * The default sort order for this table
        */
	  
		public static final String NEWS_FAVOURITE_ID = "_id";
		public static final String NEWS_FAVOURITE_NEWS_ID = "news_id";
		public static final String NEWS_FAVOURITE_NEWS_TITLE = "news_title";
		public static final String NEWS_FAVOURITE_NEWS_DESCRIPTION = "news_description";
		public static final String NEWS_FAVOURITE_NEWS_URL = "news_url";
		public static final String NEWS_FAVOURITE_NEWS_TITLE_URL = "news_title_url";
   }

}
