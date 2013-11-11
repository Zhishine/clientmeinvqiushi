package com.news.qiushi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

public class VideoActivity extends Activity {
	WebView mWebFlash =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		 mWebFlash = (WebView) findViewById(R.id.web_flash);
	     WebSettings settings = mWebFlash.getSettings();
	     settings.setPluginState(PluginState.ON);
	  settings.setJavaScriptEnabled(true);
	  settings.setAllowFileAccess(true);
	  settings.setDefaultTextEncodingName("GBK");
	  mWebFlash.setBackgroundColor(0);
	  String url=getIntent().getStringExtra("videoUrl");
	  mWebFlash.loadUrl(url);
	}

	protected void onDestroy() {
		      super.onDestroy();
		      mWebFlash.removeAllViews();
		      mWebFlash.destroy();
		}


}
