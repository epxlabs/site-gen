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


  

