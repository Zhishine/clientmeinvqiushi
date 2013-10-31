package com.news.qiushi;

import com.news.tool.NewsProviderInfo;
import com.news.tool.NewsProviderInfo.Favourite;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class FavouriteActivity extends Activity implements OnClickListener {
	String[] m_favProjection={
			Favourite.NEWS_FAVOURITE_NEWS_ID,
			Favourite.NEWS_FAVOURITE_NEWS_TITLE,
			Favourite.NEWS_FAVOURITE_NEWS_DESCRIPTION,
			Favourite.NEWS_FAVOURITE_NEWS_URL
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite);
		View back=findViewById(R.id.back);
		back.setOnClickListener(this);
		Cursor cursor = getContentResolver().query(Favourite.CONTENT_URI, m_favProjection,
				null, null, null);
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.favourite_row, cursor,
				new String[] {Favourite.NEWS_FAVOURITE_NEWS_TITLE,Favourite.NEWS_FAVOURITE_NEWS_DESCRIPTION },
				new int[] { R.id.list_title,R.id.list_description });
	
        ListView mListView = (ListView)findViewById(android.R.id.list);
         mListView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.back){
			this.finish();
		}
	}


}
