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
 * 浏览器类，实现了浏览器的基本功能和一个简易窗口
 */
public class BrowserMainWindow extends QMainWindow {
	private static final Logger LOGGER = Logger
			.getLogger(BrowserMainWindow.class.getName());
	public QWebView browser;
	private QLineEdit field;
	private MyCookieJar jar;
	private String encoding = "UTF-8";
	/* 页数标志，每当翻页结束时清零 */
	private int pageNum = 0;
	private boolean enableCookies = true;
	/* 存储目录 */
	private  String savePath;
	/* 脚本文件名 */
	private String jsFileName;
	/* 网页集列表 */
	private List<String> urlFile;
	/* 下一即将访问的网址在网页集列表中的索引 */
	private int index = 0;
	/* 记录抓取一个网页的开始时间 */
	private Date startTime = null;
	/* 描述网页是否加载完成的标志 */
	private boolean done = false;
	// ----------------------------------------fan----------------------------------------
	/* 给定增量式抓取的终止时间 */
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
	//用户登录需要用到的脚本
	public String loginjs="bufanye.js";
	//下面暂存登录前用户传过来的参数
	public  String login_savePath="";
	public String login_jsFileName;
	public List<String> login_urlFile;		 
	
	 
	
	
	//*********爬取新浪微博评论所需暂存变量
	public List<String> sina_cookieNames;
	public String sourceUrl="null";
	public String oldurl="";
	public String sourceUserName="null";
	public String sourceComment="null";
	public String sourceDate="null";
	public String sourceCommentNum="null";
	public String sourceForwardNum="null";
	public String sourceUserType="null";	 
	public  int commentpageNum=1;//评论页数
	boolean fanye=false;
	 
	public  ArrayList<Comment> commentlist=new ArrayList<Comment>(); 
   public boolean isSourceCommentAdd=false;
   public boolean isForwardCommentAdd=false;
	
	/**
	 * 重载构造方法。
	 * 
	 * @param urlFile
	 *            byVal 网址列表
	 * @param savePath
	 *            byVal 存储目录
	 * @param jsFileName
	 *            byVal 脚本文件名
	 */
	// ----------------------------------------fan----------------------------------------
	public BrowserMainWindow(List<String> urlFile, String savePath,
			String jsFileName, String deadtime,
			QTextBrowser crawlInfoTextBrowser) {
		this(null, urlFile, savePath, jsFileName, deadtime);
	}

	/**
	 * 重载构造方法。
	 * 
	 * @param urlFile
	 *            byVal 网址列表
	 * @param savePath
	 *            byVal 存储目录
	 */
	// ----------------------------------------fan----------------------------------------
	public BrowserMainWindow(List<String> urlFile, String savePath,
			String deadtime) {
		this(null, urlFile, savePath, null, deadtime);
	}

	
	//我加上的，不知道能不能用，姑且试一下吧	
		public BrowserMainWindow(List<String> urlFile, String savePath,
				String jsFileName, String deadtime) {
			 this(null, urlFile, savePath, jsFileName, deadtime);
		}

	
	

	
 
	
	
	/**
	 * 带参数构造函数，初始化浏览器。包括根据参数初始化类中的对应变量， 绘制浏览器窗口，为窗口中的控件添加事件响应，启动Cookie管理，
	 * 读取Cookie文件中，读取配置文件，设置浏览器行为，显示浏览器窗口。
	 * 
	 * @param parent
	 *            byVal 父窗口
	 * @param urlFile
	 *            byVal 网址列表
	 * @param savePath
	 *            byVal 存储目录
	 * @param jsFileName
	 *            byVal 脚本文件名
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
		//-------------------------------新浪微博评论页面提取信息设置的cookie名称：
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
	 * 设置哈希码。
	 * 
	 * @param str
	 *            byVal 原始串
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
	 * 将浏览器地址栏置为变化后的网址。 当浏览器访问的网址变化时，触发此函数。
	 * 
	 * @param url
	 *            变化后的网址
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
	 * 使在框架的JavaScript上下文环境中可以访问名称为ww的浏览器对象。
	 */
	public void populateJavaScriptWindowObject() {
		browser.page().mainFrame().addToJavaScriptWindowObject("ww", this);
	}

	/**
	 * 以HTML格式保存浏览器当前呈现的网页的内容，文件以经过处理后的网址命名， 同时将此网页的抓取信息输出到日志文件中。
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
	 * 读取脚本文件内容。
	 * 
	 * @param fullPathFilename
	 *            待读取的脚本文件的相对或绝对路径名
	 * @return 脚本文件内容
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
	 * 在浏览器窗口的状态栏中显示开始某个网址开始加载的信息。
	 */
	public void loadStarted() {
		statusBar().showMessage("Starting to load:" + field.text());
		startTime = new Date();
	}

	/**
	 * 在浏览器窗口的状态栏中显示加载完成信息，并执行由jsFileName指定的翻页脚本。
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
					// 不翻页，可能需要等待，应写一个不翻页的脚本，不应使用wait()
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
	 * 在浏览器状态栏显示加载进度。
	 * 
	 * @param x
	 *            加载进度，取值范围是0-100
	 */
	public void loadProgress(int x) {
		this.processValue = x;
		statusBar().showMessage("Loading" + x + " %");
	}

	/**
	 * 对网址进行完整性检查，浏览器访问此网址。
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
	 * 浏览器打开地址栏的网页，并启动超时重载机制。
	 */
	public void open() {
		String text = checkUrl();
		this.hash.clear();
		browser.load(new QUrl(text));
		reloadStart();
		
	}
	/**
	 * 超时重载机制，网页打开20秒后还未加载完毕，即可启动，如果20秒后还未加载完毕，直接跳过。
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
	 * 取网页集文件中当前地址的后一个，如果已到达文件尾，就关闭浏览器； 否则，浏览器地址访问下一个地址。
	 */
	public void endOfPage() {
		reloadCount = 0;
		this.commentpageNum=1;
		timer.cancel();
		System.out.println("endOfPage():timer.cancel()");
		if (urlFile.size() == 0) {
			if(!isApi)
			crawlInfoTextBrowser.append("\nThe file is blank.");
			if(!this.isLogin)//不是用来登录的页面
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
		if (urlFile.size() == 0) {//输入网页集为空
			if(!isApi)
			crawlInfoTextBrowser.append("\nThe file is blank.");
			if(!this.isLogin)//不是用来登录的页面API
			close();
		} else if (index >= urlFile.size()) {
			if(!isApi)
			crawlInfoTextBrowser.append("\nFinished.");
			//this.commentpageNum++;
			writeCommentList(this.commentlist,this.oldurl);//写入文件
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
	 * 将出错信息输出到日志文件中。
	 * 
	 * @param msg
	 *            出错信息
	 */
	public void genErrorMsg(String msg) {
		LOGGER.error("- error message: " + msg);
		System.out.println(msg);
	}

	/**
	 * 从配置文件中读取浏览器的相关配置信息，设置浏览器行为和参数。
	 * 
	 * @return 是否设置成功
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
		// 使用cache
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
		
		//获取该页面所有cookie
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
				//	System.out.println("toString结果："+name);
					int index=name.indexOf(cookieName+"");
					int end=name.indexOf("; domain=");
					cookievalue=name.substring(index+cookieName.length()+1,end);//获取值
					cookievalue=replaceBlank(cookievalue);//去掉回车、空格、制表符等
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
						String time=getTime(cookievalue);//从微博内容中获取时间
						if(mySourceComment.getDate().equals("null"))//原来没有时间
							mySourceComment.setDate(time);
						
						mySourceComment.setComment(cookievalue.trim());
					}
					else if(cookieName.equals("source_date"))
					{//*************************此处的时间需要再处理下，因为如果是企业用户的话时间是在整个评论什么里头的
						System.out.println("cookievalue:"+cookievalue);
						String time=getTime(cookievalue);
						if(!time.isEmpty())//前后两次进行时间的检测，避免有没拿到时间的情况
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
								{//时间格式不是标准的
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
	 
	 //根据时间新建文件夹
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

 public  String replaceBlank(String str) {//去掉回车空格制表符等
     String dest = "";
     dest.replaceAll("\\s{1,}", " ") ;
     if (str!=null) {
         Pattern p = Pattern.compile("\\t|\r|\n");
         Matcher m = p.matcher(str);
         dest = m.replaceAll("");
     }
     return dest;
 } 
 
 
 public String getNum(String res) {//提取转发评论数目
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
 System.out.println("页数："+this.commentpageNum);
 }
 
 
 public String getcommentpageNum()
  {
	 return URLEncoder.encode(this.commentpageNum+"");
  }
 
}
