package com.news.qiushi;

import com.news.tool.AppDataManager;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AdjustActivity extends Activity implements OnClickListener {
    View m_smallView=null;
    View m_midView=null;
    View m_largeView=null;
    View m_largerView=null;
    ImageView m_smallSelect=null;
    ImageView m_midSelect=null;
    ImageView m_largeSelect=null;
    ImageView m_largerSelect=null;
    int m_currentLevel=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adjust);
		m_smallView=findViewById(R.id.small_container);
		m_smallView.setOnClickListener(this);
		m_midView=findViewById(R.id.mid_container);
		m_midView.setOnClickListener(this);
		m_largeView=findViewById(R.id.large_container);
		m_largeView.setOnClickListener(this);
		m_largerView=findViewById(R.id.larger_container);
		m_largerView.setOnClickListener(this);
		View back=findViewById(R.id.back);
		back.setOnClickListener(this);
		m_smallSelect=(ImageView) findViewById(R.id.select1);
		m_midSelect=(ImageView) findViewById(R.id.select2);
		m_largeSelect=(ImageView) findViewById(R.id.select3);
		m_largerSelect=(ImageView) findViewById(R.id.select4);
		
		m_currentLevel=AppDataManager.getInstance().getFontSizeLevel();
		updateUI(m_currentLevel);
	}
	
	void updateUI(int level){
		switch(level){
		case -1:
			m_smallSelect.setVisibility(View.VISIBLE);
			m_midSelect.setVisibility(View.INVISIBLE);
			m_largeSelect.setVisibility(View.INVISIBLE);
			m_largerSelect.setVisibility(View.INVISIBLE);
			break;
		case 0:
			m_smallSelect.setVisibility(View.INVISIBLE);
			m_midSelect.setVisibility(View.VISIBLE);
			m_largeSelect.setVisibility(View.INVISIBLE);
			m_largerSelect.setVisibility(View.INVISIBLE);
			break;
		case 1:
			m_smallSelect.setVisibility(View.INVISIBLE);
			m_midSelect.setVisibility(View.INVISIBLE);
			m_largeSelect.setVisibility(View.VISIBLE);
			m_largerSelect.setVisibility(View.INVISIBLE);
			break;
		case 2:
			m_smallSelect.setVisibility(View.INVISIBLE);
			m_midSelect.setVisibility(View.INVISIBLE);
			m_largeSelect.setVisibility(View.INVISIBLE);
			m_largerSelect.setVisibility(View.VISIBLE);
			break;	
		}
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.small_container:
			if(m_currentLevel==-1)
				return;
			m_currentLevel=-1;
			break;
		case R.id.mid_container:
			if(m_currentLevel==0)
				return;
			m_currentLevel=0;
			break;	
		case R.id.large_container:
			if(m_currentLevel==1)
				return;
			m_currentLevel=1;
			break;	
		case R.id.larger_container:
			if(m_currentLevel==2)
				return;
			m_currentLevel=2;
			break;		
		case R.id.back:
			this.finish();
			return;
		}
		AppDataManager.getInstance().setFontSizeLevel(m_currentLevel);
		updateUI(m_currentLevel);
	}


}
