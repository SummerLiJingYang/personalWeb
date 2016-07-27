// JavaScript Document
$(function(){
	function setHeight(){
			var h=$(window).height()-$("#top").innerHeight()-5;
			$("#left_menu").css('height',h);
			$("#right_content").css('height',h);
	};
	setHeight();
	$("#myiframe_2").height($(window).height()-$("#top").innerHeight()-10);
	$("#myiframe_1").height($(window).height()-$("#top").innerHeight()-10);
	$(window).resize(function() {
 			setHeight();
			$("#myiframe_2").height($(window).height()-$("#top").innerHeight()-10);
			$("#myiframe_1").height($(window).height()-$("#top").innerHeight()-10);
	});
	$(".nav a").click(function(){
		$(".nav a").removeClass("active");
		$(this).addClass("active");
		$("#myiframe_1").attr('src',$(this).attr('data_url'));
	});
	
	
	$("#check_all").click(function(){
			if($(this).attr('checked')){
				$(":input[name='check']").attr('checked',true);
				$(".set_btn").removeClass('inp_style3').attr('disabled',false);
			}else{
				$(":input[name='check']").attr('checked',false);
				$(".set_btn").addClass('inp_style3').attr('disabled',true);
			}
		});
	$(":input[name='check']").click(function(){
			if($(":input[name=check][checked]").length>1){
				$(".set_btn").removeClass('inp_style3').attr('disabled',false);
			}else{
				$(".set_btn").addClass('inp_style3').attr('disabled',true);
			}
	});
	

	//左侧栏
	$(".menu_ul li").click(function(){
        $(".menu_ul li").removeClass("active");
        $(this).addClass("active");
    });
    $(".menu_model").each(function(i){
        $(this).click(function(){
            $(".menu_model").removeClass("open");
            $(this).addClass("open");
            $(".menu_ul").hide();
            $(".menu_ul").eq(i).show();
        })
    });

    $(".menu_model").eq(0).addClass("open");
    $(".menu_ul").hide();
    $(".menu_ul").eq(0).show();
    $(".menu_ul").eq(0).find("li").eq(0).addClass("active");
    $(".menu_ul a:first i").click();

})

