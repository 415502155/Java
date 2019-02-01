//菜单，name为空表示不生成菜单
var nav={
    "1":{
        "name":"用户中心",

        "isShow":false,
        "icon":"fa-users",
        "describe":"该功能可以查看所有的认证和未认证的学员资料",
        "subShow":false,
        "nextNav":{
            "11":{
                "name":"用户管理",
                "url":"userCenter/index.html",
                "isShow":false,
                "describe":"用户管理列表"
            },
            "12":{
                "name":"",
                "url":"userCenter/detail.html",
                "isShow":false,
                "describe":"用户管理详情"
            }
        }
    },
    "2":{
        "name":"学员通知",

        "isShow":false,
        "icon":"fa-bell-o",
        "describe":"该功能可以给学员发送通知，查看通知记录",
        "subShow":false,

        "nextNav":{
            "21":{
                "name":"发送通知",
                "url":"stuNotice/send.html",
                "isShow":false,
                "describe":"发布通知页面"
            },
            "22":{
                "name":"发送记录",
                "url":"stuNotice/list.html",
                "isShow":false,
                "describe":"所有的通知列表页面"
            },
            "23":{
                "name":"",
                "url":"stuNotice/classes.html",
                "isShow":false,
                "describe":"查看通知的发送的班级"
            }
        }
    },
    "3":null,//教师通知
    "4":{
        "name":"网上招生",

        "isShow":false,
        "icon":"fa-tachometer",
        "describe":"该功能可以创建招生计划，查看招生记录",
        "subShow":false,
        "nextNav":{
            "41":{
                "name":"创建招生",
                "url":"signOnline/add.html",
                "isShow":false,
                "describe":"发布通知页面"
            },
            "42":{
                "name":"招生记录",
                "url":"signOnline/list.html",
                "isShow":false,
                "describe":"所有的通知列表页面"
            }
        }
    },
    "5":{"name":"学员管理",

        "isShow":false,
        "icon":"fa-address-card-o",
        "describe":"该功能可以学员的转班，退费，催缴，删除，打印凭证操作",
        "subShow":false,
        "nextNav":{
            "51":{
                "name":"学员管理",
                "url":"studentMC/index.html",
                "isShow":false,
                "describe":"学员转班，退费，催缴，删除，打印凭证页面"
            }
        }
    },
    "6":{"name":"班级管理",

        "isShow":false,
        "icon":"fa-newspaper-o",
        "describe":"下载开课统计表，缴费明细，下载各班考勤表花名册页面",
        "subShow":false,
        "nextNav":{
            "61":{
                "name":"创建班级",
                "url":"classMC/add.html",
                "isShow":false,
                "describe":"学员转班，退费，催缴，删除，打印凭证页面"
            },
            "62":{
                "name":"导入班级",
                "url":"classMC/loadExcel.html",
                "isShow":false,
                "describe":"通过上传excel导入学生数据页面"
            },
            "63":{
                "name":"管理班级",
                "url":"classMC/index.html",
                "isShow":false,
                "describe":"学员转班，退费，催缴，删除，打印凭证页面"
            },
            "64":{
                "name":"",
                "url":"classMC/student.html",
                "isShow":false,
                "describe":"班级的学员列表"
            }
        }
    },
    "7":{"name":"财务管理",

        "isShow":false,
        "icon":"fa-credit-card",
        "describe":"该功能可以缴费查看、进行退费，财务统计的操作！",
        "subShow":false,
        "nextNav":{
            "71":{
                "name":"退费处理",
                "url":"financeMC/refund.html",
                "isShow":false,
                "describe":"学员退费页面"
            },
            "72":{
                "name":"财务统计",
                "url":"financeMC/statistics.html",
                "isShow":false,
                "describe":"导出财务统计列表"
            }
        }
    },
    "8":{"name":"打印导出",

        "isShow":false,
        "icon":"fa-print",
        "describe":"该功能可以打印票据、开课统计表，缴费明细，下载各班考勤表花名册",
        "subShow":false,
        "nextNav":{
            "81":{
                "name":"筛选导出",
                "url":"print/index.html",
                "isShow":false,
                "describe":"下载开课统计表，缴费明细，下载各班考勤表花名册页面"
            }
        }
    },
    "9":null,//工资发布
    "a":null,//新闻推送
    "b":null,//问卷调查
    "c":null,//校长邮箱
    "d":{
        "name":"教师管理",

        "isShow":false,
        "icon":"fa-user-circle-o",
        "describe":"该功能可以进行教师的添加、编辑、停用、删除操作",
        "subShow":false,
        "nextNav":{
            "d1":{
                "name":"创建教师",
                "url":"teacherMC/detail.html",
                "isShow":false,
                "describe":"添加新教师页面"
            },
            "d2":{
                "name":"教师管理",
                "isShow":false,
                "url":"teacherMC/list.html",
                "describe":"教师查看删除页面"
            }
        }
    },
    "e":{
        "name":"操作日志",

        "isShow":false,
        "icon":"fa-exclamation-circle",
        "describe":"该功能可以查看操作日志",
        "subShow":false,
        "nextNav":{
            "e1":{
                "name":"操作查询",
                "url":"operationLog/index.html",
                "isShow":false,
                "describe":"按操作动作，操作内容，开始结束进行操作插叙操作的页面"
            }
        }
    },
    "f":{
        "name":"基础设置",

        "isShow":false,
        "icon":"fa-cog",
        "describe":"该功能可以进行校区、科目、学期、教室、合作机构、凭证规则添加管理设置",
        "subShow":true,
        "nextNav":{
            "f1":{
                "name":"校区管理",

                "describe":"校区的增加及编辑，删除",
                "isShow":false,
                "icon":"fa-building",
                "nextNav":{
                    "f11":{
                        "name":"校区创建",
                        "url":"baseSet/campus/add.html",
                        "isShow":false,
                        "describe":"添加编辑学期页面！"
                    },
                    "f12":{
                        "name":"校区管理",
                        "url":"baseSet/campus/list.html",
                        "isShow":false,
                        "describe":"校区查看及删除页面！"
                    },
                }
            },
            "f2":{
                "name":"科目管理",

                "describe":"科目及类目的增加，编辑删除页面",
                "isShow":false,
                "icon":"fa-puzzle-piece",
                "nextNav":{
                    "f21":{
                        "name":"创建科目",
                        "url":"baseSet/subject/addSubject.html",
                        "isShow":false,
                        "describe":"科目添加编辑页面！"
                    },
                    "f22":{
                        "name":"创建类目",
                        "url":"baseSet/subject/addCategory.html",
                        "isShow":false,
                        "describe":"类目增加及编辑页面！"
                    },
                    "f23":{
                        "name":"科目管理",
                        "url":"baseSet/subject/list.html",
                        "isShow":false,
                        "describe":"科目管理（列表）查看及删除页面！"
                    }
                }
            },
            "f3":{
                "name":"学期管理",
                "describe":"学期查看，增加及管理页面",
                "isShow":false,
                "icon":"fa-clock-o",
                "nextNav":{
                    "f31":{
                        "name":"学期创建",
                        "url":"baseSet/term/add.html",
                        "isShow":false,
                        "describe":"添加编辑学期页面！"
                    },
                    "f32":{
                        "name":"学期管理",
                        "url":"baseSet/term/list.html",
                        "isShow":false,
                        "describe":"校区查看及删除页面！"
                    },
                }
            }
            ,
            "f4":{
                "name":"教室管理",
                "describe":"教室添加及管理页面",
                "isShow":false,
                "icon":"fa-braille",
                "nextNav":{
                    "f41":{
                        "name":"创建教室",
                        "url":"baseSet/classroom/addRoom.html",
                        "isShow":false,
                        "describe":"添加编辑教室页面！"
                    },
                    "f43":{
                        "name":"教室管理",
                        "url":"baseSet/classroom/list.html",
                        "isShow":false,
                        "describe":"教室管理（列表）页面！"
                    },
                }
            },
            "f5":{
                "name":"合作管理",

                "describe":"合作机构管理页面",
                "isShow":false,
                "icon":"fa-graduation-cap",
                "nextNav":{
                    "f51":{
                        "name":"创建机构",
                        "url":"baseSet/cooperative/add.html",
                        "isShow":false,
                        "describe":"添加编辑机构页面！"
                    },
                    "f52":{
                        "name":"机构管理",
                        "url":"baseSet/cooperative/list.html",
                        "isShow":false,
                        "describe":"合作机构管理（列表）页面！"
                    },
                }
            }
            ,
            "f6":{
                "name":"凭证管理",
                "describe":"打印设置页面",
                "isShow":false,
                "icon":"fa-newspaper-o",
                "nextNav":{
                    "f61":{
                        "name":"模板设计",
                        "url":"baseSet/print/addAddInfo.html",
                        "isShow":false,
                        "describe":"凭证元素显示位置设置！"
                    },
                    "f62":{
                        "name":"内容编辑",
                        "url":"baseSet/print/addStr.html",
                        "isShow":false,
                        "describe":"自定义显示项！"
                    },
                }
            }
            ,
            "f7":{
                "name":"规则设置",
                "describe":"缴费规则和认证设置页面",
                "isShow":false,
                "icon":"fa-sliders",
                "nextNav":{
                    "f71":{
                        "name":"缴费设置",
                        "url":"baseSet/rule/rule.html",
                        "isShow":false,
                        "describe":"缴费规则设置页面！"
                    },
                    "f72":{
                        "name":"认证设置",
                        "url":"baseSet/rule/authentication.html",
                        "isShow":false,
                        "describe":"学生身份认证提示信息设置页面！"
                    },
                }
            }
        }
    },
    "g":{
        "name":"权限管理",
        "isShow":false,
        "icon":"fa-id-card",
        "describe":"该功能可以查看操作日志",
        "subShow":true,
        "nextNav":{
            "g1":{
                "name":"管理权限",
                "describe":"管理角色创建，角色管理功能",
                "isShow":false,
                "icon":"fa-id-badge",
                "nextNav":{
                    "g11":{
                        "name":"创建管理员",
                        "url":"role/administrator/add.html",
                        "isShow":false,
                        "describe":"缴费规则设置页面！"
                    },
                    "g12":{
                        "name":"管理员列表",
                        "url":"role/administrator/list.html",
                        "isShow":false,
                        "describe":"学生身份认证提示信息设置页面！"
                    },
                }
            },
            "g2":{
                "name":"角色设置",
                "describe":"角色创建及管理页面",
                "isShow":false,
                "icon":"fa-user-secret",
                "nextNav":{
                    "g21":{
                        "name":"角色设置",
                        "url":"role/role/add.html",
                        "isShow":false,
                        "describe":"角色创建页面！"
                    },
                    "g22":{
                        "name":"角色管理",
                        "url":"role/role/list.html",
                        "isShow":false,
                        "describe":"角色管理页面！"
                    },
                }
            }
        }
    }

};

var navHtml="";
var router={"404":"../common/404.html"};

//按权限设置菜单显示
changeNavShow(JSON.parse(sessionStorage.tech_roles));

//生成路由
for (var i in nav){
    var obj=nav[i];
    if(obj==null){
        continue
    }
   /* if(obj.isShow==true){
        router[i]="html/"+obj.url;
    }*/
    for(var m in obj.nextNav){

     var subobj=obj.nextNav[m];

       if(subobj.isShow==true && subobj.url!==undefined){
            router[m]="html/"+subobj.url;
        }
        if(subobj.nextNav!==undefined){
            for(var n in subobj.nextNav){
                var third=subobj.nextNav[n];
                if(third.isShow==true){
                    router[n]="html/"+third.url;
                }
            }
        }

    }
}

//生成菜单
for (var i in nav){
    var obj=nav[i];
    if(obj !== null){
        if(obj.isShow==true){
            //一级菜单
            navHtml += "<li title='"+obj.describe+"' data-id='"+i+"'>" +
                "<i class='fa "+obj.icon+" appIcon'></i>" +
                "<span class='appName-text'>"+obj.name+"</span>" +
                "</li>";
            //如果需要显示二级菜单则显示二级菜单
            if(obj.subShow==true){
                navHtml+= "<ul class='sub_nav'>";
                for(var m in obj.nextNav){

                    var subobj=obj.nextNav[m];
                    if(subobj.isShow==true){

                        navHtml += "<li title='"+subobj.describe+"' data-id='"+m+"'>" +
                            "<i class='fa "+subobj.icon+" appIcon'></i>" +
                            "<span class='appName-text'>"+subobj.name+"</span>" +
                            "</li>";
                    }
                }
                navHtml+= "</ul>";
            }
        }
    }

}

//生成面包屑及三级导航
function creatThirdMenu(id) {

    if(window.location.hash==""){
        //生成面包屑
        var defaultNav=nav[getFirstAttr(router)];
    }else{
        if(id==undefined){

        }else{
            //生成面包屑
            var defaultNav=nav[id.split("?")[0]];
        }

    }

    if(defaultNav!==undefined){
        $("#firstlMenu").text(defaultNav.name).attr("href","#"+id);
    }else{
        defaultNav=nav[id.split("?")[0].substr(0,1)];
        $("#firstlMenu").text(defaultNav.name).attr("href","#"+id.split("?")[0].substr(0,1));;
    }

   //三级菜单
    if(defaultNav.subShow==true){

        var  cHtml="";

       $("#secondlMenu").show().text(defaultNav.nextNav[getRouterId(id).substr(0,2)].name);

        if(id.length==1) {
            var now = getObjFirst(defaultNav.nextNav)

            //for(var i in defaultNav.nextNav ) {


            for (var x in  defaultNav.nextNav[now].nextNav) {
                var thirdNav = defaultNav.nextNav[now].nextNav[x];
                if (thirdNav.isShow == true) {

                    if (x == getRouterId(id)) {
                        cHtml += '<li class="nLeftTitle"><a class="cateParentALL" href="#' + x + '" >' + thirdNav.name + '</a></li> ';
                    } else {
                        cHtml += '<li class="nLeftTitle"><a class="cateParentA" href="#' + x + '" >' + thirdNav.name + '</a></li> ';
                    }
                }

            }
        }else {

            for(var i in defaultNav.nextNav[id.substr(0,2)].nextNav) {
                // n+=1;
                var menuObj = defaultNav.nextNav[id.substr(0,2)].nextNav[i];
                //判断三级菜单是否显示
                if(menuObj.isShow==true){
                    if(id.length==2){
                        if (n == 1) {
                            cHtml += '<li class="nLeftTitle"><a class="cateParentALL" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                        } else {

                            cHtml += '<li class="nLeftTitle"><a class="cateParentA" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                        }

                    }else {
                        if (i == id) {
                            cHtml += '<li class="nLeftTitle"><a class="cateParentALL" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                        } else {

                            cHtml += '<li class="nLeftTitle"><a class="cateParentA" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                        }
                    }
                }
            }


        }



       //}

               /*if(id.length==1){*/

                  /* $("#secondlMenu").show().text(defaultNav.nextNav[id+"1"].name);
                   var n=0;
                   for(var i in defaultNav.nextNav[id+"1"].nextNav) {
                       n+=1;
                       var menuObj = defaultNav.nextNav[id+"1"].nextNav[i];

                       if (n == 1) {
                           cHtml += '<li class="nLeftTitle"><a class="cateParentALL" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                       } else {

                           cHtml += '<li class="nLeftTitle"><a class="cateParentA" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                       }
                   }*/

        /*       }else{
                   $("#secondlMenu").show().text(defaultNav.nextNav[id.substr(0,2)].name);

                   //var n=0;
                   cHtml += '<li class="nLeftTitle"><a class="cateParentALL" href="#' + id + '" >' + defaultNav.nextNav[id.substr(0,2)].name+ '</a></li> ';

                  for(var i in defaultNav.nextNav[id.substr(0,2)].nextNav) {
                      // n+=1;
                       var menuObj = defaultNav.nextNav[id.substr(0,2)].nextNav[i];
                       //判断三级菜单是否显示
                       if(menuObj.isShow==true){
                           if(id.length==2){
                               if (n == 1) {
                                   cHtml += '<li class="nLeftTitle"><a class="cateParentALL" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                               } else {

                                   cHtml += '<li class="nLeftTitle"><a class="cateParentA" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                               }

                           }else {
                               if (i == id) {
                                   cHtml += '<li class="nLeftTitle"><a class="cateParentALL" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                               } else {

                                   cHtml += '<li class="nLeftTitle"><a class="cateParentA" href="#' + i + '" >' + menuObj.name + '</a></li> ';
                               }
                           }
                       }
                   }
               }*/

        $("#topNav li").remove();

        $("#topNav").append(cHtml);

    }else{

        $("#secondlMenu").hide().text("");

        var m=0,cHtml="";

        for(var i in defaultNav.nextNav){
            m+=1;
            var menuObj=defaultNav.nextNav[i];
            //判断三级是否显示
            if(menuObj.isShow==true){


                if(menuObj.name!==""){
                    if(id.length==1){
                        if(m==1){
                            cHtml+='<li class="nLeftTitle"><a class="cateParentALL" href="#'+i+'" >'+menuObj.name+'</a></li> ';
                        }else {

                            cHtml+='<li class="nLeftTitle"><a class="cateParentA" href="#'+i+'" >'+menuObj.name+'</a></li> ';
                        }
                    }else{
                        if(i==id){
                            cHtml+='<li class="nLeftTitle"><a class="cateParentALL" href="#'+i+'" >'+menuObj.name+'</a></li> ';
                        }else {
                            cHtml+='<li class="nLeftTitle"><a class="cateParentA" href="#'+i+'" >'+menuObj.name+'</a></li> ';
                        }
                    }
                }else{

                    //不显示在菜单的路由处理
                    if(id==23){
                        setTimeout(function () {
                            $("a[href='#22']").addClass('cateParentALL').removeClass('cateParentA');
                        },100)

                    }
                    if(id==12){
                        setTimeout(function () {
                            $("a[href='#11']").addClass('cateParentALL').removeClass('cateParentA');
                        },100)
                    }
                    if(id==64){
                        setTimeout(function () {
                            $("a[href='#63']").addClass('cateParentALL').removeClass('cateParentA');
                        },100)
                    }
                }
            }


        }
        $("#topNav li").remove();
        $("#topNav").append(cHtml);

    }
}



//渲染菜单
$("#leftNav ul").html(navHtml);

/**
 * 获取JSON对象的第一个元素
 * 描述： 封装一个方法，对json进行循环，实际上执行一次就return了，返回第一个属性
 * param{object}:[obj] JSON对象
 */
function getFirstAttr(obj) {
    for (var k in obj) return k;
}

/**
 * 描述：跳转到指定页面，点击触发
 * param{number or string}: window.location.hash
*/
function toPage(id) {
    var index = id ? id : 1;
    window.location.hash = "#" + index;
    //默认的显示样式
};

//加载不同应用页面
function getHTML(url, okCallback, data, ajaxmethod) {
    var tempbg = $('.loading-bg').attr('loading-id');
    var tempimg = $('.loadingimg').attr('loading-id');
    $.ajax({
        url: url,
        data: data,
        async: true,
        type: ajaxmethod,
        /*  dataType:'json',*/
        beforeSend: function () {
            //$.showPreloader(tipText);
            loadbgOpen()
        },
        success: function (result, textStatus, jqXHR) {
            //console.log(result)
            //console.log(textStatus)
            //console.log(jqXHR)
            if (jqXHR.status == 200) {
                //回调
                okCallback(result);
            }
            ;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if(XMLHttpRequest.status=="404"){
                var url = router[404];
                getHTML(url, function (r) {
                    $("#loadbox").html(r)
                },{});
            }
        },
        complete: function () {
            loadbgOver(tempbg, tempimg);

        },
    });
};


/**
 * 描述：通过HASH值加载对应页面，并更新菜单渲染（creatThirdMenu(id);）
 *
*/
function changeHash(hashId) {
    var tid=hashId.split("?")[0]==""?getObjFirst(router):hashId.split("?")[0];
    if(tid=="64" || tid=="63"){
    }else{
        if(sessionStorage.classMC!==undefined){
            sessionStorage.removeItem("classMC")
        }
    }
    var id=getRouterId(tid);
    //sessionStorage.classMC
    creatThirdMenu(id);
    if(router[id] !== undefined){
        //默认的显示样式
        $("#leftNav li.active").removeClass("active");

        //如果包含子菜单则子菜单。
        if($("#leftNav li[data-id="+id.substr(0,1)+"]").next().is("ul.sub_nav")){

            var idstr=id.substr(0,1);
            $("#leftNav li[data-id="+idstr+"]").addClass("active");
            $("#leftNav li[data-id="+idstr+"]").next().slideDown();
            if(id.length==1){
                $("#leftNav li[data-id="+id+"1]").addClass("active");
            }else{
                $("#leftNav li[data-id="+id.substr(0,2)+"]").addClass("active");
            }

        }else{
            var idstr=id.substr(0,1);
            $("#leftNav li[data-id="+id.substr(0,1)+"]").addClass("active");
        }

        var url = router[id];
    }else{
        //路由未注册，404
        var url = router[404];
    }

    getHTML(url, function (r) {
        $("#loadbox").html(r)
    },{});

    $(".nLeftTitle").click(function () {

        if($(this).find("a").hasClass("cateParentALL")){
            return false
        }
        $(".nLeftTitle a.cateParentALL").removeClass("cateParentALL").addClass("cateParentA");

        $(this).find("a").addClass("cateParentALL").removeClass("cateParentA");
    })

};




/**
* 函数：统计数字动画
* 参数说明：
* param{number}:[id] domd对象
* param{number}:[countSum] 数字使用方法：ajax回调
*/
function countup(id,countSum,start){
    var options = {
        useEasing: true,
        useGrouping: true,
        separator: ',',
        decimal: '.',
    };
    var flash = new CountUp(id, start==undefined?0:Number(start), countSum, 0, 1.8, options);
    flash.start();
}


/**
 * 名称:开关
 * 调用方式：honeySwitch.init();
 * html示例：
 * */
var honeySwitch = {};
honeySwitch.themeColor = "rgb(100, 189, 99)";
honeySwitch.init = function() {
    var s = "<span class='slider'></span>";
    $("[class^=switch]").append(s);
    $("[class^=switch]").click(function() {
        if ($(this).hasClass("switch-disabled")) {
            return;
        }
        if ($(this).hasClass("switch-on")) {
            $(this).removeClass("switch-on").addClass("switch-off");
            $(".switch-off").css({
                'border-color' : '#dfdfdf',
                'box-shadow' : 'rgb(223, 223, 223) 0px 0px 0px 0px inset',
                'background-color' : 'rgb(255, 255, 255)'
            });
        } else {
            $(this).removeClass("switch-off").addClass("switch-on");
            if (honeySwitch.themeColor) {
                var c = honeySwitch.themeColor;
                $(this).css({
                    'border-color' : c,
                    'box-shadow' : c + ' 0px 0px 0px 16px inset',
                    'background-color' : c
                });
            }
            if ($(this).attr('themeColor')) {
                var c2 = $(this).attr('themeColor');
                $(this).css({
                    'border-color' : c2,
                    'box-shadow' : c2 + ' 0px 0px 0px 16px inset',
                    'background-color' : c2
                });
            }
        }
    });
    window.switchEvent = function(ele, on, off) {
        $(ele).click(function() {
            if ($(this).hasClass("switch-disabled")) {
                return;
            }
            if ($(this).hasClass('switch-on')) {
                if ( typeof on == 'function') {
                    on();
                }
            } else {
                if ( typeof off == 'function') {
                    off();
                }
            }
        });
    }
    if (this.themeColor) {
        var c = this.themeColor;
        $(".switch-on").css({
            'border-color' : c,
            'box-shadow' : c + ' 0px 0px 0px 16px inset',
            'background-color' : c
        });
        $(".switch-off").css({
            'border-color' : '#dfdfdf',
            'box-shadow' : 'rgb(223, 223, 223) 0px 0px 0px 0px inset',
            'background-color' : 'rgb(255, 255, 255)'
        });
    }
    if ($('[themeColor]').length > 0) {
        $('[themeColor]').each(function() {
            var c = $(this).attr('themeColor') || honeySwitch.themeColor;
            if ($(this).hasClass("switch-on")) {
                $(this).css({
                    'border-color' : c,
                    'box-shadow' : c + ' 0px 0px 0px 16px inset',
                    'background-color' : c
                });
            } else {
                $(".switch-off").css({
                    'border-color' : '#dfdfdf',
                    'box-shadow' : 'rgb(223, 223, 223) 0px 0px 0px 0px inset',
                    'background-color' : 'rgb(255, 255, 255)'
                });
            }
        });
    }
};
honeySwitch.showOn = function(ele) {
    $(ele).removeClass("switch-off").addClass("switch-on");
    if(honeySwitch.themeColor){
        var c = honeySwitch.themeColor;
        $(ele).css({
            'border-color' : c,
            'box-shadow' : c + ' 0px 0px 0px 16px inset',
            'background-color' : c
        });
    }
    if ($(ele).attr('themeColor')) {
        var c2 = $(ele).attr('themeColor');
        $(ele).css({
            'border-color' : c2,
            'box-shadow' : c2 + ' 0px 0px 0px 16px inset',
            'background-color' : c2
        });
    }
}
honeySwitch.showOff = function(ele) {
    $(ele).removeClass("switch-on").addClass("switch-off");
    $(".switch-off").css({
        'border-color' : '#dfdfdf',
        'box-shadow' : 'rgb(223, 223, 223) 0px 0px 0px 0px inset',
        'background-color' : 'rgb(255, 255, 255)'
    });
}
$(function() {
    honeySwitch.init();
});

/**
 * 分页插件
 * 使用方法：
*/
(function($) {
        var ms = {
            init: function(obj, args) {
                return (function() {
                        ms.fillHtml(obj, args);
                        ms.bindEvent(obj, args);
                    }
                )();
            },
            fillHtml: function(obj, args) {
                return (function() {
                        obj.empty();

                        //将来可能用···首页和尾页
                       /*  if (args.current > 1) {
                            obj.append('<a href="javascript:;" style="font-size: 12px"  class="prevPage0">首页</a>');
                        } else {
                            obj.append('<a href="javascript:;" style="font-size: 12px;display: none" class="prevPage0">首页</a>');
                        }*/
                        /*      if (args.current < args.pageCount) {
                             obj.append('<a href="javascript:;" style="font-size: 12px"  class="prevPage1">尾页</a>');
                         } else {
                             obj.append('<a href="javascript:;" style="font-size: 12px;display: none" class="prevPage1">尾页</a>');
                         }*/

                        if (args.current > 1) {
                            obj.append('<li title="上一页" class="gui-page-prev"><a><i class="fa fa-angle-left"></i></a></li>');
                        } else {
                            obj.remove('.prevPage');
                            obj.append('<li title="已经是第一页" class="gui-page-prev gui-page-disabled"><a><i class="fa fa-angle-left"></i></a></li>');
                        }


                        if (args.current != 1 && args.current >= 4 && args.pageCount != 4) {
                            //obj.append('<a href="javascript:;" class="tcdNumber">' + 1 + '</a>');
                            obj.append('<li title="1" class="gui-page-item "><a>1</a></li>');

                        }
                        if (args.current - 2 > 2 && args.current <= args.pageCount && args.pageCount > 5) {
                            obj.append('<li class="gui-page-item-jump-next"><a><i class="fa fa-ellipsis-h"></i></a></li>');
                        }

                        var start = args.current - 2
                            , end = args.current + 2;
                        if ((start > 1 && args.current < 4) || args.current == 1) {
                            end++;
                        }
                        if (args.current > args.pageCount - 4 && args.current >= args.pageCount) {
                            start--;
                        }

                        for (; start <= end; start++) {
                            if (start <= args.pageCount && start >= 1) {
                                if (start != args.current) {
                                    //obj.append('<a href="javascript:;" class="tcdNumber">' + start + '</a>');
                                    obj.append('<li title="' + start + '" class="gui-page-item"><a>' + start + '</a></li>');
                                } else {
                                    //obj.append('<span class="current">' + start + '</span>');
                                    obj.append('<li title="' + start + '" class="gui-page-item gui-page-item-active"><a>' + start + '</a></li>');;
                                }
                            }
                        }

                        if (args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5) {
                            obj.append('<li title="向后 5 页" class="gui-page-item-jump-next"><a><i class="fa fa-ellipsis-h"></i></a></li>');
                        }
                        if (args.current != args.pageCount && args.current < args.pageCount - 2 && args.pageCount != 4) {
                            obj.append('<li title="' + args.pageCount + '" class="gui-page-item"><a>' + args.pageCount + '</a>');
                        }

                        if (args.current < args.pageCount) {
                            obj.append('<li title="下一页" class="gui-page-next"><a><i class="fa fa-angle-right"></i></a></li>');
                        } else {
                            obj.remove('.nextPage');
                            obj.append('<li title="已经是最后一页" class="gui-page-next gui-page-disabled"><a><i class="fa fa-angle-right"></i></a></li>');
                        }

                    }
                )();
            },
            bindEvent: function(obj, args) {
                obj.off("click", ".gui-page-item");
                obj.off("click", "li.gui-page-prev");
                obj.off("click", "li.gui-page-next");
                return (function() {
                       //点击页码
                        obj.on("click", ".gui-page-item", function() {

                            var current = parseInt($(this).text());
                            $('#dang').text(current);
                            $('#page').val(current)
                            ms.fillHtml(obj, {
                                "current": current,
                                "pageCount": args.pageCount
                            });
                            if (typeof (args.backFn) == "function") {
                                args.backFn(current);
                            }
                        });

                        //上一页
                        obj.on("click", "li.gui-page-prev", function() {

                            if($(this).hasClass("gui-page-disabled")){
                                return false
                            }
                            var current = parseInt(obj.children(".gui-page-item-active").text());
                            //aa = current - 1;
                            $('#dang').text(current - 1);
                            $('#page').val(current - 1)

                            ms.fillHtml(obj, {
                                "current": current - 1,
                                "pageCount": args.pageCount
                            });
                            if (typeof (args.backFn) == "function") {
                                args.backFn(current - 1);
                            }
                        });

                        //下一页
                        obj.on("click", "li.gui-page-next", function() {

                            if($(this).hasClass("gui-page-disabled")){
                                return false
                            }

                            var current = parseInt(obj.children(".gui-page-item-active").text());
                            //aa = current + 1;
                            $('#dang').text(current + 1);
                            $('#page').val(current + 1)

                            ms.fillHtml(obj, {
                                "current": current + 1,
                                "pageCount": args.pageCount
                            });
                            if (typeof (args.backFn) == "function") {
                                args.backFn(current + 1);
                            }
                        });

                        //到尾页
                        /*
                            obj.on("click", "a.prevPage1", function() {
                                var current = ye;

                                $('#dang').text(current);
                                $('#page').val(current)
                                ms.fillHtml(obj, {
                                    "current": current,
                                    "pageCount": args.pageCount
                                });
                                if (typeof (args.backFn) == "function") {
                                    args.backFn(current);
                                }
                            });
                            //到首页
                            obj.on("click", "a.prevPage0", function() {
                                var current = 1;

                                $('#dang').text(current);
                                $('#page').val(current)
                                ms.fillHtml(obj, {
                                    "current": current,
                                    "pageCount": args.pageCount
                                });
                                if (typeof (args.backFn) == "function") {
                                    args.backFn(current);
                                }
                            });*/

                    //obj.on("click", "li.gui-page-next", function() {
                        $("#page").keyup(function(e) {
                            //如果按回车执行提交操作
                            if (e.keyCode == 13) {
                                var now=parseInt($("#page").val()),total=parseInt($('#allPage').text());
                               if (now > total) {
                                    now = total;
                                    $('#page').val(total);
                                } else if (now < 1) {
                                    now = 1;
                                    $('#page').val("1");
                                }

                                ms.fillHtml(obj, {
                                    "current": now,
                                    "pageCount": args.pageCount
                                });
                                if (typeof (args.backFn) == "function") {
                                    args.backFn(now);
                                }
                            }

                        });

                        $("#limit").change(function() {
                            $("#page").val("");
                            ms.fillHtml(obj, {
                                "current": 1,
                                "pageCount": args.pageCount
                            });
                            if (typeof (args.backFn) == "function") {
                                args.backFn(1);
                            }
                        });

                       //指定页跳转
                /*        $("#zhuan").click(function() {

                            var tiao = "";
                            tiao = $('#page').val();

                            if ($('#page').val() > ye) {
                                tiao = ye;
                                $('#page').val(ye);
                            } else if ($('#page').val() < 1) {
                                tiao = 1;
                                $('#page').val("1");
                            } else {
                                tiao = tiao;
                            }

                            $(".current").remove('.current');

                            obj.append('<span style="display:none" href="javascript:;" class="current">' + tiao + '</span>');
                            var current = parseInt(obj.children("span.current").text());
                            //aa = current;
                            $('#dang').text(current);
                            $('#page').val(current)
                            ms.fillHtml(obj, {
                                "current": current,
                                "pageCount": args.pageCount
                            });
                            if (typeof (args.backFn) == "function") {
                                args.backFn(current);
                            }
                        });*/


                    }
                )();
            }
        }
        $.fn.createPage = function(options) {
            var args = $.extend({
                                    pageCount: 10,
                                    current: 1,
                                    backFn: function() {}
                                }, options);
            ms.init(this, args);
        }
    })(jQuery);
