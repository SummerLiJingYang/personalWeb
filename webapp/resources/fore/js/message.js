$(function(){

//判断是否为手机
$.ismobile=function(string){
    return string.match(/^1[34578][0-9]{9}$/);
};

//判断是否为电话号码
$.isphone=function(string){
    return string.match(/^((0\d{2,3})-)(\d{7,8})(-(\d{3,4}))?$/);
};




	$(".leave_message").each(function(){
        $(this).click(function(){
            // var json = $(this).data("json");
            // $(".expert_name").html(json.name);
            //$('input[name="techCounselorId"]').val(json.techId);
            // $(".input_checkbox").each(function(){
            //     if(json.techSupportType == ($(this).data("id"))){
            //         $(this).addClass("check");
            //         $(this).next("input").prop("checked", true);
            //     }else{
            //         $(this).removeClass("check");
            //         $(this).next("input").prop("checked", false);
            //     }
            // });
            $(".message_tip").show();
            
        });
    });

    $(".message_tip .info").find(".input_checkbox").each(function(){
        $(this).click(function(){
            $(this).toggleClass("check");
        });
    });

    $(".message_tip .close").click(function(){
        $(".message_tip").hide();
        $("#content").val("")
		$(".input_text").val("");
    });
    $(".message_success .close").click(function(){
        $(".message_success").hide();
    });


	//设置消息提示
    function set_msg(msg){
        $(".error").html(msg).show();
        setTimeout(function(){
            $(".error").hide();
        },2000);
    }

	$("#messageForm .btn").click(function(){
		var content=$.trim($("#content").val());
		var username=$("#username").val();
		var mobile=$("#mobile").val();
		var techType=$("input[name=techType]:checked");
			
		if(content==null||content==""){
			set_msg("请留下需要咨询的问题");
			$("#content").focus();
			return false;
		}else if(content.length>300){
			set_msg("您填写的信息太长了");
			$("#content").focus();
			return false;
		};
		if(username==null||username==""){
			set_msg("请留下您的姓名");
			$("#username").focus();
			return false;
		};
		if(mobile==null||mobile==""){
			set_msg("请留下联系电话");
			$("#mobile").focus();
			return false;
		}else if(!$.ismobile(mobile)&&!$.isphone(mobile)){
            set_msg("请填写正确的联系电话");
            $("#mobile").focus();
            return false;
        };
		if(techType.length==0){
			set_msg("请至少选择一种类型");
			return false;
		};
		var ids="";
          $(".check").each(function(){
          	ids+=$(this).data("id")+",";
          });
        $("input[name='typeIds']").val(ids);
		$.ajax({
			url: '/techService/add',
			dataType:'json',
			type:"get",
			data:$("#messageForm").serialize(),
			success:function(rs){
				$("#content").val("")
				$(".input_text").val("");
				$(".message_tip").hide();
				$(".message_success .content").html(rs.msg);
				$(".message_success").show();
			}
		});

	});
})