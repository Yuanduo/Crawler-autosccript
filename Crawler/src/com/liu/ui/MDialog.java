package com.liu.ui;

import com.liu.core.httpGet;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextEdit;

/**
 * MDialog�࣬ʵ������ץȡ�Կ�������ץȡ�Ĺ����������������ڽ��棬��ӻ�ɾ�����˴����غ������ɺ�������չ������
 * 
 */
public class MDialog extends QDialog {

	public httpGet getter;
	private int Flag;
	private QTextEdit area1 = new QTextEdit(this);
	private QLabel inputLabel = new QLabel("���룬���Իس����");
	private QPushButton addBtn = new QPushButton("ȷ��������");
	private QPushButton delBtn = new QPushButton("���");
	private QGridLayout mainLayout = new QGridLayout();

	/**
	 * ���캯������ʼ����
	 * 
	 * @param flag
	 *            byVal ���������ͣ�1������������2���ذ�������3���ɰ�������4����չ����
	 * @param getter
	 *            byVal ����ץȡ��
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
			this.setWindowTitle("��Ӳ�������");
			break;
		case 2:
			this.setWindowTitle("��ӱذ�����");
			break;
		case 3:
			this.setWindowTitle("��ӿɰ�����");
			break;
		case 4:
			this.setWindowTitle("��ӿ�չ����");
			break;
		default:
			break;
		}
	}
	/**
	 * ����Ӵ���Ϊ��ʱ����ɾ����ťΪ�ɵ��ģʽ��
	 */
	public void enabledeleteButton() {
		delBtn.setEnabled(true);
	}

	/**
	 * �����ʾ���еĴ�������Ĵ��롣
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
	 * �����ʾ����
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
