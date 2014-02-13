function init(){
    var hasNextPage=0;
	var a_set=document.getElementsByTagName("a");
    ww.downloadSource_static();
    for(var i=0;i<a_set.length;i++) {
        if (a_set[i].getAttribute("title")==="下一页") {
            hasNextPage=1;
			simulate(a_set[i],"click");
			break;
		}
	}
    if(hasNextPage==0) {//到达尾页||只有一页 ：没有分页块
		ww.endOfPage();
		return true;
	}
}

init();
