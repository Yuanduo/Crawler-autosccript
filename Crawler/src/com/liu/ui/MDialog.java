package com.liu.ui;

import com.liu.core.httpGet;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextEdit;

/**
 * MDialog类，实现链接抓取对可用链接抓取的过滤条件，构建窗口界面，添加或删除过滤串、必含串、可含串、可展开串。
 * 
 */
public class MDialog extends QDialog {

	public httpGet getter;
	private int Flag;
	private QTextEdit area1 = new QTextEdit(this);
	private QLabel inputLabel = new QLabel("输入，请以回车相隔");
	private QPushButton addBtn = new QPushButton("确定并保存");
	private QPushButton delBtn = new QPushButton("清空");
	private QGridLayout mainLayout = new QGridLayout();

	/**
	 * 构造函数，初始化。
	 * 
	 * @param flag
	 *            byVal 本窗口类型，1：不包含串，2：必包含串，3：可包含串，4：可展开串
	 * @param getter
	 *            byVal 链接抓取器
	 */
	public MDialog(int flag, httpGet getter) {
		this.Flag = flag;
		this.getter = getter;
		area1.setFocus();
		addBtn.setFixedWidth(75);
		delBtn.setFixedWidth(75);

		mainLayout.setSpacing(10);
		mainLayout.setMargin(10);
		mainLayout.addWidget(inputLabel, 0, 0, 1, 1);
		mainLayout.addWidget(area1, 1, 0, 2, 3);
		mainLayout.addWidget(addBtn, 3, 1, 1, 1);
		mainLayout.addWidget(delBtn, 3, 2, 1, 1);

		addBtn.clicked.connect(this, "add()");
		delBtn.clicked.connect(this, "delete()");

		this.setLayout(mainLayout);

		switch (Flag) {
		case 1:
			this.setWindowTitle("添加不包含串");
			break;
		case 2:
			this.setWindowTitle("添加必包含串");
			break;
		case 3:
			this.setWindowTitle("添加可包含串");
			break;
		case 4:
			this.setWindowTitle("添加可展开串");
			break;
		default:
			break;
		}
	}
	/**
	 * 当添加串不为空时设置删除按钮为可点击模式。
	 */
	public void enabledeleteButton() {
		delBtn.setEnabled(true);
	}

	/**
	 * 添加显示栏中的串进入核心代码。
	 */
	public void add() {
		switch (Flag) {
		case 1:
			getter.setFilterLen(0);
			break;
		case 2:
			getter.setContainAllLen(0);
			break;
		case 3:
			getter.setContainOrLen(0);
			break;
		case 4:
			getter.setPriorityStringLen(0);
			break;
		default:
			break;
		}
		String[] temp = area1.toPlainText().split("\n");
		for (int i = 0; i < temp.length; i++) {
			switch (Flag) {
			case 1:
				getter.addFilter(temp[i]);
				break;
			case 2:
				getter.addContainAll(temp[i]);
				break;
			case 3:
				getter.addContainOr(temp[i]);
				break;
			case 4:
				getter.addpriorityString(temp[i]);
				break;
			default:
				break;
			}
		}
		this.setVisible(false);
	}

	/**
	 * 清空显示栏。
	 */
	public void delete() {
		switch (Flag) {
		case 1:
			getter.setFilterLen(0);
			break;
		case 2:
			getter.setContainAllLen(0);
			break;
		case 3:
			getter.setContainOrLen(0);
			break;
		case 4:
			getter.setPriorityStringLen(0);
			break;
		default:
			break;
		}
		area1.clear();
	}

}
