function timeCount(){
scrollBy(0,10000);
setTimeout("findUserInfo_SourceWeibo()",5000);
}

//***********************************************************	
//��ȡ�����û�Դ΢��
function findSourceComments_geren()
{
////alert("����22");
	var sourceComment="";		
	var div_set=document.getElementsByTagName("div");	 
	for(var i=0;i<div_set.length;i++)
		{
		if(div_set[i].className=="WB_detail")
			{
			var div_set_2=div_set[i].getElementsByTagName("div");
			for(var j=0;j<div_set_2.length;j++)
			{
				if(div_set_2[j].className=="WB_text"&&div_set_2[j].hasAttribute("node-type")&&div_set_2[j].getAttribute("node-type")=="feed_list_content")//΢������
					{
					sourceComment=div_set_2[j].textContent;			 
					
					}
				if(div_set_2[j].className=="WB_media_expand SW_fun2 S_line1 S_bg1"&&div_set_2[j].hasAttribute("isforward"))//ת��΢��
					{
				
					findZhuanfa(div_set_2[j]);
					}
				if(div_set_2[j].className=="WB_func clearfix")
					{
					//��ȡ���ں�ʱ��
					////alert("huoqushijian function");
					getDateAndNum(div_set_2[j]);
					} 								
			  }	//for-div_set_2
			}//if
		}//for-div-set
	  
		document.cookie="source_Comment="+encodeURI(sourceComment);
		
}

	 
//***********************************************************	
//��ȡ��ҵ�û�Դ΢��
function findSourceComments_qiye()
{
	 ////alert("start");
	var sourceComment="";
	var date="";
	var commentNum="";
	var forwardNum="";	
	var weiboDetail_div=document.getElementById("pl_content_weiboDetail");//�ҵ�weiboDetail��Ӧ��div��ǩ
	var div_set=weiboDetail_div.getElementsByTagName("div");//�ҵ�weiboDetail������div
	for(var i=0;i<div_set.length;i++)
	{
		if(div_set[i].className=="feed_lists W_linka W_texta")
		{//��ҵ΢��		 
    	var dd_set=div_set[i].getElementsByTagName("dd");    	
 		for(var d_index=0;d_index<dd_set.length;d_index++)
 			{
			if(dd_set[d_index].className=="content")
				{				
				p_set=dd_set[d_index].getElementsByTagName("p");
				////alert("p");
				for(var p=0;p<p_set.length;p++)
					{				
					if(p_set[p].hasAttribute("node-type")&&p_set[p].getAttribute("node-type")=="feed_list_content")
						{
						sourceComment=p_set[p].textContent;
						////alert(sourceComment);						 
						}
					if(p_set[p].className=="info W_linkb W_textb")
						{
						   date=p_set[p].textContent;
						////alert(date);
						var forward_A_set=p_set[p].getElementsByTagName("a");
						for(var fa=0;fa<forward_A_set.length;fa++)
						{
							if(forward_A_set[fa].hasAttribute("node-type"))
								{
								if(forward_A_set[fa].getAttribute("node-type")=="forward_counter")
									{
									forwardNum=forward_A_set[fa].textContent;//ת����Ŀ
								    ////alert(forwardNum);
								    }
								if(forward_A_set[fa].getAttribute("node-type")=="comment_counter")
									{
									commentNum=forward_A_set[fa].textContent;//������Ŀ
								    ////alert(commentNum);
									}
								}
						}//for fa
						}//if info W_linkb W_textb
  				}//for p
				
 				}//if content
 			}//for d_index
 		
 		var dl_set=weiboDetail_div.getElementsByTagName("dl");
 		for(var di=0;di<dl_set.length;di++)
 			{
 			if(dl_set[di].className=="comment W_textc W_linecolor W_bgcolor"&&dl_set[di].hasAttribute("isforward"))
 			{
 				////alert("diaoyong hanshu")
 				findForwardComment(dl_set[di]);
 				break;
 			}
 			}		
 		
		
		}//if qiye
	}
	
	document.cookie="source_Comment="+encodeURI(sourceComment);
	document.cookie="source_date="+encodeURI(date);
	document.cookie="source_commentNum="+encodeURI(commentNum);
	document.cookie="source_forwardNum="+encodeURI(forwardNum);	
	
	//	ww.refreshCookie();
}


//***********************************************************	
//��ȡ��ҵ�û�ת����΢��
function findForwardComment(dl)
{
	////alert("fiandZhuanfa");
	var zhuanfaName="";
	var zhuanfaComment="";
	var zhuanfaDate="";
	var zhuanfaUrl="";
	var zhuanfaForwardNum="";
	var zhuanfaCommentNum="";
	var zhuanfaWeiboURL="";
	
	var dt_set=dl.getElementsByTagName("dt");	 
	for(var i=0;i<dt_set.length;i++)
		{
		if(dt_set[i].hasAttribute("node-type")&&dt_set[i].getAttribute("node-type")=="feed_list_forwardContent")
			{	
			var a_tag=dt_set[i].getElementsByTagName("a")[0];
			if(a_tag.hasAttribute("title"))
				{				
				zhuanfaName=a_tag.getAttribute("title");
				zhuanfaUrl=a_tag.getAttribute("href");
				}
			zhuanfaComment=dt_set[i].textContent;
			break;
			}
		}
	
	var dd_set=dl.getElementsByTagName("dd");	
	for(var j=0;j<dd_set.length;j++)
		{
		if(dd_set[j].className=="info W_linkb W_textb")
			{	
			var a_set=dd_set[j].getElementsByTagName("a");			
			for(var ai=0;ai<a_set.length;ai++)
				{
				if(a_set[ai].textContent.indexOf("����")!=-1)
				{
				 zhuanfaCommentNum=a_set[ai].textContent;
				}
			    if(a_set[ai].textContent.indexOf("ת��")!=-1)
			    	{	 
			    	zhuanfaForwardNum=a_set[ai].textContent;
			    	}
			    if(a_set[ai].className=="date")
				  { 
				  zhuanfaDate=a_set[ai].textContent;
				  }
			  if(a_set[ai].hasAttribute("target")&&a_set[ai].getAttribute("target")=="_blank")
				  {	 
				  zhuanfaName=a_set[ai].textContent;
				  zhuanfaUrl=a_set[ai].getAttribute("href");
				  }
				}
			 
		
			break;
			}
		
		}		
	////alert("cunchu000");
	document.cookie="zhuanfaName="+encodeURI(zhuanfaName);
	document.cookie="zhuanfaComment="+encodeURI(zhuanfaComment);
	document.cookie="zhuanfaDate="+encodeURI(zhuanfaDate);
	document.cookie="zhuanfaUrl="+encodeURI(zhuanfaUrl);
	document.cookie="zhuanfaForwardNum="+encodeURI(zhuanfaForwardNum);
	document.cookie="zhuanfaCommentNum="+encodeURI(zhuanfaCommentNum);
	
	
}









//***********************************************************	
//��ȡ�û�������Ϣ��Դ΢����ת��΢������
	function findUserInfo_SourceWeibo()
	{		 
		//  //alert("findUserInfo_SourceWeibo");
		var url="";
		var name="";
		var userType="";
		var level="";			
		var body=document.getElementsByTagName("body")[0];//��ȡ���е�div��ǩ	
		////alert(body);			
		if(body.className=="S_profile B_onefeed L-zh-cn")
			{//�����û�����ý���û�,����1	
			////alert("geren");
			userType="person";			 
			findSourceComments_geren();
			 
			var userInfo_div=document.getElementById("pl_profile_hisInfo");//��ø��˻�����Ϣdiv			 
			 var divInfo_set=userInfo_div.getElementsByTagName("div");			 
			 for(var i=0;i<divInfo_set.length;i++)
				 {				 
				 if(divInfo_set[i].className=="pf_name bsp clearfix")
					 {							 
					 var firstspan=divInfo_set[i].getElementsByTagName("span")[0];//����û���
					 name=firstspan.textContent;
					// //alert(firstspan.textContent);
					 var clearfix_div=divInfo_set[i];
					 var set_div2=clearfix_div.getElementsByTagName("div");
					 for(var j=0;j<set_div2.length;j++)
						 {
						 if(set_div2[j].className=="pf_icon clearfix")
							{//V ��־							
							 var a_set=set_div2[j].getElementsByTagName("a");								 
							 for(var m=0;m<a_set.length;m++)
							 {
								// //alert(m); 								
								 if(a_set[m].hasAttribute("href")&&a_set[m].getAttribute("href")=="http://verified.weibo.com/verify")
									 {//��֤�û�									 
									 userType="persion verify";									
									 }//								
								 else if(a_set[m].hasAttribute("suda-data")&&a_set[m].getAttribute("suda-data")=="key=tblog_grade_float&value=grade_icon_click")
									 {//�û��ȼ�									
									 var span_set=a_set[m].getElementsByTagName("span");									
									 var span=span_set[1];	
									 if(span.hasAttribute("title"))
										 {
										 level=span.getAttribute("title");
										 }									 
									 }
								 else if(a_set[m].className=="pf_lin S_link1")
									 {//����û�url
									 url=a_set[m].textContent;
									// //alert(a_set[m].textContent);
									 }
							 }//for a							 
							 }
						 }//for div2					 
					 break;
					 }//if
				 }//for divInfo_set			 
					 
			}//if�����û�
			
 		if(body.className.indexOf("S_profile B_profile")==0)
 			{//�ڶ����û�����
 			////alert("����2");
 			userType="official-person";
 			findSourceComments_geren();
 		var div_set=document.getElementsByTagName("div")		 
 			for(var di=0;di<div_set.length;di++)
 				{ 				
 				if(div_set[di].className=="pf_name bsp clearfix")
 					{ 					 
 					var span_set=div_set[di].getElementsByTagName("span"); 					 
 					for(var si=0;si<span_set.length;si++)
 						{
 						if(span_set[si].className=="name")
 							{
 							name=span_set[si].textContent;
 							////alert(name);
 							break;
 							}
 						}//for-span
 					var a_set=div_set[di].getElementsByTagName("a");
 					for(var ai=0;ai<a_set.length;ai++)
 						{
 						if(a_set[ai].className.indexOf("pf_lin S_link")==0)
 							{
 							url=a_set[ai].textContent;
 							////alert(url);
 							break;
 							}
 						}
 					}//if-div
 				}//for
 			}//����2
		
		if(body.className.indexOf("B_one_feed")==0)
			{//��ҵ�û�
			////alert("qiye");
			findSourceComments_qiye();
			var div_bu=document.getElementById("pl_leftNav_profilePersonal");//ͷ����Id			 
			var A_set_bu=div_bu.getElementsByTagName("A");			 
			for(var a=0;a<A_set_bu.length;a++)
				{			
				if(A_set_bu[a].className=="logo_img")
					{
					url=A_set_bu[a].getAttribute("href");					
					name=A_set_bu[a].getAttribute("title");
					break;
					}
				}
			
			userType="business";
			}//else if
		 
		 document.cookie="source_userType="+userType;
		 document.cookie="source_username="+encodeURI(name);		
		 document.cookie="source_userurl="+url;
		 ww.refreshCookie();
		 findComment();
		
	}
	
	//***********************************************************	
	//��ȡ�����û���΢���������ڼ�����ת����Ŀ
function getDateAndNum(div)//��ȡԴ΢�������ڼ�����ת����Ŀ
{
	////alert("getDateAndNum");
	var date="";
	var commentNum="";
	var forwardNum="";
	var div_set=div.getElementsByTagName("div");
	for(var j=0;j<div_set.length;j++)
		{
	     if(div_set[j].className=="WB_from")
	      {
	 		 var a_set=div_set[j].getElementsByTagName("a"); 					 
	 		 for(var aa=0;aa<a_set.length;aa++)
	 		 {	 						 
				 if(a_set[aa].className=="S_link2 WB_time"&&a_set[aa].hasAttribute("title"))
				 {
					date=a_set[aa].getAttribute("title");						
					  break;
				 }						
	 		 }//for-a					
	 		}//if wb-from
		 if(div_set[j].className=="WB_handle")
			 {//ת��������صı�ǩ
			  var a_set_sourceWeibo=div_set[j].getElementsByTagName("a");//��ȡhandle��ǩ�����е�la��ǩ
			  for(var m=0;m<a_set_sourceWeibo.length;m++)
				 {
					 if(a_set_sourceWeibo[m].hasAttribute("node-type")&&a_set_sourceWeibo[m].getAttribute("node-type")=="forward_counter")
						 {//��ȡת����Ŀ
							forwardNum=a_set_sourceWeibo[m].textContent;								 
							}
					if(a_set_sourceWeibo[m].hasAttribute("node-type")&&a_set_sourceWeibo[m].getAttribute("node-type")=="comment_counter")
						 {//��ȡ������Ŀ
						    commentNum=a_set_sourceWeibo[m].textContent;							   
						 }						
				}//for a					
			}//if handle
		}//for-div
										
	document.cookie="source_date="+encodeURI(date);
	document.cookie="source_commentNum="+encodeURI(commentNum);
	document.cookie="source_forwardNum="+encodeURI(forwardNum);	
}
	
//***********************************************************	
//��ȡ�����û���ת��΢��
function findZhuanfa(div)//�����û�ת��΢��
{
	////alert("zhuanfa");
	var zhuanfaName="";
	var zhuanfaComment="";
	var zhuanfaDate="";
	var zhuanfaUrl="";
	var zhuanfaForwardNum="";
	var zhuanfaCommentNum="";
	var zhuanfaWeiboURL="";
	var zh_div_set=div.getElementsByTagName("div");
 
	for(var zh=0;zh<zh_div_set.length;zh++)
		{
		if(zh_div_set[zh].className=="WB_info")
			{			 
			var zh_a=zh_div_set[zh].getElementsByTagName("a")[0];			 
			if(zh_a.className=="WB_name S_func3"&&zh_a.hasAttribute("title")&&zh_a.hasAttribute("href"))
			{ 
				zhuanfaName=zh_a.getAttribute("title");
				zhuanfaUrl=zh_a.getAttribute("href");	 
			}		
			}
		if(zh_div_set[zh].className=="WB_text")
			{			 
			zhuanfaComment=zh_div_set[zh].textContent ;			
			}
		if(zh_div_set[zh].className=="WB_from")
			{//��ȡʱ���			 
			 var a_set=zh_div_set[zh].getElementsByTagName("a");			 
			 for(var i=0;i<a_set.length;i++)
				 {
				 if(a_set[i].className=="S_func2 WB_time"&&zh_a.hasAttribute("title"))
					 {					
					 zhuanfaDate=a_set[i].getAttribute("title") ;
					 zhuanfaWeiboURL=a_set[i].getAttribute("href");					
					 }
				 if(a_set[i].className=="S_func4"&&a_set[i].getAttribute("href")!=zhuanfaWeiboURL&&a_set[i].getAttribute("href").indexOf(zhuanfaWeiboURL)!=-1)
					 {					 
					 zhuanfaForwardNum=a_set[i].textContent;					 
					 }
				 if(a_set[i].className=="S_func4"&&a_set[i].getAttribute("href")==zhuanfaWeiboURL)
				 {   
					 zhuanfaCommentNum=a_set[i].textContent;					 
				 }
				 }
			}
		}
	document.cookie="zhuanfaName="+encodeURI(zhuanfaName);
	document.cookie="zhuanfaComment="+encodeURI(zhuanfaComment);
	document.cookie="zhuanfaDate="+encodeURI(zhuanfaDate);
	document.cookie="zhuanfaUrl="+encodeURI(zhuanfaUrl);
	document.cookie="zhuanfaForwardNum="+encodeURI(zhuanfaForwardNum);
	document.cookie="zhuanfaCommentNum="+encodeURI(zhuanfaCommentNum);
}
	
	
//***********************************************************	
//��ȡ���۵ĺ���
	
function findComment()
{ 
	////alert("kaishi");
	var comment="";
	var username="";
	var date="";
	var url="";	 
	var dl_set=document.getElementsByTagName("dl");
	for(var i=0;i<dl_set.length;i++)
		{
		if(dl_set[i].className=="comment_list S_line1"||dl_set[i].className=="comment_list W_linecolor clearfix")
			{//��һ������
			var dd =dl_set[i].getElementsByTagName("dd")[0];			 
			comment=dd.textContent;//����
			var a=dd.getElementsByTagName("a")[0];
			url=a.getAttribute("href");
			username=a.getAttribute("title");
			var span=dd.getElementsByTagName("span")[0];
			date=span.textContent;			
			
			document.cookie="user_comment="+encodeURI(comment);
		    document.cookie="user_userName="+encodeURI(username);
		    document.cookie="user_userurl="+url;
			document.cookie="user_date="+encodeURI(date);	
			ww.refreshCookie();
			}
		
		}//for
	ww.refreshCookie();
	ww.writeCookietoFile();
	findp();
}	



//***********************************************************	
//��ҳ����

function findp(){
var hasnextpage=0; 
 
////alert("findP");
var A_set= document.getElementsByTagName("A");
////alert("aa"+A_set.length);
for(var i=0;i<A_set.length&&!hasnextpage;i++)
{		 	
	if(A_set[i].className=="W_btn_c btn_page_next"||A_set[i].className=="W_btn_a"){	
	//	alert(ww.getcommentpageNum());
		if(ww.getcommentpageNum()>4)
			{
		//	alert("dayu");
			break;
			}
    if(A_set[i].textContent.indexOf("��һҳ")!=-1 ||A_set[i].textContent.indexOf("��ҳ")!=-1 ){
		hasnextpage=1;		 
		var span_set=A_set[i].getElementsByTagName("span");		
		var mark=span_set[0];			
 		simulate(mark, "click");
 		ww. changePageNum(); 		
		 break;
		}
	}
}

if(hasnextpage==0){
	 
ww.endOfPage();
return true;
}
timeCount();
}
timeCount();


 



  



