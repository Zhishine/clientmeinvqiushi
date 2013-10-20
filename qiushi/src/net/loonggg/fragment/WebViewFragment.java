package net.loonggg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.news.qiushi.R;
import com.news.qiushi.WebViewActivity;
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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WebViewFragment extends android.support.v4.app.Fragment  implements OnClickListener {
	private ImageView lv_left;
	private ImageView iv_right;
    private int m_id;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.webview, null);
		mView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//((WebViewActivity)getActivity()).showRight();
			}
			
		});
		WebView web=(WebView)mView.findViewById(R.id.webview);
        String url  = getActivity().getIntent().getStringExtra("url");
        m_id=getActivity().getIntent().getIntExtra("id", 0);
		web.loadUrl(url);
		ImageView back=(ImageView) mView.findViewById(R.id.back);
		back.setOnClickListener(this);
		ImageView share=(ImageView) mView.findViewById(R.id.share);
		share.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((WebViewActivity)getActivity()).showRight();
			}}); 
		return mView;
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


}
