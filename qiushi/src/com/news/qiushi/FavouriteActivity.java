package com.news.qiushi;

import com.news.tool.NewsProviderInfo;
import com.news.tool.NewsProviderInfo.Favourite;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class FavouriteActivity extends Activity implements OnClickListener {
	String[] m_favProjection={
			Favourite.NEWS_FAVOURITE_ID,
			Favourite.NEWS_FAVOURITE_NEWS_ID,
			Favourite.NEWS_FAVOURITE_NEWS_TITLE,
			Favourite.NEWS_FAVOURITE_NEWS_DESCRIPTION,
			Favourite.NEWS_FAVOURITE_NEWS_URL,
			Favourite.NEWS_FAVOURITE_NEWS_TITLE_URL
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite);
		View back=findViewById(R.id.back);
		back.setOnClickListener(this);
		Cursor cursor = getContentResolver().query(Favourite.CONTENT_URI, m_favProjection,
				null, null, null);
		
		final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.favourite_row, cursor,
				new String[] {Favourite.NEWS_FAVOURITE_NEWS_TITLE,Favourite.NEWS_FAVOURITE_NEWS_DESCRIPTION },
				new int[] { R.id.list_title,R.id.list_description });
	
        final ListView mListView = (ListView)findViewById(R.id.list);
        mListView.setCacheColorHint(Color.WHITE);
        mListView.setDividerHeight(0);
        mListView.setFadingEdgeLength(0);
        mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Cursor sc = (Cursor)mListView.getAdapter().getItem(arg2);
				Intent intent = new Intent(FavouriteActivity.this,WebViewActivity.class);
                intent.putExtra("url",sc.getString(4));
                intent.putExtra("id",sc.getString(1));
                intent.putExtra("titleImageUrl",sc.getString(5));
                intent.putExtra("description",sc.getString(3));
                intent.putExtra("title",sc.getString(2));
                FavouriteActivity.this.startActivity(intent);
			}
        	
        });
        mListView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				final AdapterView<?> adapterView=arg0;
				new AlertDialog.Builder(FavouriteActivity.this).setTitle("提示")
                .setMessage("是否确定删除?")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                    	setResult(RESULT_OK);//确定按钮事件
                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //取消按钮事件
                    	setResult(RESULT_OK);//确定按钮事件
                    	Cursor sc = (Cursor)mListView.getAdapter().getItem(arg2);
                    			long id = sc.getLong(0);
                    	Uri uri = ContentUris.withAppendedId(Favourite.CONTENT_URI, id);
                    	FavouriteActivity.this.getContentResolver().delete(uri, null, null);
                    	sc.requery();
                    	adapter.notifyDataSetChanged();
                    	mListView.invalidate();
                    }
                })
                .show();
				return false;
			}
        	
        });
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
