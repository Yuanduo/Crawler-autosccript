import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Mytest { 
  
  
 
 public static void main(String[] args) {
	 
	 
	String ele=" С�������椣��Һ�ϲ����������ĺ���Ŷ�����Ұ�@�ε����� @��ɫ���� @XX��_5d7 @Ѿ�^deľ�^ @mc���� ��ַ��http://t.cn/zOBc5Ep"
	+"(2012-3-23 13:34)"+"�ٱ�|2013-4-05 13:12"
		+"	�ظ�		����	ͬʱת�����ҵ�΢��";
//	boolean bb=ele.contains("<body");
//    boolean cc=ele.contains("/html>");
//    System.out.println(bb);
//    System.out.println(cc);
 //   String test="<li class=\"fore1\" id=\"loginbar\" clstag=\"homepage|keycount|home2013|01b\">���ã���ӭ����������<a href=\"javascript:login()\">[��¼]</a>&nbsp;<a href=\"javascript:regist()\">[���ע��]</a></li>";
	
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
//	{//�������ӱ�ǩ
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
//				res=res.substring(0,end_1);//ȡ�����е�����
//				if(res.contains(element))
//				{//�������ӵ�����
//					int start_0=res.indexOf(element);
//					res=res.substring(start_0+element.length()+1,res.length()).trim();//��ȡelement����֮�������
//					if (res.contains(" "))
//					{//������������ԣ� 
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
// public static String deleteOtherAttr(String res) {//��ʼ�ǲ����ģ�������Ϊ���Ե����Ŷ���ȥ���ˣ������п��ܺ�߻������������ԣ�Ҫ�Ѷ��������ȥ��
//		//��getLink�����У�����зֳ�������:/all_artistlrc/8/44850.htm class=current 
//		 if(res.contains(" "))
//		 {
//			 int blank=res.indexOf(" ");
//			  res=res.substring(0,blank);				 
//		 }
//		 return res;
//		 
//	 }
 
}