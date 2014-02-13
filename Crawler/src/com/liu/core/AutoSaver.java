package com.liu.core;

import com.trolltech.qt.core.QBasicTimer;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QTime;
import com.trolltech.qt.core.QTimerEvent;

public class AutoSaver extends QObject{
	private QBasicTimer m_timer;
	private QTime m_firstChange;
	static int MAXWAIT = 1000 * 15;
	static int AUTOSAVE_IN = 1000 * 3;
	private Signal0 save = new Signal0();

	public AutoSaver(QObject parent) {
		super(parent);
		//System.out.println("AutoSaver.java:----¹¹Ôìº¯Êý");
		
		this.save.connect(parent(),"save()");
		m_timer = new QBasicTimer();
		m_firstChange = new QTime();
	}

	public void saveIfNecessary() {
		
		//System.out.println("AutoSaver.java:----saveIfNecessary£¨£©");
		   if (!m_timer.isActive())
		        return;
		    m_timer.stop();
		    m_firstChange =new QTime();
		    save.emit();
//		    if (!QMetaObject.invokeMethod(parent(), "save", Qt::DirectConnection)) {
//		        System.out.println("AutoSaver: error invoking slot save() on parent");;
//		    }
	}

	public void changeOccurred() {
		//System.out.println("AutoSaver.java:----changeOccurred£¨£©");
		if (m_firstChange.isNull())
			m_firstChange.start();

		if (m_firstChange.elapsed() > MAXWAIT) {
			saveIfNecessary();
		} else {
			m_timer.start(AUTOSAVE_IN, this);
		}
	}

	protected void timerEvent(QTimerEvent event) {
		//System.out.println("AutoSaver.java:----timerEvent(QTimerEvent event)");
		if (event.timerId() == m_timer.timerId()) {
			saveIfNecessary();
		} else {
			super.timerEvent(event);
		}
	}
}
