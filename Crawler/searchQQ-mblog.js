function timeCount(){
scrollBy(0,10000);
setTimeout("findp()",2000);
}
function findp(){
var hasnextpage=0;
ww.downloadSource();
var div_set=document.getElementsByTagName("div");
for(var j=0;j<div_set.length;j++)
	{	 
	   if(div_set[j].className=="noresult")
		   {		   
		   alert("������ѹ������������˺ţ�");
		   ww.endOfPage();
		   return true;
		   }
	   if(div_set[j].className=="pageNav blueFoot")
		   {
		   var A_set= document.getElementsByTagName("A");
		   for(var i=0;i<A_set.length&&!hasnextpage;i++){
		   	if(A_set[i].textContent.indexOf("��һҳ")!=-1 ||A_set[i].textContent.indexOf("��ҳ")!=-1 ){
		   		hasnextpage=1;
		   		simulate(A_set[i], "click");      
		   		}
		   			}
		   break;
		   
		   }
	}//for div

if(hasnextpage==0){
ww.endOfPage();
return true;
}
timeCount();
}
timeCount();