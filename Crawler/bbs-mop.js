function init() {
    var hasNextPage=0;
    var hasPageBar=0;
	var div_set=document.getElementsByTagName("div");
    ww.downloadSource_static();
    for(var i=0;i<div_set.length;i++) {
        if (div_set[i].className==="page") {
            hasPageBar=1;
			var aset=div_set[i].getElementsByTagName("a");
			for(var j=0;j<aset.length;j++)
                if(aset[j].className==="end" && aset[j].innerHTML!=="上一页") {
                    hasNextPage=1;
					simulate(aset[j],"click");
                    break;
				}
			break;
		}
	}
    if(hasPageBar==0||hasPageBar==1&&hasNextPage==0) {//到达尾页||只有一页 ：没有分页块
        ww.endOfPage();
		return true;
	}
}
init();
