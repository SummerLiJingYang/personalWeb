$(function(){
    //定时刷新首页
	setInterval(function(){
      location.reload();
	},600000);

    //技术服务切换
    var slideX = {
        thisUl: $('.technical_list ul'),
        btnLeft: $('.technical a.prev'),
        btnRight: $('.technical a.next'),
        thisLi: $('.technical_list ul li'),
        init: function () {
            slideX.thisUl.width(slideX.thisLi.length * 202);
            slideX.slideAuto();
            slideX.btnLeft.click(slideX.slideLeft).hover(slideX.slideStop, slideX.slideAuto);
            slideX.btnRight.click(slideX.slideRight).hover(slideX.slideStop, slideX.slideAuto);
            slideX.thisUl.hover(slideX.slideStop, slideX.slideAuto);
        },
        slideLeft: function () {
            slideX.btnLeft.unbind('click', slideX.slideLeft);
            slideX.thisUl.find('li:last').prependTo(slideX.thisUl);
            slideX.thisUl.css('marginLeft', -202);
            slideX.thisUl.animate({ 'marginLeft': 0 }, 350, function () {
                slideX.btnLeft.bind('click', slideX.slideLeft);
            });
            return false;
        },
        slideRight: function () {
            slideX.btnRight.unbind('click', slideX.slideRight);
            slideX.thisUl.animate({ 'marginLeft': -202 }, 350, function () {
                slideX.thisUl.css('marginLeft', '0');
                slideX.thisUl.find('li:first').appendTo(slideX.thisUl);
                slideX.btnRight.bind('click', slideX.slideRight);
            });
            return false;
        },
        slideAuto: function () {
            slideX.intervalId = window.setInterval(slideX.slideRight, 3000);
        },
        slideStop: function () {
            window.clearInterval(slideX.intervalId);
        }
    }
    slideX.init();

    //技术服务
    $(".technical_list li").each(function(){
        $(this).mouseover(function(){
            $(this).find(".info").show();
        }).mouseout(function(){
            $(this).find(".info").hide();
        });
    });



    //设备展区切换
    (function ($) {
        $.fn.Tab = function (idName, selecd, noselecd) {
            $("#" + idName).find(".tab li").each(function (i) {
                $(this).mouseover(function () {
                    $("#" + idName).find(".tab li").removeClass(selecd);
                    $("#" + idName).find(".tab li").addClass(noselecd);
                    $(this).removeClass(noselecd);
                    $(this).addClass(selecd);
                    $("#" + idName).find(".content_div").hide();
                    $("#" + idName).find(".content_div").eq(i).show();
                })
            })
        };
    })(jQuery);
    $().Tab("equipment","on","no_on");
    $("#equipment .company li").eq(5).addClass("last");
    
    //限制文字
    $("#technical .intercept_text").each(function(){
        $(this).interceptText(25);
    });
    $("#equipment .intercept_text").each(function(){
        $(this).interceptText(28);
    });
    $("#plastics .intercept_text").each(function(){
        $(this).interceptText(47);
    });


            

    //电梯效果
    $(window).scroll(function(){
        if($(window).scrollTop()>100){
            $(".lift").css({"top":"0"});
        }else{
            $(".lift").css({"top":"192px"});
        };
    });
    var lift={
        pos:{},
        get_pos:function(){
            $("body").find(".floor_flag").each(function(index){
                lift.pos[index]=$(this).offset().top;
            });
        },
        
        go:function(){
            $(".lift ul").find("li").on("click",function(){
                $(window).off("scroll",lift.scroll);
                var index=$(this).index();
                $("html,body").animate({scrollTop:lift.pos[index]},500,function(){
                    $(window).on("scroll",lift.scroll); 
                });
                $(".lift").css("top","0")
            });
        },
        go_top:function(){
            $(".lift").find(".go_top").on("click",function(){
                $("html,body").animate({scrollTop:"0"},500);
            });
        },
        scroll:function(){
            var s_top=$(window).scrollTop();
            if(s_top>lift.pos[0]){
                $(".lift").fadeIn(100);                
            }else{
                $(".lift").fadeOut(100);
            }
        },
        init:function(){
            lift.get_pos();
            lift.go();
            lift.go_top();
            $(window).on("scroll",lift.scroll);
        }
    };
    lift.init();
});
