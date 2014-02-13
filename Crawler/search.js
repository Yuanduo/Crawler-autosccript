function timeCount(){
scrollBy(0,10000);
setTimeout("findp()",2000);
}
function findp(){
var hasnextpage=0;
ww.downloadSource();
var A_set= document.getElementsByTagName("A");
for(var i=0;i<A_set.length&&!hasnextpage;i++){
	if(A_set[i].textContent.indexOf("ÏÂÒ»Ò³")!=-1 ||A_set[i].textContent.indexOf("ÏÂÒ³")!=-1 ){
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