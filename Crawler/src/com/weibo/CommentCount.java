package com.weibo;

public class CommentCount {
	public int comment_num;
	public int forward_num;
	public int total_num;
	String date="";
	
	public CommentCount(){}
	public  CommentCount(String res)
	{
				
		int pinglun=res.indexOf("评论");
		String sub1=res.substring(pinglun+1,res.length()-1);	
		if(res.charAt(pinglun+2)=='(')
		{//有评论，即评论后面有括号
			int end_pnglun=sub1.indexOf(")");
			int maohao=sub1.indexOf(":");
			String sub2=sub1.substring(end_pnglun+2,maohao+3);
			date=sub2;
		//	System.out.println("date:"+sub2)		;
		}
		else{
			int maohao=sub1.indexOf(":");
		    String sub2=sub1.substring(2,maohao+3);
		    date=sub2;
		//	System.out.println("date:"+sub2);
		}
	}

	
	public void pro_comment_count(String res)
	{
		
	}
	
	
	public int getComment_num() {
		return comment_num;
	}

	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}

	public int getForward_num() {
		return forward_num;
	}

	public void setForward_num(int forward_num) {
		this.forward_num = forward_num;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
