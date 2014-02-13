function setmycookies()
{
	alert("a");
		var res="mytest=ÇëÎÊÄãÊÇË­£¿";
		
	document.cookie=encodeURI(res);
     alert("b");
     
  //   alert(document.cookie);
     //ww.refreshCookie();
    alert( ww.getcommentpageNum());
    alert("b");
}

setmycookies();