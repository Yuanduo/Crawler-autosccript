package com.liu.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.webkit.QWebElement;

/**
 * JsGenerator类，实现生成脚本的核心代码。
 * 
 */
public class JsGenerator extends QMainWindow {

	public enum RefreshPolicy {
		RefreshAll, PatialRefresh
	}

	public enum PaginationPolicy {
		HasNextPageTag, NoNextPageTag
	}

	private boolean autoscroll;
	private int scrollNum;
	private int delayTime;
	private StringBuffer content;
	private File file;
	private FileWriter bw;
	private String encoding;

	private RefreshPolicy m_refresh;
	private PaginationPolicy m_pagination;

	/**
	 * 构造函数，初始化脚本生成器。
	 */
	public JsGenerator() {
		m_refresh = RefreshPolicy.PatialRefresh;
		m_pagination = PaginationPolicy.HasNextPageTag;
		content = new StringBuffer("");
		autoscroll = false;
		scrollNum = 0;
		delayTime = 2000;
	}

	/**
	 * 根据提供的标签，生成脚本的查找标签代码。
	 * 
	 * @param nextTag
	 *            byVal 标签
	 */
	public void addPaginationStruc(QWebElement nextTag) {
		String tag = nextTag.tagName();
        String set = tag+"_set";

        content.append("var ").append(set).append("= document.getElementsByTagName(\"").append(tag).append("\");\n");
        content .append( "for(var i=0;i<").append( set ).append(".length&&!hasnextpage;i++){\n");
        if(nextTag.hasAttribute("class"))
        {
            String clas = nextTag.attribute("class");
            content .append( "\tif(").append( set ).append("[i].className==\"").append( clas ).append("\"){\n");
        }

        content .append( "\tif(").append( set ).append("[i].textContent.indexOf(\"下一页\")!=-1 ||").append( set ).append("[i].textContent.indexOf(\"下页\")!=-1 ){\n");
        content .append( "\t\thasnextpage=1;\n");
        content .append( "\t\tsimulate(").append( set ).append("[i], \"click\");\n");

        content.append("\n\t\t}\n");
        content.append("\t\t\t}\n");
        if(nextTag.hasAttribute("class"))
            content.append("}\n");
        //writeFile(content);
	}

	/**
	*/
	public void addPaginationStruc(QWebElement current, QWebElement other) {

	}

	/**
	 * 打开或打开一个文件，默认编码模式为“GBK”。
	 * 
	 * @param fileName
	 *            byVal 文件名
	 */
	public void openFile(String filePathName) {
		file = new File(filePathName);
		// if (encoding==null)
		encoding = "GBK";
		openFile2(filePathName, encoding);// ,encoding);
	}

	/**
	 * 打开或打开一个文件，并开始生成脚本的初始代码。
	 * 
	 * @param fileName
	 *            byVal 文件名
	 * @param encoding
	 *            byVal 编码模式
	 */
	public void openFile2(String filePathName, String encoding) {
		this.encoding = encoding;
		// System.out.println(filePathName);
		file = new File(filePathName);

		if (!file.exists())
			try {
				file.createNewFile();

			} catch (IOException e) {
				e.printStackTrace();
			}
		try {
			bw = new FileWriter(filePathName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// if (!file.open(QIODevice.ReadWrite| QIODevice.Text))
		// return;

		if (scrollNum > 1)
			content.append("var count = 0;\n");

		content.append("function timeCount(){\n");
		if (getAutoScroll())
			content.append("scrollBy(0,10000);\n");
//		if (scrollNum > 1) {
			content.append("count++;\n");
			content.append("if(count<").append(scrollNum).append(")\n");
			content.append("setTimeout(\"timeCount()\",").append(delayTime)
					.append(");\n");
			content.append("else { \n");
//		}

		content.append("count = 0;\n setTimeout(\"findp()\",").append(delayTime).append(
				");\n}\n}\n");
		content.append("function findp(){\n");
		content.append("var hasnextpage=0;\n");
		if(refreshPolicy()==RefreshPolicy.PatialRefresh)
		{
			content.append("ww.downloadSource();\n");
		}else if(refreshPolicy()==RefreshPolicy.RefreshAll){
			content.append("ww.downloadSource_static();\n");
		}
		
		// writeFile(content);
	}

	/**
	 * 将脚本的全部代码写入到文件当中。
	 * 
	 * @param content
	 *            byVal 生成脚本全部代码
	 */
	public void writeFile(String content) {
		// System.out.println(content);
		try {
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// QTextStream out = new QTextStream(file.);
		// out.setCodec(encoding);
		// out.writeString(content);
		// out.flush();
		// file.size();
	}

	/**
	 * 生成脚本的结尾代码，关闭文件。
	 */
	public String closeFile() {
		content.append("if(hasnextpage==0){\n");
		content.append("ww.endOfPage();\n");
		content.append("return true;\n}\n");

		if (m_refresh == RefreshPolicy.PatialRefresh)
			content.append("timeCount();\n");

		content.append("}\ntimeCount();");
		writeFile(content.toString());
		// String array = file.readAll().toString();
		// file.close();
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	public final RefreshPolicy refreshPolicy() {
		return m_refresh;
	}

	public void setRefreshPolicy(RefreshPolicy policy) {
		m_refresh = policy;
	}

	public final PaginationPolicy paginationPolicy() {
		return m_pagination;
	}

	public void setPaginationPolicy(PaginationPolicy policy) {
		m_pagination = policy;
	}

	public void setAutoScroll(boolean scroll) {
		this.autoscroll = scroll;
	}

	public boolean getAutoScroll() {
		return this.autoscroll;
	}

	public void setDelayTime(int time) {
		this.delayTime = time;
	}

	public void setScrollNum(int num) {
		this.scrollNum = num;
	}

}
