var count=0;
function init(){
    var hasNextPage=-1;
    var hasPageBar=0;
    var div_set=document.getElementsByTagName("div");
    ww.downloadSource();
	if(div_set!==null){
        for(var i=0;i<div_set.length;i++)
            if(div_set[i].className==="clearfix") {
                hasPageBar=-1;
                if (div_set[i].style.display==="") {
                    hasPageBar=1;
                    var span_set=div_set[i].getElementsByTagName("span");
                    for(var j=0;j<span_set.length;j++)
                        if(span_set[j].innerHTML==="下一页"&&span_set[j].className==="pgi pgb iblock fc03 bgc9 bdc0"){
                            hasNextPage=1;
                            simulate(span_set[j],"click");
                            count=0;
                            break;
                        }
                        else if (span_set[j].innerHTML==="下一页"&&span_set[j].className==="pgi pgb iblock fc03 bgc9 bdc0 js-znpg-097"){
                            hasNextPage=0;
                            break;
                        }
                }
            }
	}
    if(hasPageBar===0||hasPageBar===1&&hasNextPage===0||(count++)>20){
		ww.endOfPage();
		return true;
	}
    else
        setTimeout("init()",250);
}

//setTimeout("init()",2000);
init();
