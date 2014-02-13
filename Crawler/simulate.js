function simulate(t, c) {
	var evt = document.createEvent("MouseEvents");   
	evt.initEvent(c, true, true);
	t.dispatchEvent(evt);
}
