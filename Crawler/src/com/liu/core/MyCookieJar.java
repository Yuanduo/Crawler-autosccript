package com.liu.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDateTime;
import com.trolltech.qt.core.QSettings;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.core.QSettings.Format;
import com.trolltech.qt.network.QNetworkCookie;
import com.trolltech.qt.network.QNetworkCookieJar;
import com.trolltech.qt.network.QNetworkCookie.RawForm;
import com.trolltech.qt.webkit.QWebSettings;

/**
* Cookie类，实现对Cookie的读取、保存、添加和删除等操作。
*
*/
public class MyCookieJar extends QNetworkCookieJar{
	/*描述是否已经读取Cookie的标志*/
	private boolean m_loaded;
	/*Cookie的接收策略*/
	private AcceptPolicy m_acceptCookies;
	/*Cookie的保存策略*/
    private KeepPolicy m_keepCookies;
	private AutoSaver m_saveTimer;
	public enum AcceptPolicy {
        Always,
        Never,
        AcceptOnlyFromSitesNavigatedTo
    };

    public enum KeepPolicy {
        KeepUntilExpire,
        KeepUntilExit,
        KeepUntilTimeLimit
    };
    
	public Signal0 cookiesChanged = new Signal0();
    /**
	* 构造方法，初始化一个空的cookie jar。
	*/
	public MyCookieJar() {
		
		super();
		
		//System.out.println("MyCookieJar.java:---构造函数，构造空Cookie jar");
		m_loaded = false;
		m_acceptCookies = AcceptPolicy.AcceptOnlyFromSitesNavigatedTo;
		m_saveTimer = new AutoSaver(this);
	}
//	setAllCookies and allCookies are final method that cannot be override
	/**
	 * 读取Cookie文件，构造QNetworkCookie对象。
	 */
	public void load() {
		//System.out.println("MyCookieJar.java:---load()");
		if(m_loaded)
			return;
    	QSettings cookieSettings = new QSettings(BrowserApplication.dataFilePath("cookies.ini"), Format.IniFormat);
    	Object ob = cookieSettings.value("cookies");
    	if(ob != null)
    	{
    		String cookieHeader = ob.toString();
    		if(!cookieHeader.isEmpty())
    		{
    			List<QNetworkCookie> newCookies = QNetworkCookie.parseCookies(new QByteArray(cookieHeader));  		
            	setAllCookies(newCookies);
    		}
    	}
		m_loaded = true;
		loadSettings();
//		System.out.println("READING COOKIES:"+ allCookies());
	}
	/**
	 * 移除SessionCookie，将剩余的Cookie保存至文件中。
	 */
	public void save() {
		//System.out.println("MyCookieJar.java:---save()");
//		System.out.println("save method in class MyCookieJar is invoked!");
		if(!m_loaded)
			return;
		purgeOldCookies();
		StringBuffer cookieString = new StringBuffer();
	//	System.out.println();
		QSettings cookieSettings = new QSettings(BrowserApplication.dataFilePath("cookies.ini"), Format.IniFormat);
		List<QNetworkCookie> cookies = allCookies();
    	for (int i = cookies.size()-1; i >= 0; i--) {
            if (cookies.get(i).isSessionCookie())
                cookies.remove(i);
        }
    	for (int i = 0; i < cookies.size(); i++)
    	{
            cookieString.append(cookies.get(i).toRawForm(RawForm.Full));
            if(i<cookies.size()-1)
            	cookieString.append(",");
    	}
    	cookieSettings.setValue("cookies", cookieString.toString());
//    	System.out.println("SAVING COOKIES:"+ cookieString.toString());
	}
	/**
	 * 清除所有Cookie。
	 */
	public void clear() {
	//System.out.println("MyCookieJar.java:---clear()");
		setAllCookies(new ArrayList<QNetworkCookie>());
		m_saveTimer.changeOccurred();
	}
	
	@Override
	public List<QNetworkCookie> cookiesForUrl(QUrl url) {
	//	System.out.println("MyCookieJar.java:---cookiesForUrl(QUrl url)"+url);
	    MyCookieJar that = this;
	    if (!m_loaded)
	        that.load();

	    QWebSettings globalSettings = QWebSettings.globalSettings();
	    if (globalSettings.testAttribute(QWebSettings.WebAttribute.PrivateBrowsingEnabled)) {
	        List<QNetworkCookie> noCookies = new ArrayList<QNetworkCookie>();
	        return noCookies;
	    }
		return super.cookiesForUrl(url);
	}
	
	/**
	 * 根据保存策略和接收策略，将cookieList中的Cookie有选择性地添加到当前的cookie jar中，
	 * 域和路径默认与url中的一致。
	 * @param cookieList
	 * @param url
	 * @return 是否设置成功
	 */
	@Override
	public boolean setCookiesFromUrl(List<QNetworkCookie> cookieList, QUrl url) {
		//System.out.println("MyCookieJar.java:---setCookiesFromUrl(List<QNetworkCookie> cookieList, QUrl url)");
		if (!m_loaded)
	        load();
		
		boolean addedCookies = false;
		boolean acceptInitially = (m_acceptCookies != AcceptPolicy.Never);
		if(acceptInitially) {
			QDateTime soon = QDateTime.currentDateTime();
	        soon = soon.addDays(90);
	        for(QNetworkCookie cookie : cookieList) {
	        	List<QNetworkCookie> lst = new ArrayList<QNetworkCookie>();
	        	if (m_keepCookies == KeepPolicy.KeepUntilTimeLimit
	                    && !cookie.isSessionCookie()
	                    && cookie.expirationDate().toTime_t()> soon.toTime_t()) {
	                    cookie.setExpirationDate(soon);
	                }
	                lst.add(cookie);
	            if(super.setCookiesFromUrl(lst, url)) {
	            	addedCookies = true;
	            }else {
	            	// finally force it in if wanted
                    if (m_acceptCookies == AcceptPolicy.Always) {
                        List<QNetworkCookie> cookies = allCookies();
                        for (Iterator<QNetworkCookie> it = cookies.iterator(); it.hasNext();) {
                            // does this cookie already exist?
                            if (cookie.name() == it.next().name() &&
                                cookie.domain() == it.next().domain() &&
                                cookie.path() == it.next().path()) {
                                // found a match
                                cookies.remove(it.next());
                                break;
                            }
                        }

                        cookies.add(cookie);
                        setAllCookies(cookies);
                        addedCookies = true;
                    }
	            }
	        }
		}
		if (addedCookies) {
			m_saveTimer.changeOccurred();
		}
		return addedCookies;
	}
	/**
	 * 返回所有Cookie。
	 */
	public ArrayList<QNetworkCookie> cookies() {
		//System.out.println("MyCookieJar.java:---cookies() ---返回所有cookies");
		if(!m_loaded)
			load();
		return (ArrayList<QNetworkCookie>) allCookies();
	}
	/**
	 * 设置Cookie。
	 * @param cookies
	 */
	public void setCookies(List<QNetworkCookie> cookies) {
		//System.out.println("MyCookieJar.java:---setCookies(List<QNetworkCookie> cookies)");
		if (!m_loaded)
	        load();
	    setAllCookies(cookies);
	    cookiesChanged.emit();
	}
	/**
	 * 返回接收策略。
	 */
	public final AcceptPolicy acceptPolicy() {
		//System.out.println("MyCookieJar.java:--- acceptPolicy()--返回接受策略");
		if(!m_loaded)
			load();
		return m_acceptCookies;
	}
	/**
	 * 设置接收策略。
	 * @param policy
	 */
    void setAcceptPolicy(AcceptPolicy policy) {
    	//System.out.println("MyCookieJar.java:---setAcceptPolicy(AcceptPolicy policy)--设置接受策略");
    	if (!m_loaded)
            load();
        if (policy == m_acceptCookies)
            return;
        m_acceptCookies = policy;
        m_saveTimer.changeOccurred();
    }
    /**
	 * 返回保存策略。
	 */
    public final KeepPolicy keepPolicy() {
    	//System.out.println("MyCookieJar.java:---keepPolicy()--返回保存策略");
    	
    	if(!m_loaded)
			load();
		return m_keepCookies;
    }
    /**
	 * 设置保存策略。
	 * @param policy
	 */
    void setKeepPolicy(KeepPolicy policy) {
    	//System.out.println("MyCookieJar.java:---setKeepPolicy(KeepPolicy policy)--设置保存策略");
    	if (!m_loaded)
            load();
        if (policy == m_keepCookies)
            return;
        m_keepCookies = policy;
        m_saveTimer.changeOccurred();
    }
    /**
	 * 读取配置文件中Cookie的相关配置信息，设置Cookie的保存策略和接收策略。
	 */
    private void loadSettings() {
    	//System.out.println("MyCookieJar.java:---loadSettings()");
    	
    	int index;
    	QSettings settings = new QSettings(BrowserApplication.dataFilePath("Appsettings.ini"), Format.IniFormat);
    	settings.beginGroup("Cookies");
    	String value = settings.value("acceptCookies", 2).toString();
    	index=Integer.valueOf(value);
    	m_acceptCookies = index==0?AcceptPolicy.Always:(index==1?AcceptPolicy.Never:AcceptPolicy.AcceptOnlyFromSitesNavigatedTo);
    	
    	value = settings.value("keepCookiesUntil", 0).toString();
    	index=Integer.valueOf(value);
    	m_keepCookies = index==0?KeepPolicy.KeepUntilExpire:(index==1?KeepPolicy.KeepUntilExit:KeepPolicy.KeepUntilTimeLimit);
    	
    	if (m_keepCookies == KeepPolicy.KeepUntilExit)
            setAllCookies(new ArrayList<QNetworkCookie>());
    
    }
    /**
	 * 释放资源，销毁对象。
	 */
    @Override
    protected void disposed() {
    	//System.out.println("MyCookieJar.java:---disposed()---释放资源 销毁对象");
    	if (m_loaded && m_keepCookies == KeepPolicy.KeepUntilExit)
            clear();
    	m_saveTimer.saveIfNecessary();
    	super.disposed();
    }
    
    private void purgeOldCookies()
    {
    	//System.out.println("MyCookieJar.java:---purgeOldCookies()");
        List<QNetworkCookie> cookies = allCookies();
        if (cookies.isEmpty())
            return;
        int oldCount = cookies.size();
        QDateTime now = QDateTime.currentDateTime();
        for (int i = cookies.size() - 1; i >= 0; --i) {
            if (!cookies.get(i).isSessionCookie() && cookies.get(i).expirationDate().compareTo(now)<0)
                cookies.remove(i);
        }
        if (oldCount == cookies.size())
            return;
        setAllCookies(cookies);
        cookiesChanged.emit();
    }
}
