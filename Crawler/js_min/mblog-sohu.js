function clickNext(){var d=0;var e=0;var g=document.getElementById("t-people");var a=g.getElementsByTagName("p");ww.downloadSource();for(var b=0;b<a.length;b++){if(a[b].className==="page"){d=1;var f=a[b].getElementsByTagName("a");for(var c=0;c<f.length;c++){if(f[c].className==="pg pgNx crjs_pg"){e=1;simulate(f[c],"click");break}}break}}if(d==0||d==1&&e==0){ww.endOfPage();return true}}function compareTo(b){var a=b.substring(0,4);a=a.concat(b.substring(5,7));a=a.concat(b.substring(8,10));if(deadTime<=a){return true}else{return false}}var count=0;function init(){var f=document.getElementById("twitter_container_u");if(f===null){ww.endOfPage();return true}var e=f.getElementsByTagName("div");var c=0;for(var b=0;b<e.length;b++){if(e[b].className==="twiB"){var d=e[b].getElementsByTagName("b");for(var a=0;a<d.length;a++){if(d[a].className==="tm"){if(compareTo(d[a].title)){c=1;break}else{ww.endOfPage();return true}}}break}}if(c===1){count=0;clickNext()}if((count++)>20){ww.endOfPage();return true}setTimeout("init();",250)}setTimeout("init()",1000);