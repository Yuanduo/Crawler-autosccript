package com.weibo;


public class Comment { 
	
	String name="null";	//�û��ǳ�	 
	String url="null";		//�û�url
	String userType="null";//�û�	
	String comment="null";	//�û�����
	String date="null";	//��������
	String commentNum="null"; //������
	String replyNum="null";	//ת����Ŀ
	String totalNum="null";	//����Ŀ
	
	
 
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
		String res="�û��ǳ�:"+this.name+ "\r\n";
	    res=res+"�û�url: "+url+"\r\n";	
	    res=res+"�û����ͣ� "+userType+"\r\n";
		res=res+"�û�����: " + comment+ "\r\n";
		res=res+"���۷���ʱ��: " + date + "\r\n";
		res=res+"΢���ܹ�ע��Ŀ: " + totalNum + "\r\n";
		res=res+"΢����ת����Ŀ: " + this.replyNum + "\r\n";
		res=res+"΢��������Ŀ: " + this.commentNum + "\r\n";
		return res;
	}
}
