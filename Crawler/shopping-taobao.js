function init() {
	var hasPinglun = 0;
	var tabbar = document.getElementById("J_TabBar");
	var a_set = tabbar.getElementsByTagName("a");
	for ( var i = 0; i < a_set.length; i++)
		if (a_set[i].getAttribute("data-index") == 1
				&& a_set[i].href.indexOf("#reviews") !== -1) {
			hasPinglun = 1;
			simulate(a_set[i],"click");
			break;
		}
	if (hasPinglun == 1)
		setTimeout("clickNext()", 3000);
	else {
		ww.endOfPage();
		return true;
	}
}
function clickNext() {
	var hasPageBar = 0;
	var hasNextPage = 0;
	ww.downloadSource();
	var pageBar = document.getElementById("reviews");// ������û�����۶�Ӧ����
	alert("a");
	hasPageBar = 1;	 	 		
	
	var a_set = pageBar.getElementsByTagName("a");	 
	alert("b");
	for ( var i = 0; i < a_set.length; i++) {
		if (a_set[i].className == "page-next") {
			hasNextPage = 1;
			simulate(a_set[i],"click");
			break;
		}
	}
	if (hasPageBar == 0 || hasPageBar == 1 && hasNextPage == 0) {// ����βҳ(ֻ��1ҳ)||û�з�ҳ��(������=0)
		ww.endOfPage();
		return true;
	}
	setTimeout("clickNext()", 1000);
 
}
setTimeout("init()",2000);
