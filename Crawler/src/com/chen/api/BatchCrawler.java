package com.chen.api;

import java.util.ArrayList;

import com.liu.core.BrowserMainWindow;

import com.mywie.utils.FileHelp;
import com.trolltech.qt.core.QDir;
import com.trolltech.qt.gui.QApplication;


public class BatchCrawler {
	private String urlSetpath="";
	private String savePath="";
	private String jsFileName="bufanye.js";
	private String deadtime="NoTime";
	private String endcoding="utf-8";	
    private BrowserMainWindow browser = null;
    private boolean islogin=false;
   
    
    public BatchCrawler(){}
    public BatchCrawler(String urlSetpath,String savePath,String jsFileName,String deadtime,String encoding)
    {    	
    
    //	QApplication.initialize(args);
    	this.urlSetpath=urlSetpath;
    	this.savePath=savePath;
    	this.jsFileName=jsFileName;
    	this.deadtime=deadtime;
    	if(!encoding.equals(""))
    	this.endcoding=encoding;    
    	//start();  
		 
    }
	
       
    public void start()
    { 
    	
    //	QApplication.initialize(args);
    	if(urlSetpath.equals(""))
    	{
    		browser=new BrowserMainWindow(new ArrayList<String>(),"","NoTime");
       	}else if(this.jsFileName.equals(""))
       	{//为选择js文件
       		browser=new BrowserMainWindow(FileHelp.getURLs(this.urlSetpath),this.savePath,"NoTime");      		
       	}
       	else{//检查savePath目录是否为空
       		if(this.savePath.equals(""))
       		{
       			
       		}else{//不空，检查是否存在，若不存在，创建，创建不了，提示目录不合法
       			//用catch exception试试
       			QDir dir=new QDir(savePath);
       			if(!dir.exists())
       			{
       				Boolean mkdir=dir.mkpath(savePath);
       				if(mkdir==false)//创建失败
       				{}
       			}
       			
       		}
       		
       		
       	}
    	
    	browser=new BrowserMainWindow(FileHelp.getURLs(this.urlSetpath),this.savePath,this.jsFileName,this.deadtime);
    	browser.setEncoding(this.endcoding);
    	//抓取信息的输出？？？？？
    	
    	browser.show();
    	
    	//QApplication.exec();
    	
    }
    
    
    
	public String getUrlSetpath() {
		return urlSetpath;
	}
	public void setUrlSetpath(String urlSetpath) {
		this.urlSetpath = urlSetpath;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getJsFileName() {
		return jsFileName;
	}
	public void setJsFileName(String jsFileName) {
		this.jsFileName = jsFileName;
	}
	public String getDeadtime() {
		return deadtime;
	}
	public void setDeadtime(String deadtime) {
		this.deadtime = deadtime;
	}
	public String getEndcoding() {
		return endcoding;
	}
	public void setEndcoding(String endcoding) {
		this.endcoding = endcoding;
	}
	public BrowserMainWindow getBrowser() {
		return browser;
	}
	public void setBrowser(BrowserMainWindow browser) {
		this.browser = browser;
	}
	
	public void loginAndStart()
	{
		browser=new BrowserMainWindow(new ArrayList<String>(),"",this.jsFileName,this.deadtime);
    	
		browser.setEncoding(this.endcoding);
    	browser.setLoginPar(this.savePath,this.jsFileName,FileHelp.getURLs(this.urlSetpath),true,true);
    	//browser.isLogin=true;
    	//browser.isApi=true;
    	browser.show();
	}
	
	public void login(String args[])
	{
		QApplication.initialize(args);
        browser=new BrowserMainWindow(new ArrayList<String>(),"",this.jsFileName,"NoTime");
    	browser.setEncoding(this.endcoding);
    	//browser.isLogin=true;
    	browser.isApi=true;
    	browser.isLogin=true;
    	browser.show();
		QApplication.exec();
	}
	public boolean isIslogin() {
		return islogin;
	}
	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}
	
	

	
	

}
