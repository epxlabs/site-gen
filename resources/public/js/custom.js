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

    $('#contactForm').submit(function(){
      console.log(this)
      var name = $('#name').val()
      var email = $('#email').val()
      var message = $('#message').val()
      if (!name || !email || !message) {
        return
      }
      var data = {name: name, email: email, message: message}
      var URL = "https://eql5336ltg.execute-api.us-east-1.amazonaws.com/dev/contact-us"
      $.ajax({
        type: 'POST',
        url: URL,
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
          console.log('Form successfully submitted')
        },
        error: function () {
          alert('There was a problem submitting your form.')
        }
      })
    })

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
    var d = document, s = d.createElement('script');
    s.src = '//www-epxlabs-com.disqus.com/embed.js';
    s.setAttribute('data-timestamp', +new Date());
    (d.head || d.body).appendChild(s);
  })();
