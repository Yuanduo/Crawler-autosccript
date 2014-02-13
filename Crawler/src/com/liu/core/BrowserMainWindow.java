package com.liu.core;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import com.mywie.utils.FileHelp;
import com.mywie.utils.FileHelper;
import com.trolltech.qt.core.*;
import com.trolltech.qt.core.QSettings.Format;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QDesktopServices.StandardLocation;
import com.trolltech.qt.network.QNetworkCookie;
import com.trolltech.qt.network.QNetworkDiskCache;
  
import com.trolltech.qt.webkit.QWebFrame;
import com.trolltech.qt.webkit.QWebPage;
import com.trolltech.qt.webkit.QWebSettings;
import com.trolltech.qt.webkit.QWebView;
import com.trolltech.qt.webkit.QWebSettings.WebAttribute;
import com.weibo.Comment;


/**
 * ������࣬ʵ����������Ļ������ܺ�һ�����״���
 */
public class BrowserMainWindow extends QMainWindow {
	private static final Logger LOGGER = Logger
			.getLogger(BrowserMainWindow.class.getName());
	public QWebView browser;
	private QLineEdit field;
	private MyCookieJar jar;
	private String encoding = "UTF-8";
	/* ҳ����־��ÿ����ҳ����ʱ���� */
	private int pageNum = 0;
	private boolean enableCookies = true;
	/* �洢Ŀ¼ */
	private  String savePath;
	/* �ű��ļ��� */
	private String jsFileName;
	/* ��ҳ���б� */
	private List<String> urlFile;
	/* ��һ�������ʵ���ַ����ҳ���б��е����� */
	private int index = 0;
	/* ��¼ץȡһ����ҳ�Ŀ�ʼʱ�� */
	private Date startTime = null;
	/* ������ҳ�Ƿ������ɵı�־ */
	private boolean done = false;
	// ----------------------------------------fan----------------------------------------
	/* ��������ʽץȡ����ֹʱ�� */
	private String deadtime = "";
	private QTextBrowser crawlInfoTextBrowser;
	private Hashtable<String, Integer> hash;
	private boolean isStatic = false;
	public Timer timer;
	public reloadTimerTask timerTask;
	public int processValue;
	public int reloadCount;
	public boolean testBool = false;

	
	
	public boolean isApi=false;
	public boolean isLogin=false;
	public boolean loginDone=false;
	//�û���¼��Ҫ�õ��Ľű�
	public String loginjs="bufanye.js";
	//�����ݴ��¼ǰ�û��������Ĳ���
	public  String login_savePath="";
	public String login_jsFileName;
	public List<String> login_urlFile;		 
	
	 
	
	
	//*********��ȡ����΢�����������ݴ����
	public List<String> sina_cookieNames;
	public String sourceUrl="null";
	public String oldurl="";
	public String sourceUserName="null";
	public String sourceComment="null";
	public String sourceDate="null";
	public String sourceCommentNum="null";
	public String sourceForwardNum="null";
	public String sourceUserType="null";	 
	public  int commentpageNum=1;//����ҳ��
	boolean fanye=false;
	 
	public  ArrayList<Comment> commentlist=new ArrayList<Comment>(); 
   public boolean isSourceCommentAdd=false;
   public boolean isForwardCommentAdd=false;
	
	/**
	 * ���ع��췽����
	 * 
	 * @param urlFile
	 *            byVal ��ַ�б�
	 * @param savePath
	 *            byVal �洢Ŀ¼
	 * @param jsFileName
	 *            byVal �ű��ļ���
	 */
	// ----------------------------------------fan----------------------------------------
	public BrowserMainWindow(List<String> urlFile, String savePath,
			String jsFileName, String deadtime,
			QTextBrowser crawlInfoTextBrowser) {
		this(null, urlFile, savePath, jsFileName, deadtime);
	}

	/**
	 * ���ع��췽����
	 * 
	 * @param urlFile
	 *            byVal ��ַ�б�
	 * @param savePath
	 *            byVal �洢Ŀ¼
	 */
	// ----------------------------------------fan----------------------------------------
	public BrowserMainWindow(List<String> urlFile, String savePath,
			String deadtime) {
		this(null, urlFile, savePath, null, deadtime);
	}

	
	//�Ҽ��ϵģ���֪���ܲ����ã�������һ�°�	
		public BrowserMainWindow(List<String> urlFile, String savePath,
				String jsFileName, String deadtime) {
			 this(null, urlFile, savePath, jsFileName, deadtime);
		}

	
	

	
 
	
	
	/**
	 * ���������캯������ʼ����������������ݲ�����ʼ�����еĶ�Ӧ������ ������������ڣ�Ϊ�����еĿؼ�����¼���Ӧ������Cookie����
	 * ��ȡCookie�ļ��У���ȡ�����ļ��������������Ϊ����ʾ��������ڡ�
	 * 
	 * @param parent
	 *            byVal ������
	 * @param urlFile
	 *            byVal ��ַ�б�
	 * @param savePath
	 *            byVal �洢Ŀ¼
	 * @param jsFileName
	 *            byVal �ű��ļ���
	 */
	// ----------------------------------------fan----------------------------------------
	public BrowserMainWindow(QWidget parent, final List<String> urlFile,
			String savePath, String jsFileName, String deadtime) {
		super(parent);

			
			this.savePath = savePath;
			this.jsFileName = jsFileName;
			this.urlFile = urlFile;
		
	    	jar = new MyCookieJar();
	    	field = new QLineEdit();
		    browser = new QWebView();
		    sina_cookieNames=new ArrayList<String>();
		// ----------------------------------------fan----------------------------------------
		this.deadtime = deadtime;
		//-------------------------------����΢������ҳ����ȡ��Ϣ���õ�cookie���ƣ�
		sina_cookieNames.add("source_userurl");
		sina_cookieNames.add("source_username");
		sina_cookieNames.add("source_userType");
		sina_cookieNames.add("source_Comment");
		sina_cookieNames.add("source_date");
		sina_cookieNames.add("source_commentNum");
		sina_cookieNames.add("source_forwardNum");
		
		sina_cookieNames.add("user_comment");		 
		sina_cookieNames.add("user_userurl");
		sina_cookieNames.add("user_userName");
		sina_cookieNames.add("user_date");
		
		sina_cookieNames.add("zhuanfaName");
		sina_cookieNames.add("zhuanfaComment");
		sina_cookieNames.add("zhuanfaDate");
		sina_cookieNames.add("zhuanfaUrl");
		sina_cookieNames.add("zhuanfaForwardNum");
		sina_cookieNames.add("zhuanfaCommentNum");
		
		// Toolbar...
		QToolBar toolbar = addToolBar("Actions");
		toolbar.addAction(browser.pageAction(QWebPage.WebAction.Back));
		toolbar.addAction(browser.pageAction(QWebPage.WebAction.Forward));
		toolbar.addAction(browser.pageAction(QWebPage.WebAction.Reload));
		toolbar.addAction(browser.pageAction(QWebPage.WebAction.Stop));
		toolbar.addWidget(field);
		toolbar.setFloatable(false);
		toolbar.setMovable(false);
		setCentralWidget(browser);
		statusBar().show();
		setWindowTitle("WebKit Browser");
		// ----------------------------------------fan----------------------------------------
		this.setWindowIcon(new QIcon("./data/browser.png"));
		this.hash = new Hashtable<String, Integer>();
		this.processValue = 0;
		this.reloadCount = 0;

		// Connections and Actions
		field.returnPressed.connect(this, "open()");
		browser.loadStarted.connect(this, "loadStarted()");
		browser.loadProgress.connect(this, "loadProgress(int)");
		browser.loadFinished.connect(this, "loadDone()");
		browser.urlChanged.connect(this, "urlChanged(QUrl)");
		browser.page().mainFrame().javaScriptWindowObjectCleared.connect(this,
				"populateJavaScriptWindowObject()");
		// set cookiejar
		if (enableCookies) {
			browser.page().networkAccessManager().setCookieJar(jar);
			// jar.load();
			// printCookies(jar.cookies());
		}
		loadSettings();
		QApplication.invokeLater(new Runnable() {
			public void run() {
				// System.out.println("Ko1");
				if (index < urlFile.size()) {
					try {
						field.setText(URLDecoder.decode(urlFile.get(index),
								"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					++index;
					open();
				}
			}
		});
		
	}
	
	public void closeEvent(QCloseEvent event) {
		endOfPage();
		 
	if(isLogin)
		{
		
			BrowserMainWindow browser=new BrowserMainWindow(this.login_urlFile,this.login_savePath,this.login_jsFileName,this.deadtime);
	    	browser.show();
		}
		
		this.dispose();		
	}

	
	
	/**
	 * ���ù�ϣ�롣
	 * 
	 * @param str
	 *            byVal ԭʼ��
	 */
	public int BKDRHash(String str) {
		int seed = 131; // 31 131 1313 13131 131313 etc..
		int hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash * seed) + str.charAt(i);
		}

		return (hash & 0x7FFFFFFF);
	}

	/**
	 * ���������ַ����Ϊ�仯�����ַ�� ����������ʵ���ַ�仯ʱ�������˺�����
	 * 
	 * @param url
	 *            �仯�����ַ
	 */
	public void urlChanged(QUrl url) {
		try {
			
			field.setText(URLDecoder.decode(url.toString(), "UTF-8"));
			 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		done = false;
	}

	/**
	 * ʹ�ڿ�ܵ�JavaScript�����Ļ����п��Է�������Ϊww�����������
	 */
	public void populateJavaScriptWindowObject() {
		browser.page().mainFrame().addToJavaScriptWindowObject("ww", this);
	}

	/**
	 * ��HTML��ʽ�����������ǰ���ֵ���ҳ�����ݣ��ļ��Ծ�����������ַ������ ͬʱ������ҳ��ץȡ��Ϣ�������־�ļ��С�
	 */
	public void downloadSource() {
		++pageNum;
		QWebFrame frame = browser.page().mainFrame();
		
		String content = frame.toHtml();
		
		
		if (field.text().isEmpty()) {
			System.out.println("no file name specified.");
		}
		String urlChange = field.text().replaceAll("[./\\:?*]", "-");
		int len = urlChange.length();
		if (len > 200) {
			urlChange = urlChange.substring(0, 50)
					+ urlChange.substring(len - 1 - 50, len - 1);
		}
		urlChange += "-" + pageNum + ".html";
		String path = savePath + "/" + urlChange;
		FileHelp.writeFile(path, content, getEncoding());

		LOGGER.info(field.text() + " PAGE " + pageNum);
		LOGGER.info("- start time: " + startTime);
		LOGGER.info("- end time: " + new Date());
		LOGGER.info("- file name: " + urlChange);
		LOGGER.info("- file size: " + FileHelp.formatDirSize(content.length()));
		LOGGER.info("");
		startTime = new Date();
		
//		 System.out.println("download content from " + field.text() + " PAGE "
//		 + pageNum);
		//System.out.println(content);
		if(!isApi)
		crawlInfoTextBrowser.append("download content from " + field.text()
				+ " PAGE " + pageNum);
	}

	
	
	
	public void downloadSource_static() {
		if (!isStatic)
			isStatic = true;
		QWebFrame frame = browser.page().mainFrame();
		String content = frame.toHtml();
		if (field.text().isEmpty()) {
			System.out.println("no file name specified.");
		}
		String urlChange = field.text().replaceAll("[./\\:?*]", "-");
		int len = urlChange.length();
		if (len > 200) {
			urlChange = urlChange.substring(0, 50)
					+ urlChange.substring(len - 1 - 50, len - 1);
		}

		int strValue = this.BKDRHash(urlChange.toLowerCase());
		this.hash.put(urlChange.toLowerCase(), strValue);

		urlChange += "-" + ".html";
		String path = savePath + "/" + urlChange;
		FileHelp.writeFile(path, content, getEncoding());

		LOGGER.info(field.text());
		LOGGER.info("- start time: " + startTime);
		LOGGER.info("- end time: " + new Date());
		LOGGER.info("- file name: " + urlChange);
		LOGGER.info("- file size: " + FileHelp.formatDirSize(content.length()));
		LOGGER.info("");
		startTime = new Date();
//		 System.out.println("download content from " + field.text());
		if(!isApi)
		crawlInfoTextBrowser.append("Download content from " + field.text());
	}

	

	
	
	/**
	 * ��ȡ�ű��ļ����ݡ�
	 * 
	 * @param fullPathFilename
	 *            ����ȡ�Ľű��ļ�����Ի����·����
	 * @return �ű��ļ�����
	 */
	private String readAll(String fullPathFilename) {
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(
					fullPathFilename));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
			}
			reader.close();
			return sb.toString();
		} catch (FileNotFoundException fe) {
			System.out.println("File not found: " + fullPathFilename);
		} catch (IOException fe) {
			System.out.println("IOException happened in reading js files");
		}
		return null;
	}

	/**
	 * ����������ڵ�״̬������ʾ��ʼĳ����ַ��ʼ���ص���Ϣ��
	 */
	public void loadStarted() {
		statusBar().showMessage("Starting to load:" + field.text());
		startTime = new Date();
	}

	/**
	 * ����������ڵ�״̬������ʾ���������Ϣ����ִ����jsFileNameָ���ķ�ҳ�ű���
	 */
	public void loadDone() {
		reloadCount = 0;
		timer.cancel();
		System.out.println("loadDone():timer.cancel()");
		if (done == false) {// Execute JavaScript
			done = true;
			// System.out.println("Finished loading.");
			statusBar().showMessage("Finished loading.");
			QWebFrame frame = browser.page().mainFrame();
			if (jsFileName != null && !jsFileName.isEmpty()) {
				if (jsFileName.equals("bufanye.js")) {
					// ����ҳ��������Ҫ�ȴ���Ӧдһ������ҳ�Ľű�����Ӧʹ��wait()
					frame.evaluateJavaScript(readAll("bufanye.js"));
				} else {
					if (new QUrl(field.text()).host().indexOf("home.news.cn") > 0) {
						frame
								.evaluateJavaScript(readAll("blog-xinhua-preprocessing.js"));
					}
					frame.evaluateJavaScript(readAll("simulate.js"));

					if (deadtime == "NoTime")
						frame.evaluateJavaScript(readAll(jsFileName));
					else
						frame.evaluateJavaScript("var deadTime=\"" + deadtime
								+ "\";\n" + readAll(jsFileName));
				}
			}
		}
	}

	/**
	 * �������״̬����ʾ���ؽ��ȡ�
	 * 
	 * @param x
	 *            ���ؽ��ȣ�ȡֵ��Χ��0-100
	 */
	public void loadProgress(int x) {
		this.processValue = x;
		statusBar().showMessage("Loading" + x + " %");
	}

	/**
	 * ����ַ���������Լ�飬��������ʴ���ַ��
	 */
	public String checkUrl() {
		String textUrl = field.text();
		if (textUrl.indexOf("://") < 0) {
			textUrl = "http://" + textUrl;
		}
		try {
			textUrl = URLDecoder.decode(textUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return textUrl;
	}
	/**
	 * ������򿪵�ַ������ҳ����������ʱ���ػ��ơ�
	 */
	public void open() {
		String text = checkUrl();
		this.hash.clear();
		browser.load(new QUrl(text));
		reloadStart();
		
	}
	/**
	 * ��ʱ���ػ��ƣ���ҳ��20���δ������ϣ��������������20���δ������ϣ�ֱ��������
	 */
	public void reloadStart() {
		timer = new Timer();
		timerTask = new reloadTimerTask(this);
		timer.schedule(timerTask.myAbstractTimerTask, 20000, 20000);
		timerTask.needReload.connect(this, "needReloadSlot()");
		
		timerTask.needLoadNext.connect(this, "endOfPage()");
//		System.out.println("new Timer()");
	}

	public void needReloadSlot() {
		browser.reload();
//		System.out.println("reload()");
	}

	public boolean hashCheck(String str) {
		String temp = str.replaceAll("[./\\:?*]", "-");
		int len = temp.length();
		if (len > 200) {
			temp = temp.substring(0, 50)
					+ temp.substring(len - 1 - 50, len - 1);
		}
		return this.hash.containsKey(temp.toLowerCase());
	}

	/**
	 * ȡ��ҳ���ļ��е�ǰ��ַ�ĺ�һ��������ѵ����ļ�β���͹ر�������� �����������ַ������һ����ַ��
	 */
	public void endOfPage() {
		reloadCount = 0;
		this.commentpageNum=1;
		timer.cancel();
		System.out.println("endOfPage():timer.cancel()");
		if (urlFile.size() == 0) {
			if(!isApi)
			crawlInfoTextBrowser.append("\nThe file is blank.");
			if(!this.isLogin)//����������¼��ҳ��
			close();
		} else if (index >= urlFile.size()) {
			if(!isApi)
			crawlInfoTextBrowser.append("\nFinished.");
			close();
		} else {
			String temp = urlFile.get(index);
			if (isStatic)
				while (hashCheck(temp)) {
					index++;
					if (index > urlFile.size()) {
						if(!isApi)
						crawlInfoTextBrowser.append("\nFinished.");
						close();
						return;
					}
					temp = urlFile.get(index);
				}
			try {
				browser.load(new QUrl(URLDecoder.decode(temp, "UTF-8")));
				reloadStart();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			++index;
			pageNum = 0;
			done = false;
		}
		
		
	}
	
	public void endOfPage_sina() {
		reloadCount = 0;
		timer.cancel();		
		System.out.println("endOfPage():timer.cancel()");
		if (urlFile.size() == 0) {//������ҳ��Ϊ��
			if(!isApi)
			crawlInfoTextBrowser.append("\nThe file is blank.");
			if(!this.isLogin)//����������¼��ҳ��API
			close();
		} else if (index >= urlFile.size()) {
			if(!isApi)
			crawlInfoTextBrowser.append("\nFinished.");
			//this.commentpageNum++;
			writeCommentList(this.commentlist,this.oldurl);//д���ļ�
		//	this.commentpageNum=0;
			close();
		} else {
			String temp = urlFile.get(index);
			if (isStatic)
				while (hashCheck(temp)) {
					index++;
					if (index > urlFile.size()) {
						if(!isApi)
						crawlInfoTextBrowser.append("\nFinished.");
						close();
						return;
					}
					temp = urlFile.get(index);
				}
			try {
				browser.load(new QUrl(URLDecoder.decode(temp, "UTF-8")));
				reloadStart();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			++index;
			pageNum = 0;
			done = false;
		}		
		
	}
	
	

	public void printCookies(List<QNetworkCookie> cookieList) {
		if (cookieList == null || cookieList.isEmpty()) {
			System.out.println("COOKIELIST IS EMPTY");
		} else {
			int count = 0;
			for (QNetworkCookie cookie : cookieList) {
				// if(cookie.isSessionCookie())
				// continue;
				System.out.println("---------------------");
				System.out.println("NAME:" + cookie.name());
				System.out.println("VALUE:" + cookie.value());
				System.out.println("IS SESSION COOKIE:"
						+ cookie.isSessionCookie());
				System.out.println("PATH:" + cookie.path());
				System.out.println("DOMAIN:" + cookie.domain());
				System.out
						.println("EXPIRATION DATE:" + cookie.expirationDate());
				count++;
			}
			System.out.println("cookie number : " + count);
		}
	}

	/**
	 * ��������Ϣ�������־�ļ��С�
	 * 
	 * @param msg
	 *            ������Ϣ
	 */
	public void genErrorMsg(String msg) {
		LOGGER.error("- error message: " + msg);
		System.out.println(msg);
	}

	/**
	 * �������ļ��ж�ȡ����������������Ϣ�������������Ϊ�Ͳ�����
	 * 
	 * @return �Ƿ����óɹ�
	 */
	private boolean loadSettings() {
		QSettings app = new QSettings(BrowserApplication
				.dataFilePath("Appsettings.ini"), Format.IniFormat);
		QWebSettings settings = browser.settings();
		app.beginGroup("WebSettings");
		settings.setAttribute(WebAttribute.AutoLoadImages, Boolean
				.parseBoolean(app.value("AutoLoadImages", false).toString()));
		settings.setAttribute(WebAttribute.JavascriptEnabled, Boolean
				.parseBoolean(app.value("JavascriptEnabled", true).toString()));
		settings.setAttribute(WebAttribute.PluginsEnabled, Boolean
				.parseBoolean(app.value("PluginsEnabled", false).toString()));
		settings
				.setAttribute(WebAttribute.JavascriptCanOpenWindows, Boolean
						.parseBoolean(app.value("blockPopupWindows", false)
								.toString()));
		settings.setAttribute(WebAttribute.PrintElementBackgrounds, false);
		app.endGroup();
		// ʹ��cache
		// System.out.println("cacheLocation: " + location);

		QNetworkDiskCache diskCache = new QNetworkDiskCache(this);
		String location = QDesktopServices
				.storageLocation(StandardLocation.CacheLocation);
		diskCache.setCacheDirectory(location);
		app.beginGroup("Cache");
		diskCache.setMaximumCacheSize(Long.valueOf(app.value("maxCache", 50)
				.toString()));
		app.endGroup();
		browser.page().networkAccessManager().setCache(diskCache);

		settings.setAttribute(WebAttribute.LocalStorageDatabaseEnabled, true);
		settings.setAttribute(WebAttribute.OfflineStorageDatabaseEnabled, true);
		settings.setAttribute(WebAttribute.OfflineWebApplicationCacheEnabled,
				true);
		// settings.setOfflineStoragePath("D:/offlineStorage/");

		setAttribute(Qt.WidgetAttribute.WA_DeleteOnClose);
		return true;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return this.encoding;
	}

	public void setCrawlInfo(QTextBrowser textBrowser) {
		if(!isApi)
		this.crawlInfoTextBrowser = textBrowser;
	}
	
	
	public void setLoginPar(String savaPath,String jsFileName,List<String> urlFile,boolean isLogin,boolean isApi)
	{
		this.login_jsFileName=jsFileName;
		this.login_savePath=savaPath;
		this.login_urlFile=urlFile;
		this.isLogin=isLogin;
		this.isApi=isApi;
	}
	
	
	
	public void refreshCookie()	{
		 
		QWebFrame frame = browser.page().mainFrame();
		QUrl url=frame.url();	
		//this.commentpageNum=1;
		this.sourceUrl=url.toString();
		if(this.oldurl.equals(""))
		{
			this.oldurl=this.sourceUrl;
			 
		}
		Comment mySourceComment=new Comment();
		Comment userComment=new Comment();
		Comment zhuanfaComment=new Comment();
		
		//��ȡ��ҳ������cookie
	    List <QNetworkCookie> mycookieList=	jar.cookiesForUrl(url);
	    for(QNetworkCookie cookie:mycookieList)
	    {
	    	String cookieName=cookie.name().toString();
	    	String cookievalue="";
	    	if(this.sina_cookieNames.contains(cookieName))
	    	{	    		
	    	String res=cookie.toString(); 
	    	
				try {
					String name = URLDecoder.decode(res,"UTF-8");
				//	System.out.println("toString�����"+name);
					int index=name.indexOf(cookieName+"");
					int end=name.indexOf("; domain=");
					cookievalue=name.substring(index+cookieName.length()+1,end);//��ȡֵ
					cookievalue=replaceBlank(cookievalue);//ȥ���س����ո��Ʊ����
					if(cookieName.equals("source_userurl"))
					{
						mySourceComment.setUrl(cookievalue.trim());
					}
					else if(cookieName.equals("source_username"))
					{
						mySourceComment.setName(cookievalue.trim());
					}
					else if(cookieName.equals("source_userType"))
					{
						mySourceComment.setUserType(cookievalue.trim());
					}
					else if(cookieName.equals("source_Comment"))
					{
						String time=getTime(cookievalue);//��΢�������л�ȡʱ��
						if(mySourceComment.getDate().equals("null"))//ԭ��û��ʱ��
							mySourceComment.setDate(time);
						
						mySourceComment.setComment(cookievalue.trim());
					}
					else if(cookieName.equals("source_date"))
					{//*************************�˴���ʱ����Ҫ�ٴ����£���Ϊ�������ҵ�û��Ļ�ʱ��������������ʲô��ͷ��
						System.out.println("cookievalue:"+cookievalue);
						String time=getTime(cookievalue);
						if(!time.isEmpty())//ǰ�����ν���ʱ��ļ�⣬������û�õ�ʱ������
						mySourceComment.setDate(time);
						if(cookievalue.contains("(")||time.contains(")"))
								{
							cookievalue=cookievalue.replace('(',' ');
							cookievalue=cookievalue.replace(')',' ');
							if(mySourceComment.getDate().equals("null")||mySourceComment.getDate().equals(""))
								mySourceComment.setDate(cookievalue);
								}
					}
					else if(cookieName.equals("source_commentNum"))
					{
						String num=getNum(cookievalue);
						if(!num.isEmpty())
						mySourceComment.setCommentNum(num);
					}
					else if(cookieName.equals("source_forwardNum"))
					{
						String num=getNum(cookievalue);
						if(!num.isEmpty())
						 mySourceComment.setReplyNum(num);
					}
					else if(cookieName.equals("user_comment"))
					{
						String time=getTime(cookievalue);
						userComment.setDate(time);
						userComment.setComment(cookievalue.trim());
					}
					else if(cookieName.equals("user_userurl"))
					{
						userComment.setUrl(cookievalue.trim());
					}
					else if(cookieName.equals("user_userName"))
					{
						userComment.setName(cookievalue.trim());
					}
					else if(cookieName.equals("user_date"))
					{
						String time=getTime(cookievalue);
						if(time.isEmpty()&&!cookievalue.isEmpty())
								{//ʱ���ʽ���Ǳ�׼��
							if(cookievalue.contains("("))
							cookievalue=cookievalue.replace('(',' ');
							if(cookievalue.contains(")"))
							cookievalue=cookievalue.replace(')',' ');
							userComment.setDate(cookievalue.trim());
								}
						else 
						userComment.setDate(time);
					}
					else if(cookieName.equals("zhuanfaName"))
					{
						zhuanfaComment.setName(cookievalue.trim());
					//	System.out.println("zhuanfaName:"+cookievalue);
					}
					else if(cookieName.equals("zhuanfaComment"))
					{
						zhuanfaComment.setComment(cookievalue.trim());
						//System.out.println(cookievalue);
					}
					else if(cookieName.equals("zhuanfaDate"))
					{
						zhuanfaComment.setDate(cookievalue.trim());
					//	System.out.println(cookievalue);
					}
					else if(cookieName.equals("zhuanfaUrl"))
					{
						zhuanfaComment.setUrl(cookievalue.trim());
						//System.out.println(cookievalue);
					}
					else if(cookieName.equals("zhuanfaForwardNum"))
					{
						String num=getNum(cookievalue);
						zhuanfaComment.setReplyNum(num);
					}
					else if(cookieName.equals("zhuanfaCommentNum"))
					{
						String num=getNum(cookievalue);
						zhuanfaComment.setCommentNum(num);
					}
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
					
	    	}//if
	    	
	    }//for
		
	  
	    	if(!isSourceCommentAdd)
	    	{
				 this.commentlist.add(mySourceComment);
				 this.isSourceCommentAdd=true;
	    	}
	    	if(!this.isForwardCommentAdd)
	    	{
	    		this.commentlist.add(zhuanfaComment);
	    		this.isForwardCommentAdd=true;
	    	}
			 if(!this.commentlist.contains(userComment)&&!userComment.getComment().trim().equals("null"))
				 this.commentlist.add(userComment);
		 			
		
	}
	
	
	public void writeCookietoFile()
	{
		writeCommentList(this.commentlist,this.sourceUrl);
		this.commentlist=new ArrayList<Comment>();
		this.isSourceCommentAdd=false;
		this.isForwardCommentAdd=false;
	}
	
	
	
	
	
 public void writeCommentList(List<Comment> list,String name)
 {
	 FileHelper fileHelper=new FileHelper();
	 if(name.contains("#"))
	 {
		 int end=name.indexOf("#");
		 name=name.substring(0,end);
	 }
	 String filename=name.toString().replaceAll("[./\\:?*]", "-");
	 
	 //����ʱ���½��ļ���
	 java.util.Calendar c=java.util.Calendar.getInstance();    
     java.text.SimpleDateFormat f=new java.text.SimpleDateFormat("yyyy-MM-dd");
     String time_name=f.format(c.getTime());
     String floderPath=savePath+ "/"+time_name;        
     fileHelper.newFolder(floderPath);
     String destFilename="";
     
      destFilename = floderPath+"/"+filename+"-"+this.commentpageNum+".txt";
	    
		String destPathEnd = fileHelper.createNewFile(destFilename);
	 
	 try {
		fileHelper.writeCommentFile(destPathEnd,commentlist);
		crawlInfoTextBrowser.append("download content from " + destFilename);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
	
 public String getTime(String text) { 
     String dateStr = text.replaceAll("r?n", " "); 
     dateStr = dateStr.replaceAll("\\s+", " ");        
     
     try { 
         
         List<String> matches = null; 
         Pattern p = Pattern.compile("(\\d{1,4}[-|\\/]\\d{1,2}[-|\\/]\\d{1,2} \\d{1,2}:\\d{1,2})", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE); 
         Matcher matcher = p.matcher(dateStr); 
         if (matcher.find() && matcher.groupCount() >= 1) { 
             matches = new ArrayList<String>(); 
             for (int i = 1; i <= matcher.groupCount(); i++) { 
                 String temp = matcher.group(i); 
                 matches.add(temp); 
             } 
         } else { 
             matches = Collections.EMPTY_LIST; 
         }            
         
         if (matches.size() > 0) { 
             return ((String) matches.get(0)).trim(); 
         } else { 
             return ""; 
         } 
         
     } catch (Exception e) { 
         return ""; 
     } 
 }

 public  String replaceBlank(String str) {//ȥ���س��ո��Ʊ����
     String dest = "";
     dest.replaceAll("\\s{1,}", " ") ;
     if (str!=null) {
         Pattern p = Pattern.compile("\\t|\r|\n");
         Matcher m = p.matcher(str);
         dest = m.replaceAll("");
     }
     return dest;
 } 
 
 
 public String getNum(String res) {//��ȡת��������Ŀ
		// String a="love23next234csdn3423javaeye";
		 String regEx="[^0-9]";   
		 Pattern p = Pattern.compile(regEx);   
		 Matcher m = p.matcher(res);   
		 String result=""+m.replaceAll("").trim();
		return result;
		 }
	
 
 public void changeFanye()
 {
	this.fanye=true;	
//	this.commentpageNum++;	 
 }
 public void changePageNum(){
	 this.commentpageNum++;
	 
	// this.sourceComment=new sourceComment();	 
 System.out.println("ҳ����"+this.commentpageNum);
 }
 
 
 public String getcommentpageNum()
  {
	 return URLEncoder.encode(this.commentpageNum+"");
  }
 
}
