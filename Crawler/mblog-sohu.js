
function clickNext() {
    var hasPageBar=0;
    var hasNextPage=0;
    var div=document.getElementById("t-people");
    var p_set=div.getElementsByTagName("p");
    ww.downloadSource();
    for (var j=0;j<p_set.length;j++)
        if(p_set[j].className==="page") {//ÓÐ·­Ò³¿é
            hasPageBar=1;
            var a_set = p_set[j].getElementsByTagName("a");
			for (var i=0;i<a_set.length;i++)
                if(a_set[i].className==="pg pgNx crjs_pg") {
                    hasNextPage=1;
					simulate(a_set[i],"click");
					break;
				}
			break;
		}
    if(hasPageBar==0||hasPageBar==1&&hasNextPage==0) {
		ww.endOfPage();
		return true;
	}
}
function compareTo(time) {
    var s=time.substring(0,4);
    s=s.concat(time.substring(5,7));
    s=s.concat(time.substring(8,10));
    if (deadTime<=s)
        return true;
    else
        return false;
}
var count=0;
function init() {
    var div=document.getElementById("twitter_container_u");
    if (div===null) {
        ww.endOfPage();
        return true;
    }
    var div_set=div.getElementsByTagName("div");
    var opt=0;
    for (var i=0;i<div_set.length;i++)
        if (div_set[i].className==="twiB") {
            var b_set=div_set[i].getElementsByTagName("b");
            for (var j=0;j<b_set.length;j++)
                if (b_set[j].className==="tm") {
                    if (compareTo(b_set[j].title)) {
                        opt=1;
                        break;
                    }
                    else {
                        ww.endOfPage();
                        return true;
                    }
                }
            break;
        }
    if (opt===1) {
        count=0;
        clickNext();
    }
    if ((count++)>20) {
        ww.endOfPage();
        return true;
    }
    setTimeout("init();",250);
}

setTimeout("init()",1000);
