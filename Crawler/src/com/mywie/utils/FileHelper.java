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
	
	//获取文件名
	public String[] getFileName(String url) {
		File file = new File(url);
		String[] fileLists = file.list();
		return fileLists;
	}
	
	//判断文件么是否存在，若存在，返回一个新的文件名，否则返回原文件名
	public String isFileExist(String url) {
		File file = new File(url);
		String newUrl = url;
		int i = 2; //文件重名计数器
		while ( file.exists() ) {
//			System.out.println("url:"+url);
			newUrl = url.substring(0,url.length()-4)+"_"+i+url.substring(url.length()-4);
//			System.out.println(newUrl);
			file = new File(newUrl);
			i++;
		}
		return newUrl;
	}
	
	
	//新建文件夹
	public void newFolder(String folderPath) {  
	    try {   
	      String filePath = folderPath;   
	      File myFilePath = new File(filePath);  
	      if (!myFilePath.exists()) {  
	        myFilePath.mkdir();  
	      }   
	    } catch (Exception e) {   
	      System.out.println("新建文件夹操作出错");  
	      e.printStackTrace();  
	    }  
	  }

	
	//创建新文件
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
			System.out.println("无法创建文件");
			e.printStackTrace();
		}
		return newUrl;
	}
	
	//将数据写入文件
	public void writeFile(String pathname, HashSet<String> hashSet) throws IOException {
		FileWriter fw = new FileWriter(pathname);
		Iterator<String> iterator = hashSet.iterator();
		while (iterator.hasNext()) {
			fw.write(iterator.next() + "\r\n");
		}
		fw.close();
	}
	
	//将数据写入文件
	public void writeCommentFile(String pathname, ArrayList<Comment> commentlist) throws IOException {
		FileWriter fw = new FileWriter(pathname);
		for(int i=0;i<commentlist.size();i++)
		 {			 
			Comment comment = commentlist.get(i);
//			System.out.println("KEY!:" + key);
			fw.write("用户昵称: " + comment.getName() + "\r\n");
			fw.write("用户url: " + comment.getUrl() + "\r\n");
			fw.write("用户类型: " + comment.getUserType() + "\r\n");	 
			fw.write("用户评论: " + comment.getComment() + "\r\n");
			fw.write("评论发布时间: " + comment.getDate() + "\r\n");
			fw.write("微博总关注数目: " + comment.getTotalNum() + "\r\n");
			fw.write("微博被转发数目: " + comment.getReplyNum() + "\r\n");
			fw.write("微博评论数目: " + comment.getCommentNum() + "\r\n");
			fw.write("******************" + "\r\n");
		}
		fw.close();
	}
	
	//通过文件名，获取微博ID 解决fanfan爬虫文件格式问题
	//新浪版本
	public void getSinaWeiboID(String url) {
		String[] filenames= getFileName(url);
		for (String filename : filenames ){
//			System.out.println(filename);
			String x = filename.replaceAll("-", "/");
			String y = x.substring(7, x.length()-7);
			//以e.weibo.com开头的微博ID
			if ( (y.charAt(0)+"").endsWith("e")) {
				y = "e.weibo.com" + y.substring(11);
			}else {	//以weibo.com开头的ID
				y = "weibo.com" + y.substring(9);
			}
			System.out.println(y);
		}
	}
	
	//通过文件名，获取微博ID 解决fanfan爬虫文件格式问题
	//腾讯版本
	public String getTecentWeiboID(String url) {
		//截断微博ID后无用格式
		int flag = url.indexOf("-", 25);
		String x = url.substring(7,flag).substring(0,8);
		String y = url.substring(7,flag).substring(9);
		return x.replace("-", ".")+"-"+y;
	}
	
	public void writeFile(String str,String savapath){//将抽奖结果保存到文件中  
		FileWriter fw;  
	    BufferedWriter bf;
	     boolean append =false;  
	     File file = new File(savapath);  
	     try{  
	      if(file.exists()) append =true;  
	      fw = new FileWriter(savapath,append);//同时创建新文件  
	      //创建字符输出流对象  
	       bf = new BufferedWriter(fw);  
	      //创建缓冲字符输出流对象  
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
