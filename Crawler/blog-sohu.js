function timeCount(){
scrollBy(0,250);
setTimeout("findp()",200);
}
function findp(){
var hasnextpage=0;
ww.downloadSource();
alert("save")
var div=document.getElementById("pageText");    
var A_set= div.getElementsByTagName("A");
for(var i=0;i<A_set.length&&!hasnextpage;i++){
	if(A_set[i].textContent.indexOf("��һҳ")!=-1 ||A_set[i].textContent.indexOf("��ҳ")!=-1 ){
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












