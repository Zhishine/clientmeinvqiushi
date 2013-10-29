package com.news.qiushi;

import net.loonggg.fragment.RightFragment;
import net.loonggg.fragment.WebViewFragment;
import net.loonggg.view.SlidingMenu;

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
import android.view.View;
import android.view.View.OnClickListener;


public class WebViewActivity extends FragmentActivity {
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
//		setContentView(R.layout.webview);
//		WebView web=(WebView)findViewById(R.id.webview);
//		 Intent intent = getIntent();
//         String url  = intent.getStringExtra("url");
//         m_id=intent.getIntExtra("id", 0);
//		web.loadUrl(url);
//		ImageView back=(ImageView) findViewById(R.id.back);
//		back.setOnClickListener(this);
//		ImageView share=(ImageView) findViewById(R.id.share);
//		share.setOnClickListener(this); 
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    /**ʹ��SSO��Ȩ����������´��� */
	    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.meinvqiushi.share",
                RequestType.SOCIAL);
	    
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
	public void showLeft() {
		mSlidingMenu.showLeftView();
	}

	public void showRight() {
		mSlidingMenu.showRightView();
	}

}
