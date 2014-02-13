package com.liu.ui;

import java.util.ArrayList;
import com.liu.core.MyCookieJar;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.network.*;

/**
 * CookieModel类，实现cookie管理窗口中的显示cookie操作的核心代码。
 * 
 */
public class CookieModel extends QAbstractTableModel {
	public MyCookieJar m_cookieJar;

	/**
	 * 构造函数，初始化cookie操作。
	 * 
	 * @param parent
	 *            byVal 父窗口
	 */
	CookieModel(MyCookieJar cookieJar, QObject parent) {
		super(parent);
		m_cookieJar = cookieJar;
//		System.out.println(m_cookieJar.cookies());
		m_cookieJar.cookiesChanged.connect(this, "cookiesChanged()");
		m_cookieJar.load();
	}

	/**
	 * 给出cookie管理窗口当中的cookie信息的各列。
	 * 
	 * @param parent
	 *            byVal 父窗口
	 */
	public final Object headerData(int section, Qt.Orientation orientation,
			int role) {
		if (role == Qt.ItemDataRole.SizeHintRole) {
			QFont font = new QFont();
			font.setPointSize(15);
			QFontMetrics fm = new QFontMetrics(font);
			int height = fm.height() + fm.height() / 3;
			int width = fm.width(headerData(section, orientation,
					Qt.ItemDataRole.DisplayRole).toString());
			return new QSize(width, height);
		}

		if (orientation == Qt.Orientation.Horizontal) {
			if (role != Qt.ItemDataRole.DisplayRole)
				return new Object();
			switch (section) {
			case 0:
//				System.out.println("Website");
				return tr("Website");
			case 1:
//				System.out.println("Name");
				return tr("Name");
			case 2:
//				System.out.println("Path");
				return tr("Path");
			case 3:
//				System.out.println("Secure");
				return tr("Secure");
			case 4:
//				System.out.println("Expires");
				return tr("Expires");
			case 5:
//				System.out.println("Contents");
				return tr("Contents");
			default:

				return new Object();
			}
		}
		return super.headerData(section, orientation, role);
	}

	/**
	 * 给出cookie管理窗口当中的cookie信息。
	 * 
	 * @param parent
	 *            byVal 父窗口
	 */
	public final Object data(final QModelIndex index, int role) {
		ArrayList<QNetworkCookie> lst = null;
		if (m_cookieJar != null)
			lst = m_cookieJar.cookies();
//		System.out.println(m_cookieJar.cookies());
		if (index.row() < 0 || index.row() >= lst.size())
			return new Object();

		switch (role) {
		case Qt.ItemDataRole.DisplayRole:
		case Qt.ItemDataRole.EditRole: {
			QNetworkCookie cookie = lst.get(index.row());
			switch (index.column()) {
			case 0:
//				System.out.println(cookie.domain());
				return cookie.domain();
			case 1:
//				System.out.println(cookie.name());
				return cookie.name();
			case 2:
//				System.out.println(cookie.path());
				return cookie.path();
			case 3:
//				System.out.println(cookie.isSecure());
				return cookie.isSecure();
			case 4:
//				System.out.println(cookie.expirationDate());
				return cookie.expirationDate();
			case 5:
//				System.out.println(cookie.value());
				return cookie.value();
			}
		}
		case Qt.ItemDataRole.FontRole: {
			QFont font = new QFont();
			font.setPointSize(10);
			return font;
		}
		}

		return new Object();
	}

	/**
	 * 列数。
	 */
	public final int columnCount(final QModelIndex parent) {
		return (parent != null) ? 0 : 6;
	}

	/**
	 * 行数。
	 */
	public final int rowCount(final QModelIndex parent) {
		return (parent != null || m_cookieJar == null) ? 0 : m_cookieJar
				.cookies().size();
	}

	/**
	 * 删除某列。
	 * 
	 * @param row
	 *            byVal 某列
	 * @param count
	 *            byVal 数目
	 * @param parent
	 *            byVal 父窗口
	 */
	public boolean removeRows(int row, int count, final QModelIndex parent) {
		if (parent != null || m_cookieJar != null)
			return false;
		int lastRow = row + count - 1;
		beginRemoveRows(parent, row, lastRow);
		ArrayList<QNetworkCookie> lst = m_cookieJar.cookies();
		for (int i = lastRow; i >= row; --i) {
			lst.remove(i);
		}
		m_cookieJar.setCookies(lst);
		endRemoveRows();
		return true;
	}

	/**
	 * 槽，cookie发生变化时，重新加载。
	 */
	public void cookiesChanged() {
		reset();
	}

}
