package net.loonggg.fragment;

import com.lurencun.android.common.RandomUtil;
import com.news.qiushi.R;
import com.news.tool.AppShareManager;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.ImageView;

public class RightFragment extends Fragment implements OnClickListener {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		com.umeng.socom.Log.LOG = true;
		View view = inflater.inflate(R.layout.right_fragment, null);
		view.setBackgroundResource(R.drawable.booklist_menu_bg_unit);
		ImageView share=(ImageView)view.findViewById(R.id.share);
		share.setOnClickListener(this);
		ImageView comment=(ImageView)view.findViewById(R.id.comment);
		comment.setOnClickListener(this);
		ImageView fankui=(ImageView)view.findViewById(R.id.fankui);
		fankui.setOnClickListener(this);
		ImageView adjust=(ImageView)view.findViewById(R.id.adjust);
		adjust.setOnClickListener(this);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.share:
			String description=getActivity().getIntent().getExtras().getString("description");
			String titleImageUrl=getActivity().getIntent().getExtras().getString("titleImageUrl");
			String redirectUrl=getActivity().getIntent().getExtras().getString("url");
			AppShareManager.getInstance().share(getActivity(), description, "http://php1.hontek.com.cn/wordpress/wp-content/uploads/2013/09/logo-144.png", redirectUrl);
			break;
		case R.id.comment:
			 int id=getActivity().getIntent().getIntExtra("id", 0);
			 UMSocialService controller = UMServiceFactory.getUMSocialService("meinvqiushi.news."+id, RequestType.SOCIAL);
	        /**
	        * 参数说明
	        * arg0  上下文，传入当前Activity的实例即可
	        * arg1  评论时是否需要登录，取值false将以游客的身份进行评论
	        */

	        controller.openComment(getActivity(), false);
			break;
		case R.id.fankui:
			 FeedbackAgent agent = new FeedbackAgent(getActivity());
			    agent.startFeedbackActivity();
		    break;
		case R.id.adjust:
			WebView wv=(WebView) getActivity().findViewById(R.id.webview);
			WebSettings settings = wv.getSettings();
			settings.setSupportZoom(true);
			ZoomDensity[] array=ZoomDensity.values();
			//settings.setBuiltInZoomControls(true);
			wv.zoomIn();
			wv.setInitialScale(25);
			//settings.setDefaultZoom(ZoomDensity.CLOSE);//webSettings.setDefaultZoom(zoomDensity); 
		    break;    
		}
	}
	
	 
}
