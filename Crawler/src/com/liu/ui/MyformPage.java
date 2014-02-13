package com.liu.ui;

import com.liu.core.BrowserApplication;
import com.liu.core.httpGet;
//import com.trolltech.qt.QSignalEmitter.Signal0;
import com.trolltech.qt.core.*;
import com.trolltech.qt.core.QSettings.*;
import com.trolltech.qt.gui.*;

/**
 * MDialog类，实现对生成脚本的界面。
 * 
 */
public class MyformPage extends QWidget {

	private QHBoxLayout midLayout_1 = new QHBoxLayout();
	private QHBoxLayout midLayout_2 = new QHBoxLayout();
	private QGridLayout MyformMainLayout = new QGridLayout();
	private QHBoxLayout buttonLayout = new QHBoxLayout();

	private QLabel addressLable = new QLabel("提取地址");
	private QLineEdit addressEdit = new QLineEdit(this);
	private QLabel saveFileLabel = new QLabel("保存文件");
	private QLineEdit saveFileEdit = new QLineEdit(this);
	private QPushButton scanBtn = new QPushButton("浏览");
	private QRadioButton wholePageBtn = new QRadioButton("整页抓取");
	private QRadioButton selRBtn = new QRadioButton("定量抓取");
	private QSpinBox scrollNum = new QSpinBox(this);
	public QLabel status = new QLabel();
	private QPushButton submitBtn = new QPushButton("提取");
	private QPushButton clearBtn = new QPushButton("清除");

	public QTextBrowser area = new QTextBrowser(this);
	private httpGet getter = new httpGet();
	private MDialog dial_filter = new MDialog(1, getter);
	private MDialog dial_conall = new MDialog(2, getter);
	private MDialog dial_conor = new MDialog(3, getter);
	private MDialog dial_priority = new MDialog(4, getter);
	private QPushButton filter_Btn = new QPushButton("添加不包含串");
	private QPushButton conall_Btn = new QPushButton("添加必包含串");
	private QPushButton conor_Btn = new QPushButton("添加可包含串");
	private QPushButton priority_Btn = new QPushButton("添加可展开串");

	private QSpacerItem spacer1 = new QSpacerItem(190, 20);
	private QSpacerItem spacer2 = new QSpacerItem(150, 20);
	private QSpacerItem spacer3 = new QSpacerItem(150, 20);
	
	/**
	 * 构造函数，初始化链接抓取管理窗口。
	 * 
	 * @param parent
	 *            byVal 父窗口
	 */
	public MyformPage(QWidget parent) {
		wholePageBtn.setChecked(true);
		scrollNum.setEnabled(false);
		scrollNum.setFixedWidth(90);
		scrollNum.setMaximum(1000000000);
		scrollNum.setSingleStep(100);
		scanBtn.setFixedWidth(75);
		area.setReadOnly(true);
		filter_Btn.setFixedWidth(100);
		conall_Btn.setFixedWidth(100);
		conor_Btn.setFixedWidth(100);
		priority_Btn.setFixedWidth(100);
		addressLable.setFixedWidth(100);
		saveFileLabel.setFixedWidth(100);
		submitBtn.setFixedWidth(100);
		clearBtn.setFixedWidth(100);

		midLayout_1.addWidget(wholePageBtn);
		midLayout_1.addItem(spacer1);
		midLayout_1.addWidget(filter_Btn);
		midLayout_1.addWidget(conall_Btn);

		midLayout_2.addWidget(selRBtn);
		midLayout_2.addWidget(scrollNum);
		midLayout_2.addItem(spacer2);
		midLayout_2.addWidget(conor_Btn);
		midLayout_2.addWidget(priority_Btn);

		buttonLayout.addWidget(status);
		buttonLayout.addItem(spacer3);
		buttonLayout.addWidget(submitBtn);
		buttonLayout.addWidget(clearBtn);

		MyformMainLayout.addWidget(addressLable, 0, 0, 1, 1);
		MyformMainLayout.addWidget(addressEdit, 0, 1, 1, 3);
		MyformMainLayout.addWidget(saveFileLabel, 1, 0, 1, 1);
		MyformMainLayout.addWidget(saveFileEdit, 1, 1, 1, 3);
		MyformMainLayout.addWidget(scanBtn, 1, 4, 1, 1);

		MyformMainLayout.addLayout(midLayout_1, 2, 1, 1, 3);
		MyformMainLayout.addLayout(midLayout_2, 3, 1, 1, 3);
		MyformMainLayout.addWidget(area, 4, 1, 2, 3);
		MyformMainLayout.addLayout(buttonLayout, 6, 1, 1, 3);
		MyformMainLayout.setMargin(20);

		this.setLayout(MyformMainLayout);

		scanBtn.clicked.connect(this, "scanBtnWork()");
		wholePageBtn.clicked.connect(this, "wholePageBtnWork()");
		selRBtn.clicked.connect(this, "selRBtnWork()");
		submitBtn.clicked.connect(this, "startProcess()");
		clearBtn.clicked.connect(this, "clearBtnWork()");
		filter_Btn.clicked.connect(this, "acquireDialog_F()");
		conall_Btn.clicked.connect(this, "acquireDialog_CA()");
		conor_Btn.clicked.connect(this, "acquireDialog_CO()");
		priority_Btn.clicked.connect(this, "acquireDialog_Pri()");
		
		getter.finished.connect(this, "finishedSlot()");
		getter.newStr.connect(this, "appendNewStr(String)");
		
		loadSettings();
	}
	
	public void appendNewStr(String str) {
		area.append(str);
		status.setText(""+getter.getNum()+" urls have been catched.");
	}
	
	public void finishedSlot() {
		area.append("\nFinished.");
	}
	
	/**
	 * 槽，设置保存地址。
	 */
	public void scanBtnWork() {
		String savePath = QFileDialog.getSaveFileName(this, "保存为", "Catch.txt");
		saveFileEdit.setText(savePath);
	}

	/**
	 * 槽，设置是否整页抓取地址。
	 */
	public void wholePageBtnWork() {
		scrollNum.setEnabled(false);
	}

	/**
	 * 槽，开始抓取。
	 * @throws InterruptedException 
	 */
	public void startProcess() {
		getter.setSaveDir(saveFileEdit.text());
		if (scrollNum.isEnabled())
			{getter.setNumber(Integer.parseInt(scrollNum.text(), 10));
		System.out.println(scrollNum.text());}
		else
			getter.setNumber(Integer.parseInt("0", 10));
		
		if (addressEdit.text().startsWith("http://") || addressEdit.text().startsWith("https://"))
			getter.setStartUrl(addressEdit.text());
		else
			getter.setStartUrl("http://"+addressEdit.text());
		
		Thread thread = new Thread(getter);
		thread.start();
	}

	/**
	 * 显示可展开串管理窗口。
	 */
	public void acquireDialog_Pri() {
		dial_priority.show();
	}

	/**
	 * 显示不包含串管理窗口。
	 */
	public void acquireDialog_F() {
		dial_filter.show();
	}

	/**
	 * 显示必包含串管理窗口。
	 */
	public void acquireDialog_CA() {
		dial_conall.show();
	}

	/**
	 * 显示可包含串管理窗口。
	 */
	public void acquireDialog_CO() {
		dial_conor.show();
	}

	/**
	 * 槽，设置抓取链接数。
	 */
	public void selRBtnWork() {
		scrollNum.setEnabled(true);
		scrollNum.setFocus();
		
	}

	/**
	 * 槽，清楚显示栏。
	 */
	public void clearBtnWork() {
		area.clear();
		status.clear();
	}

	/**
	*/
	public boolean loadSettings() {
		QSettings setSettings = new QSettings(BrowserApplication
				.dataFilePath("Appsettings.ini"), Format.IniFormat);
		setSettings.beginGroup("FileManager");
		saveFileEdit.setText(setSettings.value("saveDirectory", "").toString());
		addressEdit.setText(setSettings.value("address", "").toString());
		String temp = setSettings.value("NumLink", "").toString();
		if (temp.compareTo("") == 0)
			temp = "0";
		scrollNum.setValue(Integer.parseInt(temp, 10));
		setSettings.endGroup();
		return true;
	}

	/**
	*/
	public boolean writeSettings() {
		QSettings setSettings = new QSettings(BrowserApplication
				.dataFilePath("Appsettings.ini"), Format.IniFormat);

		setSettings.beginGroup("FileManager");
		setSettings.setValue("saveDirectory", saveFileEdit.text());
		setSettings.setValue("address", addressEdit.text());
		setSettings.setValue("NumLink", scrollNum.value());
		
		setSettings.endGroup();
		return true;
	}
}
