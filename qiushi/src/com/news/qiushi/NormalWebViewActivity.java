package com.news.qiushi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class NormalWebViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WebView webview=new WebView(this);
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		webview.setLayoutParams(lp);
		 Intent intent = getIntent();
         String url  = intent.getStringExtra("url");
         webview.loadUrl(url);
         setContentView(webview);
	}

}
