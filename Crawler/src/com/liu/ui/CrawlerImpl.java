package com.liu.ui;

import java.io.IOException;

import com.trolltech.qt.gui.*;
/**
* 运行类
*/
public class CrawlerImpl extends QMainWindow {

    Crawler ui = new Crawler();

    public static void main(String[] args) {
        QApplication.initialize(args);

        CrawlerImpl testCrawlerImpl = new CrawlerImpl();
        
        testCrawlerImpl.show();
        QApplication.exec();//上面两句加起来显示UI界面
    }

    public CrawlerImpl() {
        try {
			ui.setupUi(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public CrawlerImpl(QWidget parent) {
        super(parent);
        try {
			ui.setupUi(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    protected void closeEvent(QCloseEvent arg) {
    	ui.closeEvent(arg);
    	super.closeEvent(arg);
    }
}
