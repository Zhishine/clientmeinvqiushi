package com.news.qiushi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import cn.jpush.android.api.JPushInterface;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huewu.pla.lib.MultiColumnListView.OnLoadMoreListener;
import com.huewu.pla.lib.MultiColumnPullToRefreshListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.news.adapter.ImageAdapter;
import com.news.adapter.ImagesAdapter;
import com.news.adapter.NewsAdapter;
import com.news.modal.MAd;
import com.news.modal.MAppData;
import com.news.modal.MImage;
import com.news.modal.MNews;
import com.news.modal.MProduct;
import com.news.modal.MSystem;
import com.news.tool.AppDataClient;
import com.news.tool.AppDataManager;
import com.news.tool.AppDataObserver;
import com.news.tool.AppUtil;
import com.news.tool.DensityUtil;
import com.news.view.MyGallery;
import com.umeng.analytics.MobclickAgent;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener,AppDataObserver  {
	private Context mContext;
	private final  String mPageName = "Home";
	public static boolean isForeground = false;
	LinearLayout m_imageLayout=null;
	
	LinearLayout m_newsLayout=null;
	LinearLayout m_content=null;
    int IMG_ID=1;
    int NEWS_ID=2;
    int GAME_ID=3;
    int NAV_ID=4;
    int BOTTOMHEIGHT=50;
    int m_oldSelectIndex=0;
    int m_newSelectIndex=0;
    int m_newsPageNO=1;
    final int m_newsPageSize=8;
    boolean m_newsRequest=false;
    
    
    int m_adHeight=150;
    PullToRefreshListView m_pullToRefreshListView=null;
    MultiColumnPullToRefreshListView m_multiColumnPullToRefreshListView = null;
    
    int m_ImagePageNO = 1;
    final int m_ImagePageSize = 8;
    boolean m_ImageRequest = false;
    
    AppDataClient m_client=null;
    NewsAdapter m_newsAdapter=null;
    ImagesAdapter m_imageAdapter = null;
    
    private List<Drawable> m_imgList = new ArrayList<Drawable>();
    private int preSelImgIndex = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		m_imageLayout=new LinearLayout(this);
		m_newsLayout=new LinearLayout(this);
		m_newsLayout.setOrientation(1);
		m_imageLayout.setOrientation(1);
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
		int contentHeight=DensityUtil.getLogicalHeight()-getBarHeight();
		int bottomHeight=contentHeight/10;
		LinearLayout container=new LinearLayout(this);
		container.setOrientation(1);
		//container.setBackgroundColor(Color.BLUE);
		container.setBackgroundResource(R.drawable.bg);
		container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		setContentView(container);
		m_content=new LinearLayout(this);
		LinearLayout.LayoutParams lp0=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,contentHeight-bottomHeight);
		m_content.setLayoutParams(lp0);
		
		LinearLayout bottom=new LinearLayout(this);
		
		//bottom.setWeightSum( 2f);
		bottom.setBackgroundResource(R.drawable.bottom_bg);//底部工具兰背景图片
		//bottom.setBackgroundColor(Color.RED);
		
		
		RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,bottomHeight);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		bottom.setLayoutParams(lp1);
		
	    ImageView imgBtn=new ImageView(this);
	    imgBtn.setAdjustViewBounds(true);
	    imgBtn.setScaleType(ScaleType.FIT_XY);
	    imgBtn.setClickable(true);
	    imgBtn.setBackgroundColor(Color.GREEN);
	    imgBtn.setOnClickListener(this);
	    imgBtn.setBackgroundResource(R.drawable.image_click);
	    imgBtn.setId(IMG_ID);
	   
	    LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(width,LayoutParams.MATCH_PARENT);
		//lp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	    imgBtn.setLayoutParams(lp2);
		bottom.addView(imgBtn);
		
	    ImageView newsBtn=new ImageView(this);
	   newsBtn.setAdjustViewBounds(true);
	    newsBtn.setClickable(true);
	    newsBtn.setScaleType(ScaleType.FIT_XY);
	    newsBtn.setBackgroundColor(Color.RED);
	    newsBtn.setOnClickListener(this);
	    newsBtn.setBackgroundResource(R.drawable.news_no_click);
	    newsBtn.setId(NEWS_ID);
	    LinearLayout.LayoutParams lp3=new LinearLayout.LayoutParams(width,LayoutParams.MATCH_PARENT);
	    //lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	    newsBtn.setLayoutParams(lp3);
		bottom.addView(newsBtn);
		
		
		 Rect rect = new Rect();
         MainActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
         View view = MainActivity.this.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
         int topS = rect.top;//状态栏高度
         int topT = rect.height() - view.getHeight();
		
		
		
		//int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
		//Rect rect= new Rect();  
		//this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect); ;
		//int screenHeight=DensityUtil.getLoHeight();
		//int contentHeight=DensityUtil.getLogicalHeight()-bottomHeight-getBarHeight()-DensityUtil.dip2px(1);
		//int height=DensityUtil.dip2px(contentHeight);
		
		RelativeLayout.LayoutParams lp4=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		m_imageLayout.setLayoutParams(lp4);
	    lp4.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	
		RelativeLayout.LayoutParams lp5=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		m_newsLayout.setLayoutParams(lp5);
		lp5.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		
		//m_content.setBackgroundColor(Color.RED);
		
		//m_content.setWeightSum(8f);
		m_content.addView(m_imageLayout);
	    container.addView(m_content);
	    container.addView(bottom);
	   //MultiColumnPullToRefreshListView pinList=new MultiColumnPullToRefreshListView(this,null);
	    //MultiColumnListView tt= new MultiColumnListView(this,null);
	    
	    // create news layout
	    
	 
	    createNewsLayout();
	    
	    createImageLayout();
	    
	   
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("deprecation")
	void createNavIcon(){
		int screenWidth=DensityUtil.getActualWidth();
		int width=DensityUtil.dip2px(screenWidth/2);
		RelativeLayout navLayout=new RelativeLayout(this);
	
		
		RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		navLayout.setLayoutParams(lp1);
	    ImageView gameBtn=new ImageView(this);
	    gameBtn.setAdjustViewBounds(true);
	    gameBtn.setScaleType(ScaleType.FIT_CENTER);
	    //imgBtn.setBackgroundColor(Color.GREEN);
	    gameBtn.setOnClickListener(this);
	    Drawable leftUpIcon=AppDataManager.getInstance().getLeftUpIcon();
	    gameBtn.setImageDrawable(leftUpIcon);
	    //gameBtn.setBackgroundResource(R.drawable.game);
	    //gameBtn.setId(IMG_ID);
	   
	    RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(width,LayoutParams.WRAP_CONTENT);
		lp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		gameBtn.setLayoutParams(lp2);
	    navLayout.addView(gameBtn);
		
	    ImageView navBtn=new ImageView(this);
	    //newsBtn.setBackgroundColor(Color.RED);
	    navBtn.setOnClickListener(this);
	    navBtn.setAdjustViewBounds(true);
	    navBtn.setScaleType(ScaleType.FIT_CENTER);
	    // navBtn.setBackground(AppDataManager.getInstance().getRightUpIcon());
	    navBtn.setImageDrawable(AppDataManager.getInstance().getRightUpIcon());
	    //navBtn.setBackgroundResource(R.drawable.navigation);
	    navBtn.setId(NAV_ID);
	    RelativeLayout.LayoutParams lp3=new RelativeLayout.LayoutParams(width,LayoutParams.WRAP_CONTENT);
	    lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	    navBtn.setLayoutParams(lp3);
	    navLayout.addView(navBtn);
		m_newsLayout.addView(navLayout);
		
	}
	
	void createImageLayout(){
		
		m_multiColumnPullToRefreshListView = new MultiColumnPullToRefreshListView(this);
		LayoutParams lp1=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		m_multiColumnPullToRefreshListView.setLayoutParams(lp1);
		m_multiColumnPullToRefreshListView.setOnRefreshListener(new com.huewu.pla.lib.MultiColumnPullToRefreshListView.OnRefreshListener(){

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				//refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				m_multiColumnPullToRefreshListView.setShowLastUpdatedText(true);
				//m_multiColumnPullToRefreshListView.setLastUpdatedDateFormat(label);
				
				m_ImagePageNO=1;
				m_client.getImage(m_ImagePageNO, m_ImagePageSize);
				m_ImageRequest=true;
			}
			
		});
		
		m_multiColumnPullToRefreshListView.setOnItemClickListener(new com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(PLA_AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		m_multiColumnPullToRefreshListView.setOnLoadMoreListener(new OnLoadMoreListener(){

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				if(m_ImageRequest){
					return;
				}
				m_client.getImage(++m_ImagePageNO, m_ImagePageSize);
				m_ImageRequest=true;
				//m_ImagePageNO;
				
			}
			
		});

		m_imageLayout.addView(m_multiColumnPullToRefreshListView);
		//RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,DensityUtil.dip2px(m_adHeight))
		   
		m_client.getImage(this.m_ImagePageNO, this.m_ImagePageSize);
		m_ImageRequest=true;
	}
	
	
	
	void createAdBanner(){
		InitImgList();
		final LinearLayout focusContainer=new LinearLayout(this);
		FrameLayout adFrameLayout=new FrameLayout(this);
		FrameLayout.LayoutParams lp1=new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,DensityUtil.dip2px(m_adHeight));
		adFrameLayout.setLayoutParams(lp1);
		
		//create nav and game
		
		
		MyGallery gallery=new MyGallery(this);
		gallery.setFadingEdgeLength(0);
		gallery.setSoundEffectsEnabled(false);
		gallery.setKeepScreenOn(true);
		gallery.setBackgroundColor(Color.TRANSPARENT);
		LayoutParams lp4=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);//DensityUtil.dip2px(m_adHeight)
		gallery.setLayoutParams(lp4);
		adFrameLayout.addView(gallery);
		gallery.setAdapter(new ImageAdapter(this,this.m_imgList));
		gallery.setFocusable(true);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

		    public void onItemSelected(AdapterView<?> arg0, View arg1,
			    int selIndex, long arg3) {
			//修改上一次选中项的背景
		    	selIndex = selIndex % m_imgList.size();
		    	
			ImageView preSelImg = (ImageView) focusContainer.findViewById(preSelImgIndex);
		preSelImg.setImageDrawable(MainActivity.this.getResources().getDrawable(R.drawable.ic_focus));
			//修改当前选中项的背景
			ImageView curSelImg = (ImageView) focusContainer
				.findViewById(selIndex);
			curSelImg
				.setImageDrawable(MainActivity.this
					.getResources().getDrawable(
						R.drawable.ic_focus_select));
			preSelImgIndex = selIndex;
		    }

		    public void onNothingSelected(AdapterView<?> arg0) {
		    }
		});
		
		
		LinearLayout bottomNavPoint=new LinearLayout(this);
		LinearLayout.LayoutParams lp3=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT );
		lp3.gravity=Gravity.CENTER;
		//bottomNavPoint.setPadding(0, 0, 0, DensityUtil.dip2px(5));
		bottomNavPoint.setOrientation(LinearLayout.VERTICAL);
		bottomNavPoint.setBackgroundColor(Color.TRANSPARENT);
		bottomNavPoint.setLayoutParams(lp3);
		adFrameLayout.addView(bottomNavPoint);
		
		
		LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT );
		lp2.gravity=Gravity.CENTER;
		focusContainer.setOrientation(LinearLayout.HORIZONTAL);
		focusContainer.setLayoutParams(lp2);
		focusContainer.setGravity(Gravity.CENTER_HORIZONTAL);
		focusContainer.setPadding(0, DensityUtil.dip2px(m_adHeight-15), 0, 0);
		for (int i = 0; i < m_imgList.size(); i++) {
		    ImageView localImageView = new ImageView(this);
		    localImageView.setId(i);
		    ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
		    localImageView.setScaleType(localScaleType);
		    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
		    		DensityUtil.dip2px(12), DensityUtil.dip2px(12));
		    localImageView.setLayoutParams(localLayoutParams);
		    localImageView.setPadding(DensityUtil.dip2px(3),DensityUtil.dip2px(3),DensityUtil.dip2px(3),DensityUtil.dip2px(3));
		    localImageView.setImageResource(R.drawable.ic_focus);
		    focusContainer.addView(localImageView);
		}
		
		
		bottomNavPoint.addView(focusContainer);
	
		this.m_newsLayout.addView(adFrameLayout);
	}
	

	  private void InitImgList() {
			// 加载图片数据（本demo仅获取本地资源，实际应用中，可异步加载网络数据）
		  m_imgList.add(this.getResources().getDrawable(R.drawable.img1));
		  m_imgList.add(this.getResources().getDrawable(R.drawable.img2));
		  m_imgList.add(this.getResources().getDrawable(R.drawable.img3));
		  m_imgList.add(this.getResources().getDrawable(R.drawable.img4));
		  m_imgList.add(this.getResources().getDrawable(R.drawable.img5));
		  m_imgList.add(this.getResources().getDrawable(R.drawable.img6));
		    }
	  
	void createNewsLayout(){
		 createAdBanner();
		 
		 createNavIcon();
		 
		 ImageView infoBg=new ImageView(this);
		 LayoutParams lp1=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);//DensityUtil.dip2px(30)
		 infoBg.setLayoutParams(lp1);
		 infoBg.setBackgroundResource(R.drawable.news_info_bg);
		 infoBg.setAdjustViewBounds(true);
		 infoBg.setScaleType(ScaleType.FIT_CENTER);
		 m_newsLayout.addView(infoBg);
		 
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
		 
		 m_pullToRefreshListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

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
		   //RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,DensityUtil.dip2px(m_adHeight))
		   
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
			img.invalidate();
			news.invalidate();
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
			img.invalidate();
			news.invalidate();
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
			m_ImageRequest=false;
			if(imageList==null)
				return;
			if(pageIndex==1){
				
				m_imageAdapter=new ImagesAdapter(this,imageList);
			    
				
				//ListView actualListView=this.m_pullToRefreshListView.getRefreshableView();
			    //actualListView.setFadingEdgeLength(0);
			    //m_pullToRefreshListView.onRefreshComplete();
				m_multiColumnPullToRefreshListView.onRefreshComplete();
				//actualListView.setAdapter(m_newsAdapter);
				m_multiColumnPullToRefreshListView.setAdapter(m_imageAdapter);
				m_imageAdapter.notifyDataSetChanged();
			}
			else
			{
				m_imageAdapter.addImages(imageList);
				m_imageAdapter.notifyDataSetChanged();
			}
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
