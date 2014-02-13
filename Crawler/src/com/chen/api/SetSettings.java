package com.chen.api;

import java.io.File;
import java.io.IOException;

import com.liu.core.BrowserApplication;
import com.mywie.utils.FileHelp;
import com.trolltech.qt.core.QSettings;
import com.trolltech.qt.core.QSettings.Format;




public class SetSettings {
	
	
	public enum acceptCookies{
		 always,
		 never,
		 onlyVisistedSite;

	   public String toString(){  //覆盖了父类Enum的toString()   
	   return super.toString();   
	   }   
	    
	  
	}

	
// 	private String  acceptCookies[]={"总是","从不","只允许从浏览过的站点" };
	public enum keepCookiesUntil{
		cookiesFailure,
		exitAppilcation,
		maximum_90_days;
		
	   public String toString(){  
		  return super.toString();   
		}
	}
	
	
	
	
	
	private java.lang.Integer acceptCookieIndex=0;	
	private int keepCookiesUntilIndex=0;
	private boolean blockPopupWindows=true;
	private boolean AutoLoadImages=true;
	private boolean JavascriptEnabled=true;
	private boolean PluginsEnabled=false;
	private String maxCache="50";
	
	public int getAcceptCookieIndex() {
		return acceptCookieIndex;
	}

	public void setAcceptCookieIndex(int acceptCookieIndex) {
		this.acceptCookieIndex = acceptCookieIndex;
	}


	public int getKeepCookiesUntilIndex() {
		return keepCookiesUntilIndex;
	}


	public void setKeepCookiesUntilIndex(int keepCookiesUntilIndex) {
		this.keepCookiesUntilIndex = keepCookiesUntilIndex;
	}


	public boolean isBlockPopupWindows() {
		return blockPopupWindows;
	}


	public void setBlockPopupWindows(boolean blockPopupWindows) {
		this.blockPopupWindows = blockPopupWindows;
	}


	public boolean isAutoLoadImages() {
		return AutoLoadImages;
	}


	public void setAutoLoadImages(boolean autoLoadImages) {
		AutoLoadImages = autoLoadImages;
	}


	public boolean isJavascriptEnabled() {
		return JavascriptEnabled;
	}


	public void setJavascriptEnabled(boolean javascriptEnabled) {
		JavascriptEnabled = javascriptEnabled;
	}


	public boolean isPluginsEnabled() {
		return PluginsEnabled;
	}


	public void setPluginsEnabled(boolean pluginsEnabled) {
		PluginsEnabled = pluginsEnabled;
	}


	public String getMaxCache() {
		return maxCache;
	}


	public void setMaxCache(String maxCache) {
		this.maxCache = maxCache;
	}


	
//	private boolean acceptCookies=true;
	
	
	public /*boolean*/void deleteCookieFile() {
    	File file = new File(BrowserApplication.dataFilePath("cookies.ini"));
    	//return file.delete();
    	file.delete();    	
    }
	
	
	  public void clearcache() throws IOException {
	    	String path = BrowserApplication.dataFilePath("cache");
	    	FileHelp.deleteFoder(new File(path));
	    	
		}
	  
	
	public /*boolean*/void writeSettings() {//保存设置
    	QSettings setSettings = new QSettings(BrowserApplication.dataFilePath("Appsettings.ini"), Format.IniFormat);
    	
    	setSettings.beginGroup("WebSettings");
	    setSettings.setValue("blockPopupWindows", blockPopupWindows);
	    setSettings.setValue("AutoLoadImages", AutoLoadImages);
	    setSettings.setValue("JavascriptEnabled", JavascriptEnabled);
	    setSettings.setValue("PluginsEnabled", PluginsEnabled);
	    setSettings.endGroup();
	    
	    setSettings.beginGroup("Cache");
	    setSettings.setValue("maxCache", maxCache);
	    setSettings.endGroup();
	    
	    setSettings.beginGroup("Cookies");
    	setSettings.setValue("acceptCookies",acceptCookieIndex );
    	setSettings.setValue("keepCookiesUntil", keepCookiesUntilIndex);
    	setSettings.endGroup();   	
    	
    	
    	//return true;
    }

}
