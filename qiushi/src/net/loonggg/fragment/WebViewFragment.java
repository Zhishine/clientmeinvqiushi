package net.loonggg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.news.qiushi.R;
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
			         * ����˵��
			         * descriptor UMSocialService�ı�ʶ���������һ��Acitvity��ʹ�ö��UMSocialService�������ָ����Ӱ��,
			         *           ��ʱ����ʹ��ģ������;���ֻʹ��һ��������ʹ�����Acitvity��class name��Ϊ������
			         * type     ����UMSocialService��Ȩ��.��ữ���ͳһΪ��RequestType.SOCIAL
			         */
			        // UMSocialService controller = UMServiceFactory.getUMSocialService("meinvqiushi.news."+m_id, RequestType.SOCIAL);
			        /**
			        * ����˵��
			        * arg0  �����ģ����뵱ǰActivity��ʵ������
			        * arg1  ����ʱ�Ƿ���Ҫ��¼��ȡֵfalse�����ο͵���ݽ�������
			        */
			
			       // controller.openComment(getActivity().getApplicationContext(), false);
			((WebViewActivity)getActivity()).showRight();
			
		}
	}


}
