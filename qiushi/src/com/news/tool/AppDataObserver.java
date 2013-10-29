package com.news.tool;

import java.util.List;

import com.news.modal.MAd;
import com.news.modal.MAppData;
import com.news.modal.MGallery;
import com.news.modal.MImage;
import com.news.modal.MNews;
import com.news.modal.MProduct;
import com.news.modal.MSystem;

public interface AppDataObserver
{
	public void getSystemResponse(MSystem system);
	public void getAppDataResponse(MAppData appData);
	public void getImageResponse(List<MImage> imageList,int pageIndex);
	public void getImageResponse(MImage image);
	public void getNewsResponse(List<MNews> newsList,int pageIndex);
	public void getNewsResponse(MNews news);
	public void getProductResponse(List<MProduct> productList,int pageIndex);
	public void getProductResponse(List<MProduct> productList);
	public void getProductResponse(MProduct product);
	public void getAdResponse(List<MAd> adList);
	public void getNewsGalleryResponse(List<MGallery> galleryList);
	public void getImageGalleryResponse(List<MGallery> galleryList);
	public void getAdGalleryResponse(List<MGallery> galleryList);
}