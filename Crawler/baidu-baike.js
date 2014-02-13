var count = 0;
var fuzhi=0;
var arr=null;
function find_pagination() {
	ww.emitClickSignal();
	var ol = document.getElementById("sublemma-options-list");
	if (ol == null) {// 只有一页 ：没有分页块,测试另一种结构
		ww.singleUrlFinished();
		return true;
	}
	else
	{
		if(!fuzhi)
		{
		var li_set = ol.getElementsByTagName("li");
		
		arr = new Array(li_set.length-1);
		for(var i=0,j=0;i<li_set.length,j<arr.length;i++)
		{
			if(li_set[i].className != "expand")
			{
				arr[j]=li_set[i];
				j++;
			}
		}
		fuzhi=1;
		}
		
		
		if (count >= arr.length) {
			ww.singleUrlFinished();
			return true;
		}if (arr[count].className != "expand") {
			var anchor = arr[count].getElementsByTagName("a");
//			ww.genErrorMsg("click:" + anchor[0].innerHTML);
			simulate(anchor[0], "click");
		}
		count++;
		timeCount();
	}
}
function timeCount() {
	setTimeout("find_pagination()", 2000);	
}
timeCount();