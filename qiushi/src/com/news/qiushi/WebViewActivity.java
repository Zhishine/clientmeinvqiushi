package com.news.qiushi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		WebView web=(WebView)findViewById(R.id.webview);
		 Intent intent = getIntent();
         String url  = intent.getStringExtra("url");
		web.loadUrl(url);
	}

}
