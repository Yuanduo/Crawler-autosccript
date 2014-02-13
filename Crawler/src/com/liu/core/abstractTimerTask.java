package com.liu.core;

import java.util.TimerTask;

public class abstractTimerTask extends TimerTask{
	private BrowserMainWindow parent1;
	private reloadTimerTask parent2;

	public abstractTimerTask(BrowserMainWindow parent1,reloadTimerTask parent2) {
		
			super();
			//System.out.println("abstractTimerTask.java,---¹¹Ôìº¯Êı");
		this.parent1 = parent1;
		this.parent2 = parent2;
	}

	public void run() {
		//System.out.println("abstractTimerTask.java,---run()");
		if (parent1.processValue < 100)
			if (++parent1.reloadCount < 3) {
				parent2.needReload.emit();
				System.out.println("emit():"+parent1.reloadCount);
			} else
				parent2.needLoadNext.emit();
	}

}
