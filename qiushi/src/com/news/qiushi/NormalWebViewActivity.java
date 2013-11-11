package com.news.qiushi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class NormalWebViewActivity extends Activity {
	WebView webview=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	 webview=new WebView(this);
	 webview.setWebViewClient(new NewsWebViewClient());
	 webview.getSettings().setJavaScriptEnabled(true);
	 webview.getSettings().setAllowFileAccess(true);
	 webview.getSettings().setPluginState(PluginState.ON);
	 webview.setBackgroundColor(Color.rgb(233, 233, 233));
		webview.setWebViewClient(new NewsWebViewClient());
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		webview.setLayoutParams(lp);
		 Intent intent = getIntent();
         String url  = intent.getStringExtra("url");
         webview.loadUrl(url);
         setContentView(webview);
	}
	protected void onDestroy() {
	      super.onDestroy();
	      if(webview!=null){
	    	  webview.removeAllViews();
	    	  webview.destroy();
	      }
	}
	 class NewsWebViewClient extends WebViewClient{ 

	       //��дshouldOverrideUrlLoading������ʹ������Ӻ�ʹ��������������򿪡� 

	    @Override 

	    public boolean shouldOverrideUrlLoading(WebView view, String url) { 
   
	    	 boolean result= super.shouldOverrideUrlLoading(view, url);
//	    	 if(!result){
//	    		 view.loadUrl(url);
//	    		 return true;
//	    	 }
             return result;
	        //�������Ҫ�����Ե�������¼��Ĵ�����true�����򷵻�false 

	    } 

	        

	   } 
}
