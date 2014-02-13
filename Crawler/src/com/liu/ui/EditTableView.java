package com.liu.ui;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

/****************************************************************************
 ** 
 ** Copyright (C) 2011 Nokia Corporation and/or its subsidiary(-ies). All rights
 * reserved. Contact: Nokia Corporation (qt-info@nokia.com)
 ** 
 ** This file is part of the demonstration applications of the Qt Toolkit.
 ** 
 ** $QT_BEGIN_LICENSE:LGPL$ GNU Lesser General Public License Usage This file may
 * be used under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation and appearing in the file
 * LICENSE.LGPL included in the packaging of this file. Please review the
 * following information to ensure the GNU Lesser General Public License version
 * 2.1 requirements will be met:
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
 ** 
 ** In addition, as a special exception, Nokia gives you certain additional
 * rights. These rights are described in the Nokia Qt LGPL Exception version
 * 1.1, included in the file LGPL_EXCEPTION.txt in this package.
 ** 
 ** GNU General Public License Usage Alternatively, this file may be used under
 * the terms of the GNU General Public License version 3.0 as published by the
 * Free Software Foundation and appearing in the file LICENSE.GPL included in
 * the packaging of this file. Please review the following information to ensure
 * the GNU General Public License version 3.0 requirements will be met:
 * http://www.gnu.org/copyleft/gpl.html.
 ** 
 ** Other Usage Alternatively, this file may be used in accordance with the terms
 * and conditions contained in a signed written agreement between you and Nokia.
 ** 
 ** 
 ** 
 ** 
 ** 
 ** $QT_END_LICENSE$
 ** 
 ****************************************************************************/
/**
 * EditTableView类，实现cookie管理窗口中的cookie信息的删除工作。
 * 
 */
public class EditTableView extends QTableView {
	/**
	 * 构造函数，初始化。
	 * 
	 * @param parent
	 *            byVal 父窗口
	 */
	public EditTableView(QWidget parent) {
		super(parent);
	}

	@Override
	/**
	 */
	public void keyPressEvent(QKeyEvent event) {
		if ((event.key() == Qt.Key.Key_Delete.value() || event.key() == Qt.Key.Key_Backspace
				.value())
				&& model() != null) {
			removeOne();
		} else {
//			new QAbstractItemView().keyPressEvent(event);
//			keyPressEvent(event);
		}
	}

	/**
	 * 删除某个cookie。
	 */
	public void removeOne() {
//		System.out.println("Koremove");
		if (model() == null || selectionModel() == null)
			return;
		int row = currentIndex().row();
//		System.out.println(model().rowCount());
		model().removeRow(row, rootIndex());
//		System.out.println(model().rowCount());
		QModelIndex idx = model().index(row, 0, rootIndex());
		if (idx == null)
			idx = model().index(row - 1, 0, rootIndex());
		if (QItemSelectionModel.SelectionFlag.SelectCurrent != null)
			selectionModel().select(idx,
					QItemSelectionModel.SelectionFlag.SelectCurrent);
		else if (QItemSelectionModel.SelectionFlag.Rows != null)
			selectionModel()
					.select(idx, QItemSelectionModel.SelectionFlag.Rows);
	}

	/**
	 * 删除所有cookie。
	 */
	public void removeAll() {
//		System.out.println("KoremoveAll");
		if (model() != null)
			model().removeRows(0, model().rowCount(rootIndex()), rootIndex());
	}

}