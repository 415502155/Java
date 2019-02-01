/**
 * 静态下拉配置及生成
 */

var domain ='https://t.shijiwxy.5tree.cn/newsng'; //'http://localhost:8080/sng';//http://192.168.0.132:8080/newsng
var loginMain = 'https://t.shijiwxy.5tree.cn/';//http://127.0.0.1:8080/登陆获取教师用

var cookieData;
if(!sessionStorage.loginUser && document.cookie !== ''){
    var thisPort = domain + '/portal/login/managerLogin.htm';
	function getCookie (){
		var cookie = document.cookie.split(';');
		var data = {};
		var checks = new RegExp("=");

		for(var i =0;i<cookie.length;i++){
			var index = cookie[i].indexOf(checks.exec(cookie[i]));
			var key = cookie[i].substring(0,index).trim();
			var value = cookie[i].substring(index+1,cookie[i].length);
			if(key == "sng_t"){
				data["token"] = value;
			}else if(key == "sng_u"){
				data["udid"] = value;
			}else{
				data[key] = value;
			}
		}
		try {
			return data;
		} catch (error) {

		}
	}
	var param1 = getCookie();
	//从后台进入,通过cookie获取身份信息调该机构接口获取数据
	$.ajax({
		url: thisPort,
		data: param1,
		type : "GET",
		async : false,
		dataType : "json",
		success : function (res) {
			if (res.code != 200 && res.success != true) {
				if (!window.location.href.match(/login.html/)) {
					myAlert("未获取到您的登录信息,请重新登录", 2, function () { window.location.href = "login.html" })
				}
			} else {
				cookieData = JSON.parse(res.data).data;
                sessionStorage.token = cookieData.token;
                sessionStorage.udid = cookieData.udid;
                var techObj = cookieData.orgusers;
                var logininfo = {
                    "tech_id": techObj[0].teacher.tech_id,
                    "user_id": techObj[0].user_id,
                    "useName": techObj[0].teacher.tech_name,
                    "orgName": techObj[0].organization.org_name_cn,
                    "org_id": techObj[0].org_id,
                    "rlids": techObj[0].rlids,
                    "Phone": techObj[0].user_mobile,
                    "roleCam": "all"
                }
                sessionStorage.loginUser = JSON.stringify(logininfo);
                sessionStorage.roleCam = "all";
                sessionStorage.org_id = logininfo.org_id;
                sessionStorage.tech_roles = techObj[0].roles[0].authorities; //获取教师的角色信息
			}
		},
		error : function (res) {
			if (!window.location.href.match(/login.html/)) {
				myAlert("未获取到您的登录信息,请重新登录", 2,null,function () { window.location.href = "login.html" })
				throw new Error("请先登录后再操作");
			}
		}
	})
	// getData(thisPort, param1, function (res) {
	// 	if(res.code != 200){
	// 		if(!window.location.href.match(/login.html/)){
	// 			myAlert("请先登录后再操作", 2, function () { window.location.href = "login.html" })
	// 		}
	// 	}else{
	// 		cookieData = JSON.parse(res.data).data;
	// 	}
	// }, "GET", false)
}

var option={
	 allRelation : ['父亲', '母亲', '爷爷', '奶奶', '外公', '外婆', '其他'],
	 signStatus: {'1':'老生待续费','2':'名额保留中','3':'报名待缴费','4':'报名已作废','5':'缴费已完成','6':'退费待审核','7':'退费已审核','8':'退费已完成'},
	 //payType: {'1':'现金','2':'银行卡','3':'手机'},//0尚未缴费1微信支付2现金缴费3刷卡缴费4手机银联
     payType: {'1':'微信支付','2':'现金缴费','3':'刷卡缴费','4':"手机银联",'0':"尚未缴费"},//0尚未缴费1微信支付2现金缴费3刷卡缴费4手机银联
	 printType: {'1':'已打印','0':'未打印'},
	 allTime:{ "1": "周一", "2": "周二", "3": "周三", "4": "周四", "5": "周五", "6": "周六", "7": "周日", "8": "其他" },
	 certificationType: { "1": "已认证", "0": "未认证" },
	 idType: { "1": "身份证", "2": "台胞证", "3": "护照" },
	 termType: {'1':'学期','2':'假期'},
     tradingType: {'1':'缴费','2':'退费'},
	 classType: {'1':'老生班','2':'新生班'},
     teacherType: {'1':'专职','2':'外聘','3':'联合'},
     allAction:{1:"创建班级",2:"编辑班级",3:"删除班级",4:"导出班级",5:"导入班级",6:"名额保留",7:"学员缴费",8:"学员转班",9:"批量转班", 10:"删除学员", 11:"批量删除学员", 12:"后台退费", 13:"退费审核", 14:"批量退费审核", 15:"学员撤销", 16:"执行退费", 17:"学员插班", 18:"导入学员",19:"缴费成功"},

     allTerm:null,
     allYear:null,
     allCampus:null,
     allCategory:null,
     allCooperatives:null,
     allClassroom:null,
     allTeachers:null,
     org_id:sessionStorage.org_id,
     udid:sessionStorage.udid,
     token:sessionStorage.token,
     roleCam:sessionStorage.roleCam,


     switchShow:function (obj,number) {
	     //开关赋值
        if(number =="0"){
            $("#"+obj).addClass("switch-off").removeClass("switch-on").attr("style","border-color: rgb(223, 223, 223); box-shadow: rgb(223, 223, 223) 0px 0px 0px 0px inset; background-color: rgb(255, 255, 255);");
        }else{
            $("#"+obj).addClass("switch-on").removeClass("switch-off").attr("style","border-color: #86b7ea; box-shadow:#86b7ea 0px 0px 0px 16px inset; background-color: rgb(0, 188, 211);");
        }
     },
     downMenu:function () {
     },
     getUrlParam:function(paraName) {
        var url = document.location.toString();
        var arrObj = url.split("?");
        if (arrObj.length > 1) {
            var arrPara = arrObj[1].split("&");
            var arr;
            for (var i = 0; i < arrPara.length; i++) {
                arr = arrPara[i].split("=");
                if (arr != null && arr[0] == paraName) {
                    return arr[1];
                }
            }
            return "";
        }
        else {
            return "";
        }
    },
    goBack:function() {
        window.history.back();
    },
    quit:function() {

        myConfirm("你确定要退出登录吗？", function () {
            window.location.href = 'login.html';
            //清空本地缓存
            sessionStorage.clear();
            //清空服务器缓存

        }, null, null,0);


    },
}



/**所有下拉菜单的生成
* 0.所有年份 （ajax）（班级学期查询）
* 1.学期（ajax）
* 2.类目科目联动（ajax）
* 3.校区（根据角色会不同）（ajax）
* 3a.合作机构 （ajax）（添加教师模块）
* 3b.操作动作 （ajax）（日志模块）
*
* 4.班级类型  {'1':'老生班','2':'新生班'}
* 5.学员报名状态    {'1':'老生待续费','2':'名额保留中','3':'报名待缴费','4':'报名已作废','5':'缴费已完成','6':'退费待审核','7':'退费已审核','8':'退费已完成'}
* 6.缴费/支付方式   {'1':'微信','2':'现金','3':'刷卡'}
* 7.凭证状态    {'1':'已打印','2':'未打印'}
* 8.上课时间    { "1": "周一", "2": "周二", "3": "周三", "4": "周四", "5": "周五", "6": "周六", "7": "周日", "8": "其他" }
* 9.认证状态    { "1": "已认证", "0": "未认证" }
* 10.身份证类型 { "1": "身份证", "2": "台胞证", "3": "护照" }
* 11.学期类型   {'1':'学期','2':'假期'}
* 12 缴费操作   {'1':'名额保留','2':'现金缴费','3':'刷卡缴费'}
* 13 教师类型   {'1':'专职','2':'外聘','3':'联合'}
* */
function allSelect() {

    //获取所有学期的年份
    if($("#allYear").length==1){

        var get={
            "parameter":{
                "org_id":option.org_id,
                "token":option.token
            },
            "url":domain+"/plan/yearList.json"
        };

        getData(get.url,get.parameter,function (data) {

            if(data.code==200 && data.success==true){
            	var yearLength=data.data.length;
            	var yearHtml="";
            	if(yearLength>0){
            		for(var i=0; i<yearLength;i++){
            		    var yearObj=data.data[i]
                        yearHtml+="<option value='"+yearObj+"'>"+yearObj+"年</option>";
					}
                    $("#allYear").html(yearHtml);
				}
            }else {
                myAlert(data.message,2,data.code,null);
            }
        },"POST",false);

    }

	//获取学期
    if($("#allTerm").length==1){

       /* if(sessionStorage.allTerm==undefined){*/
            var get={
                "parameter":{
                    "org_id":option.org_id,
                    "needCount":false,
                    "cur_year":""
                },
                "url":domain+"/baseSet/termManage/queryTermListInfo.json"
            };

            getData(get.url,get.parameter,function (res) {
                if(res.code==200 && res.success==true){
                    var obj=res.data.data,html="";
                    //更新本地数据
                    if(obj.length==0){
                      myAlert("暂无学期！",2,res.code,null);
                      return false
                    }
                    sessionStorage.allTerm=JSON.stringify(obj);
                    handleTerm(obj)

                }else {
                    myAlert(res.msg,2,res.code,null);
                }
            },"POST",false)
      /*  }else{

            var obj=JSON.parse(sessionStorage.allTerm);
            handleTerm(obj)
        }*/

        function handleTerm(obj) {
             var html="";
            if(obj.length>0){
                for(var i=0,m=obj.length; i<m;i++){
                    if(obj[i].falg=="true"){
                        var select="selected=selected";
                    }else{
                        var select="";
                    }
                    html+="<option value='"+obj[i].term_id+"' "+select+">"+obj[i].term_name+"</option>";
                }
                $("#allTerm").html(html);
            }
        }
    }

    //获取所有类目科目
    if($("#allCategory").length==1){
       /* if(sessionStorage.allCategory==undefined){*/
            var cam_list={
                "parameter":{
                    "org_id":option.org_id,
                    "isQueryConditions":true
                },
                "url":domain+"/baseSet/categoryManage/querySubjectListInfo.json"
            };

            getData(cam_list.url,cam_list.parameter,function (res) {

                if(res.code==200 && res.success==true){
                    var obj=res.data;

                    if(obj==null){
                        myAlert("暂无类目",2,null,null);
                        return false
                    }

                    if(obj.length>0){
                        var allCategoryJson={};
                        for(var i=0,m=obj.length; i<m;i++){

                            allCategoryJson[obj[i].category_id]={};
                            allCategoryJson[obj[i].category_id]["list"]={};
                            allCategoryJson[obj[i].category_id]["name"]=obj[i].category_name;

                            if(obj[i].list.length>0){
                                for(var x=0,y=obj[i].list.length;x<y;x++){
                                    allCategoryJson[obj[i].category_id]["list"][obj[i].list[x].subject_id]=obj[i].list[x].subject_name;
                                }
                            }
                        }

                        sessionStorage.allCategory=JSON.stringify(allCategoryJson);
                        handleCategory(allCategoryJson);
                    }

                }else {
                    myAlert(res.msg,2,res.code,null);
                }
            },"POST",false);
     /*   }else{
            var obj=JSON.parse(sessionStorage.allCategory);
            handleCategory(obj);
        }*/



        function handleCategory(obj){

            var html="<option value=''>选择类目</option>";

            for(var x in obj){
                html+="<option value='"+x+"'>"+obj[x].name+"</option>";
            }

            $("#allCategory").html(html);

            //类目切换
            $("#allCategory").change(function () {
                var val=$(this).val();
                if(val==""){
                    $("#subject").attr({"disabled":true,"title":"该类目下没有科目"}).html("<option value=''>选择科目</option>");
                }else{
                    var dataObj=obj[val].list;

                    if(getJsonLength(dataObj)>0){
                        var subjectHtml="<option value=''>选择科目</option>";
                        for(var i in dataObj){
                            subjectHtml+="<option value='"+i+"'>"+dataObj[i]+"</option>";
                        }

                        $("#subject").removeAttr("disabled title").html(subjectHtml);
                    }else{
                        $("#subject").attr({"disabled":true,"title":"该类目下没有科目"}).html("<option value=''>选择科目</option>");
                    }
                }
            });
        }

    }

    //获取所有校区
    if($("#allCampus").length==1){

        /*if(sessionStorage.allCampus==undefined){*/

            var cam_list={
                "parameter":{
                    "org_id":option.org_id,
                    "needCount":false
                },
                "url":domain+"/baseSet/campusManage/queryCampusListInfo.json"
            };

            getData(cam_list.url,cam_list.parameter,function (res) {

                if(res.code==200 && res.success==true){
                    var obj=res.data.data;

                    sessionStorage.allCampus=JSON.stringify(obj);
                    handleCampus(obj);

                }else {
                    myAlert(res.msg,2,res.code,null);
                }
            },"POST",false);

       /* } else {
            var obj=JSON.parse(sessionStorage.allCampus);
            handleCampus(obj);
        }*/

        function handleCampus(obj) {
            var html="<option value='"+option.roleCam+"'>选择校区</option>";
            if(option.roleCam=="all"){
                if(obj.length>0){
                    for(var i=0,m=obj.length; i<m;i++) {
                        html += "<option value='" + obj[i].cam_id + "'>" + obj[i].cam_name + "</option>";
                    }
                }
            }else{
                var roleCamArray=option.roleCam.split(",");
                if(obj.length>0){
                    for(var i=0,m=obj.length; i<m;i++){
                        if($.inArray(obj[i].cam_id.toString(), roleCamArray)>=0){
                            html+="<option value='"+obj[i].cam_id+"'>"+obj[i].cam_name+"</option>";
                        }
                    }
                }
            }

            $("#allCampus").html(html);

        }

    }

    //获取所有合作机构
    if($("#allCooperatives").length==1){
        var cam_list={
            "parameter":{
                "org_id":option.org_id,
                "needCount":false,
            },
            "url":domain+"/baseSet/cooperativeManage/queryCooperativeListInfo.json"
        };

        getData(cam_list.url,cam_list.parameter,function (res) {
            if(res.code==200 && res.success==true){

                var obj=res.data.data,html="";

                if(obj.length>0){
                    for(var i=0,m=obj.length; i<m;i++){
                        html+="<option value='"+obj[i].cooperative_id+"'>"+obj[i].name+"</option>";
                    }
                    $("#allCooperatives").append(html);
                }

            }else {
                myAlert(res.msg,2,res.code,null);
            }
        },"POST",false)
    }
    //获取所有教室
    if($("#allClassrooms").length==1){
        var cam_list={
            "parameter":{
                "org_id":option.org_id,
                "needCount":false,
            },
            "url":domain+"/baseSet/classRoomManage/queryClassRoomListInfo.json",

        };

        getData(cam_list.url,cam_list.parameter,function (res) {
            if(res.code==200 && res.success==true){
                var obj=res.data.data;
                //存储到本地
                option.allClassroom=obj;
                var html="";
                if(obj.length>0){
                    for(var i=0,m=obj.length; i<m;i++){
                        var str=(obj[i].building!==null? obj[i].building+" ":"") + (obj[i].floor!==null?obj[i].building+" ":"")+obj[i].classroom_name;
                        html+="<option value='"+obj[i].classroom_id+"' data-camid='"+obj[i].cam_id+"'>"+str+"</option>";
                    }
                    $("#allClassrooms").append(html);
                }

            }else {
                myAlert(res.msg,2,res.code,null);
            }
        },"POST",false)
    }




    //班级类型
    if($("#classType").length==1){
        createSelect("classType",option.classType);
    }

    //教师类型
    if($("#teacherType").length==1){
        createSelect("teacherType",option.teacherType);
    }

    //学员报名状态
    if($("#stuStatus").length==1){
        createSelect("stuStatus",option.signStatus);
    }
    //学员打印状态
    if($("#printType").length==1){
        createSelect("printType",option.printType);
    }

    //缴费方式
    if($("#payType").length==1){
        createSelect("payType",option.payType);
    }

    //缴费方式
    if($("#tradingType").length==1){
        createSelect("tradingType",option.tradingType);
    }


    //上课时间
    if($("#allTime").length==1){
        createSelect("allTime",option.allTime);
    }

    //所有日志操作动作
    if($("#allAction").length==1){
        createSelect("allAction",option.allAction);
    }

    //证件类型
    if($("#idType").length==1){
        createSelect("idType",option.idType);
    }

    if($("#certificationType").length==1){
        createSelect("certificationType",option.certificationType);
    }


    //静态生成
    function createSelect(id,obj) {
        var  dropDownhtml="";
        if($("#"+id+" option").length>1){
          return false
        }
        for(var i in obj){
            dropDownhtml+="<option value='"+i+"'>"+obj[i]+"</option>";
        }
        $("#"+id).append(dropDownhtml);
    }

}


/**全选函数：表格用
 * @author 刘双双
 * @param {obj} 一般默认值是this
 * @param {string} calssname  input的name值
 */
  function all_click(obj, calssname,callBack) {
    if ($(obj).is(":checked")) {
      $("input[name='" + calssname + "']").prop("checked","true");
    } else {
      $("input[name='" + calssname + "']").removeAttr("checked");
    }
    if(callBack!=null||callBack!=undefined) {
        callBack();
    };
  }




 /**
  * 我的自定义alert函数
  * @param {string} message：提示信息
  * @param {number} number：[1,2,3,4]里的一种，显示类型：1.成功 2.错误 3.疑问 7.警告
  * @param {function} okCallback:回调函数
  */

function myAlert(message,number,code,okCallback) {
  layer.alert(message,
          {icon: number,
              title:"提示",
              area: '360px;',
              zIndex: layer.zIndex,
              success: function(layero){
              layer.setTop(layero); //重点2
        },cancel: function(index){
                  //右上角关闭函数
                  if(okCallback!=null) okCallback();
              }

        },
          function(index){
               if(okCallback!=null) okCallback();
               if(code=="9003"){
                  window.location="login.html";
               }
              layer.close(index);
         }
   );
}
 /*
  * 我的自定义Confirm函数
  * 类型：1.报错 2.成功 3.疑问 7.警告
  */

function  myConfirm(message,okCallback,cancleCallback,closeCallback,icon){
 layer.confirm(message,{
       icon: (icon==undefined?3:icon),
       title:'提示信息',
       cancel: function(index){
        //右上角关闭函数
         if(closeCallback!=null) closeCallback();
      },
     btn: ['确定','取消'] //按钮
    }, function(index){
     //确定函数
     if(okCallback!=null) { okCallback();layer.close(index);}else{null}

  }, function(){
       //取消函数
       if(cancleCallback!=null) {cancleCallback();}
  });
}



/*ajax加载*/
function loadurl(url, tag) {
    $("#" + tag).after("<div class='loadingbg'></div><div class='loadingimg'><img src='resource/images/loading.gif'><div>数据加载中，请稍后...</div></div>");
    htmlobj=$.ajax({
      type:'GET',
      //cache: false
      url:url,
      async:false,
      beforeSend: function(xhr){},
      contentType:"application/x-www-form-urlencoded",
      error:function(XMLHttpRequest, textStatus, errorThrown) {
        $("#" + tag).html("装载应用时发生异常：" +"对象："+ XMLHttpRequest.status +"错误信息"+XMLHttpRequest.readyState+"错误提示"+textStatus);
      }
    });

    $(".loadingbg").fadeOut("fast");
    $(".loadingimg").fadeOut("fast");

    $("#" + tag).html(htmlobj.responseText);

    $("#" + tag).attr("datapage-url",url);

    setTimeout(function(){
      $(".loadingbg").remove();
      ;
      $(".loadingimg").remove();
    }
    ,100);
    return false;
}


/**
 * 日期控件
 * 使用方法：<input type="text" name="" data-date="YYYY-MM-DD hh:mm:ss">
 * 支持一下格式：
 *  1、 YYYY-MM-DD hh:mm:ss 或者 YYYY年MM月DD日 hh:mm:ss
 *	2、 YYYY-MM-DD hh:mm
 *	3、 YYYY-MM-DD
 *	4、 YYYY-MM
 *	5、 YYYY
 *	6、 hh:mm:ss
 *	7、 hh:mm
 */
$("body").on("focus","input[data-date]",function(){
    var $this=$(this);
	var timeformat=$(this).attr("data-date");
	$(this).jeDate({
	  	format:timeformat,
        okfun:function(obj2) {
            $this.blur()
        }
    })
});



$("body").on("click",".gui-alert .fa-times",function(){
   $(this).closest(".gui-alert").fadeOut();
});
/**
 * 保存获取接口
 * 参数说明：
 * param:[url] 请求地址
 * param:[data] 参数
 * param:[okCallback] 回调函数
 * param:[post|get]方法
 * 使用方法：getData("dep/getAllTeachers.htm?a="+Math.random(),{},function(data){console.info(data);})
 * async:默认为异步的/false则为同步
 */
function getData(url,data,okCallback,method,async) {

    //判断是否包含网络
    if(window.navigator.onLine!==true) {
        myAlert("网络出现了问题，请检查你的网络",2,null,null);
        return false
    }
    var ajaxmethod = method||"get";

   if(loginMain+'/esb/api/orgloginOrg'!==url && loginMain+'/esb/api/orgloginSel' !== url){
       data.org_id=option.org_id;
       data.token=option.token;
       data.udid=option.udid;
   }
    data.timestamp=new Date().getTime();

   if(async==false){
    	var asynct = false;
    }else{
       var asynct = true;
    }
	var tempbg = $('.loading-bg').attr('loading-id');
	var tempimg = $('.loadingimg').attr('loading-id');

    $.ajax({
        url : url,
        data : data,
        async : asynct,
        timeout:30000,
		type: ajaxmethod,
		cache: false,
        dataType:'json',
        beforeSend:function(){
           loadbgOpen();
        },
        success : function(result,textStatus,jqXHR) {
            if(jqXHR.status==200){
                //回调
                okCallback(result);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            myAlert(XMLHttpRequest.statusText,2,null,null);
        },
        complete: function(XMLHttpRequest,status) {
            //超时,status还有success,error等值的情况
            if(status=='timeout'){
                myAlert("服务器响应超时，请稍后再试！",2,null,null);
            }
            loadbgOver(tempbg,tempimg);
        },
    });
};

function getHtmlData(url,data,okCallback,method,async) {
    var ajaxmethod = method||"get";
    if(async==false){
    	async = false;
    }else{
    	async = true;
    }
	var tempbg = $('.loading-bg').attr('loading-id');
	var tempimg = $('.loadingimg').attr('loading-id');
    $.ajax({
        url : url,
        data : data,
        async : async,
		type: ajaxmethod,
		//cache: false,
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		dataType:'html',
        beforeSend:function(){
           loadbgOpen();
        },
        success : function(result,textStatus,jqXHR) {
            if(jqXHR.status==200){
                //回调
                okCallback(result);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            myAlert(textStatus,2,null);
        },
        complete: function() {
            loadbgOver(tempbg,tempimg);
        },
    });
};

/**
 * 判断遮罩层id
 * @author 柳爽
*/


//遮罩层开启
function loadbgOpen(){
    var loadbgId = $('.loading-bg').attr('loading-id');
    var loadimgId = $('.loadingimg').attr('loading-id');

	$("[loading-id="+loadbgId+"]").show();
	$('[loading-id='+loadimgId+']').show();

	if (loadbgId != 'loadbg'){
		loadbgId = +loadbgId + 1 + '';
		loadimgId = +loadimgId + 1 + '';
		$('.loading-bg').attr('loading-id', loadbgId);
		$('.loadingimg').attr('loading-id', loadimgId);
	}else{
		$('.loading-bg').attr('loading-id', '1');
		$('.loadingimg').attr('loading-id', '2');
	}
}

function loadbgOver(bg,img){

    var loadbgId = $('.loading-bg').attr('loading-id');
    var loadimgId = $('.loadingimg').attr('loading-id');

	if(bg==(Number(loadbgId)-1) && img==(Number(loadimgId)-1)){
		$("[loading-id="+loadbgId+"]").hide();
		$("[loading-id="+loadimgId+"]").hide();
	}else if(loadbgId==1&&loadimgId==2){
		$("[loading-id="+loadbgId+"]").hide();
		$("[loading-id="+loadimgId+"]").hide();
	} else if (loadbgId == 'loadbg' && loadimgId == 'loadimg'){
		$('.loading-bg').hide();
		$('.loadingimg').hide();
	}
}

/**
 * 自定义窗口
 * @param html     要显示的html （定义成模板）
 * @param titlle   标题
 * @param closeCallback   右上角关闭回调（×）
 * @param size      窗口尺寸[400px ,200px]
 * @param btnText   按钮文字
 * @param Callback  按钮回调[function,function]
 */
function mydialogForm(html, title, closeCallback, size, btnText, Callback) {
    var config = {
        type : 1,
        title : title,// 不显示标题栏
        //anim : 0,
        shade : [ 0.16, '#393D49' ],
        // id: 'LAY_layuipro' ,//设定一个id，防止重复弹出
        maxmin : false,// 是否显示最大最小化窗口按钮
        closeBtn : 1,// 是否右上角显示关闭按钮
        // area: ['700px', '240px'], //宽高
        area : size,
        content : "<div style='padding:0px;'>"+html+"</div>",// 要获取的内容
        resize : false,
        shadeClose : true,
        // shade: false,
        cancel : function(index) {
            // 右上角关闭函数
            if (closeCallback != null)
                closeCallback();
        },
        btn : btnText,
        anim : 5,
        zIndex : layer.zIndex, // 重点1
        success : function(layero) {
            layer.setTop(layero); // 重点2
        }
    };
    // 传参数
    for (var n = 0; n < arguments[4].length; n++) {
        config["btn" + (n + 1)] = arguments[5][n];
    }
    layer.open(config);
}


/**
 *  获取JSON长度
 * @author 刘双双
 * @param {jsonData} 要判断的JSON对象
*/
function getJsonLength(jsonData){
    var jsonLength = 0;
    for(var item in jsonData){
        jsonLength++;
    }
    return jsonLength;
};



/**
* 函数：数组去重，给数组原型添加方法
* @author 刘双双
* 用法：arr.unique()
* 描述：返回去重后的数据
*/
Array.prototype.unique = function(){
    var res = [];
    var json = {};
    for(var i = 0; i < this.length; i++){
        if(!json[this[i]]){
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
};


/**
 *  函数：删除数组中的指定元素
 *  @author 刘双双
 *  用法：arr.removeByValue(val)
 * @param { string } val  要删除的对象
 *
 */
Array.prototype.removeByValue = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
}

/**
 * 函数：获取字符串长度
 * @author 刘双双
 * @param startDiffTime:
 * 使用:"string".gblen();
 */

String.prototype.gblen = function () {
    var len = 0;
    for (var i = 0; i < this.length; i++) {
        if (this.charCodeAt(i) > 127 || this.charCodeAt(i) == 94) {
            len += 2;
        } else {
            len++;
        }
    }
    return len;
}



/**
 * 回到顶部
 * @param gd 内容所在的元素
 */

function backTop(gd){
	if(gd){
		gd=gd;
	}else{
		gd=window;
	}
	$(gd).scroll(function(){
		var BTop = $(gd).scrollTop();
		if(BTop>800){
			$('#backTop').show();
		}else{
			$('#backTop').hide();
		}
	})
	$('#backTop').click(function(){
		var Gd='';
		if(gd==window){
			Gd='body,html';
		}else{
			Gd=gd;
		}
		$(Gd).animate({scrollTop: '0px'},500);
	});
}



/**
 * 通过【图片名称】获取图片的完整路径
 * @author 刘双双
 * @param imgName{string} 图片的名称
 * @returns {string} 返回图片的完整路径
 */
function changeImgUrl(imgName){
    var cutName = imgName.split(".")[0];
    var url = loginMain+"/../esb/res/pic/" + Math.floor(+cutName / 10000) + "/" + Math.floor(+cutName / 100) + "/" + imgName;
    return url;
}


/**
 * 名称：获取域名中指定参数的值
 * @author 刘双双
 * @param paraName 要获取字段的名称
 * @returns 返回指定字段的值
 * 描述：GetUrlParam（"classId"）
 * 基本应用
 **/

function GetUrlParam(paraName) {
    var url = document.location.toString();
    var arrObj = url.split("?");
    var urllength=arrObj.length;
    if (arrObj.length > 1) {
        var arrPara = arrObj[urllength-1].split("&");
        var arr;
        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");
            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}

/**
 * JSON转GET请求参数
 * @author 刘双双
 * @param {json } data  json对象
 * @returns {string}
 * 目前主要用在下载Excel
 */
function parseParams(data) {
    try {
        var tempArr = [];
        for (var i in data) {
            var key = encodeURIComponent(i);
            var value = encodeURIComponent(data[i]);
            tempArr.push(key + '=' + value);
        }
        var urlParamsStr = tempArr.join('&');
        return urlParamsStr;
    } catch (err) {
        return '';
    }
}

/**
 * 本系统获取所有教师
 * @param changedTech{array} 已选择教师
 */
function changedTeacher(changedTech,result){

    var allTeacher=null;
    var changed={
        changedTea:[],
        teachertName:[]
    };

    //如果本地包含教师数据则从本地取，否则去加载

    /*if(option.allTeachers==null){*/
        var cam={
            "parameter":{
                "org_id":option.org_id,
                "token":option.token,
                "teacher_name" :option.name,
                "needCount":false,
            },
            "url":loginMain+"/esb/api/tech/getTechRolesOforg"
        };
        getData(cam.url,cam.parameter,function (res) {
            if(res.code==200 && res.success==true){
                allTeacher=res.data;
                option.allTeachers=res.data;
                dialogShowTearcher();

            }else {
                myAlert(res.message,2,res.code,null);
            }
        },"GET",false);
/*    }else{
        allTeacher=option.allTeachers;
        dialogShowTearcher();
    }*/




    function dialogShowTearcher(){
        var changedTearcher=changedTech==undefined||changedTech==null?changedTearcher=[]:changedTech;
        var res = allTeacher;
        var unchangedHtml='',changedHtml="";

        for(var i=res.length-1;i>=0;i--){
            var teacherObj=res[i];
            if($.inArray(teacherObj.user_id.toString(),changedTearcher)>=0){
                var changeTip='class=\"changed\" title=\"'+teacherObj.teacher_name+'老师已添加\"';
                changedHtml+='<li class="changed" data-id="'+teacherObj.user_id+'" title="删除">' +
                    '<i class="fa fa-user"></i>' +
                    '<span class="t_name">'+teacherObj.tech_name+'</span>' +
                    '(<span class="t_mob">'+teacherObj.user_mobile+'</span>)' +
                    '<i class="fa fa-times-circle"></i>' +
                    '</li>';
            }else{
                var changeTip='class="unChanged" title="添加 '+teacherObj.tech_name+' 老师"';
            }

            unchangedHtml+='<li '+changeTip+' id="'+teacherObj.user_id+'">' +
                '<i class="fa fa-user"></i>' +
                '<span class="t_name">'+teacherObj.tech_name+'</span>' +
                '(<span class="t_mob">'+teacherObj.user_mobile+'</span>)' +
                '<i class="fa fa-plus-square-o"></i>' +
                '</li>';
        }


        var templateThtm='<div class="tech_Wrap">' +
            '<div class="tech_item allTeachers"  >' +
            '      <div class="dialogSearch">' +
            '         <input type="text" name="" id="searchTName" placeholder="请输入教师姓名或者手机号" class="gui-input" maxlength="20" >' +
            '         <div id="toSearchTeacher" title="点击查询"><i class="fa fa-search"></i></div>' +
            '       </div>' +
            '       <ul id="unChangedT">' +
            unchangedHtml +
            '       </ul>' +
            '</div>' +
            '<div class="tech_item changedTeachers" >' +
            '    <div class="changedTtitle">已选教师</div>' +
            '     <ul id="changedT">' +
            changedHtml +
            '     </ul>' +
            '</div>' +
            '</div>';

        mydialogForm(templateThtm, "选择教师", null, ['720px', '510px'], ["保存", "取消"], [function(t){

            if($("#changedT [data-id]").length>0){

                //遍历所选教师
                $("#changedT [data-id]").each(function(){
                    var t_id=$(this).attr("data-id");
                    var t_name=$(this).find(".t_name").text();
                    changed.changedTea.push(t_id);
                    changed.teachertName.push(t_name);
                });

                //更新界面
                if(changed.teachertName.length==0){
                    $("#changedTeachers span").text("共选择0个老师");
                }else{
                    $("#changedTeachers span").text(changed.teachertName.join(","));
                    $("#changedTeachers input").val(changed.changedTea.join(",")).focus().blur();
                }

                //更新本地选择结果
                eval(result).teachertName=changed.teachertName;
                eval(result).changedTea=changed.changedTea;
                layer.close(t);

            }else{
                myAlert("请选择一位教师！",2,null,null);
                return false
            }


        },null]);

    }


    //注册查询方法--按名字和手机号查询教师
    $("body").off("click","#toSearchTeacher");
    $("body").on("click","#toSearchTeacher",function () {

        var tech_name=$("#searchTName").val();
        if(tech_name==""){
            myAlert("请输入教师姓名或手机号！",2,null,function () {
                $("#searchTName").focus();
            });
            return false
        }

        if($(this).find(".fa").hasClass("fa-close")){
            $("#unChangedT li").show();
            $(this).find(".fa").addClass("fa-search").removeClass("fa-close").attr("title","查询");
            $("#searchTName").val("");
            return false
        }


        //更新按钮样式
        $(this).find(".fa-search").addClass("fa-close").removeClass("fa-search").attr("title","清空所选");


        $("#unChangedT li").hide();
        $("#unChangedT li").each(function () {

            var name=$(this).find(".t_name").text();
            var mob=$(this).find(".t_mob").text();

            if(name.indexOf(tech_name) !== -1 || mob.indexOf(tech_name) !== -1){
                $(this).show();
            }

        });

    });

    $("body").off("keydown","#searchTName");
    $("body").on("keydown","#searchTName",function (e) {
        var e=e||event;
        if(e.keyCode==13){
            var val=$.trim($(this).val());
            if(val==""){
                myAlert("请输入教师姓名或手机号！",2,null,function () {
                    $(this).focus();
                });
                return false
            };

            if($("#toSearchTeacher").find(".fa").hasClass("fa-close")){
                $("#unChangedT li").show();
                $("#toSearchTeacher").find(".fa").addClass("fa-search").removeClass("fa-close").attr("title","查询");
                $("#searchTName").val("");
                return false
            }

            //更新按钮样式
            $("#toSearchTeacher").find(".fa-search").addClass("fa-close").removeClass("fa-search").attr("title","清空所选");


            $("#unChangedT li").hide();
            $("#unChangedT li").each(function () {

                var name=$(this).find(".t_name").text();
                var mob=$(this).find(".t_mob").text();

                if(name.indexOf(val) !== -1 || mob.indexOf(val) !== -1){
                    $(this).show();
                }

            });

        }
    });


    //注册添加方法
    $("body").off("click","#unChangedT li.unChanged");
    $("body").on("click","#unChangedT li.unChanged",function () {
        var tech_id=$(this).attr("id");
        var tech_name=$(this).find(".t_name").text();
        var tech_mob=$(this).find(".t_mob").text();
        //更新本身
        $(this).addClass("changed").attr("title",tech_name+"老师已添加").removeClass("unChanged");
        //更新界面
        var html='<li class="changed" data-id="'+ tech_id +'" title="删除">' +
            '<i class="fa fa-user"></i>' +
            '<span class="t_name">'+tech_name+'</span>' +
            '(<span class="t_mob">'+tech_mob+'</span>)' +
            '<i class="fa fa-times-circle"></i>' +
            '</li>';
        $("#changedT").append(html);
    });

    //注册删除方法
    $("body").off("click","#changedT li");
    $("body").on("click","#changedT li",function () {
        var tech_id=$(this).attr("data-id");
        //更新本身
        $(this).remove();
        //更新界面
        $("#"+tech_id).addClass("unChanged").attr("title","添加").removeClass("changed");
    });

    return changed;
}

function resetForm() {
    $("#add-form")[0].reset()
}

/**
 * 只允许输入数字
 */
$("body").on("keyup","[data-onlyNumber=true]",function(){
    var editeVal=$(this).val().replace(/[^\d]/g,'');
    $(this).val(editeVal);
});

/**
 * 输入框错误提示
 * @param id
 * @param msg
 * 描述：验证不合格的表单提示错误信心
 */
function dialogFormItemerrorTip(id,msg){
    $("#"+id).addClass("gui-form-input-error");
    if($("#"+id+"-error").length==1){
        $("#"+id+"-error").text(msg).show();
        return false
    }else{
        $("#"+id).after("<span id=\""+id+"-error\" class=\"gui-form-item-error\">"+msg+"</span>");
    }
    //注册方法
    $("#"+id).focus(function(){
        $("#"+id).removeClass("gui-form-input-error");
        $("#"+id+"-error").remove();
    })
}


//图片上传
function imgUpload(){
    var $wrap = $('#uploader'),
        // 图片容器
        $queue = $('<ul class="filelist"></ul>')
            .appendTo($wrap.find('.queueList')),
        // 状态栏，包括进度和控制按钮
        $statusBar = $wrap.find('.statusBar'),
        // 文件总体选择信息。
        $info = $statusBar.find('.info'),
        // 上传按钮
        $upload = $wrap.find('.uploadBtn'),
        // 没选择文件之前的内容。
        $placeHolder = $wrap.find('.placeholder'),
        $progress = $statusBar.find('.progress').hide(),
        // 添加的文件数量
        fileCount = 1,
        // 添加的文件总大小
        fileSize = 1024,
        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,
        // 缩略图大小
        thumbnailWidth = 110 * ratio,
        thumbnailHeight = 110 * ratio,
        // 可能有pedding, ready, uploading, confirm, done.
        state = 'pedding',
        // 所有文件的进度信息，key为file id
        percentages = {},
        // 判断浏览器是否支持图片的base64
        isSupportBase64 = (function() {
            var data = new Image();
            var support = true;
            data.onload = data.onerror = function() {
                if(this.width != 1 || this.height != 1) {
                    support = false;
                }
            }
            data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
            return support;
        })(),
        // 检测是否已经安装flash，检测flash的版本
        flashVersion = (function() {
            var version;

            try {
                version = navigator.plugins['Shockwave Flash'];
                version = version.description;
            } catch(ex) {
                try {
                    version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                        .GetVariable('$version');
                } catch(ex2) {
                    version = '0.0';
                }
            }
            version = version.match(/\d+/g);
            return parseFloat(version[0] + '.' + version[1], 10);
        })(),
        supportTransition = (function() {
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                    'WebkitTransition' in s ||
                    'MozTransition' in s ||
                    'msTransition' in s ||
                    'OTransition' in s;
            s = null;
            return r;
        })(),

        uploader;
     // 负责view的销毁
    function removeFile(file) {
        var $li = $('#' + file.id);

        //delete percentages[ file.id ];
        //updateTotalProgress();
        $li.off().find('.file-panel').off().end().remove();
    }
    //图片上传
    var uploader = WebUploader.create({
        // 选完文件后，是否自动上传。
        auto: true,
        // swf文件路径
        swf: domain + 'src/main/webapp/web/js/Uploader.swf',
        // 文件接收服务端。
        server:loginMain+'/newsng/admin/fileupload.htm',
        fileNumLimit:1,
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        fileSizeLimit:524288,
        pick: '#upimg-btn',
        formData: {"token":option.token,"udid":option.udid,"org_id":option.org_id},
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/png' //修改这行
        },
     /*   supportTransition = (function () {
            var s = document.createElement('p').style,
                    r = 'transition' in s ||
                    'WebkitTransition' in s ||
                    'MozTransition' in s ||
                    'msTransition' in s ||
                    'OTransition' in s;
            s = null;
            return r;
        })()*/
    });

    // 当有文件添加进来的时候
    uploader.on('fileQueued', function(file) {

        setTimeout(function (){
            $("#"+file.id).attr("img-width",file._info.width).attr("img-height",file._info.height);
        },50)
        var $list = $("#fileList"),
            $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail" style="padding: 3px;" title="' + file.name + '" img-width="" img-height="">' +
                '<p class="imgWrap"><img></p>' +
                /*'<div class="attachment-name" title="'+file.name+'">' + file.name + '</div>' +*/
                '</div>'
            ),
            /*$btns = $('<div class="file-panel">' +
                '<span class="cancel">删除</span>' +
                '<span class="rotateRight">向右旋转</span>' +
                '<span class="rotateLeft">向左旋转</span></div>').appendTo($li),*/
            $btns = $('<div class="file-panel">' +
                '<span class="cancel">删除</span>').appendTo($li),
            $img = $li.find('img');
        $wrap = $li.find('p.imgWrap'),
            //2017419
            $li.on('mouseenter', function() {
                $btns.stop().animate({
                    height: 30
                });
                percentages[file.id] = [file.size, 0];
                file.rotation = 0;
            });

        $li.on('mouseleave', function() {
            $btns.stop().animate({
                height: 0
            });
        });
        $btns.on('click', 'span', function() {
            var index = $(this).index(),
                deg;

            switch(index) {
                case 0:
                    fileCount--;
                    var removeId = $(this).parent().parent().attr('pictureId');
                    imgId=imgId.filter(function(e){
                        return e!=removeId;
                    })
                    removeFile(file);
                    uploader.removeFile(file);
                    uploader.removeFile(file.id, true);
                    return;

                case 1:
                    file.rotation += 90;
                    break;

                case 2:
                    file.rotation -= 90;
                    break;
            }

            if(supportTransition) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $li.find('p.imgWrap').css({
                    '-webkit-transform': deg,
                    '-mos-transform': deg,
                    '-o-transform': deg,
                    'transform': deg
                });
            } else {
                $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
            }

        });
        //2017419
        // $list为容器jQuery实例
        $list.append($li);
        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
        uploader.makeThumb(file, function(error, src) {
            if(error) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr('src', src);
        }, 100, 100);

    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function(file, percentage) {
        var $li = $('#' + file.id),
            $percent = $li.find('.progress span');

        // 避免重复创建
        if(!$percent.length) {
            $percent = $('<p class="progress"><span></span></p>')
                .appendTo($li)
                .find('span');
        }

        $percent.css('width', percentage * 100 + '%');
    });
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function(file,res) {
        $('#' + file.id).addClass('upload-state-done');
        $('#' + file.id).attr('pictureId',res.id);
        imgId.push(res.id);
        //更新界面
        $("#upimg-btn").attr("data-img",changeImgUrl(res.id));
        $("#ImgPr").attr("src",changeImgUrl(res.id));

    });

    // 文件上传失败，显示上传出错。
    uploader.on('uploadError', function(file,rea) {
        var $li = $('#' + file.id),
            $error = $li.find('div.error');
        // 避免重复创建
        if(!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }

        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on('uploadComplete', function(file) {
        $('#' + file.id).find('.progress').remove();
    });
}

/**
 * 只允许输入数字
 */
$("body").on("keyup","[data-onlyNumber=true]",function(){
    var editeVal=$(this).val().replace(/[^\d]/g,'');
    $(this).val(editeVal);
});
/**
 * 验证文本框输入空格
 */
$("body").on("keyup","input[type=text]",function(){
    var editeVal=$.trim($(this).val());
    $(this).val(editeVal);
});

/**
 * 验证手机号
 */
function testMob(mob){
    var reg = /^1[37985][0-9]\d{8}/;
    var tip=true
    if(!reg.test(mob)){
        tip=false
    }
    return tip
}

/**
 * 判断日期格式yyyy-mm-dd和正确的日期是否合法
 */
function IsDate(str) {
    var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
    var r = str.match(reg);
    if(r==null)return false;
    r[2]=r[2]-1;
    var d= new Date(r[1], r[2],r[3], r[4],r[5], r[6]);
    if(d.getFullYear()!=r[1])return false;
    if(d.getMonth()!=r[2])return false;
    if(d.getDate()!=r[3])return false;
    if(d.getHours()!=r[4])return false;
    if(d.getMinutes()!=r[5])return false;
    if(d.getSeconds()!=r[6])return false;
    return true
}



//根据权限显示对应菜单
function changeNavShow(powers) {
    var role_arr = [
        "sngyhzx0101", //用户中心0      用户管理=1
        "sngxytz0101", //学员通知1      发送通知=5 | 发送记录=3
        "sngwszs0101", //网上招生2      创建招生=5 | 招生记录=3
        "sngxygl0101", //学员管理3      学员管理=1
        "sngbjgl0101", //班级管理4      创建班级=5 | 导入班级=4097 | 管理班级=513
        "sngcwgl0101", //财务管理5      退费处理=3 | 财务统计=4097
        "sngdydc0101", //打印导出6      筛选导出=1
        "sngjsgl0101", //教师管理7      创建教师=5 | 教师管理=3
        "sngczrz0101", //操作日志8      操作查询=1
        "sngxqgl0101", //校区管理9      创建校区=5 | 校区管理=3
        "sngkmgl0101", //科目管理10     创建科目和类目=5 | 科目管理 = 3
        "sngxueqgl0101", //学期管理11   创建学期=5 | 学期管理=3
        "sngjshgl0101", //教室管理12    创建教师=5 | 教师管理=3
        "snghzjg0101", //合作管理13     创建机构=5 | 机构管理=3
        "sngpzmb0101", //凭证管理14     模板设计=5 | 内容编辑=3
        "snggzsz0101", //规则设置15     缴费规则=5 | 认证规则=3
        "sngglqx0101", //管理权限16     建管理员=5 | 管理员表=3
        "sngjssz0101" //角色设置17      创建角色=5 | 角色管理=3
    ];
    var nav_arr = [
        {//用户中心
            "1":"11",//用户管理
            "3":"12"//用户管理详情,默认带
        },
        {//学员通知
            "5":"21",//发送通知
            "3":"22",//发送记录
            "1":"23"//查看发送的班级,默认带
        },
        {//网上招生
            "5":"41",//创建招生
            "3":"42"//招生记录
        },
        {//学员管理
            "1":"51"//学员管理
        },
        {//班级管理
            "5":"61",//创建班级
            "4097":"62",//导入班级
            "513":"63",//管理班级
            "1":"64"//班级学员列表,默认带
        },
        {//财务管理
            "3":"71",//退费处理
            "4097":"72"//财务统计
        },
        {//打印导出
            "1":"81"//筛选导出
        },
        {//教师管理
            "5":"d1",//创建教师
            "3":"d2"//教师管理
        },
        {//操作日志
            "1":"e1"//操作查询
        },
        {//校区管理
            "5":"f11",//校区创建
            "3":"f12",//校区管理
        },
        {//科目管理
            "5":"f21",//创建科目和类目
            "3":"f23"//科目管理
        },
        {//学期管理
            "5":"f31",//学期创建
            "3":"f32"//学期管理
        },
        {//教师管理
            "5":"f41",//创建教师
            "3":"f43"//教室管理
        },
        {//合作管理
            "5":"f51",//创建机构
            "3":"f52"//机构管理
        },
        {//凭证管理
            "5":"f61",//模板设计
            "3":"f62"//内容编辑
        },
        {//规则设置
            "5":"f71",//缴费设置
            "3":"f72"//认证设置
        },
        {//管理权限
            "5":"g11",//创建管理员
            "3":"g12"//管理员列表
        },
        {//角色设置
            "5":"g21",//角色设置
            "3":"g22"//角色管理
        }
    ];
    if (typeof powers.all === "undefined") {
        for (var i in powers) {//powers[i]是权限的值 例如262143
            var index = role_arr.indexOf(i); //遍历获取拥有的权限,每次获取一个 获取最外层菜单是第几个
            var this_nav = '',first_nav = '',second_nav = '',third_nav = '';
            for(var l in nav_arr[index]){//获取最外层菜单下的所有子菜单权限值和权限标识 l是值,nav_arr[index][l]是标识
                this_nav = nav_arr[index][l];//遍历获取每次的标识
                first_nav = this_nav.substring(0,1);//标识的第一个字也就是最外层菜单,去用nav[first_nav]找
                if(this_nav.length > 1){
                    second_nav = this_nav.substring(0,2);//如果标识长度达到2 second_nav 是第二层标识
                }
                if(this_nav.length > 2){
                    third_nav = this_nav.substring(0,3);//如果标识长度达到3 third_nav 是第三层标识
                }
                if((+l | +powers[i]) == powers[i]){//判断此次权限的值是否包含遍历的权限值,如果包含,显示该菜单
                    nav[first_nav].isShow = true;//第一层显示
                }
                if (nav[first_nav].nextNav != undefined){//如果第一层下有子菜单,遍历子菜单
                    var second_list = nav[first_nav].nextNav;//第二层菜单
                    if((+l | +powers[i]) == powers[i]){//判断此次权限的值是否包含遍历的权限值,如果包含,显示该菜单
                        second_list[second_nav].isShow = true;//第二层该菜单显示
                    }
                    if (second_list[second_nav].nextNav != undefined){//如果第二层下还有子菜单,继续遍历其子菜单
                        var third_list = second_list[second_nav].nextNav;//第三层菜单
                        for(var j in third_list){//遍历第三层菜单
                            if((+l | +powers[i]) == powers[i]){//判断此次权限的值是否包含遍历的权限值,如果包含,显示该菜单
                                third_list[third_nav].isShow = true;//第三层该菜单显示
                                //创建科目和类目特殊处理
                                if (this_nav == 'f21') {
                                    nav["f"].nextNav["f2"].nextNav["f22"].isShow = true;
                                }
                            }
                        }
                    }
                }

            }

            /* 真cnml */
            //暂时这样设置主菜单nav显示隐藏
        }
    }else{
        for (var i in nav) {
           if(nav[i]!==null){
               nav[i].isShow = true;
               if(nav[i].nextNav != undefined){
                   var second_list = nav[i].nextNav;
                   for(var j in second_list){
                       second_list[j].isShow = true;
                       if(second_list[j].nextNav != undefined){
                           var third_list = second_list[j].nextNav;
                           for(var c in third_list){
                              var fourth_list=third_list[c];
                               fourth_list.isShow = true;
                           }
                       }
                   }
               }
           }
        }
    }
}
/**
 * 获取接送对象的第一个元素
 */
function getObjFirst(jsonObj) {
    var obj=jsonObj;
    for(var i in obj){
        if(i!=="404"){
            return i;
            break
        }

    }
}
/**
 * 获取路由中显示的
 */
function getRouterId(z) {
    var obj=router;
    for(var i in obj){
        if(i.indexOf(z)==0){
            return i;
            break
        }
    }
}
