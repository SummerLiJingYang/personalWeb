// JavaScript Document
jQuery.support.cors = true;
$(function() {
    //城市
    $.fn.dwt = function(option) {
        var btn = true;
        var that = null;
        $(this).click(function() {

            var $dwt = $(this);
            if ($("#mask")) {
                that = $(this);
                $("#mask").remove();
            }
            if ($("#dwt")) {
                $("#dwt").remove();
            }
            $('body').append('<div id="mask"></div>');
            $("#mask").css('height', $('html,body').height());
            $("#mask").show();
            var arr = [{
                '110000': '北京'
            }, {
                '340000': '安徽'
            }, {
                '350000': '福建'
            }, {
                '440000': '广东'
            }, {
                '450000': '广西'
            }, {
                '500000': '重庆'
            }, {
                '520000': '贵州'
            }, {
                '620000': '甘肃'
            }, {
                '820000': '澳门'
            }, {
                '130000': '河北'
            }, {
                '220000': '吉林'
            }, {
                '230000': '黑龙江'
            }, {
                '320000': '江苏'
            }, {
                '360000': '江西'
            }, {
                '410000': '河南'
            }, {
                '420000': '湖北'
            }, {
                '430000': '湖南'
            }, {
                '460000': '海南'
            }, {
                '140000': '山西'
            }, {
                '150000': '内蒙古'
            }, {
                '210000': '辽宁'
            }, {
                '310000': '上海'
            }, {
                '370000': '山东'
            }, {
                '510000': '四川'
            }, {
                '610000': '陕西'
            }, {
                '630000': '青海'
            }, {
                '640000': '宁夏'
            }, {
                '120000': '天津'
            }, {
                '330000': '浙江'
            }, {
                '530000': '云南'
            }, {
                '540000': '西藏'
            }, {
                '650000': '新疆'
            }, {
                '810000': '香港'
            }];
            var html1 = ' <div id="dwt"><div class="province_cont"><p class="p_cont"><span class="dwt_address">请选择地址</span><a class="dwt_close" href="javascript:void(0)">X</a></p><div class="province">';
            var html2 = '</div></div><div class="city"></div></div>';
            var html = '';
            for (var i = 0; i < arr.length; i++) {
                for (var k in arr[i]) {
                    html += '<a pid="' + k + '" href="javascript:void(0)">' + arr[i][k] + '</a>';
                }
            }
            var new_html = html1 + html + html2;
            $("body").append(new_html);

            $("#dwt").css({
                'top': ($(window).height() - $("#dwt").height()) / 2 - 50 + $(window).scrollTop()
            });
            $("#dwt").show();
            $(".province a").click(function(e) {
                btn = false;
                e.stopPropagation();
                $(".province a").removeClass('hover');
                $(this).addClass('hover');
                var str = $(this).html();
                that.val(str);
                var pid = $(this).attr('pid'); //根据pid到数据库查询城市
                var url = option.url + "?parentId=" + pid;
                $dwt.parent().find(".province").val(str); //赋值隐藏域省份值
                $dwt.parent().find(".province_id").val(pid); //赋值隐藏域省份id
                $.ajax({
                    url: url,
                    type: 'GET',
                    dataType: 'json',
                    crossDomain: true,
                    success: function(data) {
                        var aTag = '';
                        var regs = data;
                        for (k in regs) {
                            aTag += ' <a href="javascript:void(0)" cityId="' + regs[k].city_id + '">' + regs[k].city + '</a>'
                        }
                        $("#dwt .city").html(aTag);

                        $(".city a").each(function() {
                            $(this).click(function(e) {
                                btn = true;
                                e.stopPropagation();
                                var str2 = $(this).html();
                                $dwt.parent().find(".city").val(str2); //赋值隐藏域城市值
                                $dwt.parent().find(".city_id").val($(this).attr('cityId')); //赋值隐藏域城市id
                                var val = that.val();
                                that.val(val + "-" + str2);
                                $("#dwt").hide();
                                $("#mask").hide();
                            })
                        });

                    }
                });

            });

            $(".dwt_close").click(function() {
                if (btn) {
                    $("#dwt").hide();
                    $("#mask").hide();
                }
            });

        });




    }
})