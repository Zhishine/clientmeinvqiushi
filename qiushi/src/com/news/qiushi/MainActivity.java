package com.news.qiushi;

import java.lang.reflect.Field;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huewu.pla.lib.MultiColumnPullToRefreshListView;
import com.news.adapter.NewsAdapter;
import com.news.modal.MAd;
import com.news.modal.MAppData;
import com.news.modal.MImage;
import com.news.modal.MNews;
import com.news.modal.MProduct;
import com.news.modal.MSystem;
import com.news.tool.AppDataClient;
import com.news.tool.AppDataObserver;
import com.news.tool.AppUtil;
import com.news.tool.DensityUtil;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,AppDataObserver  {
	private Context mContext;
	private final  String mPageName = "Home";
	public static boolean isForeground = false;
	RelativeLayout m_imageLayout=null;
	RelativeLayout m_newsLayout=null;
	RelativeLayout m_content=null;
    int IMG_ID=1;
    int NEWS_ID=2;
    int BOTTOMHEIGHT=50;
    int m_oldSelectIndex=0;
    int m_newSelectIndex=0;
    int m_newsPageNO=1;
    final int m_newsPageSize=8;
    boolean m_newsRequest=false;
    PullToRefreshListView m_pullToRefreshListView=null;
    AppDataClient m_client=null;
    NewsAdapter m_newsAdapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		m_imageLayout=new RelativeLayout(this);
		m_newsLayout=new RelativeLayout(this);
		//setContentView(R.layout.activity_main);
		MobclickAgent.setDebugMode(true);
		 // JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		registerMessageReceiver(); 
		DensityUtil densityUtil=new DensityUtil(this);
		m_client=new AppDataClient(this);
		createView();
	}

	void createView()
	{
		int screenWidth=DensityUtil.getActualWidth();
		int width=DensityUtil.dip2px(screenWidth/2);
		RelativeLayout container=new RelativeLayout(this);
		container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		setContentView(container);
		m_content=new RelativeLayout(this);
		
		RelativeLayout bottom=new RelativeLayout(this);
		bottom.setBackgroundResource(R.drawable.bottom_bg);//底部工具兰背景图片
		int bottomHeight=DensityUtil.dip2px(BOTTOMHEIGHT);
		
		RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,bottomHeight);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		bottom.setLayoutParams(lp1);
	    ImageView imgBtn=new ImageView(this);
	    //imgBtn.setBackgroundColor(Color.GREEN);
	    imgBtn.setOnClickListener(this);
	    imgBtn.setBackgroundResource(R.drawable.image_click);
	    imgBtn.setId(IMG_ID);
	   
	    RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(width,LayoutParams.MATCH_PARENT);
		lp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	    imgBtn.setLayoutParams(lp2);
		bottom.addView(imgBtn);
		
	    ImageView newsBtn=new ImageView(this);
	    //newsBtn.setBackgroundColor(Color.RED);
	    newsBtn.setOnClickListener(this);
	    newsBtn.setBackgroundResource(R.drawable.news_no_click);
	    newsBtn.setId(NEWS_ID);
	    RelativeLayout.LayoutParams lp3=new RelativeLayout.LayoutParams(width,LayoutParams.MATCH_PARENT);
	    lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	    newsBtn.setLayoutParams(lp3);
		bottom.addView(newsBtn);
		
		 Rect rect = new Rect();
         MainActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
         View view = MainActivity.this.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
         int topS = rect.top;//状态栏高度
         int topT = rect.height() - view.getHeight();
		
		
		container.addView(bottom);
		//int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
		//Rect rect= new Rect();  
		//this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect); ;
		//int screenHeight=DensityUtil.getLoHeight();
		int contentHeight=DensityUtil.getLogicalHeight()-bottomHeight-getBarHeight()-DensityUtil.dip2px(1);
		//int height=DensityUtil.dip2px(contentHeight);
		
		RelativeLayout.LayoutParams lp4=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,contentHeight);
		m_imageLayout.setLayoutParams(lp4);
	    lp4.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	    lp4.topMargin=DensityUtil.dip2px(1);
	    //lp4.bottomMargin=DensityUtil.dip2px(1);
		m_imageLayout.setBackgroundColor(Color.BLUE);
		
		RelativeLayout.LayoutParams lp5=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,contentHeight);
		m_newsLayout.setLayoutParams(lp5);
		lp5.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		lp5.topMargin=DensityUtil.dip2px(1);
		//lp5.bottomMargin=DensityUtil.dip2px(1);
		m_newsLayout.setBackgroundColor(Color.BLACK);
		
		m_content.addView(m_imageLayout);
	    container.addView(m_content);
	    
	   MultiColumnPullToRefreshListView pinList=new MultiColumnPullToRefreshListView(this,null);
	    //MultiColumnListView tt= new MultiColumnListView(this,null);
	    
	    // create news layout
	    createNewsLayout();

	   
	}
	
	void createNewsLayout(){
		 m_pullToRefreshListView=new PullToRefreshListView(this);
		 m_pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

					// Update the LastUpdatedLabel
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					m_newsPageNO=1;
					m_client.getNews(m_newsPageNO, m_newsPageSize);
					m_newsRequest=true;
					// Do work to refresh the list here.
					//new GetDataTask().execute();
				}
			});
		// Add an end-of-list listener
		 m_pullToRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

				@Override
				public void onLastItemVisible() {
					//Toast.makeText(MainActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
					if(m_newsRequest)
						return;
					m_newsPageNO++;
					m_client.getNews(m_newsPageNO, m_newsPageSize);
					m_newsRequest=true;
				}
			});
		 m_pullToRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				ListView listView = (ListView)parent;
			    MNews news=(MNews) listView.getItemAtPosition(position);
			    Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",news.mRedirectUrl);
                MainActivity.this.startActivity(intent);
			}
			 
		 });
		   this.m_newsLayout.addView(m_pullToRefreshListView);
		   m_client.getNews(this.m_newsPageNO, this.m_newsPageSize);
		   m_newsRequest=true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onResume() {
		isForeground = true;
		super.onResume();
		MobclickAgent.onPageStart( mPageName );
		MobclickAgent.onResume(mContext);
	}
	
	@Override
	public void onPause() {
		isForeground = false;
		super.onPause();
		MobclickAgent.onPageEnd( mPageName );
		MobclickAgent.onPause(mContext);
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
	
	public void onClick(View v) {
		ImageView img=(ImageView) this.findViewById(IMG_ID);
		ImageView news=(ImageView) this.findViewById(NEWS_ID);
		if(v.getId()==IMG_ID){
			//
			m_newSelectIndex=0;
			if(m_oldSelectIndex==m_newSelectIndex){
				return;
			}
			img.setBackgroundResource(R.drawable.image_click);
			news.setBackgroundResource(R.drawable.news_no_click);
			m_content.removeAllViews();
			m_content.addView(m_imageLayout);
			m_oldSelectIndex=m_newSelectIndex;
		}
		else if(v.getId()==NEWS_ID){
			m_newSelectIndex=1;
			if(m_oldSelectIndex==m_newSelectIndex){
				return;
			}
			img.setBackgroundResource(R.drawable.image_no_click);
			news.setBackgroundResource(R.drawable.news_click);
			m_content.removeAllViews();
			m_content.addView(m_newsLayout);
			m_oldSelectIndex=m_newSelectIndex;
		}
	}
	public int getBarHeight(){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;//默认为38，貌似大部分是这样的

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
	//for receive customer msg from jpush server
		private MessageReceiver mMessageReceiver;
		public static final String MESSAGE_RECEIVED_ACTION = "com.news.qiushi.MESSAGE_RECEIVED_ACTION";
		public static final String KEY_TITLE = "title";
		public static final String KEY_MESSAGE = "message";
		public static final String KEY_EXTRAS = "extras";
		
		public void registerMessageReceiver() {
			mMessageReceiver = new MessageReceiver();
			IntentFilter filter = new IntentFilter();
			filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
			filter.addAction(MESSAGE_RECEIVED_ACTION);
			registerReceiver(mMessageReceiver, filter);
		}

		public class MessageReceiver extends BroadcastReceiver {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
	              String messge = intent.getStringExtra(KEY_MESSAGE);
	              String extras = intent.getStringExtra(KEY_EXTRAS);
	              StringBuilder showMsg = new StringBuilder();
	              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
	              if (!AppUtil.isEmpty(extras)) {
	            	  showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
	              }
	              setCostomMsg(showMsg.toString());
				}
			}
		}
		
		private void setCostomMsg(String msg){
			/* if (null != msgText) {
				 msgText.setText(msg);
				 msgText.setVisibility(android.view.View.VISIBLE);
	         }*/
		}

		@Override
		public void getSystemResponse(MSystem system) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getAppDataResponse(MAppData appData) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getImageResponse(List<MImage> imageList, int pageIndex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getImageResponse(MImage image) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getNewsResponse(List<MNews> newsList, int pageIndex) {
			// TODO Auto-generated method stub
			m_newsRequest=false;
			if(newsList==null)
				return;
			if(pageIndex==1){
				m_newsAdapter=new NewsAdapter(this,newsList);
			    ListView actualListView=this.m_pullToRefreshListView.getRefreshableView();
			    actualListView.setFadingEdgeLength(0);
			    m_pullToRefreshListView.onRefreshComplete();
				actualListView.setAdapter(m_newsAdapter);
				m_newsAdapter.notifyDataSetChanged();
			}
			else
			{
				m_newsAdapter.addNews(newsList);
				m_newsAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void getNewsResponse(MNews news) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getProductResponse(List<MProduct> productList, int pageIndex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getProductResponse(List<MProduct> productList) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getProductResponse(MProduct product) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getAdResponse(List<MAd> adList) {
			// TODO Auto-generated method stub
			
		}

}
