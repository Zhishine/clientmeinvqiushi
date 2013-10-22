package net.loonggg.fragment;

import com.lurencun.android.common.RandomUtil;
import com.news.qiushi.R;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
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
			final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",
                    RequestType.SOCIAL);
			
			// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
			String appID = "wxdea18af3126b8fc8";
			// 微信图文分享必须设置一个url 
			String contentUrl = "http://www.umeng.com/social";
			// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
			mController.getConfig().supportWXPlatform(getActivity(),appID, contentUrl);     
			// 支持微信朋友圈
			mController.getConfig().supportWXCirclePlatform(getActivity(),appID, contentUrl) ;
			// 设置分享文字     
			mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
			// 设置分享图片, 参数2为图片的地址
			mController.setShareMedia(new UMImage(getActivity(), 
			                              "http://www.umeng.com/images/pic/banner_module_social.png"));
            //设置分享内容
            mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
           //设置分享图片, 参数2为图片的地址
           mController.setShareMedia(new UMImage(getActivity(), 
             "http://www.umeng.com/images/pic/banner_module_social.png"));
           
           mController.getConfig().supportQQPlatform(getActivity(), "http://www.umeng.com/social");
           mController.getConfig().setSsoHandler( new QZoneSsoHandler(getActivity()) );
         //设置腾讯微博SSO handler
           mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
           mController.openShare(getActivity(), false);
           
        // 设置分享平台选择面板的平台显示顺序
           mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                                                    SHARE_MEDIA.QZONE,SHARE_MEDIA.QQ, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
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
