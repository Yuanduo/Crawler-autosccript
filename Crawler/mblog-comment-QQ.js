function timeCount(){
scrollBy(0,10000);
setTimeout("findp()",2000);

}

function findp(){	
var hasnextpage=0;
ww.downloadSource();
var listWrapper_div= document.getElementById("listWrapper");
var li_set=listWrapper_div.getElementsByTagName("li");
for(var i=0;i<li_set.length;i++)
	{
	if(li_set[i].className=="select")
		{
			simulate(li_set[i], "click");		
		break;
		}
	}  
 
var A_set=listWrapper_div.getElementsByTagName("A");

  for(var j=0;j<A_set.length&&!hasnextpage;j++){
	 
			if(A_set[j].className=="pageBtn"){			
			if(A_set[j].textContent.indexOf("ÏÂÒ»Ò³")!=-1 ||A_set[j].textContent.indexOf("ÏÂÒ³")!=-1 ){
				hasnextpage=1;				
				simulate(A_set[j], "click");				
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