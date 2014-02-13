package com.chen.api;

import java.util.ArrayList;

import com.liu.core.BrowserMainWindow;
import com.trolltech.qt.gui.QApplication;

public class RunCrawlerOne {
	public RunCrawlerOne(String args[],ArrayList urlSet,String savePath,String jsFilename,String encoding)
	{
		 QApplication.initialize(args);
		 QApplication.setQuitOnLastWindowClosed(true);
		 BatchCrawler bc=new BatchCrawler();
		// ArrayList list=new ArrayList();
		 BrowserMainWindow browser=new BrowserMainWindow(urlSet,savePath,jsFilename,null,null);
		 browser.setEncoding(encoding);		
		 browser.isApi=true;	
		 browser.isLogin=false;
		 browser.show();
		 QApplication.exec();
	}

}
