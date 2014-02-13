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
* JsCreatePage�࣬ʵ�ֶ����ɽű��Ľ��档
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

	private QGroupBox groupBox = new QGroupBox("��ҳ������ʽ");
	private QVBoxLayout verticalLayout = new QVBoxLayout();
	private QRadioButton hasNextRadio = new QRadioButton("�з�ҳ��־");
	private QRadioButton noNextRadio = new QRadioButton("�޷�ҳ��־");

	private QGroupBox groupBox_2 = new QGroupBox("ģʽ");
	private QVBoxLayout verticalLayout_2 = new QVBoxLayout();
	private QRadioButton partRadio = new QRadioButton("�ֲ�ˢ��");
	private QRadioButton wholeRadio = new QRadioButton("ȫ��ˢ��");

	private QGroupBox groupBox_3 = new QGroupBox("��ҳ��ȡ����");
	private QGridLayout gridLayout = new QGridLayout();
	private QLineEdit currentPageLineEdit = new QLineEdit();
	private QLabel label = new QLabel("��ҳ��ǩ");
	private QLabel label_3 = new QLabel("��ǰҳ��ǩ");
	private QLabel label_4 = new QLabel("����ҳ��ǩ");
	private QLineEdit nextPageLineEdit = new QLineEdit();
	private QLineEdit otherPageLineEdit = new QLineEdit();
	private QPushButton setCurrentBtn = new QPushButton("����");
	private QPushButton setNextBtn = new QPushButton("����");
	private QPushButton setOtherBtn = new QPushButton("����");

	private QGroupBox groupBox_4 = new QGroupBox("����");
	private QVBoxLayout verticalLayout_4 = new QVBoxLayout();
	private QHBoxLayout tempLayout_1 = new QHBoxLayout();
	private QHBoxLayout tempLayout_2 = new QHBoxLayout();
	private QCheckBox delayCheckBox = new QCheckBox("����ʱ��");
	private QLineEdit delayTime = new QLineEdit("2000");
	private QLabel label_7 = new QLabel("ms");
	private QLabel label_8 = new QLabel("��");
	private QCheckBox scrollCheckBox = new QCheckBox("�Զ�����");
	private QSpinBox scrollNum = new QSpinBox();

	private QVBoxLayout horizontalLayout_5 = new QVBoxLayout();
	private QLabel label_5 = new QLabel("����ļ���");
	private QPlainTextEdit plainTextEdit = new QPlainTextEdit();

	private QVBoxLayout northwest_Layout = new QVBoxLayout();

	private QHBoxLayout north_Layout = new QHBoxLayout();

	private QHBoxLayout mid_Layout = new QHBoxLayout();
	private QLabel label_2 = new QLabel("����ҳ��");
	private QLineEdit urlLineEdit = new QLineEdit();
	private QToolButton selectToolBtn = new QToolButton();
    
	private QPushButton generateBtn = new QPushButton("����");
	private QPushButton cancelBtn = new QPushButton("ȡ��");

	private QVBoxLayout central_Layout = new QVBoxLayout();
	private MyWebView webView = new MyWebView(this);
	/**
	* ���캯������ʼ�����ɽű��Ľ��档
	* @param parent
	* byVal ������
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

		//����ļ�
		horizontalLayout_5.addWidget(label_5);
		horizontalLayout_5.addWidget(plainTextEdit);

		//��ҳ������ʽ �� ģʽ ��������
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
		
		setWindowTitle("���ɽű�");
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
	* ������ҳʱ�����ø�����Ϣ�Ƿ���ʾ��
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
		// ʹ��cache
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
	* ����ҳ��
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
	* ���ؽ��ȡ�
	*/
	public void loadProgress(int p) {
		progress = p;
	}
	/**
	* ������ϡ�
	*/
	public void finishLoading(boolean temp) {
		progress = 100;
		adjustTitle();
	}
	/**
	* �ۣ��л�Url��
	*/
	public void urlChanged(QUrl url) {
		urlLineEdit.setText(url.toString());
	}
	/**
	* ������������
	*/
	public void adjustTitle() {
		if (progress <= 0 || progress >= 100)
			setWindowTitle(webView.title());
		else
			setWindowTitle("" + webView.title() + " (" + progress + ")");
	}
	/**
	 * ������һҳ��ǩ��
	*/
	public void setNextTag() {
		nextTag = temp;
		String ba = printTag(temp);
		nextPageLineEdit.setText(ba);
	}
	/**
	 * ���õ�ǰҳ��ǩ��
	*/
	public void setCurrentTag() {
		currentTag = temp;
		String ba = printTag(temp);
		currentPageLineEdit.setText(ba);
	}
	/**
	 * ��������ҳ��ǩ��
	*/
	public void setOtherTag() {
		otherTag = temp;
		String ba = printTag(temp);
		otherPageLineEdit.setText(ba);
	}
	/**
	 * ����ǩתΪString��
	 * @param element
	 * byVal ��ǩ
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
	 * �ۣ������ǩ��ť��
	 * @param checked
	 * byVal ������
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
	 * �ۣ�������һҳ��ǩ��
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
	 * �ۣ��Ƿ������õ����һҳ���ӳ�ʱ�䡣
	 * @param checked
	 * byVal 
	*/
	public void setDelayTime(boolean checked) {
		delayTime.setEnabled(checked);
	}
	/**
	 * �ۣ��Ƿ������ù���������
	 * @param checked
	 * byVal �������
	*/
	public void setScrollNum(boolean checked) {
		scrollNum.setEnabled(checked);
	}
	/**
	 * ���ɽű���
	*/
	public void generateJs() {
		Filter filter = new Filter("*.js");
		String file = QFileDialog.getSaveFileName(this, "����Ϊ", "untitled.js",filter);
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
			// Ĭ����ʱ2000ms
			gen.setDelayTime(2000);

		if (scrollCheckBox.isChecked())
			gen.setScrollNum(Integer.parseInt(scrollNum.text(), 10));
		else
			// Ĭ�Ϲ���1��
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
	 * �¼����ˡ�
	 * @param obj
	 * byVal ����
	 * @param event
	 * byVal �¼�
	*/
	public boolean eventFilter(QObject obj, QEvent event) {
		if (event.type() == QEvent.Type.MouseMove) {
			QMouseEvent me = (QMouseEvent) event;
			QWebHitTestResult r = webView.page().mainFrame().hitTestContent(
					me.pos());
			if (prev == r.element() || prev == r.linkElement()) {
				// ���ػ�
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
