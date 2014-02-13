
var count=1;
function timeCount(){
scrollBy(0,10000);
if(count==1)
	{
setTimeout("findp()",5000);
	}
else setTimeout("clickNext()",2000);
}

function findp(){
var title=document.getElementsByTagName("title")[0].innerHTML;
if(title.indexOf("出错啦")==0)
	{
	ww.endOfPage();
	return true;
	}

var tab_bar=document.getElementById("J_TabBarBox");//导航
var a_set1=tab_bar.getElementsByTagName("A");
for(var j=0;j<a_set1.length;j++)
	{
	if(a_set1[j].innerHTML.indexOf("累计评价")!=-1)
		{
		 simulate(a_set1[j], "click"); //点击评价标签
		// alert("dianji pingjia ");
		 break;
		}
	}

setTimeout("clickNext()",2000);

}


function clickNext()
{
	ww.downloadSource();
	var hasnextpage=0;	
	var div_set=document.getElementsByTagName("div"); 
	for(var m=0;m<div_set.length;m++)	
	{	 
		 if(div_set[m].className=="rate-paginator")
			 {
			  var A_set=div_set[m].getElementsByTagName("A");			   
			  for(var i=0;i<A_set.length&&!hasnextpage;i++){
				//  alert(A_set[i].innerHTML);
					if(A_set[i].innerHTML.indexOf("下一页")!=-1 ||A_set[i].textContent.indexOf("下页")!=-1 ){
						hasnextpage=1;
						count++;
						simulate(A_set[i], "click"); 
						break;
						}
			       }
			 break;
			 }
		 
		} 
	 
	if(hasnextpage==0){
	ww.endOfPage();
	return true;
	}
	timeCount();
	}	

 
timeCount();