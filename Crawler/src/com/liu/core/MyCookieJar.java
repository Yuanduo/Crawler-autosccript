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
* Cookie�࣬ʵ�ֶ�Cookie�Ķ�ȡ�����桢��Ӻ�ɾ���Ȳ�����
*
*/
public class MyCookieJar extends QNetworkCookieJar{
	/*�����Ƿ��Ѿ���ȡCookie�ı�־*/
	private boolean m_loaded;
	/*Cookie�Ľ��ղ���*/
	private AcceptPolicy m_acceptCookies;
	/*Cookie�ı������*/
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
	* ���췽������ʼ��һ���յ�cookie jar��
	*/
	public MyCookieJar() {
		
		super();
		
		//System.out.println("MyCookieJar.java:---���캯���������Cookie jar");
		m_loaded = false;
		m_acceptCookies = AcceptPolicy.AcceptOnlyFromSitesNavigatedTo;
		m_saveTimer = new AutoSaver(this);
	}
//	setAllCookies and allCookies are final method that cannot be override
	/**
	 * ��ȡCookie�ļ�������QNetworkCookie����
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
	 * �Ƴ�SessionCookie����ʣ���Cookie�������ļ��С�
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
	 * �������Cookie��
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
	 * ���ݱ�����Ժͽ��ղ��ԣ���cookieList�е�Cookie��ѡ���Ե���ӵ���ǰ��cookie jar�У�
	 * ���·��Ĭ����url�е�һ�¡�
	 * @param cookieList
	 * @param url
	 * @return �Ƿ����óɹ�
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
	 * ��������Cookie��
	 */
	public ArrayList<QNetworkCookie> cookies() {
		//System.out.println("MyCookieJar.java:---cookies() ---��������cookies");
		if(!m_loaded)
			load();
		return (ArrayList<QNetworkCookie>) allCookies();
	}
	/**
	 * ����Cookie��
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
	 * ���ؽ��ղ��ԡ�
	 */
	public final AcceptPolicy acceptPolicy() {
		//System.out.println("MyCookieJar.java:--- acceptPolicy()--���ؽ��ܲ���");
		if(!m_loaded)
			load();
		return m_acceptCookies;
	}
	/**
	 * ���ý��ղ��ԡ�
	 * @param policy
	 */
    void setAcceptPolicy(AcceptPolicy policy) {
    	//System.out.println("MyCookieJar.java:---setAcceptPolicy(AcceptPolicy policy)--���ý��ܲ���");
    	if (!m_loaded)
            load();
        if (policy == m_acceptCookies)
            return;
        m_acceptCookies = policy;
        m_saveTimer.changeOccurred();
    }
    /**
	 * ���ر�����ԡ�
	 */
    public final KeepPolicy keepPolicy() {
    	//System.out.println("MyCookieJar.java:---keepPolicy()--���ر������");
    	
    	if(!m_loaded)
			load();
		return m_keepCookies;
    }
    /**
	 * ���ñ�����ԡ�
	 * @param policy
	 */
    void setKeepPolicy(KeepPolicy policy) {
    	//System.out.println("MyCookieJar.java:---setKeepPolicy(KeepPolicy policy)--���ñ������");
    	if (!m_loaded)
            load();
        if (policy == m_keepCookies)
            return;
        m_keepCookies = policy;
        m_saveTimer.changeOccurred();
    }
    /**
	 * ��ȡ�����ļ���Cookie�����������Ϣ������Cookie�ı�����Ժͽ��ղ��ԡ�
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
	 * �ͷ���Դ�����ٶ���
	 */
    @Override
    protected void disposed() {
    	//System.out.println("MyCookieJar.java:---disposed()---�ͷ���Դ ���ٶ���");
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
