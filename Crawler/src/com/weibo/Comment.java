package com.weibo;


public class Comment { 
	
	String name="null";	//用户昵称	 
	String url="null";		//用户url
	String userType="null";//用户	
	String comment="null";	//用户评论
	String date="null";	//评论日期
	String commentNum="null"; //评论数
	String replyNum="null";	//转发数目
	String totalNum="null";	//总数目
	
	
 
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
 
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	public String getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(String replyNum) {
		this.replyNum = replyNum;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	
	
	public String getUserType() {                 
		return userType;                          
	}                                             
	public void setUserType(String userType) {    
		this.userType = userType;                 
	}                                             
	
	public String toString()
	{
		String res="用户昵称:"+this.name+ "\r\n";
	    res=res+"用户url: "+url+"\r\n";	
	    res=res+"用户类型： "+userType+"\r\n";
		res=res+"用户评论: " + comment+ "\r\n";
		res=res+"评论发布时间: " + date + "\r\n";
		res=res+"微博总关注数目: " + totalNum + "\r\n";
		res=res+"微博被转发数目: " + this.replyNum + "\r\n";
		res=res+"微博评论数目: " + this.commentNum + "\r\n";
		return res;
	}
}
