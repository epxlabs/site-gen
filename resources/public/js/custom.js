$('a[href="#contact"]').click(function(){
   $('html, body').animate({
        scrollTop: $(document).height()
    }, 1500);
    return false;
})