package com.liu.ui;

import com.liu.core.MyCookieJar;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
/**
* CookieModel类，实现cookie管理窗口。
*/
public class CookiesDialog extends QDialog {
	private MyCookieJar m_cookieJar;
	private QSortFilterProxyModel m_proxyModel;
	
	private SearchLineEdit search;
	private QPushButton removeButton;
	private QPushButton removeAllButton;
	private QDialogButtonBox buttonBox;
	private EditTableView cookiesTable;
	private QSpacerItem horizontalSpacer;
	private QHBoxLayout hLayout;
	private QVBoxLayout vLayout;
	private QHBoxLayout hLayout_1;
	private QSpacerItem horizontalSpacer_1;
	/**
	* 构造函数，初始化cookie管理窗口。
	* @param cookieJar
	* byVal cookie
	* @param parent
	* byVal 父窗口
	*/
	CookiesDialog(MyCookieJar cookieJar, QWidget parent) {
		m_cookieJar = cookieJar;
		setWindowFlags(Qt.WindowType.Sheet);
		CookieModel model = new CookieModel(cookieJar, this);
		m_proxyModel = new QSortFilterProxyModel(this);
		
		search = new SearchLineEdit(this);
		removeButton = new QPushButton("Remove");
		removeAllButton = new QPushButton("RemoveAll");
		buttonBox = new QDialogButtonBox();
		cookiesTable = new EditTableView(this);
		horizontalSpacer = new QSpacerItem(10, 20,
				QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum);
		hLayout = new QHBoxLayout();
		vLayout = new QVBoxLayout();
		hLayout_1 = new QHBoxLayout();
		horizontalSpacer_1 = new QSpacerItem(10, 20,
				QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum);
		
		this.setGeometry(300, 300, 600, 370);

		hLayout.addItem(horizontalSpacer);
		hLayout.addWidget(search);
		vLayout.addLayout(hLayout);
		vLayout.addWidget(cookiesTable);
		hLayout_1.addWidget(removeButton);
		hLayout_1.addWidget(removeAllButton);
		hLayout_1.addItem(horizontalSpacer_1);
		hLayout_1.addWidget(buttonBox);
		vLayout.addLayout(hLayout_1);
		this.setLayout(vLayout);

		search.textChanged.connect(m_proxyModel,
				"setFilterFixedString(String)");
		removeButton.clicked.connect(cookiesTable, "removeOne()");
		removeAllButton.clicked.connect(cookiesTable, "removeAll()");
		
		m_proxyModel.setSourceModel(model);
		cookiesTable.verticalHeader().hide();
		cookiesTable
				.setSelectionBehavior(QAbstractItemView.SelectionBehavior.SelectRows);
		cookiesTable.setModel(m_proxyModel);
		cookiesTable.setAlternatingRowColors(true);
		cookiesTable.setTextElideMode(Qt.TextElideMode.ElideMiddle);
		cookiesTable.setShowGrid(false);
		cookiesTable.setSortingEnabled(true);
		QFont f = font();
		f.setPointSize(10);
		QFontMetrics fm = new QFontMetrics(f);
		int height = fm.height() + fm.height() / 3;
		cookiesTable.verticalHeader().setDefaultSectionSize(height);
		cookiesTable.verticalHeader().setMinimumSectionSize(-1);
		for (int i = 0; i < model.columnCount(); ++i) {
			int header = cookiesTable.horizontalHeader().sectionSizeHint(i);
			switch (i) {
			case 0:
				header = fm.width("averagehost.domain.com");
				break;
			case 1:
				header = fm.width("_session_id");
				break;
			case 4:
				header = fm.width(QDateTime.currentDateTime().toString(
						Qt.DateFormat.LocaleDate));
				break;
			}
			int buffer = fm.width("xx");
			header += buffer;
			cookiesTable.horizontalHeader().resizeSection(i, header);
		}
		cookiesTable.horizontalHeader().setStretchLastSection(true);
	}
	/**
	* 槽，关闭窗口时的操作。
	* @param event
	* byVal 事件
	*/
	public void closeEvent(QCloseEvent event) {
		m_cookieJar.save();
		event.accept();
	}

}
