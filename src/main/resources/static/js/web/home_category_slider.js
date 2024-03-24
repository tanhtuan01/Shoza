$(document).ready(function(){
  var countItem = document.querySelectorAll(".category-list a").length;
  var rowValue = (countItem >= 20) ? 2 : 1;

  $('.category-list').slick({
    slidesToShow: 10,
    rows: rowValue,
    variableWidth: true,
    lazyLoad: 'ondemand',
    prevArrow: '<button type="button" class="slick-custom-arrow slick-prev"> < </button>',
    nextArrow: '<button type="button" class="slick-custom-arrow slick-next"> > </button>',
  });
});



