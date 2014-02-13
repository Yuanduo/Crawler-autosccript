package com.liu.ui;

import com.trolltech.qt.core.*;
import com.trolltech.qt.core.Qt.*;
import com.trolltech.qt.gui.*;
/**
* ExLineEdit�࣬ʵ��cookie������������Ľ����Ͳ��ֺ��Ĺ��ܡ�
* 
*/
public class ExLineEdit extends QWidget {
	protected QWidget m_leftWidget;
	public QLineEdit m_lineEdit;
	protected ClearButton m_clearButton;
	/**
	* ���캯������ʼ��E��LineEdit��
	* @param parent
	* byVal ������
	*/
	ExLineEdit(QWidget parent) {
		super(parent);
		
		m_leftWidget = null;
		m_lineEdit = new QLineEdit(this);
		m_clearButton = new ClearButton(this);
		
		setFocusPolicy(m_lineEdit.focusPolicy());
		setAttribute(Qt.WidgetAttribute.WA_InputMethodEnabled);
		setSizePolicy(m_lineEdit.sizePolicy());
		setBackgroundRole(m_lineEdit.backgroundRole());
		setMouseTracking(true);
		setAcceptDrops(true);
		setAttribute(Qt.WidgetAttribute.WA_MacShowFocusRect, true);
		QPalette p = m_lineEdit.palette();
		setPalette(p);

		// line edit
		m_lineEdit.setFrame(false);
		m_lineEdit.setFocusProxy(this);
		m_lineEdit.setAttribute(Qt.WidgetAttribute.WA_MacShowFocusRect, false);
		QPalette clearPalette = m_lineEdit.palette();
		clearPalette.setBrush(QPalette.ColorRole.Base, new QBrush(
				Qt.GlobalColor.transparent, null));
		m_lineEdit.setPalette(clearPalette);

		// clearButton
		m_clearButton.clicked.connect(m_lineEdit, "clear()");
		m_lineEdit.textChanged.connect(m_clearButton, "textChanged(String)");
		
		//updateGeometries();
		
	}
	/**
	* ����������Ľ��档
	*/
	public void Buju() {
		QStyleOptionFrameV2 panel = new QStyleOptionFrameV2();
		initStyleOption(panel);
		QRect rect = style().subElementRect(
				QStyle.SubElement.SE_LineEditContents, panel, this);

		int m_leftWidgetHeight = m_leftWidget.height();
		m_leftWidget.setGeometry(rect.x() + 2, rect.y()
				+ (this.height() - m_leftWidgetHeight) / 2, m_leftWidget.width(),
				m_leftWidget.height());

		int clearButtonWidth = this.height();
		m_lineEdit.setGeometry(/*m_leftWidget.x() +  m_leftWidget.width()*/this.height()/2, -5,
				this.width()/* - clearButtonWidth - m_leftWidget.width()*/, this.height());

		m_clearButton.setGeometry(this.width() , -2,
				clearButtonWidth-5, this.height()-5);
	}
	/**
	* ������������ߵĲ�����
	* @param widget
	* byVal ����
	*/
	public void setLeftWidget(QWidget widget) {
		m_leftWidget = widget;
		Buju();
	}
	/**
	*/
	public final QWidget leftWidget() {
		return m_leftWidget;
	}
	/**
	* �ۣ������޸�������Ľ��档
	* @param event
	* byVal �¼�
	*/
	public void resizeEvent(QResizeEvent event) {
//		Q_ASSERT(m_leftWidget);
		updateGeometries();
		super.resizeEvent(event);
	}
	/**
	* �����޸�������Ľ��档
	*/
	public void updateGeometries() {
		QStyleOptionFrameV2 panel = new QStyleOptionFrameV2();
		initStyleOption(panel);
		QRect rect = style().subElementRect(
				QStyle.SubElement.SE_LineEditContents, panel, this);

		int height = rect.height();
		int width = rect.width();

		int m_leftWidgetHeight = m_leftWidget.height();
		m_leftWidget.setGeometry(rect.x() + 2, rect.y()
				+ (height - m_leftWidgetHeight) / 2, m_leftWidget.width(),
				m_leftWidget.height());
		System.out.println(width+" "+this.width()+" "+height+" "+this.height());

		int clearButtonWidth = height;//this.height();
		m_lineEdit.setGeometry(m_leftWidget.x() + m_leftWidget.width(), 0,
				width - clearButtonWidth - m_leftWidget.width(), height);//this.height());

		m_clearButton.setGeometry(/*this.width()*/width - clearButtonWidth, 0,
				clearButtonWidth, height);//this.height());
	}
	/**
	 * 
	*/
	public final void initStyleOption(QStyleOptionFrameV2 option) {
		option.initFrom(this);
		option.setRect(contentsRect());
		option.setLineWidth(style().pixelMetric(
				QStyle.PixelMetric.PM_DefaultFrameWidth, option, this));
		option.setMidLineWidth(0);
		if (QStyle.StateFlag.State_Sunken != null)
			option.setState(QStyle.StateFlag.State_Sunken);
		if (m_lineEdit.isReadOnly() && QStyle.StateFlag.State_ReadOnly != null)
			option.setState(QStyle.StateFlag.State_ReadOnly);
		// #ifdef QT_KEYPAD_NAVIGATION
//		if (hasFocus() && QStyle.StateFlag.State_HasFocus != null)
//			option.setState(QStyle.StateFlag.State_HasFocus);
		// #endif
		option.setFeatures(QStyleOptionFrameV2.FrameFeature.None);
	}
	/**
	*/
	public final QSize sizeHint() {
		m_lineEdit.setFrame(true);
		QSize size = m_lineEdit.sizeHint();
		m_lineEdit.setFrame(false);
		return size;
	}
	/**
	* �ۣ�������ƽ�������֮����ֵ��¼���
	* @param event
	* byVal �¼�
	*/
	public void focusInEvent(QFocusEvent event) {
		//System.out.println("ExLineEdit"+"  focusInEvent");
		m_lineEdit.event(event);
		super.focusInEvent(event);
	}
	/**
	* �ۣ�������Ƴ�������֮����ֵ��¼���
	* @param event
	* byVal �¼�
	*/
	public void focusOutEvent(QFocusEvent event) {
		//System.out.println("ExLineEdit"+"  focusOutEvent");
		m_lineEdit.event(event);

		if (m_lineEdit.completer() != null) {
			m_lineEdit.completer().activated.connect(m_lineEdit, "setText(String)");
			m_lineEdit.completer().highlighted.connect(m_lineEdit,
					"_q_completionHighlighted(String)");
		}
		super.focusOutEvent(event);
	}
	/**
	* �ۣ����û�����֮����ֵ��¼���
	* @param event
	* byVal �¼�
	*/
	public void keyPressEvent(QKeyEvent event) {
		//System.out.println("ExLineEdit"+"  keyPressEvent");
		m_lineEdit.event(event);
	}
	/**
	* �ۣ�һ���¼���Ӧ����������ʵ���С�
	* @param event
	* byVal �¼�
	*/
	public boolean event(QEvent event) {
		if (event.type() == QEvent.Type.ShortcutOverride) {
			return m_lineEdit.event(event);
		}
		return  super.event(event);
//		return new QWidget().event(event);
	}
	/**
	* �ۣ�����������Ľ��档
	* @param event
	* byVal �¼�
	*/
	public void paintEvent(QPaintEvent event) {
		QPainter p = new QPainter(this);
		QStyleOptionFrameV2 panel = new QStyleOptionFrameV2();
		initStyleOption(panel);
		style().drawPrimitive(QStyle.PrimitiveElement.PE_PanelLineEdit, panel,
				p, this);
		p.end();
	}
	/**
	*/
	public final Object inputMethodQuery(InputMethodQuery property) {
		return m_lineEdit.inputMethodQuery(property);
	}
	/**
	*/
	public void inputMethodEvent(QInputMethodEvent e) {
		m_lineEdit.event(e);
	}

}
