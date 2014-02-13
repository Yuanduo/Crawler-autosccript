package com.liu.core;

import java.util.*;
import java.util.zip.GZIPInputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.trolltech.qt.QSignalEmitter;

/**
 * httpGet�࣬ʵ�ֶ����ӵ�ץȡ�����档
 * 
 */
public class httpGet extends QSignalEmitter implements Runnable {

	private ArrayList<String> filter;
	private ArrayList<String> containAll;
	private ArrayList<String> containOr;
	private ArrayList<String> priorityString;
	private long urlNumber;
	private long num;
	private StringBuffer listShow;
	private StringBuffer list;
	private String urlRoot;
	private int filterLen;
	private int containAllLen;
	private int containOrLen;
	private int timeout;
	private int priorityStringLen;
	private int start, end;
	private String saveDir;
	private Hashtable<String, Integer> hash;
	private String startUrl;
	public Signal0 finished = new Signal0();
	public Signal1<String> newStr = new Signal1<String>();

	/**
	 * ���캯������ʼ������ץȡ����
	 */
	public httpGet() {	
		this.saveDir = "";
		this.list = new StringBuffer();
		this.listShow = new StringBuffer();
		this.filterLen = 0;
		this.containAllLen = 0;
		this.containOrLen = 0;
		this.priorityStringLen = 0;
		this.urlNumber = 0;
		this.num = 0;
		this.timeout = 0;
		this.filter = new ArrayList<String>();
		this.containAll = new ArrayList<String>();
		this.containOr = new ArrayList<String>();
		this.priorityString = new ArrayList<String>();
		this.hash = new Hashtable<String, Integer>();
	}

	/**
	 * ����ץȡ��������
	 */
	public void setNumber(long n) {
		this.urlNumber = n;
		this.num = 0;
	}

	/**
	 * ������ȡHtml�������ʱ�ӡ�
	 * 
	 * @param time
	 *            byVal ��
	 */
	public void setTime(float time) {
		this.timeout = (int) (time * 1000);
	}

	/**
	 * ����ԭʼUrl��
	 * 
	 * @param str
	 *            byVal Url
	 */
	private String rootReturn(String str) {	
	
		int i;
		for (i = 7; i < str.length(); i++)
			if (str.charAt(i) == '/')
				break;
		return str.substring(0, i);
	}

	/**
	 * ����Html����
	 * 
	 * @param Url
	 *            byVal ����ȡHtml��Url
	 */
	public BufferedReader downloadFile(String Url) {
			this.urlRoot = this.rootReturn(Url);
		URL url = null;
		try {
			url = new URL(Url);
			try {

				System.out.println("�����ӣ�"+url);
				URLConnection urlConn = url.openConnection(); // ����վ����s
				if (this.timeout != 0) {
					urlConn.setConnectTimeout(this.timeout);
					urlConn.setReadTimeout(this.timeout);
				}
				urlConn.setRequestProperty("User-Agent", "Mozilla/5.0");
				String Encode = urlConn.getContentEncoding();
				InputStream in = urlConn.getInputStream();
				if (Encode != null) {
					if (Encode.toLowerCase().indexOf("gzip") >= 0) {
						in = new GZIPInputStream(in);
					}
				}
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));// ʵ����������������ȡ��ҳ����
				return br;
			} catch (IOException eio1) {
				System.out.println(eio1);
				return null;
			}

		} catch (MalformedURLException eurl) {
			System.out.println(eurl);
			return null;
		}
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
	 * ��֤�Ƿ����������������
	 * 
	 * @param str
	 *            byVal ��������//�����С�����������ʱ������0�����򷵻�1	 	
	 */
	private int filterCheck(String str) {
	    int i;
		str = str.toLowerCase();
		if (this.filterLen == 0)
			return 1;
		else
			for (i = 0; i < this.filterLen
					&& str.indexOf(this.filter.get(i).toLowerCase(), 0) < 0; i++)
				;
		if (i == this.filterLen)
			return 1;
		else
			return 0;
	}

	/**
	 * ��֤�Ƿ�������ɰ��������������С��ɰ�������ʱ����1�����򷵻�0,  �ǻ�Ĺ�ϵ,���ٰ�������һ�����ɰ����������ַ������ǺϷ���
	 *
	 * @param str
	 *            byVal �ɰ�����
	 */
	private int containOrCheck(String str) {
		int i;
		str = str.toLowerCase();
		if (this.containOrLen == 0)
			return 1;
		else {
			for (i = 0; i < this.containOrLen; i++)
				if (str.indexOf(this.containOr.get(i).toLowerCase(), 0) >= 0)
					return 1;
		}
		return 0;
	}

	/**
	 * ��֤�Ƿ�������ذ������������ַ����к������С��ذ�������ʱ������1�����򷵻�0�����Ȱ�������֮������Ĺ�ϵ
	 * 
	 * @param str
	 *            byVal �ذ�����
	 */
	private int containAllCheck(String str) {	
		int i;
		str = str.toLowerCase();
		if (this.containAllLen == 0)
			return 1;
		else
			for (i = 0; i < this.containAllLen
					&& str.indexOf(this.containAll.get(i).toLowerCase(), 0) >= 0; i++)
				;
		if (i == (this.containAllLen))
			return 1;
		else
			return 0;
	}

	/**
	 * ��֤�Ƿ��������չ������
	 * 
	 * @param str
	 *            byVal ��չ����
	 */
	private int priorityCheck(String str) {
	str = str.toLowerCase();
		if (this.priorityStringLen == 0)
			return 0;
		else {
			for (int i = 0; i < this.priorityStringLen; i++)
				if (str.indexOf(this.priorityString.get(i).toLowerCase(), 0) >= 0)
					return 1;
		}
		return 0;
	}

	/**
	 * ��ӡ�����������
	 * 
	 * @param str
	 *            byVal ��������
	 */
	
	//���ȵ���setFilterLen(0)�����filter��Ȼ��һ����һ��������ӡ�������addFilter��������������
	
	public void addFilter(String str) {//���ȵ���setFilterLen(0)�����filter��Ȼ��һ����һ��������ӡ�������addFilter
		this.filter.add(str);
		this.filterLen += 1;
	}

	/**
	 * ɾ��������������
	 * 
	 * @param str
	 *            byVal ��������
	 */
	public void deleteFilter(String str) {
		if (this.filterLen == 0)
			return;
		this.filter.remove(str);
		this.filterLen -= 1;
	}

	/**
	 * ��ӡ���չ������
	 * 
	 * @param str
	 *            byVal ��չ����
	 */
	public void addpriorityString(String str) {
		this.priorityStringLen += 1;
		this.priorityString.add(str);
	}

	/**
	 * ɾ������չ������
	 * 
	 * @param str
	 *            byVal ��չ����
	 */
	public void deletepriorityString(String str) {
		if (this.priorityStringLen == 0)
			return;
		this.priorityString.remove(str);
		this.priorityStringLen -= 1;
	}

	/**
	 * ��ӡ��ذ�������
	 */
	public void addContainAll(String str) {
	    this.containAll.add(str);
		this.containAllLen += 1;
	}

	/**
	 * ɾ�����ذ�������
	 * 
	 * @param str
	 *            byVal �ذ�����
	 */
	public void deleteContainAll(String str) {
	    if (this.containAllLen == 0)
			return;
		this.containAll.remove(str);
		this.containAllLen -= 1;
	}

	/**
	 * ��ӡ��ɰ�������
	 * 
	 * @param str
	 *            byVal �ɰ�����
	 */
	public void addContainOr(String str) {
	   this.containOr.add(str);
		this.containOrLen += 1;
	}

	/**
	 * ɾ�����ɰ�������
	 * 
	 * @param str
	 *            byVal �ɰ�����
	 */
	public void deleteContainOr(String str) {
	   if (this.containOrLen == 0)
			return;
		this.containOr.remove(str);
		this.containOrLen -= 1;
	}

	/**
	 * ��֤���Ӵ��Ƿ�Ϊ��͵��������ӡ�
	 * 
	 * @param str
	 *            byVal ���Ӵ�
	 */
	private int remove(String s) {		
	int i;
		String camp[] = { ".ico", ".css", ".swf", ".jpg", ".png", ".pdf",
				".jpeg", ".gif" };
		for (i = 0; i < 8; i++)
			if (s.endsWith(camp[i]))
				return 0;
		return 1;
	}

	/**
	 * �����Ӵ����У���ȡ��ǩtag��elementԪ�ص�ֵ��
	 * 
	 * @param str
	 *            byVal ���Ӵ�
	 * @param tag
	 *            byVal ��ǩ
	 * @param element
	 *            byVal Ԫ��
	 */
	 private String getLink(String str, String tag, String element)
	  {
	    int elementEnd;
	    int elementBegin = -1;
	    boolean bb=str.contains("<body");
	    boolean cc=str.contains("/html");	
	   System.out.println("str:"+str);
	      if(!(bb&cc))//����ҳ��
	      str=htmlToLowerCase(str);

	    int flag = 0;
	    if ((str.contains("'")) || (str.contains("\"")))
	    {
	      str = str.replaceAll("'", "\"");
	      flag = 1;
	    }

	    int begin = str.indexOf('<' + tag, 0);
	    if (-1 == begin) {
	      this.start = 0;
	      this.end = 0;
	      return "";
	    }
	    int end_0 = str.indexOf(tag + '>', begin);
	    if (-1 != end_0)
	      this.end = (end_0 + tag.length() + 1);
	    else
	      this.end = (begin + tag.length() + 1);
	    String str_0 = str.substring(begin, this.end);

	    if (flag == 1)
	      elementBegin = str_0.indexOf(element + "=\"", 0);
	    else { elementBegin = str_0.indexOf(element + "=", 0);
	    }

	    if (-1 == elementBegin) {
	      this.start = begin;
	      return "";
	    }
	    if (flag == 1)
	      elementEnd = str_0.indexOf("\"", elementBegin + element.length() + 3);
	    else elementEnd = str_0.indexOf(">", elementBegin + element.length() + 3);
	    this.start = begin;
	    if (-1 != end_0)
	      this.end = (end_0 + tag.length() + 1);
	    else
	      this.end = elementEnd;
	    if (elementBegin + element.length() + 2 >= elementEnd)
	    {
	      String result = "";
	      if (flag == 1) {
	        result = str_0.substring(elementBegin + element.length() + 2, 
	          elementBegin + element.length() + 3);
	      } else {
	        result = str_0.substring(elementBegin + element.length() + 1, 
	          elementBegin + element.length() + 3);
	        result = deleteOtherAttr(result);
	      }
	      return result;
	    }

	    String result = "";
	    if (flag == 1) {
	      result = str_0.substring(elementBegin + element.length() + 2, elementEnd);
	    } else {
	      result = str_0.substring(elementBegin + element.length() + 1, elementEnd);
	      result = deleteOtherAttr(result);
	    }

	    return result;
	  }

	/**
	 * ��Html�����У���ȡ���ӡ�
	 * 
	 * @param bw
	 *            byVal Html��
	 */
	private void spider(BufferedReader bw) {	
		int returnRoot = 0;
		int linkOver;
		int flag_priority = 0;
		StringBuffer temp;
		String s, read;
		try {
			if (bw == null)
				return;
			while ((read = bw.readLine()) != null) {
				temp = new StringBuffer(read);
				// System.out.println(temp);
				while (true) {
					linkOver = 1;
					// System.out.println(temp.toString());
					while (true) {
						s = getLink(temp.toString(), "iframe", "src");
						temp.delete(this.start, this.end);
						if (this.start != 0 || this.end != 0) {
							linkOver = 0;
							break;
						}

						s = getLink(temp.toString(), "span", "usercard");
						temp.delete(this.start, this.end);
						if (s != "") {
							if (s.indexOf("id=") == 0) {
								s.replace("id=", "/");
							}
						}
						if (this.start != 0 || this.end != 0) {
							linkOver = 0;
							break;
						}

						s = getLink(temp.toString(), "a", "href");
						temp.delete(this.start, this.end);
						if (this.start != 0 || this.end != 0) {
							linkOver = 0;
							break;
						}
						break;
					}

					if (linkOver == 1)
						break;

					if (0 == s.trim().indexOf("http", 0))
						returnRoot = 0;
					else if (0 == s.indexOf("/", 0) && s.length() > 1)
						returnRoot = 1;
					else
						continue;
					if (1 == returnRoot)
						s = this.urlRoot.concat(s);

					if (this.remove(s) == 0)
						continue;

//					if (s.endsWith("/"))
//						s = s.substring(0, s.length() - 1);

					if (this.filterCheck(s) == 0)						
						continue;
				//	int tempCheck = 0;
					if (this.containAllCheck(s) == 0
							|| this.containOrCheck(s) == 0) {
//						tempCheck = this.priorityCheck(s);
						if (this.priorityCheck(s) == 1)
							flag_priority = 1;
						else
							continue;
					} else
						if (this.priorityCheck(s) == 1)
							flag_priority = 2;

					int strValue = this.BKDRHash(s.toLowerCase());
					if (this.hash.containsKey(s.toLowerCase()))
						continue;
					else
						this.hash.put(s.toLowerCase(), strValue);

					if (this.num < this.urlNumber || this.urlNumber == 0) {
						if (flag_priority == 0 || flag_priority ==2) {
							this.num += 1;
							this.listShow.append(s + "\r\n");
							newStr.emit(s);
						}
						if (flag_priority == 1 || flag_priority ==2)
							this.list.append(s + '\n');
						flag_priority = 0;
					} else
						return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * ���ñ����ַ��
	 * 
	 * @param saveDir
	 *            byVal �����ַ
	 */
	public void setSaveDir(String saveDir) {
	this.saveDir = saveDir;
	}

	/**
	 * ����ץȡ���������Ӽ���
	 * 
	 * @param list
	 *            byVal �������Ӽ�
	 */
	private void saveList(String list) {
		if (this.saveDir.compareTo("") == 0 || this.saveDir == null)
			return;
		File file = new File(this.saveDir);
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		FileWriter bw;
		try {
			bw = new FileWriter(this.saveDir);
			bw.write(list);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFilterLen(int filterLen) {
		this.filterLen = filterLen;
		this.filter.clear();
	}

	public void setContainAllLen(int containAllLen) {
		this.containAllLen = containAllLen;
		this.containAll.clear();
	}

	public void setContainOrLen(int containOrLen) {
		this.containOrLen = containOrLen;
		this.containOr.clear();
	}

	public void setPriorityStringLen(int priorityStringLen) {
		this.priorityStringLen = priorityStringLen;
		this.priorityString.clear();
	}

	public String getListShow() {
		return listShow.toString();
	}

	public void run() {	
		if (startUrl == "") {
			return;
		}
		this.list = new StringBuffer();
		this.listShow = new StringBuffer();
		this.hash.clear();
		this.spider(this.downloadFile(startUrl));
		if (this.num >= this.urlNumber || this.urlNumber == 0) {
			hash.clear();
			this.saveList(listShow.toString());
			finished.emit();
			return;
		}
		String str;
		int end;
		while (this.num < this.urlNumber) {
			end = this.list.indexOf("\n", 0);
			if (-1 == end)
				break;
			str = this.list.substring(0, end);
			this.list.delete(0, end + 1);
			this.spider(this.downloadFile(str));
			Thread.yield();
		}

		this.saveList(listShow.toString());
		finished.emit();
	}

	public void setStartUrl(String startUrl) {
	
		this.startUrl = startUrl;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public long getNum() {
		return num;
	}

	public  String htmlToLowerCase(String string)
	{
		
		String array[]=string.split(">");
		String result="";
		 
		for(String res:array )
		{	
		 res=res.trim()+">";
	//	 System.out.println("����������"+res);
		 String res2=res+"";
		 int x1_lef=res2.indexOf("<");//��ǩ��ʼ����
		  int flag=0;
		 if(x1_lef!=-1&&x1_lef!=0)
		 {//���ǿ�ʼ��ǩ
			 res=res2.substring(0,x1_lef);
			 res2=res2.substring(x1_lef,res2.length());//������<��ǩ��ʼ	
			 flag=1;
			 x1_lef=res2.indexOf("<");//��ǩ��ʼ����
		 }
		
		 int x1_blank=res2.indexOf(" ");//��һ���ո��λ��
		 int x1_right=res2.indexOf(">");//��һ����ǩ�رշ���
		 
		 if(x1_blank<x1_right&&x1_blank!=-1&&x1_lef!=-1)
		 {
			 String ss=res2.substring(x1_lef,x1_blank);
			 String ss2=res2.substring(x1_blank,res2.length());
			 res2=ss.toLowerCase()+ss2;
		 }else if(x1_lef!=-1&&x1_right!=-1)
		 {
			 String ss=res2.substring(x1_lef,x1_right);
			 String ss2=res2.substring(x1_right,res2.length());
			 res2=ss.toLowerCase()+ss2;
		 }
		  
	     if(flag==1)
	     {
	    	 res2=res+res2;
	     }
	     result=result+res2;
		}
 
	  return result;
	}
	
	
	public String deleteOtherAttr(String res)
	  {
	    if (res.contains(" "))
	    {
	      int blank = res.indexOf(" ");

	      res = res.substring(0, blank);
	    }

	    return res.trim();
	  }
	
}
