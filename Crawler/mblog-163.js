function clickNext() {
    var hasNextPage=0;
    var hasPageBar=0;
    var div_set=document.getElementsByTagName("div");
    ww.downloadSource();
    for(var i=0;i<div_set.length;i++)
        if (div_set[i].className==="mainBox-page pages-big") {
            hasPageBar=1;
            var li_set=div_set[i].getElementsByTagName("li");
            for(var j=0;j<li_set.length;j++)
                if(li_set[j].className==="js-btn js-next") { //找到当前页
                        hasNextPage=1;
                        simulate(li_set[j],"click");
                        break;
                }
            break;
        }
    if(hasPageBar==0 ||hasPageBar==1 && hasNextPage==0) {//到达尾页||只有一页 ：没有分页块
        ww.endOfPage();
        return true;
    }
}
function compareTo(time) {
    if (time.substring(0,3)==="今天")
        return true;
    else if (time.substring(0,3)==="昨天")
        return true;
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
    var ul=document.getElementById("tweetList");
    if (ul===null){
        ww.endOfPage();
        return true;
    }
    var li_set=ul.getElementsByTagName("li");
    var opt=0;
    for (var i=0;i<li_set.length;i++) {
        var em_set=li_set[i].getElementsByTagName("em");
        for (var j=0;j<em_set.length;j++)
            if (em_set[j].className==="tweet-info-time cDGray")
                if (compareTo(em_set[j].innerHTML)) {
                    opt=1;
                    break;
                }
                else {
                    ww.endOfPage();
                    return true;
                }
        break;
    }
    if (opt===1) {
        count=0;
        clickNext();
    }
    setTimeout("init();",250);
    if ((count++)>20) {
        ww.endOfPage();
        return true;
    }
}

setTimeout("init();",1000);
