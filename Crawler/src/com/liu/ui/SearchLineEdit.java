package com.liu.ui;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

/*
 SearchLineEdit is an enhanced QLineEdit
 - A Search icon on the left with optional menu
 - When there is no text and doesn't have focus an "inactive text" is displayed
 - When there is text a clear button is displayed on the right hand side
 */
/**
* SearchLineEdit类，实现cookie搜索框的部分核心功能。
* 
*/
public class SearchLineEdit extends ExLineEdit {

	private SearchButton m_searchButton = new SearchButton(this);
	private String m_inactiveText = tr("Search");
	public Signal1<String> textChanged = new Signal1<String>();
	/**
	* 构造函数，初始化搜索框。
	* @param parent
	* byVal 父窗口
	*/
	public SearchLineEdit(QWidget parent) {
		super(parent);
		setLeftWidget(m_searchButton);
		//m_lineEdit.setText(m_inactiveText);
		QSizePolicy policy = sizePolicy();
		setSizePolicy(QSizePolicy.Policy.Preferred, policy.verticalPolicy());
		m_lineEdit.textChanged.connect(textChanged);
//		lineEdit()
	}
	/**
	* 槽，搭建搜索框的界面。
	* @param event
	* byVal 事件
	*/
	public void paintEvent(QPaintEvent event) {
		if (m_lineEdit.text().isEmpty() && !hasFocus()
				&& !m_inactiveText.isEmpty()) {
//			super.paintEvent(event);
//			System.out.println("paintEvent:"+event.toString());
			QStyleOptionFrameV2 panel = new QStyleOptionFrameV2();
			initStyleOption(panel);
			QRect r = style().subElementRect(
					QStyle.SubElement.SE_LineEditContents, panel, this);
			QFontMetrics fm = fontMetrics();
//			int horizontalMargin = this.x();
//			QRect lineRect = new QRect(horizontalMargin + r.x(), r.y()
//					+ (r.height() - fm.height() + 1) / 2, r.width() - 2
//					* horizontalMargin, fm.height());
			QRect lineRect = new QRect(20, r.y() + (r.height() - fm.height() + 1) / 2, 93, fm.height());
			QPainter painter = new QPainter(this);
			painter.setPen(palette().brush(QPalette.ColorGroup.Disabled,
					QPalette.ColorRole.Text).color());
			painter.drawText(lineRect, Qt.AlignmentFlag.AlignLeft.value()
					| Qt.AlignmentFlag.AlignVCenter.value(), m_inactiveText);
//			painter.dispose();
		} else {
			super.paintEvent(event);
		}
	}
	/**
	* 槽，重新修改搜索框的界面。
	* @param event
	* byVal 事件
	*/
	public void resizeEvent(QResizeEvent event) {
		updateGeometries();
		super.resizeEvent(event);
	}
	/**
	* 重新修改搜索框的界面。
	*/
	public void updateGeometries() {
		int menuHeight = height();
		int menuWidth = menuHeight + 1;
		if (m_searchButton.getM_menu() == null)
			menuWidth = (menuHeight / 5) * 4;
		m_searchButton.resize(new QSize(menuWidth, menuHeight));
	}

	public final String inactiveText() {
		return m_inactiveText;
	}

	public void setInactiveText(final String text) {
		m_inactiveText = text;
	}

	public void setMenu(QMenu menu) {
		if (m_searchButton.getM_menu() != null)
			m_searchButton.m_menu.clear();
		m_searchButton.m_menu = menu;
		updateGeometries();
	}

	public final QMenu menu() {
		if (m_searchButton.getM_menu() == null) {
			m_searchButton.m_menu = new QMenu(m_searchButton);
			if (isVisible())
				this.updateGeometries();
		}
		return m_searchButton.m_menu;
	}
}
