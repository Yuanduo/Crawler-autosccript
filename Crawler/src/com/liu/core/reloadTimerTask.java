package com.liu.core;

import com.trolltech.qt.QSignalEmitter;

public class reloadTimerTask extends QSignalEmitter {
	public abstractTimerTask myAbstractTimerTask;
	public Signal0 needReload = new Signal0();
	public Signal0 needLoadNext = new Signal0();

	public reloadTimerTask(BrowserMainWindow parent) {
		
				super();
			//	System.out.println("reloadTimerTask.java----¹¹Ôìº¯Êý");
		myAbstractTimerTask = new abstractTimerTask(parent,this);
	}
	
}
