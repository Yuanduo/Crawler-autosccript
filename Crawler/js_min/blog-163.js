var count=0;function init(){var f=-1;var d=0;var e=document.getElementsByTagName("div");ww.downloadSource();if(e!==null){for(var c=0;c<e.length;c++){if(e[c].className==="clearfix"){d=-1;if(e[c].style.display===""){d=1;var a=e[c].getElementsByTagName("span");for(var b=0;b<a.length;b++){if(a[b].innerHTML==="��һҳ"&&a[b].className==="pgi pgb iblock fc03 bgc9 bdc0"){f=1;simulate(a[b],"click");count=0;break}else{if(a[b].innerHTML==="��һҳ"&&a[b].className==="pgi pgb iblock fc03 bgc9 bdc0 js-znpg-097"){f=0;break}}}}}}}if(d===0||d===1&&f===0||(count++)>20){ww.endOfPage();return true}else{setTimeout("init()",250)}}init();