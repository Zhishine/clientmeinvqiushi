package com.news.qiushi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class NormalWebViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WebView webview=new WebView(this);
		webview.setWebViewClient(new NewsWebViewClient());
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		webview.setLayoutParams(lp);
		 Intent intent = getIntent();
         String url  = intent.getStringExtra("url");
         webview.loadUrl(url);
         setContentView(webview);
	}
	 class NewsWebViewClient extends WebViewClient{ 

	       //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。 

	    @Override 

	    public boolean shouldOverrideUrlLoading(WebView view, String url) { 

	        view.loadUrl(url); 

	        //如果不需要其他对点击链接事件的处理返回true，否则返回false 

	        return true; 

	    } 

	        

	   } 
}
