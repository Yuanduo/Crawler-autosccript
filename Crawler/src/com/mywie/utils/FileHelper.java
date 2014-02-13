package com.mywie.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.weibo.Comment;



public class FileHelper {
//	
	
	//��ȡ�ļ���
	public String[] getFileName(String url) {
		File file = new File(url);
		String[] fileLists = file.list();
		return fileLists;
	}
	
	//�ж��ļ�ô�Ƿ���ڣ������ڣ�����һ���µ��ļ��������򷵻�ԭ�ļ���
	public String isFileExist(String url) {
		File file = new File(url);
		String newUrl = url;
		int i = 2; //�ļ�����������
		while ( file.exists() ) {
//			System.out.println("url:"+url);
			newUrl = url.substring(0,url.length()-4)+"_"+i+url.substring(url.length()-4);
//			System.out.println(newUrl);
			file = new File(newUrl);
			i++;
		}
		return newUrl;
	}
	
	
	//�½��ļ���
	public void newFolder(String folderPath) {  
	    try {   
	      String filePath = folderPath;   
	      File myFilePath = new File(filePath);  
	      if (!myFilePath.exists()) {  
	        myFilePath.mkdir();  
	      }   
	    } catch (Exception e) {   
	      System.out.println("�½��ļ��в�������");  
	      e.printStackTrace();  
	    }  
	  }

	
	//�������ļ�
	public String createNewFile(String url) {
		String newUrl = url;
		File file = new File(newUrl);
		try {
			if ( !file.exists() ) {
				file.createNewFile();
//				System.out.println("create new file: " + newUrl);
			}
			else {
				newUrl = isFileExist(newUrl);
				file = new File(newUrl);
				file.createNewFile();
//				System.out.println("create new file: " + newUrl);
			}
		}  catch (Exception e) {
			System.out.println("�޷������ļ�");
			e.printStackTrace();
		}
		return newUrl;
	}
	
	//������д���ļ�
	public void writeFile(String pathname, HashSet<String> hashSet) throws IOException {
		FileWriter fw = new FileWriter(pathname);
		Iterator<String> iterator = hashSet.iterator();
		while (iterator.hasNext()) {
			fw.write(iterator.next() + "\r\n");
		}
		fw.close();
	}
	
	//������д���ļ�
	public void writeCommentFile(String pathname, ArrayList<Comment> commentlist) throws IOException {
		FileWriter fw = new FileWriter(pathname);
		for(int i=0;i<commentlist.size();i++)
		 {			 
			Comment comment = commentlist.get(i);
//			System.out.println("KEY!:" + key);
			fw.write("�û��ǳ�: " + comment.getName() + "\r\n");
			fw.write("�û�url: " + comment.getUrl() + "\r\n");
			fw.write("�û�����: " + comment.getUserType() + "\r\n");	 
			fw.write("�û�����: " + comment.getComment() + "\r\n");
			fw.write("���۷���ʱ��: " + comment.getDate() + "\r\n");
			fw.write("΢���ܹ�ע��Ŀ: " + comment.getTotalNum() + "\r\n");
			fw.write("΢����ת����Ŀ: " + comment.getReplyNum() + "\r\n");
			fw.write("΢��������Ŀ: " + comment.getCommentNum() + "\r\n");
			fw.write("******************" + "\r\n");
		}
		fw.close();
	}
	
	//ͨ���ļ�������ȡ΢��ID ���fanfan�����ļ���ʽ����
	//���˰汾
	public void getSinaWeiboID(String url) {
		String[] filenames= getFileName(url);
		for (String filename : filenames ){
//			System.out.println(filename);
			String x = filename.replaceAll("-", "/");
			String y = x.substring(7, x.length()-7);
			//��e.weibo.com��ͷ��΢��ID
			if ( (y.charAt(0)+"").endsWith("e")) {
				y = "e.weibo.com" + y.substring(11);
			}else {	//��weibo.com��ͷ��ID
				y = "weibo.com" + y.substring(9);
			}
			System.out.println(y);
		}
	}
	
	//ͨ���ļ�������ȡ΢��ID ���fanfan�����ļ���ʽ����
	//��Ѷ�汾
	public String getTecentWeiboID(String url) {
		//�ض�΢��ID�����ø�ʽ
		int flag = url.indexOf("-", 25);
		String x = url.substring(7,flag).substring(0,8);
		String y = url.substring(7,flag).substring(9);
		return x.replace("-", ".")+"-"+y;
	}
	
	public void writeFile(String str,String savapath){//���齱������浽�ļ���  
		FileWriter fw;  
	    BufferedWriter bf;
	     boolean append =false;  
	     File file = new File(savapath);  
	     try{  
	      if(file.exists()) append =true;  
	      fw = new FileWriter(savapath,append);//ͬʱ�������ļ�  
	      //�����ַ����������  
	       bf = new BufferedWriter(fw);  
	      //���������ַ����������  
	      bf.append(str);  
	      bf.flush();  
	      bf.close();    
	     }catch (IOException e){  
	      e.printStackTrace();  
	     }  
	    }  
	
	public ArrayList readTxtByLine(String path)
	{
		ArrayList countList=new ArrayList();
		
		 try {
                FileReader reader = new FileReader(path); 

	            BufferedReader br = new BufferedReader(reader);
	            

	            String str = null; 	            

	            while((str = br.readLine()) != null) {

	                //  sb.append(str+"/n");
	                  if(!countList.contains(str))
	                  {
	                	  countList.add(str);
	                  }

	              //    System.out.println(str);

	            } 

	            

	            br.close();

	            reader.close();

		 }catch(FileNotFoundException e) {

             e.printStackTrace();

       }

       catch(IOException e) {

             e.printStackTrace();

       }
		 return countList;


	}	

	
	
	
}
