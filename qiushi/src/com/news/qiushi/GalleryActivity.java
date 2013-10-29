package com.news.qiushi;

import java.util.List;

import com.news.adapter.GalleryAdapter;
import com.news.modal.MGallery;
import com.news.view.PhotoGallery;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryActivity extends Activity implements OnClickListener {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		ImageView back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		final List<MGallery> galleryList=(List<MGallery>)getIntent().getSerializableExtra("galleryList");
		PhotoGallery gallery=(PhotoGallery) findViewById(R.id.banner_gallery);
		
		GalleryAdapter adapter=new GalleryAdapter(this,galleryList);
		gallery.setAdapter(adapter);
		//FrameLayout container=(FrameLayout) findViewById(R.id.gallery_container);
		//container.setBackgroundResource(R.drawable.bg);
		final TextView description=(TextView) findViewById(R.id.description);
		description.setText(galleryList.get(0).mDescription);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> adpter, View arg1,
					int index, long arg3) {
				// TODO Auto-generated method stub
				description.setText(galleryList.get(index).mDescription);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view.getId()==R.id.back){
			this.finish();
		}
	}

	

}
