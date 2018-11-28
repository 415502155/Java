'use strict';
var theTime = new Date();
var timeArr = getFormatDate(theTime.getTime());
var stud_base = null;
var teac_base = null;

//(学生关注率接口,教师关注率,校园统计&作业/博客统计/问卷统计/支付统计)
var require_url = {
    stud_url: domainName + '/shijiwxy/wechat/portal/statistics/studentFollows.json',
    teac_url: domainName + '/shijiwxy/wechat/portal/statistics/teacherFollows.json',
    notice_url: domainName + '/eduWeixin/notice/counter.json',
    blog_url: domainName + '/shijiwxy/wechat/portal/blog/counter.json',
    question_url:domainName + '/shijiwxy/survey/counter.json',
    pay_url: domainName + '/shijiwxy/wechat/portal/charge/charges.json',
    attendance_url: domainName + '/shijiwxy/wechat/portal/statistics/attendance.json',
    attdata_url: domainName + '/shijiwxy/wechat/portal/statistics/getAttendanceByDate.json'
}

//请求数据
var parameter = {
    token: JSON.parse(sessionStorage.baseUser).token,
    udid: JSON.parse(sessionStorage.baseUser).udid,
    version: 3
}

if(new Date().getTime() - (+sessionStorage.teac_statistics_time) > 600000){
	sessionStorage.removeItem('classes');
    sessionStorage.removeItem('teachers');
    //sessionStorage.removeItem('attdata');
}

//获取学生关注率
if (!sessionStorage.classes) {
    requireData(require_url.stud_url, function (res) {
        stud_base = res.data.detail;
        var selector = {
            select_bar: [
                $('#students_bar .attention-percent p'),
                $('#students_bar .stud-percent'),
                $('#stud_bind'),
                $('#stud_unbind')
            ],
            select_num: {
                text1: res.data.rate,
                text2: res.data.bind,
                text3: res.data.unbind
            }
        }
        set_percent(selector);
        sessionStorage.setItem('classes', JSON.stringify(stud_base));
        sessionStorage.setItem('stud_statistics', JSON.stringify(selector.select_num));
    },function(res){$.alert(res.message)})
} else {
    var selector = {
        select_bar: [
            $('#students_bar .attention-percent p'),
            $('#students_bar .stud-percent'),
            $('#stud_bind'),
            $('#stud_unbind')
        ]
    }
    selector.select_num = JSON.parse(sessionStorage.stud_statistics);
    set_percent(selector);
}

//获取教师关注率
if(!sessionStorage.teachers){
    requireData(require_url.teac_url, function (res) {
        teac_base = res.data.detail;
        var selector = {
            select_bar: [
                $('#teachers_bar .attention-percent p'),
                $('#teachers_bar .teac-percent'),
                $('#teac_bind'),
                $('#teac_unbind')
            ],
            select_num: {
                text1: res.data.rate,
                text2: res.data.bind,
                text3: res.data.unbind
            }
        }
        set_percent(selector);
        sessionStorage.setItem('teachers', JSON.stringify(teac_base));
        sessionStorage.setItem('teac_statistics',JSON.stringify(selector.select_num));
        sessionStorage.setItem('teac_statistics_time',new Date().getTime());
    })
}else{
    var selector = {
        select_bar: [
            $('#teachers_bar .attention-percent p'),
            $('#teachers_bar .teac-percent'),
            $('#teac_bind'),
            $('#teac_unbind')
        ]
    }
    selector.select_num = JSON.parse(sessionStorage.teac_statistics);
    set_percent(selector);
}
/**
 * 调整时间主要逻辑
 * 
 * @param {object} 显示时间的button/input 
 * @param {arr} 当前时间  [年,月,日] 
 */
function show_date(date_btn, days) {
    $(date_btn).val(days);
    var orgId = JSON.parse(sessionStorage.baseUser).orguser.org_id;
    //获取通知统计
    requireData(require_url.notice_url,function(res){
        // console.log(res);
        if(!res.data){
            $('#school_notice li h4').text('0');
        }else{
            $('#school_notice li h4').text(res.data + '');
            $('#school_notice').on('click', function () {
                window.location.href = 'notice.html?date=' + timeArr;
            })
        }
    },function (res){},{
        token: JSON.parse(sessionStorage.baseUser).token,
        udid: JSON.parse(sessionStorage.baseUser).udid,
        version: 3,
        type: 1,
        date: days,
        org_id: orgId
    })

    //获取作业统计
    requireData(require_url.notice_url, function (res) {
        // console.log(res);
        if(!res.data){
            $('#homework li h4').text('0');
        }else{
            $('#homework li h4').text(res.data + '');
            $('#homework').on('click', function () {
                window.location.href = 'homework.html?date=' + timeArr;
            })
        }
    }, function (res) {}, {
        token: JSON.parse(sessionStorage.baseUser).token,
        udid: JSON.parse(sessionStorage.baseUser).udid,
        version: 3,
        type: 3,
        date: days,
        org_id: orgId
    })
    //获取博客统计
    requireData(require_url.blog_url, function (res) {
        if(!res.data){
            $('#blog li h4').text('0');
        }else{
            $('#blog li h4').text(res.data + '');
            $('#blog').on('click', function () {
                window.location.href = 'blog.html?date=' + timeArr;
            })
        }
    }, function (res) {
        //alert('亲,您点击的太快了哦');
    }, {
        token: JSON.parse(sessionStorage.baseUser).token,
        udid: JSON.parse(sessionStorage.baseUser).udid,
        version: 3,
        date: days
    })
    //获取问卷统计
    requireData(require_url.question_url, function (res) {
        if(!res.data){
            $('#question li h4').text('0');
        }else{
            $('#question li h4').text(res.data + '');
            $('#question').on('click', function () {
                window.location.href = 'question.html?date=' + timeArr;
            })
        }
    }, function (res) {
        //alert('亲,您点击的太快了哦');
    }, {
        token: JSON.parse(sessionStorage.baseUser).token,
        udid: JSON.parse(sessionStorage.baseUser).udid,
        version: 3,
        date: days
    })
    //获取出勤数据
    // if(!(sessionStorage.thisDate == days)){
        
        //console.log(JSON.parse(sessionStorage.attdata));
        requireData(require_url.attdata_url, function (res) {
            // console.log(res.data);
            if (!res.data) {

            } else {
                $('#attendenceN').text(res.data.normal_num);
                $('#attendenceL').text(res.data.late_num);
                $('#attendenceA').text(res.data.absence_num);
                $('#att_percent').text(res.data.rate);
                $('#attendance_info').on('touchstart',function(){
                    window.location.href = 'attendance.html?date=' + days;
                })
                // sessionStorage.setItem('attdata',JSON.stringify(res.data));
                // sessionStorage.setItem('thisDate',JSON.stringify(days));
            }
        }, function (res) { }, {
                token: JSON.parse(sessionStorage.baseUser).token,
                udid: JSON.parse(sessionStorage.baseUser).udid,
                version: 3,
                date: days,
                org_id: orgId
        })
    // }else{
    //     console.log(sessionStorage.attdata);
    //     console.log(sessionStorage.thisDate);
    //     var data = JSON.parse(sessionStorage.attdata);
    //     $('#attendenceN').text(data.normal_num);
    //     $('#attendenceL').text(data.late_num);
    //     $('#attendenceA').text(data.absence_num);
    //     $('#att_percent').text(data.rate);
    // }

    //获取支付情况
    requireData(require_url.pay_url, function (res) {
        $('#pay_bar').empty();
        $('#pay_info h3').remove();
        if (res.data === null || res.data.length === 0) {
            $('#pay_bar').hide();
            $('#pay_info').append('<h3 style="text-align:center;height:4rem;margin-top:2rem;font-size:1rem;">暂无数据</h3>');
        } else {
            $('#pay_bar').show();
            for (var i = 0; i < res.data.length; i++) {
                var note = '',nostart_class = '',cid = '';
                if(res.data[i].status!=3){
                    note = '(尚未开始)';
                    nostart_class = 'pay-no-start';
                }else{
                    note = res.data[i].note;
                    cid += 'cid=' + res.data[i].cid;
                }
                $('#pay_bar').append(
                    '<div class="attention-info" '+ cid +'>' +
                        '<div class="attention-student clearfix">'+
                            '<h3 class="pay-info-title" pay-info-title="'+ i +'"></h3>'+
                            '<h4>缴费进度:</h4><h4 pay-percent="' + i + '"></h4>'+
                            '<a href="javascript:;" class="statistics-details '+ nostart_class +' fr" >详情</a>' +
                        '</div>'+
                        '<div class="attention-percent"><p class="attention-bar" attention-bar-width="' + i + '" ></p></div>'+
                        '<div class="attention-nums clearfix">'+
                            '<span class="fl">已交:<span payments="' + i + '"></span>人</span>'+
                            '<span class="">已退:<span paybacks="' + i + '"></span>人</span>'+
                            '<span class="fr">未交:<span unpays="' + i + '"></span>人</span>'+
                        '</div>'+
                    '</div>')
                var selector = {
                    select_bar: [
                        $('#pay_bar p[attention-bar-width ="' + i + '"]'), 
                        $('#pay_bar h4[pay-percent="' + i +'"]'), 
                        $('#pay_bar h3[pay-info-title="' + i + '"]'), 
                        $('#pay_bar span[payments="' + i + '"]'), 
                        $('#pay_bar span[paybacks="' + i + '"]'), 
                        $('#pay_bar span[unpays="' + i + '"]')
                    ],
                    select_num: {
                        text1: note,
                        text2: res.data[i].item,
                        text3: res.data[i].ss_num,
                        text4: res.data[i].tf_num_all,
                        text5: res.data[i].nopay_num
                    }
                }
                set_percent(selector);
            }
            $('#pay_bar [cid]').on('click', function () {
                cid = $(this).attr('cid');
                window.location.href = 'class_pay.html?cid=' + cid;
            })
            sessionStorage.setItem('pay_info',JSON.stringify(res.data));
            // console.log(res.data);
        }
    }, function (res) { }, {
            token: JSON.parse(sessionStorage.baseUser).token,
            udid: JSON.parse(sessionStorage.baseUser).udid,
            version: 3,
            date: days
        })
    sessionStorage.setItem('nowDate',timeArr);
}

//绑定调整时间点击事件
$('#yesterday').on('click', function () {
    changeTime.call(this, timeArr);
    sessionStorage.nowDate = timeArr;
})
$('#tomorrow').on('click', function () {
    changeTime.call(this, timeArr);
    sessionStorage.nowDate = timeArr;
})

/**
 * 兼容移动端Date.parse();
 * @param {str} 日期 '年-月-日' 
 * @return {number} 返回时间戳数字
 */
function transformTime(str) {
    str?str = str.replace(/-/g, '/'):void(0);
    var time = (new Date(str)).getTime();
    return time;
}

/**
 * 点击左右调整时间处理
 * @param {str} 日期 '年-月-日' 
 */
function changeTime(times) {
    var now = transformTime(times);
    this.id == 'yesterday' ? now -= 86400000 : now += 86400000;
    if (now > new Date().getTime()) {
        $.alert('只能查看当天及以前的数据');
        return false;
    } else {
        timeArr = getFormatDate(now);
        show_date('#data_date', timeArr);
    }
}

/**
 * ajax请求数据
 * @param {str} 请求地址
 * @param {func}  成功回调
 * @param {func} 错误回调
 * @param {object} 请求参数 
 */
function requireData(url, okcallback, failcallback, param) {
    param = param || {
        token: JSON.parse(sessionStorage.baseUser).token,
        udid: JSON.parse(sessionStorage.baseUser).udid,
        version: 3
    }
    getData(url, param, function (res) {
        if (res.code == 200 && res.success == true) {
            parameter = param;
            okcallback(res);
        } else {
            failcallback(res);
        }
    })
}

/**
 * 包含进度条区域处理
 * @param {object} 选择器(需要替换的内容位置 第一个值为进度条) 
 */
function set_percent(selector) {
    var width = + selector.select_num.text1.slice(0, selector.select_num.text1.length - 1);
    for (var i = 0;i<selector.select_bar.length;i++){
        selector.select_bar[0].css({
            'width': width + '%',
            'transition': 'width 1s',
        })
        var sel = 'selector.select_num.text' + (i+1);
        selector.select_bar[i+1] ? selector.select_bar[i+1].text(eval(sel)) : function(){};
    }
}

/**
 * 将时间戳转换为日期
 * @param {number} 时间戳 
 * @return {str} 返回 '年-月-日'
 */
function getFormatDate(timestamp) {
    timestamp = parseInt(timestamp + '');
    var newDate = new Date(timestamp);
    Date.prototype.format = function (format) {
        var date = {
            'M+': this.getMonth() + 1,
            'd+': this.getDate(),
            'h+': this.getHours(),
            'm+': this.getMinutes(),
            's+': this.getSeconds(),
            'q+': Math.floor((this.getMonth() + 3) / 3),
            'S+': this.getMilliseconds()
        };
        if (/(y+)/i.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
        }
        for (var k in date) {
            if (new RegExp('(' + k + ')').test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1
                    ? date[k] : ('00' + date[k]).substr(('' + date[k]).length));
            }
        }
        return format;
    }
    return newDate.format('yyyy-MM-dd');
}