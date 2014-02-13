var num=1;
var count=1;
var flag=true;
function location() {
	var ct = document.getElementById("ct");
    if (ct === null) {
		count = count+1;
        if(count==10) {
			ww.endOfPage();
			return true;
		}
		setTimeout("location()",1500);
	} else {
		var aset = ct.getElementsByTagName("a");
		for ( var i = 0; i < aset.length; i++) {
            if (aset[i].innerHTML === "评论") {
				var cmtnum = aset[i].nextSibling;
				var index = cmtnum.nodeValue.indexOf(")");
				num = cmtnum.nodeValue.substring(1, index);
				num = Math.ceil(num / 10);
				break;
			}
		}
		setTimeout("init(" + 1 + ")", 2000);
	}
}
function init(pagenum) {
	var haspagebar=0;
	var hasnextpage=0;
	var cmt=document.getElementById("cmt");
	var td_set = cmt.getElementsByTagName("td");
	for(var i=0;i<td_set.length;i++)
	{
		if(td_set[i].className=="page_bar")
		{
			haspagebar=1;
			var span_set = td_set[i].getElementsByTagName("span");
			for(var j=0;j<span_set.length;j++)
			{
				if(span_set[j].style.color=="red")
				{//找到当前页的span,获得span的下一个兄弟节点
					var anchor = span_set[j].nextSibling;
					if(anchor.nodeName!="#text")
					{
						ww.downloadSource();
						hasnextpage=1;
						++pagenum;
						anchor.onclick();
						break;
					}else if(pagenum==num)
					{
						ww.downloadSource();
						break;
					}
				}
			}
			break;
		}
	}
	if(hasnextpage==0 ||haspagebar==0)
	{//到达尾页||只有一页 ：没有分页块
		if(pagenum<num);
        else {
			ww.endOfPage();
			return true;
		}
	}
	timeCount(pagenum);
}
function timeCount(pagenum)
{	
	setTimeout("init("+pagenum+")",2000);
}

location();
