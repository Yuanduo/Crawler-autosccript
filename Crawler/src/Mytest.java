import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Mytest { 
  
  
 
 public static void main(String[] args) {
	 
	 
	String ele=" 小米粒儿妞妞：我很喜欢这个活动，真的很想哦，给我吧@梦蝶斗舞 @橙色魅力 @XX杰_5d7 @丫頭de木頭 @mc花甲 地址：http://t.cn/zOBc5Ep"
	+"(2012-3-23 13:34)"+"举报|2013-4-05 13:12"
		+"	回复		◆◆	同时转发到我的微博";
//	boolean bb=ele.contains("<body");
//    boolean cc=ele.contains("/html>");
//    System.out.println(bb);
//    System.out.println(cc);
 //   String test="<li class=\"fore1\" id=\"loginbar\" clstag=\"homepage|keycount|home2013|01b\">您好！欢迎来到京东！<a href=\"javascript:login()\">[登录]</a>&nbsp;<a href=\"javascript:regist()\">[免费注册]</a></li>";
	
	String test="http://e.weibo.com/2324953757/A1v8ag8xj#1374842670311";
	 if(test.contains("#"))
	 {
		 int end=test.indexOf("#");
		 test=test.substring(0,end);
	 }
	System.out.println(test);
	 
 }
 

 
 public static String getTime(String text) { 
     String dateStr = text.replaceAll("r?n", " "); 
     dateStr = dateStr.replaceAll("\\s+", " ");        
     
     try { 
         
         List matches = null; 
         Pattern p = Pattern.compile("(\\d{1,4}[-|\\/]\\d{1,2}[-|\\/]\\d{1,2} \\d{1,2}:\\d{1,2})", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE); 
         Matcher matcher = p.matcher(dateStr); 
         if (matcher.find() && matcher.groupCount() >= 1) { 
             matches = new ArrayList(); 
             for (int i = 1; i <= matcher.groupCount(); i++) { 
                 String temp = matcher.group(i); 
                 matches.add(temp); 
             } 
         } else { 
             matches = Collections.EMPTY_LIST; 
         }            
         
         if (matches.size() > 0) { 
             return ((String) matches.get(0)).trim(); 
         } else { 
             return ""; 
         } 
         
     } catch (Exception e) { 
         return ""; 
     } 
 }

 public static String replaceBlank(String str) {
     String dest = "";
     if (str!=null) {
         Pattern p = Pattern.compile("\\t|\r|\n");
         Matcher m = p.matcher(str);
         dest = m.replaceAll("");
     }
     return dest;
 }
 
 public static String getNum(String res) {
	// String a="love23next234csdn3423javaeye";
	 String regEx="[^0-9]";   
	 Pattern p = Pattern.compile(regEx);   
	 Matcher m = p.matcher(res);   
	 String result=""+m.replaceAll("").trim();
	return result;
	 }

 
// public static String getLink(String str, String tag, String element) {
//		//	System.out.println("httpGet.java:--- getLink(String str, String tag, String element):-- "+str+"--"+tag+"--"+element);
//	   str=str.replaceAll("\"", "");
//	   str=str.replaceAll("'", "");
//	 String res="";
//	if(!str.contains(tag))
//	{
//		return "";
//	}
//	else
//	{//包含链接标签
//		int start_1=str.indexOf("<"+tag);	
//		if(start_1==-1)
//			return "";
//		else{
//			res=str.substring(start_1+tag.length()+1,str.length());
//			int end_1=res.indexOf(">");
//			if(end==-1)
//			{
//				return "";
//			}
//			else
//			{
//				res=res.substring(0,end_1);//取到所有的属性
//				if(res.contains(element))
//				{//包含链接的属性
//					int start_0=res.indexOf(element);
//					res=res.substring(start_0+element.length()+1,res.length()).trim();//获取element属性之后的内容
//					if (res.contains(" "))
//					{//包含多余的属性， 
//						int blank=res.indexOf(' ');
//						res=res.substring(0,blank);
//					}
//				}
//				
//				
//			}
//			
//		}
//		
//		
//		
//	}
//	 return res.trim();
//		}
// 
// public static String deleteOtherAttr(String res) {//开始是不会错的，但是因为属性的引号都被去掉了，所以有可能后边还有其他的属性，要把多余的属性去掉
//		//在getLink函数中，最后切分出来例如:/all_artistlrc/8/44850.htm class=current 
//		 if(res.contains(" "))
//		 {
//			 int blank=res.indexOf(" ");
//			  res=res.substring(0,blank);				 
//		 }
//		 return res;
//		 
//	 }
 
}