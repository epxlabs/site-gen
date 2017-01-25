$('a[href="#contact"]').click(function(){
  $('html, body').animate({
    scrollTop: $(document).height()
  }, 1500);
  return false;
})

$(document).mousemove(function(event) {
  currentMousePos = event.target
});

var clickEvents = function(){
  $('#contact').mouseenter(function(){
    $('li#contact').addClass('active')
  })
  $('#contact').mouseleave(function(){
    var rawpage = window.location.pathname
    if (rawpage == "/"){
      var page = "home"
    }
    else {
      var page = rawpage.slice(1).slice(0, -1)
    }
    $('li#contact').removeClass('active')
    $('li#' + page).addClass('dropdown active')
  })

  $('#footer').mouseleave(function(){
    $('li#contact-us').addClass('active')
  })}

  $(document).ready(function(){
    var links = $('.nav-links')
    for (var i = 0; i < links.length; i++) {
      var link = links[i]
      var href = $(link).attr('href')
      $(link).attr('href', "/" + href)
    }
    /* This function changes the title to match the blog post on a blog post page*/
    if($('.blog-post').length == 1){
      var title = "EPX Labs, Inc. | " + $('h1').text()
      $(document).prop('title', title)
    }
    clickEvents()
    setInterval(function(){
      if ($('li.active').length > 1){
        if ($(currentMousePos).attr('id') == "contact"){
          $($('li.active')[0]).removeClass('dropdown active')
        }
        else {
          $($('li.active')[1]).removeClass('dropdown active')
        }
      }
      else if ($('li.active').length < 1){
        $('li#contact-us').addClass('active')
      }
      clickEvents()
    }, 1)})



    var disqus_config = function () {
      this.page.url = "http://www.epxlabs.com";  // Replace PAGE_URL with your page's canonical URL variable
      this.page.identifier = window.location.pathname; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
    };

    (function() { // DON'T EDIT BELOW THIS LINE
    if (window.location.pathname.match(/\/blog\/.+/)) {
      //CONDITION ONLY LOADS DISQUS ON BLOGPOST PAGES
    var d = document, s = d.createElement('script');
    s.src = '//www-epxlabs-com.disqus.com/embed.js';
    s.setAttribute('data-timestamp', +new Date());
    (d.head || d.body).appendChild(s);
  }
  })();
