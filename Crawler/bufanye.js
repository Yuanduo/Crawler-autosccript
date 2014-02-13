function aaa() {
    try{
        scrollBy(0, 10000);
    }catch(e)
    {}
    finally
    {
        setTimeout("bbb()",2000);
    }
}
function bbb() {
    ww.downloadSource();
    ww.endOfPage();
    return true;
}
aaa();