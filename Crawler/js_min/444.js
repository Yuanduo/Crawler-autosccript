function timeCount(){scrollBy(0,10000);count++;if(count<1){setTimeout("timeCount()",2000)}else{count=0;setTimeout("findp()",2000)}}function findp(){var c=0;ww.downloadSource();var b=document.getElementsByTagName("A");for(var a=0;a<b.length&&!c;a++){if(b[a].className=="next"){if(b[a].textContent.indexOf("��һҳ")!=-1||b[a].textContent.indexOf("��ҳ")!=-1){c=1;simulate(b[a],"click")}}}if(c==0){ww.endOfPage();return true}timeCount()}timeCount();