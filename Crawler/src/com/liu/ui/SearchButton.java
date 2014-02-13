package com.liu.ui;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
/**
* SearchButton类，实现cookie搜索框查找cookie时的抽象搜索按钮。
* 
*/
public class SearchButton extends QAbstractButton {
	public QMenu m_menu = null;

	public QMenu getM_menu() {
		return m_menu;
	}
	/**
	* 构造函数，初始化抽象搜索按钮。
	* @param parent
	* byVal 事件
	*/
	public SearchButton(QWidget parent) {
		super(parent);
		setObjectName("SearchButton");
		// #ifndef QT_NO_CURSOR
//		setCursor(Qt.CursorShape.ArrowCursor);
		// #endif //QT_NO_CURSOR
		setFocusPolicy(Qt.FocusPolicy.NoFocus);
	}
	/**
	* 槽，点击鼠标时产生的事件。
	* @param event
	* byVal 事件
	*/
	public void mousePressEvent(QMouseEvent event) {
		if (m_menu != null && event.button() == Qt.MouseButton.LeftButton) {
			QWidget p = parentWidget();
			if (p != null) {
				QPoint r = p.mapToGlobal(new QPoint(0, p.height()));
				m_menu.exec(new QPoint(r.x() + height() / 2, r.y()));
			}
			event.accept();
		}
		super.mousePressEvent(event);
	}
	/**
	* 画出抽象搜索按钮。
	* @param event
	* byVal 事件
	*/
	public void paintEvent(QPaintEvent event) {
		// Q_UNUSED(event);
		QPainterPath myPath = new QPainterPath();

		int radius = (this.height() / 5) * 2;
		QRectF circle = new QRectF(height() / 3 - 1, height() / 4, radius,
				radius);
		myPath.addEllipse(circle);

		myPath.arcMoveTo(circle, 300);
		QPointF c = myPath.currentPosition();
		int diff = height() / 7;
		myPath.lineTo(qMin(width() - 2, (int) c.x() + diff), c.y() + diff);

		QPainter painter = new QPainter(this);
		painter.setRenderHint(QPainter.RenderHint.Antialiasing, true);
		painter.setPen(new QPen(QColor.darkGray, 2));
		painter.drawPath(myPath);

		if (m_menu != null) {
			QPainterPath dropPath = new QPainterPath();
			dropPath.arcMoveTo(circle, 320);
			QPointF c1 = dropPath.currentPosition();
			c1 = new QPointF(c1.x() + 3.5, c1.y() + 0.5);
			dropPath.moveTo(c1);
			dropPath.lineTo(c1.x() + 4, c1.y());
			dropPath.lineTo(c1.x() + 2, c1.y() + 2);
			dropPath.closeSubpath();
			painter.setPen(QColor.darkGray);
			painter.setBrush(QColor.darkGray);
			painter.setRenderHint(QPainter.RenderHint.Antialiasing, false);
			painter.drawPath(dropPath);
		}
		painter.end();
	}

	private double qMin(int i, int j) {
		if (i < j)
			return i;
		else
			return j;
	}

}
