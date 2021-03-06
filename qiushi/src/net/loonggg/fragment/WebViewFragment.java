package net.loonggg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.news.qiushi.MainActivity;
import com.news.qiushi.NormalWebViewActivity;
import com.news.qiushi.R;
import com.news.qiushi.VideoActivity;
import com.news.qiushi.ViewImageActivity;
import com.news.qiushi.WebViewActivity;
import com.news.tool.AppDataManager;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WebViewFragment extends android.support.v4.app.Fragment  implements OnClickListener {
	private ImageView lv_left;
	private ImageView iv_right;
    private int m_id;
    View m_View;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		m_View = inflater.inflate(R.layout.webview, null);
		m_View.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//((WebViewActivity)getActivity()).showRight();
			}
			
		});
		updateWebView();
		 WebView web=(WebView)m_View.findViewById(R.id.webview);
		 web.setWebViewClient(new NewsWebViewClient());
		 web.getSettings().setJavaScriptEnabled(true);
		 web.getSettings().setAllowFileAccess(true);
		 web.getSettings().setPluginState(PluginState.ON);
		 web.setBackgroundColor(0);
        String url  = getActivity().getIntent().getStringExtra("url");
        m_id=getActivity().getIntent().getIntExtra("id", 0);
		web.loadUrl(url);
		ImageView back=(ImageView) m_View.findViewById(R.id.back);
		back.setOnClickListener(this);
		ImageView expand=(ImageView) m_View.findViewById(R.id.expand);
		expand.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((WebViewActivity)getActivity()).showRight();
			}}); 
		return m_View;
	}

	public void removeAllView(){
		WebView web=(WebView)m_View.findViewById(R.id.webview);
		if(web!=null){
			web.removeAllViews();
			web.destroy();
		}
	}
	
	public boolean back(){
		WebView web=(WebView)m_View.findViewById(R.id.webview);
		if(web!=null&&web.canGoBack()){
			web.goBack();
			return true;
		}
		return false;
	}
	public void updateWebView(){
	   WebView web=(WebView)m_View.findViewById(R.id.webview);
		WebSettings settings = web.getSettings();
		settings.setSupportZoom(true);
		int level=AppDataManager.getInstance().getFontSizeLevel();
		switch(level){
		case -1:
			settings.setTextSize(WebSettings.TextSize.SMALLER);
			break;
		case 0:
			settings.setTextSize(WebSettings.TextSize.NORMAL);
			break;	
		case 1:
			settings.setTextSize(WebSettings.TextSize.LARGER);
			break;	
		case 2:
			settings.setTextSize(WebSettings.TextSize.LARGEST);
			break;			
		}
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
         
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view.getId()==R.id.back){
			getActivity().finish();
		}
		else if(view.getId()==R.id.share){
		
			         /**
			         * 参数说明
			         * descriptor UMSocialService的标识，你可能在一个Acitvity中使用多个UMSocialService用于区分各个子版块,
			         *           此时建议使用模块名称;如果只使用一个，建议使用相关Acitvity的class name作为参数。
			         * type     申明UMSocialService的权限.社会化组件统一为：RequestType.SOCIAL
			         */
			        // UMSocialService controller = UMServiceFactory.getUMSocialService("meinvqiushi.news."+m_id, RequestType.SOCIAL);
			        /**
			        * 参数说明
			        * arg0  上下文，传入当前Activity的实例即可
			        * arg1  评论时是否需要登录，取值false将以游客的身份进行评论
			        */
			
			       // controller.openComment(getActivity().getApplicationContext(), false);
			((WebViewActivity)getActivity()).showRight();
			
		}
	}

	 class NewsWebViewClient extends WebViewClient{ 

	       //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。 

	    @Override 

	    public boolean shouldOverrideUrlLoading(WebView view, String url) { 
  
	    	if(url.toLowerCase().lastIndexOf("videoext=true")>0){
	    		Intent intent = new Intent(WebViewActivity.getInstance(),VideoActivity.class);

	            intent.putExtra("videoUrl",url);
	            WebViewActivity.getInstance().startActivity(intent);
	    	}
	    	else if(url.toLowerCase().lastIndexOf(".png")>0||url.toLowerCase().lastIndexOf(".jpg")>0){
	    		Intent intent = new Intent(getActivity(),ViewImageActivity.class);
	    		intent.putExtra("imgurl",url);
	    		//Log.i("viewImage", "viewImage click");
	    		getActivity().startActivity(intent);
	    	}
	    	else{
	        //view.loadUrl(url); 
	    		  Intent intent = new Intent(getActivity(),NormalWebViewActivity.class);
	              intent.putExtra("url",url);
	              getActivity().startActivity(intent);
	    	}
	        //如果不需要其他对点击链接事件的处理返回true，否则返回false 

	        return true; 

	    } 

	        

	   } 
}
