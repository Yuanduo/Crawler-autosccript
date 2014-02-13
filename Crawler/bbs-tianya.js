function init() {
    var hasNextPage=0;
    var hasPageBar=0;
	var div=document.getElementById("post_head");
    ww.downloadSource_static();
    if (div !== null) {
        hasPageBar=1;
        var a_set = div.getElementsByTagName("a");
        for ( var j = 0; j < a_set.length; j++)
            if (a_set[j].innerHTML==="ÏÂÒ³") {
                hasNextPage = 1;
                simulate(a_set[j], "click");
                break;
            }
    }
    if(hasPageBar==0 || hasPageBar==1&&hasNextPage==0) {//µ½´ïÎ²Ò³
        ww.endOfPage();
        return true;
    }
}

init();
