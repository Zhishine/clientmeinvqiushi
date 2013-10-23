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

			// wx967daebe835fbeac������΢�ſ���ƽ̨ע��Ӧ�õ�AppID, ������Ҫ�滻����ע���AppID
			String appID = "wxdea18af3126b8fc8";
			// ΢��ͼ�ķ�����������һ��url 
			//String contentUrl = "http://www.umeng.com/social";
			// ����΢��ƽ̨������1Ϊ��ǰActivity, ����2Ϊ�û������AppID, ����3Ϊ�������������ת����Ŀ��url
			mController.getConfig().supportWXPlatform(activity,appID, redirectUrl);     
			// ֧��΢������Ȧ
			mController.getConfig().supportWXCirclePlatform(activity,appID, redirectUrl) ;
			// ���÷�������     
			mController.setShareContent(content);
			// ���÷���ͼƬ, ����2ΪͼƬ�ĵ�ַ
			mController.setShareMedia(new UMImage(activity, 
					imageUrl));
		// ����QQ֧��, ��������QQ�������ݵ�target url
	    mController.getConfig().supportQQPlatform(activity,false,redirectUrl);
        mController.getConfig().setSsoHandler( new QZoneSsoHandler(activity) );
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
//      //������Ѷ΢��SSO handler
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        mController.openShare(activity, false);
        
     // ���÷���ƽ̨ѡ������ƽ̨��ʾ˳��
        mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                                                 SHARE_MEDIA.QZONE,SHARE_MEDIA.QQ, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,SHARE_MEDIA.DOUBAN);
		 
	 }
	 

}