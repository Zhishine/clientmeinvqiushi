package com.news.qiushi;

import net.loonggg.fragment.RightFragment;
import net.loonggg.fragment.WebViewFragment;
import net.loonggg.view.SlidingMenu;

import com.adsmogo.adapters.AdsMogoCustomEventPlatformEnum;
import com.adsmogo.adview.AdsMogoLayout;
import com.adsmogo.controller.listener.AdsMogoListener;
import com.adwo.adsdk.AdwoAdView;
import com.news.tool.AppDataManager;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.view.ActionBarView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class WebViewActivity extends FragmentActivity implements AdsMogoListener {
    int m_id=0;
    private SlidingMenu mSlidingMenu;// �������view
	//private LeftFragment leftFragment; // ����������Ƭ��view
	private RightFragment rightFragment; // �Ҳ��������Ƭ��view
	private WebViewFragment centerFragment;// �м�������Ƭ����view
	private FragmentTransaction ft; // ��Ƭ�����������
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.webview_frame);
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		/*mSlidingMenu.setLeftView(getLayoutInflater().inflate(
				R.layout.left_frame, null));*/
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.center_frame, null));

		ft = this.getSupportFragmentManager().beginTransaction();
		//leftFragment = new LeftFragment();
		rightFragment = new RightFragment();
		//ft.replace(R.id.left_frame, leftFragment);
		ft.replace(R.id.right_frame, rightFragment);

		centerFragment = new WebViewFragment();
		ft.replace(R.id.center_frame, centerFragment);
		ft.commit();
		
	
		
		
		com.umeng.socom.Log.LOG = true;
		if(AppDataManager.getInstance().getAdIsShow()){
	      AdwoAdView.setBannerMatchScreenWidth(true);
		  AdsMogoLayout adsMogoLayoutCode = new AdsMogoLayout(this,"84bdb23a08b9421f9057e50432d84b2a"); 
		  FrameLayout.LayoutParams params = new FrameLayout.LayoutParams( 
		  FrameLayout.LayoutParams.MATCH_PARENT, 
		  FrameLayout.LayoutParams.WRAP_CONTENT); 
		  // ���ù����ֵ�λ��(�����ڵײ�) 
		  params.bottomMargin = 0; 
		  adsMogoLayoutCode.setAdsMogoListener(this); 
		  params.gravity = Gravity.BOTTOM; 
		  addContentView(adsMogoLayoutCode, params); 
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if(requestCode==1000){
	    	centerFragment.updateWebView();
	    	//showRight();
	    }
	    else
	    {
	    /**ʹ��SSO��Ȩ����������´��� */
	    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.meinvqiushi.share",
                RequestType.SOCIAL);
	    
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	    }
	}
	public void showLeft() {
		mSlidingMenu.showLeftView();
	}

	public void showRight() {
		mSlidingMenu.showRightView();
	}
	@Override
	public Class getCustomEvemtPlatformAdapterClass(
			AdsMogoCustomEventPlatformEnum arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onClickAd(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onCloseAd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onCloseMogoDialog() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFailedReceiveAd() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRealClickAd() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReceiveAd(ViewGroup arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRequestAd(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
