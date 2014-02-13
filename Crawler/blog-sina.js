var count=0;
function init() {
    var hasNextPage=0;
    var hasPageBar=0;
	var pagebar=document.getElementById("commentPaging");
    ww.downloadSource();
    if(pagebar!==null) {
        hasPageBar=1;
        var li_set=pagebar.getElementsByTagName("li");
        for(var j=0;j<li_set.length;j++)
            if(li_set[j].className==="SG_pgnext") {
                hasNextPage=1;
                var anchor=li_set[j].firstChild;
                simulate(anchor,"click");
                count=0;
                break;
            }
    }
    if(hasPageBar===0||hasPageBar===1&&hasNextPage===0||(count++)>20) {
		ww.endOfPage();
		return true;
	}
    else
        setTimeout("init()",250);
}

setTimeout("init()",2000);
