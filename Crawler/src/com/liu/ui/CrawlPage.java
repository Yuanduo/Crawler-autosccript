package com.liu.ui;

import java.util.ArrayList;
import java.util.List;
import com.liu.core.BrowserApplication;
import com.liu.core.BrowserMainWindow;
import com.mywie.utils.FileHelp;
import com.trolltech.qt.QNoNativeResourcesException;
import com.trolltech.qt.core.QDir;
import com.trolltech.qt.core.QSettings;
import com.trolltech.qt.core.QDir.Filter;
import com.trolltech.qt.core.QSettings.Format;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QFileDialog;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QRadioButton;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QSpacerItem;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QMessageBox.StandardButton;
/**
* ����ץȡ����
*
*/
public class CrawlPage extends QWidget {
	
	public QHBoxLayout crawlMainlLayout;
    public QVBoxLayout crawlLeftLayout;
    public QLabel chooseJSLabel;
    public QListWidget jSListView;
    public QGridLayout crawlRightLayout;
    public QLabel urlLabel;
    public QLineEdit urlFileLineEdit;
    public QPushButton chooseUrlFileBtn;
    public QLabel saveLabel;
    public QLineEdit saveFileLineEdit;
    public QPushButton saveFileBtn;
    public QLabel crawlInfoLabel;
    public QTextBrowser crawlInfoTextBrowser;
    public QSpacerItem horizontalSpacer;
    public QPushButton startBtn;
    public QPushButton stopBtn;
    private BrowserMainWindow browser = null;
    
    public QLabel encodingLabel;
    public QComboBox encodingComboBox;
    //----------------------------------------fan----------------------------------------
    public QPushButton refreshBtn;
    public QRadioButton timeButton;
    public QLineEdit timeEdit;
	/*��������ʽץȡ����ֹʱ��*/
	public String deadtime;
	/**
	* ���캯������ʼ������ץȡ�����ڡ�
	* @param parent
	* byVal ������
	*/    
	public CrawlPage(QWidget parent) {
		chooseJSLabel = new QLabel(this);
        chooseJSLabel.setTextFormat(com.trolltech.qt.core.Qt.TextFormat.AutoText);
        
        jSListView = new QListWidget(this);
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(jSListView.sizePolicy().hasHeightForWidth());
        jSListView.setSizePolicy(sizePolicy1);
        jSListView.setSpacing(3);
        
        crawlMainlLayout = new QHBoxLayout(this);
        crawlLeftLayout = new QVBoxLayout();
        crawlLeftLayout.setSpacing(10);
        crawlLeftLayout.setMargin(10);
        
        crawlLeftLayout.addWidget(chooseJSLabel);
        crawlLeftLayout.addWidget(jSListView);
        //----------------------------------------fan----------------------------------------
        refreshBtn = new QPushButton(this);
        refreshBtn.setText("ˢ��");
        refreshBtn.setFixedWidth(100);
        crawlLeftLayout.addWidget(refreshBtn);

        crawlMainlLayout.addLayout(crawlLeftLayout);
        
        crawlRightLayout = new QGridLayout();
        crawlRightLayout.setSpacing(10);
        crawlRightLayout.setMargin(10);
        
        urlLabel = new QLabel(this);
        urlLabel.setTextFormat(com.trolltech.qt.core.Qt.TextFormat.AutoText);

        urlFileLineEdit = new QLineEdit(this);
        QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed);
        sizePolicy2.setHorizontalStretch((byte)0);
        sizePolicy2.setVerticalStretch((byte)0);
        sizePolicy2.setHeightForWidth(urlFileLineEdit.sizePolicy().hasHeightForWidth());
        urlFileLineEdit.setSizePolicy(sizePolicy2);
        
        chooseUrlFileBtn = new QPushButton(this);
        
        saveLabel = new QLabel(this);
        
        saveFileLineEdit = new QLineEdit(this);
        
        saveFileBtn = new QPushButton(this);
        
        crawlInfoLabel = new QLabel(this);
        //----------------------------------------fan----------------------------------------
        timeButton = new QRadioButton(this);
        timeEdit = new QLineEdit(this);
        timeEdit.setEnabled(false);
        
        crawlInfoTextBrowser = new QTextBrowser(this);
        crawlInfoTextBrowser.setReadOnly(true);
        QSizePolicy sizePolicy3 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Preferred, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy3.setHorizontalStretch((byte)0);
        sizePolicy3.setVerticalStretch((byte)0);
        sizePolicy3.setHeightForWidth(crawlInfoTextBrowser.sizePolicy().hasHeightForWidth());
        crawlInfoTextBrowser.setSizePolicy(sizePolicy3);
        crawlInfoTextBrowser.setVerticalScrollBarPolicy(com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAsNeeded);
        
        horizontalSpacer = new QSpacerItem(218, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);
       
        startBtn = new QPushButton(this);
        
        stopBtn = new QPushButton(this);
        
        encodingLabel = new QLabel(this);
        
        encodingComboBox = new QComboBox(this);
        encodingComboBox.addItem("��������(GB18030)");
        encodingComboBox.addItem("Unicode(UTF-8)");
        encodingComboBox.addItem("����(ISO-8859-1)");
        encodingComboBox.addItem("��������(GBK)");
        encodingComboBox.addItem("��������(GB2312)");
        
        crawlRightLayout.addWidget(urlLabel, 0, 0, 1, 1);
        crawlRightLayout.addWidget(urlFileLineEdit, 0, 1, 1, 2);
        crawlRightLayout.addWidget(chooseUrlFileBtn, 0, 3, 1, 1);
        crawlRightLayout.addWidget(saveLabel, 1, 0, 1, 1);
        crawlRightLayout.addWidget(saveFileLineEdit, 1, 1, 1, 2);
        crawlRightLayout.addWidget(saveFileBtn, 1, 3, 1, 1);
        
        crawlRightLayout.addWidget(encodingLabel, 2, 0, 1, 1);
        crawlRightLayout.addWidget(encodingComboBox, 2, 1, 1, 2);
        //----------------------------------------fan----------------------------------------
        crawlRightLayout.addWidget(timeButton, 3, 0, 1, 1);
        crawlRightLayout.addWidget(timeEdit, 3, 1, 1, 1);
        
        crawlRightLayout.addWidget(crawlInfoLabel, 4, 0, 1, 1);
        crawlRightLayout.addWidget(crawlInfoTextBrowser, 5, 0, 1, 4);
        crawlRightLayout.addItem(horizontalSpacer, 6, 1, 1, 1);
        crawlRightLayout.addWidget(startBtn, 6, 2, 1, 1);
        crawlRightLayout.addWidget(stopBtn, 6, 3, 1, 1);

        crawlMainlLayout.addLayout(crawlRightLayout);
        crawlMainlLayout.setStretchFactor(crawlLeftLayout, 2);
        crawlMainlLayout.setStretchFactor(crawlRightLayout, 5);
        
        //----------------------------------------fan----------------------------------------
        timeButton.clicked.connect(this,"editTime()");
        refreshBtn.clicked.connect(this,"refreshJsList()");
        
        chooseUrlFileBtn.clicked.connect(this,"chooseUrlFileBtnWork()");
        saveFileBtn.clicked.connect(this,"chooseSaveFileBtnWork()");
        startBtn.clicked.connect(this,"startBtnWork()");
        stopBtn.clicked.connect(this,"stopBtnWork()");
        
        initialJSListView();
        
        chooseJSLabel.setText("ѡ��ҳ�ű���");
        urlLabel.setText("Url���ļ���");
        chooseUrlFileBtn.setText("ѡ��...");
        saveLabel.setText("�����ļ�����");
        saveFileBtn.setText("���...");
        crawlInfoLabel.setText("ץȡ��Ϣ��");
        startBtn.setText("��ʼ");
        stopBtn.setText("����");
        //----------------------------------------fan----------------------------------------
        timeButton.setText("ץȡʱ������");
        timeEdit.setText("20121008");
        timeEdit.setFixedWidth(100);
        
        encodingLabel.setText("Encoding��");
        
        loadSettings();
	}
	/**
	* ѡ��Url���Ӽ��ļ���
	*/ 
	public void chooseUrlFileBtnWork() {
		String urlSet = QFileDialog.getOpenFileName(this, "Choose Url Files",
				urlFileLineEdit.text(),new QFileDialog.Filter("*.txt"));
		urlFileLineEdit.setText(urlSet);
	}
	/**
	* ѡ�񱣴��ַ��
	*/ 
	public void chooseSaveFileBtnWork() {
		String savePath=QFileDialog.getExistingDirectory(this, "Choose Save Directory",
				saveFileLineEdit.text());
		saveFileLineEdit.setText(savePath);
	}
	/**
	* �ۣ������ʼ��ť��
	*/ 
	public void startBtnWork() {
		if(urlFileLineEdit.text().isEmpty())
		{
			//----------------------------------------fan----------------------------------------
			browser = new BrowserMainWindow(new ArrayList<String>(),"","NoTime");
			
		}else
		{
			if(jSListView.currentIndex() == null)
			{
				//----------------------------------------fan----------------------------------------
				browser = new BrowserMainWindow(FileHelp.getURLs(urlFileLineEdit.text()), 
						saveFileLineEdit.text(),"NoTime");
				
			}else
			{//Ӧ���savefileĿ¼�Ƿ�գ����գ���ʾѡ��,return��
				if(saveFileLineEdit.text().isEmpty())
				{
					QMessageBox.information(this,"Crawler","��ѡ��洢Ŀ¼");
					saveFileLineEdit.setFocus();
					return;
				}else
				{//���գ�����Ƿ���ڣ��������ڣ��������������ˣ���ʾĿ¼���Ϸ�,return;
					QDir dir = new QDir(saveFileLineEdit.text());
					if(!dir.exists())
					{
						QMessageBox.StandardButtons buttons = new QMessageBox.StandardButtons();
						buttons.set(StandardButton.Ok);
						buttons.set(StandardButton.No);
						StandardButton clickedBtn = QMessageBox.question(this,
								"Crawler", "Ŀ¼�����ڣ�Ҫ����Ŀ¼��", buttons,
								StandardButton.Ok);
						if(clickedBtn == StandardButton.Ok)
						{
							Boolean mkdir = dir.mkpath(saveFileLineEdit.text());
							if(mkdir==false)
							{
								
								QMessageBox.critical(this,"Crawler","Ŀ¼����ʧ�ܣ�����Ŀ¼�Ƿ�Ϸ�");
								saveFileLineEdit.setFocus();
								saveFileLineEdit.selectAll();
								return;
							}
						}else
						{
							saveFileLineEdit.setFocus();
							return;
						}
					}
				}
				
				//----------------------------------------fan----------------------------------------
				if (timeButton.isEnabled())
	                deadtime=timeEdit.text();
	            else
	                deadtime="NoTime";
				
				System.out.println("deadtime"+deadtime);
				browser = new BrowserMainWindow(FileHelp.getURLs(urlFileLineEdit.text()), 
						saveFileLineEdit.text(), jSListView.currentItem().text(), deadtime, crawlInfoTextBrowser);
				
			}
		}
		String encodingText = encodingComboBox.currentText();
		browser.setEncoding(encodingText.substring(encodingText.indexOf("(")+1, encodingText.indexOf(")")));
//		System.out.println(browser.getEncoding());
		browser.setCrawlInfo(crawlInfoTextBrowser);
		browser.show();
	}
	/**
	* �ۣ����������ť��
	*/ 
	public void stopBtnWork() {
		try{
			if(browser!=null)
				browser.close();
		}catch(QNoNativeResourcesException e)
		{
//			System.out.println("already closed.");
		}
	}
	/**
	* �ۣ����ˢ�°�ť��
	*/ 
	public void refreshJsList() {
		jSListView.clear();
	    initialJSListView();
	}
	/**
	* ��������ű���ʾ���档
	*/ 
	public void initialJSListView() {
        QDir dir = QDir.current();
        List<String> filters = new ArrayList<String>();
        filters.add("*.js");
        dir.setNameFilters(filters);
        dir.setFilter(Filter.Files);
        jSListView.addItems(dir.entryList());
	}
	/**
	* ������ҳʱ�����ø�����Ϣ�Ƿ���ʾ��
	*/
	private boolean loadSettings() {
    	QSettings setSettings = new QSettings(BrowserApplication.dataFilePath("Appsettings.ini"), Format.IniFormat);
	
    	setSettings.beginGroup("FileManager");
    	saveFileLineEdit.setText(setSettings.value("saveDirectory","").toString());
    	urlFileLineEdit.setText(setSettings.value("urlFileDirectory","").toString());
    	setSettings.endGroup();
    	return true;
	}
	/**
	*/
	public boolean writeSettings() {
    	QSettings setSettings = new QSettings(BrowserApplication.dataFilePath("Appsettings.ini"), Format.IniFormat);
	
    	setSettings.beginGroup("FileManager");
    	setSettings.setValue("saveDirectory", saveFileLineEdit.text());
    	setSettings.setValue("urlFileDirectory", urlFileLineEdit.text());
    	setSettings.endGroup();
    	return true;
	}
	/**
	* �ۣ����ѡ������ʽץȡ��ֹʱ�䡣
	*/
	public void editTime()
	{
	    timeEdit.setEnabled(true);
	    timeEdit.setFocus();
	}
}
