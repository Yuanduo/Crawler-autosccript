package com.liu.ui;

import com.liu.core.*;
import com.liu.core.JsGenerator.*;
import com.trolltech.qt.core.*;
import com.trolltech.qt.core.QSettings.*;
import com.trolltech.qt.core.Qt.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QDesktopServices.*;
import com.trolltech.qt.gui.QFileDialog.Filter;
import com.trolltech.qt.network.*;
import com.trolltech.qt.webkit.*;
import com.trolltech.qt.webkit.QWebSettings.*;
/**
* JsCreatePage类，实现对生成脚本的界面。
* 
*/
public class JsCreatePage extends QWidget {
	private MyCookieJar cookiejar = new MyCookieJar();
	private int progress = 0;
	private QWebElement prev;
	private QWebElement temp;
	private QWebElement nextTag;
	@SuppressWarnings("unused")
	private QWebElement currentTag;
	@SuppressWarnings("unused")
	private QWebElement otherTag;

	private QGroupBox groupBox = new QGroupBox("分页链接样式");
	private QVBoxLayout verticalLayout = new QVBoxLayout();
	private QRadioButton hasNextRadio = new QRadioButton("有翻页标志");
	private QRadioButton noNextRadio = new QRadioButton("无翻页标志");

	private QGroupBox groupBox_2 = new QGroupBox("模式");
	private QVBoxLayout verticalLayout_2 = new QVBoxLayout();
	private QRadioButton partRadio = new QRadioButton("局部刷新");
	private QRadioButton wholeRadio = new QRadioButton("全局刷新");

	private QGroupBox groupBox_3 = new QGroupBox("分页获取规则");
	private QGridLayout gridLayout = new QGridLayout();
	private QLineEdit currentPageLineEdit = new QLineEdit();
	private QLabel label = new QLabel("翻页标签");
	private QLabel label_3 = new QLabel("当前页标签");
	private QLabel label_4 = new QLabel("其他页标签");
	private QLineEdit nextPageLineEdit = new QLineEdit();
	private QLineEdit otherPageLineEdit = new QLineEdit();
	private QPushButton setCurrentBtn = new QPushButton("设置");
	private QPushButton setNextBtn = new QPushButton("设置");
	private QPushButton setOtherBtn = new QPushButton("设置");

	private QGroupBox groupBox_4 = new QGroupBox("参数");
	private QVBoxLayout verticalLayout_4 = new QVBoxLayout();
	private QHBoxLayout tempLayout_1 = new QHBoxLayout();
	private QHBoxLayout tempLayout_2 = new QHBoxLayout();
	private QCheckBox delayCheckBox = new QCheckBox("设置时延");
	private QLineEdit delayTime = new QLineEdit("2000");
	private QLabel label_7 = new QLabel("ms");
	private QLabel label_8 = new QLabel("次");
	private QCheckBox scrollCheckBox = new QCheckBox("自动滚屏");
	private QSpinBox scrollNum = new QSpinBox();

	private QVBoxLayout horizontalLayout_5 = new QVBoxLayout();
	private QLabel label_5 = new QLabel("输出文件：");
	private QPlainTextEdit plainTextEdit = new QPlainTextEdit();

	private QVBoxLayout northwest_Layout = new QVBoxLayout();

	private QHBoxLayout north_Layout = new QHBoxLayout();

	private QHBoxLayout mid_Layout = new QHBoxLayout();
	private QLabel label_2 = new QLabel("典型页面");
	private QLineEdit urlLineEdit = new QLineEdit();
	private QToolButton selectToolBtn = new QToolButton();
    
	private QPushButton generateBtn = new QPushButton("生成");
	private QPushButton cancelBtn = new QPushButton("取消");

	private QVBoxLayout central_Layout = new QVBoxLayout();
	private MyWebView webView = new MyWebView(this);
	/**
	* 构造函数，初始化生成脚本的界面。
	* @param parent
	* byVal 父窗口
	*/
	public JsCreatePage(QWidget parent) {
		verticalLayout.addWidget(hasNextRadio);
		verticalLayout.addWidget(noNextRadio);
		groupBox.setLayout(verticalLayout);

		verticalLayout_2.addWidget(partRadio);
		verticalLayout_2.addWidget(wholeRadio);
		groupBox_2.setLayout(verticalLayout_2);

		nextPageLineEdit.setFixedWidth(240);
		currentPageLineEdit.setFixedWidth(240);
		otherPageLineEdit.setFixedWidth(240);
		gridLayout.addWidget(label, 0, 0, 1, 1);
		gridLayout.addWidget(nextPageLineEdit, 1, 0, 1, 4);
		gridLayout.addWidget(setNextBtn, 1, 4, 1, 1);
		gridLayout.addWidget(label_3, 2, 0, 1, 1);
		gridLayout.addWidget(currentPageLineEdit, 3, 0, 1, 4);
		gridLayout.addWidget(setCurrentBtn, 3, 4, 1, 1);
		gridLayout.addWidget(label_4, 4, 0, 1, 1);
		gridLayout.addWidget(otherPageLineEdit, 5, 0, 1, 4);
		gridLayout.addWidget(setOtherBtn, 5, 4, 1, 1);
		groupBox_3.setLayout(gridLayout);

		delayTime.setFixedWidth(60);
		tempLayout_1.addWidget(delayTime);
		tempLayout_1.addWidget(label_7);
		scrollNum.setFixedWidth(60);
		scrollNum.setMaximum(10);
		scrollNum.setMinimum(1);
		tempLayout_2.addWidget(scrollNum);
		tempLayout_2.addWidget(label_8);
		verticalLayout_4.addWidget(delayCheckBox);
		verticalLayout_4.addLayout(tempLayout_1);
		verticalLayout_4.addWidget(scrollCheckBox);
		verticalLayout_4.addLayout(tempLayout_2);
		groupBox_4.setLayout(verticalLayout_4);
		groupBox_4.setFixedWidth(150);

		//输出文件
		horizontalLayout_5.addWidget(label_5);
		horizontalLayout_5.addWidget(plainTextEdit);

		//分页链接样式 和 模式 竖着排列
		northwest_Layout.addWidget(groupBox);
		northwest_Layout.addWidget(groupBox_2);

		north_Layout.addLayout(northwest_Layout);
		north_Layout.addWidget(groupBox_3);
		north_Layout.addWidget(groupBox_4);
		north_Layout.addLayout(horizontalLayout_5);
		
		selectToolBtn.setIcon(new QIcon("./data/select_32.png"));
		selectToolBtn.setCheckable(true);
	    selectToolBtn.setAutoRaise(false);
		mid_Layout.addWidget(label_2);
		mid_Layout.addWidget(urlLineEdit);
		mid_Layout.addWidget(selectToolBtn);
		
		mid_Layout.addWidget(generateBtn);
		mid_Layout.addWidget(cancelBtn);
		
		central_Layout.addLayout(north_Layout);
		central_Layout.addLayout(mid_Layout);
		central_Layout.addWidget(webView);
		this.setLayout(central_Layout);

		hasNextRadio.setChecked(true);
		switchPagination();
		partRadio.setChecked(true);
		delayCheckBox.setChecked(true);
		scrollCheckBox.setChecked(true);
		
		setWindowTitle("生成脚本");
		setAttribute(WidgetAttribute.WA_DeleteOnClose);
		
		urlLineEdit.returnPressed.connect(this, "open()");
		webView.loadStarted.connect(this, "loadStarted()");
		webView.loadProgress.connect(this, "loadProgress(int)");
		webView.loadFinished.connect(this, "finishLoading(boolean)");
		webView.urlChanged.connect(this, "urlChanged(QUrl)");
		webView.titleChanged.connect(this, "adjustTitle()");
		generateBtn.clicked.connect(this, "generateJs()");
		setNextBtn.clicked.connect(this, "setNextTag()");
		setCurrentBtn.clicked.connect(this, "setCurrentTag()");
		setOtherBtn.clicked.connect(this, "setOtherTag()");
		
		selectToolBtn.toggled.connect(this, "switchSelection(boolean)");
		
		hasNextRadio.toggled.connect(this, "switchPagination()");
		noNextRadio.toggled.connect(this, "switchPagination()");
		delayCheckBox.toggled.connect(this, "setDelayTime(boolean)");
		scrollCheckBox.toggled.connect(this, "setScrollNum(boolean)");

		webView.page().networkAccessManager().setCookieJar(cookiejar);
		this.setWindowIcon(new QIcon("./data/spider-black.png"));
		loadSettings();
	}
	/**
	* 加载网页时，设置各类信息是否显示。
	*/
	public void loadSettings() {
		QSettings settings = new QSettings(QDesktopServices
				.storageLocation(StandardLocation.DataLocation)
				+ "/settings.ini", Format.IniFormat);
		QWebSettings webSettings = webView.settings();
		settings.beginGroup("WebSettings");
		settings.value("AutoLoadImages", false);
		webSettings.setAttribute(WebAttribute.AutoLoadImages, false);
		settings.value("AutoLoadImages", false);
		webSettings.setAttribute(WebAttribute.PrintElementBackgrounds, false);
		settings.value("JavascriptEnabled", true);
		webSettings.setAttribute(WebAttribute.JavascriptEnabled, true);
		settings.value("blockPopupWindows", true);
		webSettings.setAttribute(WebAttribute.JavascriptCanOpenWindows, !true);
		settings.value("PluginsEnabled", true);
		webSettings.setAttribute(WebAttribute.PluginsEnabled, true);
		settings.endGroup();
		// 使用cache
		QNetworkDiskCache diskCache = new QNetworkDiskCache(this);
		String location = StandardLocation.CacheLocation.toString();
		diskCache.setCacheDirectory(location);
		settings.beginGroup("Cache");
		settings.value("maxCache", 50);
		diskCache.setMaximumCacheSize(50);
		webView.page().networkAccessManager().setCache(diskCache);
		settings.endGroup();

	}
	/**
	* 打开网页。
	*/
	public void open() {
		String text = urlLineEdit.text();
		if (text.indexOf("://") < 0)
			text = "http://" + text;

		webView.load(new QUrl(text));
		webView.setFocus();
	}
	/**
	*/
	public void loadStarted() {

	}
	/**
	* 加载进度。
	*/
	public void loadProgress(int p) {
		progress = p;
	}
	/**
	* 加载完毕。
	*/
	public void finishLoading(boolean temp) {
		progress = 100;
		adjustTitle();
	}
	/**
	* 槽，切换Url。
	*/
	public void urlChanged(QUrl url) {
		urlLineEdit.setText(url.toString());
	}
	/**
	* 调整标题栏。
	*/
	public void adjustTitle() {
		if (progress <= 0 || progress >= 100)
			setWindowTitle(webView.title());
		else
			setWindowTitle("" + webView.title() + " (" + progress + ")");
	}
	/**
	 * 设置下一页标签。
	*/
	public void setNextTag() {
		nextTag = temp;
		String ba = printTag(temp);
		nextPageLineEdit.setText(ba);
	}
	/**
	 * 设置当前页标签。
	*/
	public void setCurrentTag() {
		currentTag = temp;
		String ba = printTag(temp);
		currentPageLineEdit.setText(ba);
	}
	/**
	 * 设置其它页标签。
	*/
	public void setOtherTag() {
		otherTag = temp;
		String ba = printTag(temp);
		otherPageLineEdit.setText(ba);
	}
	/**
	 * 将标签转为String。
	 * @param element
	 * byVal 标签
	*/
	public String printTag(QWebElement element) {
		String ba;
		ba = "<" + element.tagName();
		for (String attr : element.attributeNames()) {
			ba += " " + attr + "=\"" + element.attribute(attr) + "\"";
		}
		ba += ">";
		return ba;
	}
	/**
	 * 槽，点击标签按钮。
	 * @param checked
	 * byVal 点击与否
	*/
	public void switchSelection(boolean checked) {
		if(checked)
	    {
	        webView.installEventFilter(this);
	        webView.page().installEventFilter(this);
	        webView.page().mainFrame().installEventFilter(this);
	        webView.setMouseTracking(true);
	    }else {
	        webView.removeEventFilter(this);
	        webView.page().removeEventFilter(this);
	        webView.page().mainFrame().removeEventFilter(this);
	        webView.setMouseTracking(false);
	    }
	}
	/**
	 * 槽，有无下一页标签。
	*/
	public void switchPagination() {
		if (hasNextRadio.isChecked()) {
			currentPageLineEdit.setEnabled(false);
			otherPageLineEdit.setEnabled(false);
			setCurrentBtn.setEnabled(false);
			setOtherBtn.setEnabled(false);
			nextPageLineEdit.setEnabled(true);
			setNextBtn.setEnabled(true);
		} else if (noNextRadio.isChecked()) {
			currentPageLineEdit.setEnabled(true);
			otherPageLineEdit.setEnabled(true);
			setCurrentBtn.setEnabled(true);
			setOtherBtn.setEnabled(true);
			nextPageLineEdit.setEnabled(false);
			setNextBtn.setEnabled(false);
		}
	}
	/**
	 * 槽，是否需设置点击下一页的延迟时间。
	 * @param checked
	 * byVal 
	*/
	public void setDelayTime(boolean checked) {
		delayTime.setEnabled(checked);
	}
	/**
	 * 槽，是否需设置滚屏次数。
	 * @param checked
	 * byVal 滚屏与否
	*/
	public void setScrollNum(boolean checked) {
		scrollNum.setEnabled(checked);
	}
	/**
	 * 生成脚本。
	*/
	public void generateJs() {
		Filter filter = new Filter("*.js");
		String file = QFileDialog.getSaveFileName(this, "保存为", "untitled.js",filter);
		JsGenerator gen = new JsGenerator();
		gen.setAutoScroll(true);

		if (hasNextRadio.isChecked()) {
//			gen.paginationPolicy();
			gen.setPaginationPolicy(PaginationPolicy.HasNextPageTag);
		} else if (noNextRadio.isChecked())
			gen.setPaginationPolicy(PaginationPolicy.NoNextPageTag);

		if (partRadio.isChecked())
			gen.setRefreshPolicy(RefreshPolicy.PatialRefresh);
		else if (wholeRadio.isChecked())
			gen.setRefreshPolicy(RefreshPolicy.RefreshAll);

		if (delayCheckBox.isChecked())
			gen.setDelayTime(Integer.parseInt(delayTime.text(), 10));
		else
			// 默认延时2000ms
			gen.setDelayTime(2000);

		if (scrollCheckBox.isChecked())
			gen.setScrollNum(Integer.parseInt(scrollNum.text(), 10));
		else
			// 默认滚屏1次
			gen.setScrollNum(1);

//		System.out.println("start");
		gen.openFile(file);
		gen.addPaginationStruc(nextTag);
		String array = gen.closeFile();
//		System.out.println(array);
		plainTextEdit.setPlainText(array);
//		System.out.println("end");
	}
	/**
	 * 事件过滤。
	 * @param obj
	 * byVal 对象
	 * @param event
	 * byVal 事件
	*/
	public boolean eventFilter(QObject obj, QEvent event) {
		if (event.type() == QEvent.Type.MouseMove) {
			QMouseEvent me = (QMouseEvent) event;
			QWebHitTestResult r = webView.page().mainFrame().hitTestContent(
					me.pos());
			if (prev == r.element() || prev == r.linkElement()) {
				// 不重绘
				return true;
			}
			if (!r.element().isNull()) {
				QWebElement element = r.element();
				prev = element;
			} else if (!r.linkElement().isNull()) {
				QWebElement element = r.linkElement();
				prev = element;
			}

			int h = webView.page().mainFrame().scrollBarValue(
					Orientation.Horizontal);
			int v = webView.page().mainFrame().scrollBarValue(
					Orientation.Vertical);

			QRect rect = new QRect(r.boundingRect().x() - h, r.boundingRect()
					.y()
					- v, r.boundingRect().width(), r.boundingRect().height());
			webView.setRect(rect);
			webView.update();
			return true;
		} else if (event.type() == QEvent.Type.MouseButtonPress) {
			QMouseEvent me = (QMouseEvent) event;
			QWebHitTestResult r = webView.page().mainFrame().hitTestContent(
					me.pos());
			temp = (r.element().isNull()) ? r.linkElement() : r.element();
			selectToolBtn.setChecked(false);
			return true;
		} else {
			return false;
		}
	}

}
