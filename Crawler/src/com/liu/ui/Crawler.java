/********************************************************************************
** Form generated from reading ui file 'Crawler.jui'
**
** Created: �� ʮһ�� 27 14:25:12 2011
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
* ������
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
	* �������������档
	* @param Crawler
	* byVal ������
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
        
        crawlBtn.setText("����ץȡ");
        configBtn.setText("����");
        //----------------------------------------fan----------------------------------------
        myformBtn.setText("����ץȡ");
        jscreateBtn.setText("���ɽű�");
        
        crawlBtn.clicked.connect(this,"showCrawlPage()");
        configBtn.clicked.connect(this,"showConfigPage()");
        //----------------------------------------fan----------------------------------------
        myformBtn.clicked.connect(this,"showMyform()");
        jscreateBtn.clicked.connect(this,"showJscreate()");
        
    } // setupUi
    /**
	* �ۣ���ʾ����ץȡ���档
	*/
    public void showCrawlPage(){
    	stack.setCurrentIndex(1);
    }
    /**
	* �ۣ���ʾ���ý��档
	*/
    public void showConfigPage(){
    	stack.setCurrentIndex(2);
    	String path = BrowserApplication.dataFilePath("cache");
    	configPage.cacheLabel.setText(FileHelp.formatDirSize(FileHelp.getDirSize(path)));
    }
    
    //----------------------------------------fan----------------------------------------
    /**
	* �ۣ���ʾ����ץȡ���档
	*/
    public void showMyform(){
    	stack.setCurrentIndex(0);
    }

    //----------------------------------------fan----------------------------------------
    /**
	* �ۣ���ʾ���ɽű����档
	*/
    public void showJscreate(){
    	JsCreatePage creator = new JsCreatePage(this);
        creator.show();
    }
    
	/*
	 * window��ʼ��ʱ����ע���������ļ��ж�ȡ���ã�
	 * �õ�Ӧ�ó��򱣴������ѡ�������ʼ��ĳЩ�ؼ���������
	 * saveFileLineEdit�Զ����ΪӦ�ó����ϴ����õı���Ŀ¼
	 * ����ҳ����Web������checkBox��cacheLabel��maxCacheSpinBox�ĳ�ʼ��
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

