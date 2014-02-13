package com.liu.ui;

import com.trolltech.qt.core.QRect;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QPainter.RenderHint;
import com.trolltech.qt.webkit.QWebView;
/**
* MyWebView类，实现生成脚本初期设定翻页标签的显示工作。
* 
*/
public class MyWebView extends QWebView{
	protected QRect rect;
	
	public MyWebView(QWidget parent) {
	}
	public QRect getRect() {
		return rect;
	}
    public void setRect(QRect r) {
    	this.rect = r;
    }
	public void paintEvent(QPaintEvent event) {
    	super.paintEvent(event);
    	if (this.rect != null) {
    		QPainter painter = new QPainter(this);
    		QPen pen = new QPen();
            pen.setWidth(2);
            pen.setColor(new QColor(0, 0, 255, 127));
            painter.setPen(pen);
            painter.setRenderHint(RenderHint.Antialiasing,true);
            painter.drawRect(this.rect);
            painter.end();
    	}
    }
}
