/********************************************************************************
** Form generated from reading ui file 'Crawler.jui'
**
** Created: 日 十一月 27 14:25:12 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.liu.ui;

import java.io.IOException;
import com.liu.core.BrowserApplication;
import com.mywie.utils.FileHelp;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
/**
* 主界面
* 
*/
public class Crawler extends QMainWindow
{
    public QWidget centralwidget;
    public QVBoxLayout mainLayout;
    public QHBoxLayout topLayout;
    public QSpacerItem horizontalSpacer_4;
    public QPushButton crawlBtn;
    public QPushButton configBtn;
    //----------------------------------------fan----------------------------------------
    public QPushButton myformBtn;
    public MyformPage myformPage;
    public QPushButton jscreateBtn;
    public JsCreatePage jscreatPage;
    
    public QSpacerItem horizontalSpacer_3;
    public QFrame line;
    public QStackedWidget stack;
    public CrawlPage crawlPage;
    public ConfigPage configPage;
    public QStatusBar statusbar;
    
    public Crawler() { super(); }
    /**
	* 构建工程主界面。
	* @param Crawler
	* byVal 本工程
	*/
    public void setupUi(QMainWindow crawler) throws IOException
    {
    	crawler.resize(new QSize(708, 512).expandedTo(crawler.minimumSizeHint()));
    	//----------------------------------------fan----------------------------------------
    	this.setWindowIcon(new QIcon("./data/spider-black.png"));
    	
        centralwidget = new QWidget(crawler);
        centralwidget.setGeometry(new QRect(0, 20, 701, 461));
        mainLayout = new QVBoxLayout(centralwidget);
        mainLayout.setSpacing(10);
        mainLayout.setMargin(10);
        topLayout = new QHBoxLayout();
        topLayout.setSpacing(10);
        topLayout.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetDefaultConstraint);
        horizontalSpacer_4 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);
        topLayout.addItem(horizontalSpacer_4);

        //----------------------------------------fan----------------------------------------
        myformBtn = new QPushButton(centralwidget);
        myformBtn.setMinimumSize(new QSize(65, 65));
        topLayout.addWidget(myformBtn);
        jscreateBtn = new QPushButton(centralwidget);
        jscreateBtn.setMinimumSize(new QSize(65, 65));
        topLayout.addWidget(jscreateBtn);
        
        crawlBtn = new QPushButton(centralwidget);
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(crawlBtn.sizePolicy().hasHeightForWidth());
        crawlBtn.setSizePolicy(sizePolicy);
        crawlBtn.setMinimumSize(new QSize(65, 65));
        topLayout.addWidget(crawlBtn);

        configBtn = new QPushButton(centralwidget);
        configBtn.setMinimumSize(new QSize(65, 65));
        topLayout.addWidget(configBtn);

        horizontalSpacer_3 = new QSpacerItem(458, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);
        topLayout.addItem(horizontalSpacer_3);
        mainLayout.addLayout(topLayout);

        line = new QFrame(centralwidget);
        line.setFrameShape(QFrame.Shape.HLine);
        line.setFrameShadow(QFrame.Shadow.Sunken);
        mainLayout.addWidget(line);

        stack = new QStackedWidget(centralwidget);

        crawlPage = new CrawlPage(this);
        crawlPage.setGeometry(new QRect(10, 10, 671, 341));
        configPage = new ConfigPage();
        configPage.setGeometry(new QRect(10, 10, 671, 341));
        //----------------------------------------fan----------------------------------------
        myformPage = new MyformPage(this);
        myformPage.setGeometry(new QRect(10, 10, 671, 341));
        
        //----------------------------------------fan----------------------------------------
        stack.addWidget(myformPage);
        stack.addWidget(crawlPage);
        stack.addWidget(configPage);
        
        mainLayout.addWidget(stack);

        crawler.setCentralWidget(centralwidget);
        statusbar = new QStatusBar(crawler);
        crawler.setStatusBar(statusbar);
        crawler.setWindowTitle("Crawler");
        
        crawlBtn.setText("批量抓取");
        configBtn.setText("配置");
        //----------------------------------------fan----------------------------------------
        myformBtn.setText("链接抓取");
        jscreateBtn.setText("生成脚本");
        
        crawlBtn.clicked.connect(this,"showCrawlPage()");
        configBtn.clicked.connect(this,"showConfigPage()");
        //----------------------------------------fan----------------------------------------
        myformBtn.clicked.connect(this,"showMyform()");
        jscreateBtn.clicked.connect(this,"showJscreate()");
        
    } // setupUi
    /**
	* 槽，显示批量抓取界面。
	*/
    public void showCrawlPage(){
    	stack.setCurrentIndex(1);
    }
    /**
	* 槽，显示配置界面。
	*/
    public void showConfigPage(){
    	stack.setCurrentIndex(2);
    	String path = BrowserApplication.dataFilePath("cache");
    	configPage.cacheLabel.setText(FileHelp.formatDirSize(FileHelp.getDirSize(path)));
    }
    
    //----------------------------------------fan----------------------------------------
    /**
	* 槽，显示链接抓取界面。
	*/
    public void showMyform(){
    	stack.setCurrentIndex(0);
    }

    //----------------------------------------fan----------------------------------------
    /**
	* 槽，显示生成脚本界面。
	*/
    public void showJscreate(){
    	JsCreatePage creator = new JsCreatePage(this);
        creator.show();
    }
    
	/*
	 * window初始化时，从注册表或配置文件中读取配置，
	 * 得到应用程序保存的设置选项，用来初始化某些控件，包括：
	 * saveFileLineEdit自动填充为应用程序上次设置的保存目录
	 * 配置页面中Web内容中checkBox，cacheLabel，maxCacheSpinBox的初始化
	 * 
	 */
	@Override
	protected void closeEvent(QCloseEvent arg) {
		crawlPage.writeSettings();
	}
	
	public static void main(String[] args) throws IOException {
		QApplication.initialize(args);
			Crawler mainWindow = new Crawler();
			mainWindow.setupUi(mainWindow);
			mainWindow.show();
		QApplication.exec();
	}
}

