package com.news.tool;

import android.app.Activity;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;

public class AppShareManager {
	 private static final AppShareManager m_instance=new AppShareManager();
	 final UMSocialService mController = UMServiceFactory.getUMSocialService("com.meinvqiushi.share",
             RequestType.SOCIAL);
	 private AppShareManager(){
	    	
	    }
	    public static AppShareManager getInstance(){
	    	return m_instance;
	    }
	  
	 public void share(Activity activity,String content,String imageUrl,String redirectUrl){

			// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
			String appID = "wxdea18af3126b8fc8";
			 imageUrl="http://php1.hontek.com.cn/wordpress/wp-content/uploads/2013/09/logo-144.png";
				UMImage mUMImgBitmap = new UMImage(activity, "http://www.umeng.com/images/pic/banner_module_social.png");
//				UMImage mUMImgBitmap = new UMImage(getActivity(), BitmapFactory.decodeResource(getResources(), R.drawable.icon));
//				UMImage mUMImgBitmap = new UMImage(getActivity(), BitmapFactory.decodeFile("/mnt/sdcard/test.jpg"));
//				UMImage mUMImgBitmap = new UMImage(getActivity(), new File("/mnt/sdcard/test1.png"));
				mUMImgBitmap.setTitle("分享到微信");
				// target url 必须填写
				mUMImgBitmap.setTargetUrl( redirectUrl ) ;
			// 微信图文分享必须设置一个url 
			//String contentUrl = "http://www.umeng.com/social";
			// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
			mController.getConfig().supportWXPlatform(activity,appID, redirectUrl);     
			// 支持微信朋友圈
			mController.getConfig().supportWXCirclePlatform(activity,appID, redirectUrl) ;
			// 设置分享文字     
			mController.setShareContent(content);
			// 设置分享图片, 参数2为图片的地址
			//mController.setShareMedia(new UMImage(activity,imageUrl));
		// 添加QQ支持, 并且设置QQ分享内容的target url
	    mController.getConfig().supportQQPlatform(activity,false,"100587734",redirectUrl);
        mController.getConfig().setSsoHandler( new QZoneSsoHandler(activity) );
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
//      //设置腾讯微博SSO handler
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
     // 设置分享平台选择面板的平台显示顺序
        mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                                                 SHARE_MEDIA.QZONE,SHARE_MEDIA.QQ, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,SHARE_MEDIA.DOUBAN);
        mController.openShare(activity, false);
        
     
		 
	 }
	 

}
