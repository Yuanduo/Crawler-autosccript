function init(){
    var hasNextPage=0;
	var a_set=document.getElementsByTagName("a");
    ww.downloadSource_static();
    for(var i=0;i<a_set.length;i++) {
        if (a_set[i].getAttribute("title")==="��һҳ") {
            hasNextPage=1;
			simulate(a_set[i],"click");
			break;
		}
	}
    if(hasNextPage==0) {//����βҳ||ֻ��һҳ ��û�з�ҳ��
		ww.endOfPage();
		return true;
	}
}

init();
