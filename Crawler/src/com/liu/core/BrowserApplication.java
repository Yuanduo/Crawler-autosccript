package com.liu.core;

import com.trolltech.qt.core.QCoreApplication;
import com.trolltech.qt.core.QDir;
import com.trolltech.qt.core.QFile;
import com.trolltech.qt.gui.QDesktopServices;
import com.trolltech.qt.gui.QDesktopServices.StandardLocation;
/**
* 
*
*/
public class BrowserApplication {
	
	public static String dataFilePath(final String fileName)
    {
		//System.out.println("BrowserApplication.java:-----dataFilePath(final String fileName)"+fileName);
    	String directory = QDesktopServices.storageLocation(StandardLocation.DataLocation);
    	//System.out.println(directory);
    	if(directory.isEmpty())
    		directory = QDir.homePath() +"/."+QCoreApplication.applicationName();
    	if(!QFile.exists(directory)) {
    		QDir dir = new QDir();
    		dir.mkpath(directory);
    	}
    	return directory + "/"+fileName;
    }
}
