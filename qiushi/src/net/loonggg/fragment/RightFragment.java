package net.loonggg.fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import net.youmi.android.diy.DiyManager;

import com.lurencun.android.common.RandomUtil;
import com.news.modal.MWeatherInfo;
import com.news.modal.MWeatherInfo1;
import com.news.qiushi.AdjustActivity;
import com.news.qiushi.FavouriteActivity;
import com.news.qiushi.R;
import com.news.tool.AppShareManager;
import com.news.tool.DensityUtil;
import com.news.tool.NewsProvider;
import com.news.tool.NewsProviderInfo.Favourite;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class RightFragment extends Fragment implements OnClickListener {
   
	public static MWeatherInfo mWeather;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		com.umeng.socom.Log.LOG = true;
		View view = inflater.inflate(R.layout.right_fragment, null);
		LayoutParams lp=new LayoutParams((int) (DensityUtil.getLogicalWidth()*0.85),LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		view.setBackgroundResource(R.drawable.silder_bg);
		View share=view.findViewById(R.id.share_container);
		share.setOnClickListener(this);

		View comment=view.findViewById(R.id.comment_container);
		comment.setOnClickListener(this);
		View fankui=view.findViewById(R.id.fankui_container);
		fankui.setOnClickListener(this);
		View adjust=view.findViewById(R.id.adjust_container);
		adjust.setOnClickListener(this);
		View collect=view.findViewById(R.id.collect_container);
		collect.setOnClickListener(this);
		View collectFolder=view.findViewById(R.id.collect_folder_container);
		collectFolder.setOnClickListener(this);
		TextView timeTxt=(TextView) view.findViewById(R.id.time);
		TextView otherTxt=(TextView) view.findViewById(R.id.other_info);
		TextView jingxuanTxt=(TextView) view.findViewById(R.id.jingxuan1);
		otherTxt.setTextColor(Color.LTGRAY);
		jingxuanTxt.setTextColor(Color.LTGRAY);
		 Date   curDate   =   new   Date(System.currentTimeMillis());
		String timeStr=String.format("%s:%s",curDate.getHours(),curDate.getMinutes());
		timeTxt.setText(timeStr);
		final Calendar c = Calendar.getInstance();  
	     c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
	     String  month = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份  
	     String  day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码  
	     String  way = String.valueOf(c.get(Calendar.DAY_OF_WEEK));  
	        if("1".equals(way)){  
	        	way ="天";  
	        }else if("2".equals(way)){  
	        	way ="一";  
	        }else if("3".equals(way)){  
	        	way ="二";  
	        }else if("4".equals(way)){  
	        	way ="三";  
	        }else if("5".equals(way)){  
	        	way ="四";  
	        }else if("6".equals(way)){  
	        	way ="五";  
	        }else if("7".equals(way)){  
	        	way ="六";  
	        }  
	    String temp1="";
	    String wea1="";
	    if(mWeather!=null){
	    	temp1=mWeather.temp1;
	    	wea1=mWeather.weather1;
	    }
		String otherStr=String.format("%s月%s日，星期%s，%s %s",month,day,way,wea1,temp1);
		otherTxt.setText(otherStr);
		View lookMore=view.findViewById(R.id.look_more_container);
		lookMore.setOnClickListener(this);
		m_view=view;
		return view;
	}
    View m_view;
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.share_container:
			String description=getActivity().getIntent().getExtras().getString("description");
			String titleImageUrl=getActivity().getIntent().getExtras().getString("titleImageUrl");
			String redirectUrl=getActivity().getIntent().getExtras().getString("url");
			AppShareManager.getInstance().share(getActivity(), description, titleImageUrl, redirectUrl);
			break;
		case R.id.comment_container:
			 int id=getActivity().getIntent().getIntExtra("id", 0);
			 UMSocialService controller = UMServiceFactory.getUMSocialService("meinvqiushi.news."+id, RequestType.SOCIAL);
	        /**
	        * 参数说明
	        * arg0  上下文，传入当前Activity的实例即可
	        * arg1  评论时是否需要登录，取值false将以游客的身份进行评论
	        */

	        controller.openComment(getActivity(), false);
			break;
		case R.id.fankui_container:
			 FeedbackAgent agent = new FeedbackAgent(getActivity());
			    agent.startFeedbackActivity();
		    break;
		case R.id.adjust_container:
//			WebView wv=(WebView) getActivity().findViewById(R.id.webview);
//			WebSettings settings = wv.getSettings();
//			settings.setSupportZoom(true);
//			ZoomDensity[] array=ZoomDensity.values();
//		
//			wv.zoomIn();
//			wv.setInitialScale(25);
			Intent intent=new Intent(getActivity(),AdjustActivity.class);
		    getActivity().startActivityForResult(intent,1000);
		    break;    
		case R.id.collect_folder_container:
		    intent=new Intent(getActivity(),FavouriteActivity.class);
		    getActivity().startActivityForResult(intent,1000);
			break;
		case R.id.collect_container:
			ContentValues cvalues = new ContentValues();  
			String newsId=getActivity().getIntent().getExtras().getString("id");
	        cvalues.put(Favourite.NEWS_FAVOURITE_NEWS_ID, "2");  
	        String title=getActivity().getIntent().getExtras().getString("title");
	        cvalues.put(Favourite.NEWS_FAVOURITE_NEWS_TITLE, title); 
	        description=getActivity().getIntent().getExtras().getString("description");
	        cvalues.put(Favourite.NEWS_FAVOURITE_NEWS_DESCRIPTION, description); 
	        String url=getActivity().getIntent().getExtras().getString("url");
	        cvalues.put(Favourite.NEWS_FAVOURITE_NEWS_URL, url); 
	        titleImageUrl=getActivity().getIntent().getExtras().getString("titleImageUrl");
	        cvalues.put(Favourite.NEWS_FAVOURITE_NEWS_URL, url); 
	        Uri resulturi = getActivity().getContentResolver().insert(Favourite.CONTENT_URI, cvalues);
	        Toast toast=Toast.makeText( getActivity(), R.string.add_fav_success, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			break;
		case R.id.look_more_container:
			  DiyManager.showRecommendWall(getActivity());
			break;
		}
	}
	
	 
}
