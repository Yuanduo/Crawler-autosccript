package com.liu.ui;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
/**
* ClearButton类，实现cookie管理窗口搜索框中的清除操作。
* 
*/
public class ClearButton extends QAbstractButton {
	/**
	* 构造函数，初始化清除按钮。
	* @param parent
	* byVal 父窗口
	*/
	public ClearButton(QWidget parent) {
		super(parent);
		// #ifndef QT_NO_CURSOR
		//setCursor(Qt.CursorShape.ArrowCursor);
		// #endif // QT_NO_CURSOR
		setToolTip(tr("Clear"));
		setVisible(false);
		setFocusPolicy(Qt.FocusPolicy.NoFocus);
	}
	/**
	* 槽，画出清除按钮。
	* @param event
	* byVal 事件
	*/
	public void paintEvent(QPaintEvent event) {
		// Q_UNUSED(event);
		QPainter painter = new QPainter(this);
		int height = this.height();

		painter.setRenderHint(QPainter.RenderHint.Antialiasing, true);
		//QColor color = palette().color(QPalette.ColorRole.Mid);
		painter.setBrush(isDown() ? palette().color(QPalette.ColorRole.Dark)
				: palette().color(QPalette.ColorRole.Mid));
		painter.setPen(painter.brush().color());
		int size = width();
		int offset = size / 5;
		int radius = size - offset * 2;
		painter.drawEllipse(offset, offset, radius, radius);

		painter.setPen(palette().color(QPalette.ColorRole.Base));
		int border = offset * 2;
		painter.drawLine(border, border, width() - border, height - border);
		painter.drawLine(border, height - border, width() - border, border);
	}
	/**
	* 槽，当文本由空变化为含有多个字符的时候，清除按钮显示出来。
	* @param text
	* byVal 文本
	*/
	public void textChanged(final String text) {
		//System.out.println("ClearButton"+"  textChanged");
		setVisible(!text.isEmpty());
	}

}
