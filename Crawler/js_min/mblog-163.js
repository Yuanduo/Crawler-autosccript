function clickNext(){var f=0;var c=0;var e=document.getElementsByTagName("div");ww.downloadSource();for(var b=0;b<e.length;b++){if(e[b].className==="mainBox-page pages-big"){c=1;var d=e[b].getElementsByTagName("li");for(var a=0;a<d.length;a++){if(d[a].className==="js-btn js-next"){f=1;simulate(d[a],"click");break}}break}}if(c==0||c==1&&f==0){ww.endOfPage();return true}}function compareTo(b){if(b.substring(0,3)==="����"){return true}else{if(b.substring(0,3)==="����"){return true}}var a=b.substring(0,4);a=a.concat(b.substring(5,7));a=a.concat(b.substring(8,10));if(deadTime<=a){return true}else{return false}}var count=0;function init(){var d=document.getElementById("tweetList");if(d===null){ww.endOfPage();return true}var f=d.getElementsByTagName("li");var c=0;for(var b=0;b<f.length;b++){var e=f[b].getElementsByTagName("em");for(var a=0;a<e.length;a++){if(e[a].className==="tweet-info-time cDGray"){if(compareTo(e[a].innerHTML)){c=1;break}else{ww.endOfPage();return true}}}break}if(c===1){count=0;clickNext()}setTimeout("init();",250);if((count++)>20){ww.endOfPage();return true}}setTimeout("init();",1000);