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
* SearchLineEdit�࣬ʵ��cookie������Ĳ��ֺ��Ĺ��ܡ�
* 
*/
public class SearchLineEdit extends ExLineEdit {

	private SearchButton m_searchButton = new SearchButton(this);
	private String m_inactiveText = tr("Search");
	public Signal1<String> textChanged = new Signal1<String>();
	/**
	* ���캯������ʼ��������
	* @param parent
	* byVal ������
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
	* �ۣ��������Ľ��档
	* @param event
	* byVal �¼�
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
	* �ۣ������޸�������Ľ��档
	* @param event
	* byVal �¼�
	*/
	public void resizeEvent(QResizeEvent event) {
		updateGeometries();
		super.resizeEvent(event);
	}
	/**
	* �����޸�������Ľ��档
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
