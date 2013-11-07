package com.news.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Paint.FontMetrics;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextPaint;
import android.widget.Toast;

public class AppUtil {
	
	public static final String KEY_APP_KEY = "JPUSH_APPKEY";
	 /**
     * will trim the string
     * 
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }
    
    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    // 取得AppKey
    public static String getAppKey(Context context) {
        Bundle metaData = null;
        String appKey = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
            if (null != metaData) {
                appKey = metaData.getString(KEY_APP_KEY);
                if ((null == appKey) || appKey.length() != 24) {
                    appKey = null;
                }
            }
        } catch (NameNotFoundException e) {

        }
        return appKey;
    }
    
    // 取得版本号
    public static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}
	
    public static void showToast(final String toast, final Context context)
    {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}).start();
    }
    
    public static String getOriginImage(String imageUrl){
    	if(imageUrl.lastIndexOf("x")<0)
    		return imageUrl;
        int extIndex=imageUrl.lastIndexOf(".");
        String ext=imageUrl.substring(extIndex+1);
        int thumbIndex=imageUrl.lastIndexOf("-");
        String orginImg=imageUrl.substring(0, thumbIndex);
        String result=orginImg+"."+ext;
        return result;
    }
    
    public static float getWidth(float fontSize,String text){
    	TextPaint FontPaint = new TextPaint();  
    	// 设置FontPaint属性，需要和被计算的对象属性一致  
    	FontPaint.setTextSize(fontSize);  
    	float textLength = FontPaint.measureText(text);
    	return textLength;
    }
    
    public static float getStringHeight(float fontSize,float totalWidth,float lineWidth){
    	TextPaint FontPaint = new TextPaint();  
    	FontPaint.setTextSize(fontSize);  
    	FontMetrics fm = FontPaint.getFontMetrics();  
    	float fontHeight = (float)Math.ceil(fm.descent - fm.ascent);  
    	int row=(int) (totalWidth/lineWidth)+1;
    	float totalHeight=(fontHeight+2)*row;
    	return totalHeight;
    }
}
