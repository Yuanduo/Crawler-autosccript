package com.liu.ui;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
/**
* ClearButton�࣬ʵ��cookie�������������е����������
* 
*/
public class ClearButton extends QAbstractButton {
	/**
	* ���캯������ʼ�������ť��
	* @param parent
	* byVal ������
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
	* �ۣ����������ť��
	* @param event
	* byVal �¼�
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
	* �ۣ����ı��ɿձ仯Ϊ���ж���ַ���ʱ�������ť��ʾ������
	* @param text
	* byVal �ı�
	*/
	public void textChanged(final String text) {
		//System.out.println("ClearButton"+"  textChanged");
		setVisible(!text.isEmpty());
	}

}
