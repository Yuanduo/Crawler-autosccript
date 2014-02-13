var count=1;
function clickNext() {
        ww.downloadSource();
        var mark = $("a.next");
        if (mark.attr("href") === undefined) {
            ww.endOfPage();
            return true;
        } else {
            mark.click();
        }
        //setTimeout("clickNext()",2000)
}
function init() {
        window.scroll(0, 20000);
        if (document.getElementById("comments-list") === null) {
            ww.endOfPage();
            return true;
        } else 
        {
            var cnum0_str = document.getElementById("comments-list").innerHTML;
            if (cnum0_str === "") {
                count = count+1;
                if(count==10)
                {
//					ww.genErrorMsg();
                    ww.endOfPage();
                    return true;
                }
                setTimeout("init()", 2000);
            } else {
                var cnum0 = cnum0_str.substring(1, cnum0_str.length - 1);
                if (cnum0 !== 0) {
                    var hasonecomment = 0;
                    var comment_0 = document.getElementById("comment-0");
                    var divset = comment_0.getElementsByTagName("div");
                    for ( var i = 0; i < divset.length; i++) {
                        if (divset[i].className === "item") {
                            hasonecomment = 1;
                            break;
                        }
                    }
                    if (hasonecomment == 0) {
                        setTimeout("init()", 1000);
                    } else {
                        setTimeout("clickNext()", 1000);
                    }
                } else {
                    setTimeout("clickNext()", 1000);
                }
            }
        }
        setTimeout("init()",2000);
}

init();