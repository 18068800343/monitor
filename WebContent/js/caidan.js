(function(window){

    // 导航
    $('.w_nav>li>a').click(function(){
        $(this).addClass('active').parent().siblings().find('a').removeClass('active');
        $(this).parent().siblings().find('.on').removeClass('on');
        $(this).parent().find('.w_nav2').slideToggle();
        $(this).parent().siblings().find('.w_nav2').slideUp();
    });
    $('.w_nav li li').click(function(){
        $(this).find('a').addClass('on').parent().siblings().find('a').removeClass('on');
    });


    // 列表详细内容
    $('.table td a').click(function(){
        $('.list').hide();
        $('.renwu').show();
    });
    $('.fanhui').click(function(){
        $('.list').show();
        $('.renwu').hide();
    });

    $("#boxa").mouseover(function(){
        $(".brgCdb").hide();
        $(".brgCdc").hide();
        $(".brgCdd").hide();
        $(".brgCde").hide();
        $(this).addClass("box1hover");
    }).mouseout(function(){
        $(this).removeClass("box1hover");
    })

    $("#boxb").mouseover(function(){
        $(".brgCdb").show();
        $(".brgCdc").hide();
        $(".brgCdd").hide();
        $(".brgCde").hide();
        $(this).addClass("box1hover");
    }).mouseout(function(){
        $(this).removeClass("box1hover");
    })
    $(".brgCdb").mouseleave(function(){
        $(".brgCdb").hide();
    })

    $("#boxc").mouseover(function(){
        $(".brgCdc").show();
        $(".brgCdb").hide();
        $(".brgCdd").hide();
        $(".brgCde").hide();
        $(this).addClass("box1hover");
    }).mouseout(function(){
        $(this).removeClass("box1hover");
    })
    $(".brgCdc").mouseleave(function(){
        $(".brgCdc").hide();
    })

    $("#boxd").mouseover(function(){
        $(".brgCdd").show();
        $(".brgCdb").hide();
        $(".brgCdc").hide();
        $(".brgCde").hide();
        $(this).addClass("box1hover");
    }).mouseout(function(){
        $(this).removeClass("box1hover");
    })
    $(".brgCdd").mouseleave(function(){
        $(".brgCdd").hide();
    })

    $("#boxe").mouseover(function(){
        $(".brgCde").show();
        $(".brgCdb").hide();
        $(".brgCdc").hide();
        $(".brgCdd").hide();
        $(this).addClass("box1hover");
    }).mouseout(function(){
        $(this).removeClass("box1hover");
    })
    $(".brgCde").mouseleave(function(){
        $(".brgCde").hide();
    })

    $("#boxf").mouseover(function(){
        $(".brgCdb").hide();
        $(".brgCdc").hide();
        $(".brgCdd").hide();
        $(".brgCde").hide();
        $(this).addClass("box1hover");
    }).mouseout(function(){
        $(this).removeClass("box1hover");
    })
    $("#boxg").mouseover(function(){
        $(".brgCdb").hide();
        $(".brgCdc").hide();
        $(".brgCdd").hide();
        $(".brgCde").hide();
        $(this).addClass("box1hover");
    }).mouseout(function(){
        $(this).removeClass("box1hover");
    })

    $(".qiao1").mouseover(function(){
        $(this).css("background","#0050bb").find("a").css("color","white")
    }).mouseout(function(){
        $(this).css("background","#eee").find("a").css("color","black")
    })
   
    $(".brgCdb div div a").click(function(){
        $("#box1").addClass("box1active");
    })

})(window);
