$(function() {
    /*导航nav移入效果*/
    $("#nav li").each(function() {
        if (typeof $(this).attr("class") == "undefined") {
            $(this).mouseover(function() {
                $(this).children(".hover").css("display","block");
            }).mouseout(function() {
                $(this).children(".hover").hide();
            });
        };
    });
    $("#nav .more_nav").each(function(){
        $(this).mouseover(function() {
            $(this).find(".more_nav_div").show();
        }).mouseout(function() {
            $(this).find(".more_nav_div").hide();
        });
    });


    //banner切换
    var gallery = {
        i: 0,
        len: $(".banner_list li").length,
        timer: null,
        change: function (index) {
            $(".banner_list").find("li").eq(index).fadeIn().siblings().fadeOut();
            $(".dot").find("a").eq(index).addClass("on").siblings().removeClass("on");
        },
        start: function () {
            gallery.timer = setInterval(function () {
                gallery.i == gallery.len - 1 ? gallery.i = 0 : ++gallery.i
                gallery.change(gallery.i);
            }, 3000);
        },
        stop: function () {
            clearInterval(gallery.timer);
        },
        event: function () {
            $(".dot").find("a").on("mouseover", function () {
                gallery.change($(this).index());
                gallery.stop();
            }).on("mouseout", gallery.start);
            $(".banner_list").find("li").on("mouseover", gallery.stop).on("mouseout", gallery.start);
        },
        init: function () {
            gallery.change(0);
            gallery.start();
            gallery.event();
        }
    };
    gallery.init();


});

//限制文字
$.fn.interceptText=function(interval){
    var $this=$(this);
    var content=$this.html();
    if($this.length>0){
        var content= content.replace(/\s/g,"");
        if(content.length>interval){
            content=content.substring(0,interval);
            $(this).html(content+"... ");
        };
    };
};


//添加Cookie
function addCookie(name, value, options) {
    if (arguments.length > 1 && name != null) {
        if (options == null) {
            options = {};
        }
        if (value == null) {
            options.expires = -1;
        }
        if (typeof options.expires == "number") {
            var time = options.expires;
            var expires = options.expires = new Date();
            expires.setTime(expires.getTime() + time * 1000);
        }
        document.cookie = encodeURIComponent(String(name)) + "=" + encodeURIComponent(String(value)) + (options.expires ? "; expires=" + options.expires.toUTCString() : "") + (options.path ? "; path=" + options.path : "") + (options.domain ? "; domain=" + options.domain : ""), (options.secure ? "; secure" : "");
    }
}

// 获取Cookie
function getCookie(name) {
    if (name != null) {
        var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
        return value ? decodeURIComponent(value[1]) : null;
    }
}

// 移除Cookie
function removeCookie(name, options) {
    addCookie(name, null, options);
}



