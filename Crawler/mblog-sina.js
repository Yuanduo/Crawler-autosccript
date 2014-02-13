//var deadTime = "20121201";
function clickNext_1() {
	var hasNextPage = 0;
	var hasPageBar = 0;
	var div = document.getElementById("pl_content_hisFeed");
	var div_set = div.getElementsByTagName("div");
	ww.downloadSource();
	 
	for ( var i = 0; i < div_set.length; i++)
		if (div_set[i].className === "W_pages"
				|| div_set[i].className === "W_pages W_pages_comment") {
			hasPageBar = 1;
			var spanset = div_set[i].getElementsByTagName("span");
			for ( var j = 0; j < spanset.length; j++)
				if (spanset[j].innerHTML === "下一页") {
					hasNextPage = 1;
					var evt = document.createEvent("MouseEvents");
					evt.initEvent("click", true, true);
					spanset[j].dispatchEvent(evt);
					break;
				}
			break;
		}
	if (hasPageBar == 0 || hasPageBar == 1 && hasNextPage == 0) {// 到达尾页||只有一页
		// ：没有分页块
		ww.endOfPage();
		return true;
	}
}
function clickNext_2() {
	var hasNextPage = 0;
	var hasPageBar = 0;
	var div = document.getElementById("epfeedlist");
	var div_set = div.getElementsByTagName("div");
	ww.downloadSource()";
	for ( var i = 0; i < div_set.length; i++)
		if (div_set[i].className === "fanye MIB_txtb rt") {
			var a_set = div_set[i].getElementsByTagName("a");
			;
			hasPageBar = 1;
			for ( var j = 0; j < a_set.length; j++)
				if (a_set[j].innerHTML === "<em>下一页</em>") {
					hasNextPage = 1;
					var evt = document.createEvent("MouseEvents");
					a
					evt.initEvent("click", true, true);
					a_set[j].dispatchEvent(evt);
					break;
				}
			break;
		}
	if (hasPageBar == 0 || hasPageBar == 1 && hasNextPage == 0) {// 到达尾页||只有一页
		// ：没有分页块
		ww.endOfPage();
		return true;
	} else
		setTimeout("work()", 250);
}
function compareTo_1(time) {
	var s = time.substring(0, 4);
	s = s.concat(time.substring(5, 7));
	s = s.concat(time.substring(8, 10));
	if (deadTime <= s)
		return true;
	else
		return false;
}
function compareTo_2(time) {
	var s = time.substring(14, 18);
	s = s.concat(time.substring(19, 21));
	s = s.concat(time.substring(22, 24));
	if (deadTime <= s)
		return true;
	else
		return false;
}
function findLoadNew_1() {
	var div = document.getElementById("pl_content_hisFeed");
	var div_set = div.getElementsByTagName("div");
	var opt = 0;
	for ( var i = 0; i < div_set.length; i++)
		if (div_set[i].className === "W_loading") {
			if (div_set[i].innerHTML == "<span>正在加载中，请稍候...</span>") {
				opt = 1;
				scrollBy(0, 8000);
				break;
			} else if (div_set[i].innerHTML == "<span>正在加载，请稍候...</span>") {
				opt = 1;
				break;
			}
		} else if (div_set[i].className === "W_pages") {
			opt = 2;
			clickNext_1();
			break;
		}
	if (opt == 1)
		setTimeout("findLoadNew_1()", 250);
	else if (opt == 2)
		setTimeout("work()", 10000);
	else {
		setTimeout("ww.endOfPage()", 10000);
				return true;
	}
}

var count = 0;
function work() {
	var div = document.getElementById("pl_content_hisFeed");
	var opt = 0;
	opt = 0;
	if (div !== null) {
		var div_set = div.getElementsByTagName("div");
		var a_set;
		for ( var i = 0; i < div_set.length; i++)
			if (div_set[i].className === "WB_detail") {
				a_set = div_set[i].getElementsByTagName("a");
				for ( var j = 0; j < a_set.length; j++)
					if (a_set[j].className === "S_link2 WB_time") {
						if (compareTo_1(a_set[j].title)) {
							opt = 1;
							break;
						} else {
							ww.endOfPage();
							return true;
						}
					}
				break;
			}
	} else {
		var ul = document.getElementById("feed_list");
		if (ul === null) {
			if (++count > 20) {
				ww.endOfPage();
				return true;
			}
		} else {
			var li_set = ul.getElementsByTagName("li");
			var strong_set;
			for ( var i = 0; i < li_set.length; i++) {
				strong_set = li_set[i].getElementsByTagName("strong");
				for ( var j = 0; j < strong_set.length; j++)
					if (compareTo_2(strong_set[j].outerHTML)) {
						opt = 2;
						break;
					} else {
						ww.endOfPage();
						return true;
					}
				break;
			}
		}
	}
	if (opt == 1)
		findLoadNew_1();
	else if (opt == 2)
		clickNext_2();
	else
		setTimeout("work();", 250);
}
function init() {
	var div = document.getElementById("pl_content_hisFeed");
	var a_set = div.getElementsByTagName("a");
	for ( var i = 0; i < a_set.length; i++)
		if (a_set[i].className == "item_link S_func1"&&a_set[i].innerHTML=="微博") {
			var evt = document.createEvent("MouseEvents");
			evt.initEvent("click", true, true);
			a_set[i].dispatchEvent(evt);
			break;
		}
}

setTimeout("init();", 500);
setTimeout("work();", 1000);
