(function($) {

    "use strict";

    var newItem;
    var img = $('.lightbox img');
    var href;
    var length;
    var index;

    $('.loader .bar').addClass('load');

    $('.flexslider').flexslider({
        animation: "swing",
        directionNav: true,
        controlNav: false,
        touch: false,
        start: function(){
            var activeSlide = $('.flex-active-slide');
            if(activeSlide.hasClass('light'))
                $('.top').addClass('light');
            else {
                $('.top').removeClass('light');
            }
        },
        after: function(){
            var activeSlide = $('.flex-active-slide');
            if(activeSlide.hasClass('light'))
                $('.top').addClass('light');
            else {
                $('.top').removeClass('light');
            }
        }
    });

    $('a').click(function(e){
        if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
            if (target.length) {
                $('html,body').animate({
                  scrollTop: target.offset().top - 60
                }, 500, 'easeInOutExpo');
            }
            e.preventDefault();
        } else if(!$(this).hasClass('item')) {
            var url = $(this).attr('href');
            $('body').fadeOut(600, function(){
                window.location = url;
            });
            e.preventDefault();
        }
    });

	$(window).scroll(function(){
        var scrolled = $(window).scrollTop();
        $('.fs').each(function(){
            $('.fs').css('top', (scrolled * .2) + 'px');         
        });
        $('.fo').each(function(){
            var opa = 1-(scrolled*.0015);
            $('.fo').css('opacity', opa);   
        });
    });

    $('.top').affix();

    $('.toggle').click(function(e){
    	$('nav').toggleClass('toggled');
    	e.stopPropagation();
    });

    $('html').click(function(){
    	$('nav').removeClass('toggled');
    });

    $('section').each(function() {
        $(this).waypoint(function(direction){
            var sectionClass = $(this).attr('id');
            if(direction == 'up') {
                $('nav .active').removeClass('active');
                $('.'+sectionClass).addClass('active');
            }
        }, {offset: '-50%'});
        $(this).waypoint(function(direction){
            var sectionClass = $(this).attr('id');
            if(direction == 'down') {
                $('nav .active').removeClass('active');
                $('.'+sectionClass).addClass('active');
            }
        }, {offset: 60});
    });

    $('.progress-bar').waypoint(function(direction){
        var bar = $(this);
        var bar_width = $(this).attr('aria-valuenow');
        bar.width(bar_width + '%');
    }, {offset: '90%'});

    $('a.item').click(function(e){
        e.stopPropagation();
        e.preventDefault();
        newItem = $(this);
        img.attr('src', '');
        length = $(this).parent().find('.item').length;
        index = 1+($(this).parent().find('a').index(this));
        lightboxInit(newItem);
        
    });

    function lightboxInit(e){
        var item = e;
        $('.counter .count').html(index);
        $('.counter .total').html(length);
        if(!item.next().hasClass('item') && !item.parent().next().find('a').hasClass('item'))
            $('.lightbox .next').fadeOut(200);
        else
            $('.lightbox .next').fadeIn(200);
        if(!item.prev().hasClass('item') && !item.parent().prev().find('a').hasClass('item'))
            $('.lightbox .prev').fadeOut(200);
        else
            $('.lightbox .prev').fadeIn(200);
        href = item.attr('href');
        $('.lightbox').addClass('show');
        
        $('.lightbox .title h3').html('');
        $('.lightbox .description p').html('');

        var title = item.find('.caption h4').html();
        $('.lightbox .title h3').html(title);

        var desc = item.find('.caption p').html();
        $('.lightbox .description p').html(desc);

        img.removeClass('show');
        setTimeout(showImage,1000);
    }

    function showImage(){
        img.attr('src', '');
        var newImg = img.attr('src', href).load(function(){
            setTimeout(displayImg,200);
        });
    }

    function displayImg(){
        img.addClass('show');
    }

    $('.lightbox img').click(function(){
        $(this).toggleClass('large');
    });

    $('.lightbox .x').click(function(){
        $('.lightbox').removeClass('show');
    });

    $('.lightbox .next').click(function(){
        if(newItem.next().hasClass('item')){
            index = index+1;
            newItem = newItem.next('.item');
        }
        else if(newItem.parent().next().find('a').hasClass('item')){
            newItem = newItem.parent().next().find('.item');
        }
        lightboxInit(newItem);
    });

    $('.lightbox .prev').click(function(){
        if(newItem.prev().hasClass('item')){
            index = index-1;
            newItem = newItem.prev('.item');
        }
        else if(newItem.parent().prev().find('a').hasClass('item')){
            newItem = newItem.parent().prev().find('.item');
        }
        lightboxInit(newItem);
    });

})(jQuery);

$(window).load(function(){
    setTimeout(showSite,1000);
});

function showSite(){
    $('.loader').addClass('loaded');
}