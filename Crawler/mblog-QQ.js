
function timeCount(){
scrollBy(0,10000);
setTimeout("changeLinks()",2000);
}

function changeLinks()
{
 var a_button=document.getElementById("pageSetLink");	
 //alert("zhaodao");
 if(a_button.innerHTML=="还原为滚动方式")
	 {
	 setTimeout("findp()",2000);
	 }
 else 
	 {
	 simulate(a_button, "click"); 
	 setTimeout("findp()",3000);
	 }
}

function findp(){
	var hasnextpage=0;
	ww.downloadSource();
	var A_set= document.getElementsByTagName("A");
	for(var i=0;i<A_set.length&&!hasnextpage;i++){
		if(A_set[i].textContent.indexOf("下一页")!=-1 ||A_set[i].textContent.indexOf("下页")!=-1 ){
			hasnextpage=1;
			simulate(A_set[i], "click");      
			}
				}
	if(hasnextpage==0){
	ww.endOfPage();
	return true;
	}
	timeCount();
	}


timeCount();