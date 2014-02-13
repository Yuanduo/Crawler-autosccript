package com.liu.ui;

import com.trolltech.qt.core.*;
import com.trolltech.qt.core.Qt.*;
import com.trolltech.qt.gui.*;
/**
* ExLineEdit类，实现cookie管理窗口搜索框的界面搭建和部分核心功能。
* 
*/
public class ExLineEdit extends QWidget {
	protected QWidget m_leftWidget;
	public QLineEdit m_lineEdit;
	protected ClearButton m_clearButton;
	/**
	* 构造函数，初始化E型LineEdit。
	* @param parent
	* byVal 父窗口
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
	* 构建搜索框的界面。
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
	* 设置搜索框左边的部件。
	* @param widget
	* byVal 部件
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
	* 槽，重新修改搜索框的界面。
	* @param event
	* byVal 事件
	*/
	public void resizeEvent(QResizeEvent event) {
//		Q_ASSERT(m_leftWidget);
		updateGeometries();
		super.resizeEvent(event);
	}
	/**
	* 重新修改搜索框的界面。
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
	* 槽，当光标移进搜索框之后出现的事件。
	* @param event
	* byVal 事件
	*/
	public void focusInEvent(QFocusEvent event) {
		//System.out.println("ExLineEdit"+"  focusInEvent");
		m_lineEdit.event(event);
		super.focusInEvent(event);
	}
	/**
	* 槽，当光标移出搜索框之后出现的事件。
	* @param event
	* byVal 事件
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
	* 槽，当敲击键盘之后出现的事件。
	* @param event
	* byVal 事件
	*/
	public void keyPressEvent(QKeyEvent event) {
		//System.out.println("ExLineEdit"+"  keyPressEvent");
		m_lineEdit.event(event);
	}
	/**
	* 槽，一般事件对应到框搜索框实例中。
	* @param event
	* byVal 事件
	*/
	public boolean event(QEvent event) {
		if (event.type() == QEvent.Type.ShortcutOverride) {
			return m_lineEdit.event(event);
		}
		return  super.event(event);
//		return new QWidget().event(event);
	}
	/**
	* 槽，画出搜索框的界面。
	* @param event
	* byVal 事件
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
