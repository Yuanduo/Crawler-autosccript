package com.liu.ui;

import java.io.File;
import java.io.IOException;

import com.liu.core.BrowserApplication;
import com.liu.core.MyCookieJar;
import com.mywie.utils.FileHelp;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QSettings;
import com.trolltech.qt.core.QSettings.*;
import com.trolltech.qt.core.Qt.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QMessageBox.Icon;
import com.trolltech.qt.gui.QMessageBox.StandardButton;
//import com.trolltech.qt.gui.QMessageBox.*;
/**
* 配置界面
* 
*/
public class ConfigPage extends QWidget{
	
    public QHBoxLayout configMainLayout = new QHBoxLayout();
    public QGroupBox groupBox = new QGroupBox(this);
    public QVBoxLayout webContentLayout = new QVBoxLayout(groupBox);
    public QCheckBox blockPopupCBox = new QCheckBox(groupBox);
    public QCheckBox enableImagesCBox = new QCheckBox(groupBox);
    public QCheckBox enableJSCBox = new QCheckBox(groupBox);
    public QCheckBox enablePluginCBox = new QCheckBox(groupBox);
    public QVBoxLayout configRightLayout = new QVBoxLayout();
    public QGroupBox cacheGroupBox = new QGroupBox(this);
    public QGridLayout cacheGridLayout = new QGridLayout(cacheGroupBox);
    public QHBoxLayout horizontalLayout_2 = new QHBoxLayout();
    public QLabel label = new QLabel(cacheGroupBox);
    public QLabel cacheLabel = new QLabel(cacheGroupBox);
    public QLabel label_3 = new QLabel(cacheGroupBox);
    public QPushButton clearCacheBtn = new QPushButton(cacheGroupBox);
    public QHBoxLayout horizontalLayout_3 = new QHBoxLayout();
    public QLabel label_2 = new QLabel(cacheGroupBox);
    public QSpinBox maxCacheSpinBox = new QSpinBox(cacheGroupBox);
    public QLabel label_7 = new QLabel(cacheGroupBox);
    public QGroupBox cookieGroupBox = new QGroupBox(this);
    public QVBoxLayout verticalLayout = new QVBoxLayout(cookieGroupBox);
    public QGridLayout gridLayout = new QGridLayout();
    public QLabel label_4 = new QLabel(cookieGroupBox);
    public QComboBox acceptCookieComboBox = new QComboBox(cookieGroupBox);
    public QLabel label_5 = new QLabel(cookieGroupBox);
    public QComboBox saveCookieComboBox = new QComboBox(cookieGroupBox);
    public QHBoxLayout horizontalLayout = new QHBoxLayout();
    public QLabel label_6 = new QLabel(cookieGroupBox);
    public QPushButton clearCookieBtn = new QPushButton(cookieGroupBox);
    public QSpacerItem horizontalSpacer_2 = new QSpacerItem(118, 20, QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum);
    public QHBoxLayout bottomLayout= new QHBoxLayout();
    public QSpacerItem horizontalSpacer_5 = new QSpacerItem(458, 20, QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum);
    public QVBoxLayout verticalLayout_2 = new QVBoxLayout(this);
    public QPushButton saveBtn = new QPushButton(this);
    public QPushButton cancelBtn = new QPushButton(this);
    public QPushButton cookieBtn = new QPushButton("Cookies...");
    
    public ConfigPage() throws IOException{
        configMainLayout.setSpacing(10);
        
        groupBox.setGeometry(new QRect(20, 33, 129, 181));
        
        webContentLayout.setSpacing(10);
        webContentLayout.setMargin(10);
        
        webContentLayout.addWidget(blockPopupCBox);
        webContentLayout.addWidget(enableImagesCBox);
        webContentLayout.addWidget(enableJSCBox);
        webContentLayout.addWidget(enablePluginCBox);

        configMainLayout.addWidget(groupBox);

        cacheGroupBox.setGeometry(new QRect(60, 40, 337, 76));
        cacheGridLayout.setSpacing(10);
        cacheGridLayout.setMargin(10);
        horizontalLayout_2.setSpacing(10);
        horizontalLayout_2.addWidget(label);
        horizontalLayout_2.addWidget(cacheLabel);
        horizontalLayout_2.addWidget(label_3);

        cacheGridLayout.addLayout(horizontalLayout_2, 0, 0, 1, 1);
        cacheGridLayout.addWidget(clearCacheBtn, 0, 1, 1, 1);

        horizontalLayout_3.setSpacing(10);
        horizontalLayout_3.addWidget(label_2);
        maxCacheSpinBox.setRange(0, 1024);
        horizontalLayout_3.addWidget(maxCacheSpinBox);
        label_7.setLayoutDirection(com.trolltech.qt.core.Qt.LayoutDirection.RightToLeft);
        horizontalLayout_3.addWidget(label_7);

        cacheGridLayout.addLayout(horizontalLayout_3, 1, 0, 1, 1);

        configRightLayout.addWidget(cacheGroupBox);

        cookieGroupBox.setGeometry(new QRect(60, 30, 282, 109));
        verticalLayout.setSpacing(10);
        verticalLayout.setMargin(10);
        gridLayout.setSpacing(10);
        gridLayout.addWidget(label_4, 0, 0, 1, 1);

        acceptCookieComboBox.addItem("总是");
        acceptCookieComboBox.addItem("从不");
        acceptCookieComboBox.addItem("只允许从浏览过的站点");

        gridLayout.addWidget(acceptCookieComboBox, 0, 1, 1, 1);
        gridLayout.addWidget(label_5, 1, 0, 1, 1);
        
        cookieBtn.clicked.connect(this, "showCookies()");
        saveCookieComboBox.addItem("它们失效");
        saveCookieComboBox.addItem("退出应用程序");
        saveCookieComboBox.addItem("最多90天");
        
        gridLayout.addWidget(saveCookieComboBox, 1, 1, 1, 1);
        gridLayout.addWidget(cookieBtn,1,2,1,1);
        verticalLayout.addLayout(gridLayout);

        horizontalLayout.setSpacing(10);
        horizontalLayout.setObjectName("horizontalLayout");

        horizontalLayout.addWidget(label_6);

        QFont font = new QFont();
        font.setUnderline(true);
        clearCookieBtn.setFont(font);
        clearCookieBtn.setFlat(true);
        clearCookieBtn.setCursor(new QCursor(CursorShape.PointingHandCursor));

        horizontalLayout.addWidget(clearCookieBtn);
        horizontalLayout.addItem(horizontalSpacer_2);

        verticalLayout.addLayout(horizontalLayout);

        configRightLayout.addWidget(cookieGroupBox);

        configMainLayout.addLayout(configRightLayout);
        configMainLayout.setStretchFactor(groupBox, 2);
        configMainLayout.setStretchFactor(configRightLayout, 5);
        
        bottomLayout.addItem(horizontalSpacer_5);

        bottomLayout.addWidget(saveBtn);
        bottomLayout.addWidget(cancelBtn);
        bottomLayout.setSpacing(10);
        bottomLayout.setMargin(10);

        verticalLayout_2.addLayout(configMainLayout);
        verticalLayout_2.addLayout(bottomLayout);
        
        gridLayout.setColumnStretch(0,2);
        gridLayout.setColumnStretch(1,5);
        
        groupBox.setTitle("Web 内容");
        blockPopupCBox.setText("阻止弹出窗口");
        enableImagesCBox.setText("自动载入图片");
        enableJSCBox.setText("启用JavaScript");
        enablePluginCBox.setText("允许插件");
        cacheGroupBox.setTitle("缓存管理");
        label.setText("您的缓存当前已使用");
        cacheLabel.setText("需要初始化");
        label_3.setText("磁盘空间");
        clearCacheBtn.setText("立即清除");
        label_2.setText("缓存最大为");
        label_7.setText("MB磁盘空间");
        cookieGroupBox.setTitle("Cookies");
        label_4.setText("接收Cookies：");
        label_5.setText("保存Cookies直到：");
        label_6.setText("您可能想");
        clearCookieBtn.setText("删除私人cookie");
        saveBtn.setText("确定");
        cancelBtn.setText("取消");
        
        String path = BrowserApplication.dataFilePath("cache");
        cacheLabel.setText(FileHelp.formatDirSize(FileHelp.getDirSize(path)));
        clearCookieBtn.clicked.connect(this,"deleteCookieFile()");
        clearCacheBtn.clicked.connect(this,"clearcache()");
        cancelBtn.clicked.connect(this,"loadSettings()");
        saveBtn.clicked.connect(this,"writeSettings()");
        loadSettings();
    }
    
    @SuppressWarnings("unused")
	private /*boolean*/void deleteCookieFile() {
    	File file = new File(BrowserApplication.dataFilePath("cookies.ini"));
    	//return file.delete();
    	file.delete();
    	QMessageBox.StandardButtons buttons = new QMessageBox.StandardButtons();
		buttons.set(StandardButton.Ok);
    	new QMessageBox(Icon.NoIcon, "Crawler", "删除成功！", buttons, this).exec();
    }
    
    private boolean loadSettings() {
    	QSettings setSettings = new QSettings(BrowserApplication.dataFilePath("Appsettings.ini"), Format.IniFormat);
    	
    	setSettings.beginGroup("WebSettings");
    	if (setSettings.value("blockPopupWindows",true).toString().equals("true")){
    		blockPopupCBox.setChecked(true);
    	}else{
    		blockPopupCBox.setChecked(false);
    	}
    	if (setSettings.value("AutoLoadImages",false).toString().equals("true")){
    		enableImagesCBox.setChecked(true);
    	}else{
    		enableImagesCBox.setChecked(false);
    	}
    	if (setSettings.value("JavascriptEnabled",true).toString().equals("true")){
    		enableJSCBox.setChecked(true);
    	}else{
    		enableJSCBox.setChecked(false);
    	}
    	if (setSettings.value("PluginsEnabled",false).toString().equals("true")){
    		enablePluginCBox.setChecked(true);
    	}else{
    		enablePluginCBox.setChecked(false);
    	}
    	setSettings.endGroup();
    	
    	setSettings.beginGroup("Cache");
    	maxCacheSpinBox.setValue(Integer.parseInt(setSettings.value("maxCache",50).toString()));
    	setSettings.endGroup();
    	
    	setSettings.beginGroup("Cookies");
    	acceptCookieComboBox.setCurrentIndex(Integer.parseInt(setSettings.value("acceptCookies",0).toString()));
    	saveCookieComboBox.setCurrentIndex(Integer.parseInt(setSettings.value("keepCookiesUntil",0).toString()));
    	setSettings.endGroup();
    	
    	return true;
    }
    
    //保存当前应用程序的设置
    @SuppressWarnings("unused")
	private /*boolean*/void writeSettings() {
    	QSettings setSettings = new QSettings(BrowserApplication.dataFilePath("Appsettings.ini"), Format.IniFormat);
    	
    	setSettings.beginGroup("WebSettings");
	    setSettings.setValue("blockPopupWindows", this.blockPopupCBox.isChecked());
	    setSettings.setValue("AutoLoadImages", this.enableImagesCBox.isChecked());
	    setSettings.setValue("JavascriptEnabled", this.enableJSCBox.isChecked());
	    setSettings.setValue("PluginsEnabled", this.enablePluginCBox.isChecked());
	    setSettings.endGroup();
	    
	    setSettings.beginGroup("Cache");
	    setSettings.setValue("maxCache", this.maxCacheSpinBox.value());
	    setSettings.endGroup();
	    
	    setSettings.beginGroup("Cookies");
    	setSettings.setValue("acceptCookies", this.acceptCookieComboBox.currentIndex());
    	setSettings.setValue("keepCookiesUntil", this.saveCookieComboBox.currentIndex());
    	setSettings.endGroup();
    	
    	QMessageBox.StandardButtons buttons = new QMessageBox.StandardButtons();
		buttons.set(StandardButton.Ok);
    	new QMessageBox(Icon.NoIcon, "Crawler", "保存成功！", buttons, this).exec();
    //	return true;
    }
    
    public void clearcache() throws IOException {
    	String path = BrowserApplication.dataFilePath("cache");
    	FileHelp.deleteFoder(new File(path));
    	cacheLabel.setText("0K");
	}
    
    public void showCookies()
    {
        CookiesDialog dialog = new CookiesDialog(new MyCookieJar(),this);
        dialog.show();
    }
}
