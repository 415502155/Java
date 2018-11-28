//var domain = 'https://yuntest.5tree.cn';
var domain = 'https://t.shijiwxy.5tree.cn';
//判断是否从后台直接进入,若是,数据从cookie获取
var allRelation = ['父亲', '母亲', '爷爷', '奶奶', '外公', '外婆', '其他'];
var cookieData;
if (!sessionStorage.tech_id){
	var thisPort = domain + '/shijiwxy/portal/login/managerLogin.htm';
	function getCookie (){
		var cookie = document.cookie.split(';');
		var data = {};
		var checks = new RegExp("=");

		for(var i =0;i<cookie.length;i++){
			var index = cookie[i].indexOf(checks.exec(cookie[i]));
			var key = cookie[i].substring(0,index).trim();
			var value = cookie[i].substring(index+1,cookie[i].length);
			if(key == "wx_t"){
				data["token"] = value;
			}else if(key == "wx_u"){
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
					myAlert("请先登录后再操作", 2, function () { window.location.href = "login.html" })
				}
			} else {
				cookieData = JSON.parse(res.data).data;

                 sessionStorage.setItem('token',cookieData.token);
                 sessionStorage.setItem('udid',cookieData.udid);
				 sessionStorage.setItem('org_id',cookieData.orgusers[0].org_id);
                 sessionStorage.setItem('rlids',cookieData.orgusers[0].rlids);
                 sessionStorage.setItem('user_id',cookieData.orgusers[0].user_id);
                 sessionStorage.setItem('tech_id',cookieData.orgusers[0].teacher.tech_id);
                 sessionStorage.setItem('use',cookieData.orgusers[0].teacher.tech_name);
                 sessionStorage.setItem('orgName',cookieData.orgusers[0].organization.org_name_cn);
                 sessionStorage.setItem('Phone',cookieData.orgusers[0].user_mobile);
                 sessionStorage.setItem('mapRK',JSON.stringify(cookieData.orgusers[0].identityDatas[0].mapRK));
                 sessionStorage.setItem('mapKC',JSON.stringify(cookieData.orgusers[0].identityDatas[0].mapKC));
                 sessionStorage.setItem('mapNJ',JSON.stringify(cookieData.orgusers[0].identityDatas[0].mapNJ));
                 sessionStorage.setItem('mapBZR',JSON.stringify(cookieData.orgusers[0].identityDatas[0].mapBZR));
                 sessionStorage.setItem('identity',JSON.stringify(cookieData.orgusers[0].identity));
			}
		},
		error : function (res) {
			if (!window.location.href.match(/login.html/)) {
				myAlert("请先登录后再操作", 2, function () { window.location.href = "login.html" })
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


try {
	var token = sessionStorage.getItem('token')
	var udid = sessionStorage.getItem('udid')
	var org_id = sessionStorage.getItem('org_id');
    if(sessionStorage.getItem('rlids') !== null){
        var rlids = sessionStorage.getItem('rlids');
	}
	var user_id = sessionStorage.getItem('user_id') ;
	var Tech_id = sessionStorage.getItem('tech_id') ;
	var useName = sessionStorage.getItem('use');
	var orgName = sessionStorage.getItem('orgName') ;
	var Phone = sessionStorage.getItem('Phone') ;
	var mapRK = JSON.parse(sessionStorage.getItem('mapRK'));
	var mapKC = JSON.parse(sessionStorage.getItem('mapKC')) ;
	var mapNJ = JSON.parse(sessionStorage.getItem('mapNJ'));
	var mapBZR = JSON.parse(sessionStorage.getItem('mapBZR'));
	if(sessionStorage.identity == 99){
		rlids = rlids.substring(0, rlids.length - 1).split(',');
		for(var i = 2;i<100;i++){
			rlids.push(""+i);
		}
	}
} catch (error) {

}

//判断用户是否存在角色,不存在则登出
if(typeof rlids === "undefined" || rlids === ""){
	if (!window.location.href.match(/login.html/)) {
		myAlert("您没有权限,请与管理员联系!", 2,500, function () {
			window.location.href = "login.html"
			throw new Error("您没有权限,请与管理员联系!");
		})
	}
}


//全选函数 ： 表格用
  function all_click(obj, calssname) {
    if ($(obj).is(":checked")) {
      $("input[name='" + calssname + "']").prop("checked","true");
    } else {
      $("input[name='" + calssname + "']").removeAttr("checked");
    }
  }


/*dialog*/
//例子: 按钮字符 对应的回调 采用数组格式(数值也是一一对应)，宽高也是如此
//对应格式[确定，取消] [function(){//内容},function(){//内容}] 尺寸:['700px', '240px']
function mydialog(divId,title,closeCallback,size,btnText,Callback) {
    var config={
        type: 1,
        title: title ,//不显示标题栏
        //anim:0,
        shade: [0.16, '#393D49'],
       // id: 'LAY_layuipro' ,//设定一个id，防止重复弹出
        maxmin:false,//是否显示最大最小化窗口按钮
        closeBtn: 1,//是否右上角显示关闭按钮
        //area: ['700px', '240px'], //宽高
        area:size,
        content: $('#'+divId).html(),//要获取的内容
        resize: false,
        shadeClose:true,
        //shade: false,
        cancel: function(index){
           //右上角关闭函数
           if(closeCallback!=null) closeCallback();
         },
        btn: btnText,
        anim:5,
        zIndex: layer.zIndex //重点1
        ,success: function(layero){
            layer.setTop(layero); //重点2
        }
    }
    //传参数
    for(var n=0;n<arguments[4].length;n++){
      config["btn"+(n+1)]=arguments[5][n];
    }
	layer.open(config)

}

 /*
  * 我的自定义alert函数
  * message：提示信息
  * number：显示类型：1.成功 2.错误 3.疑问 7.警告
  * okCallback:回调函数
  */

function myAlert(message,number,code,okCallback) {
	console.info(okCallback);
  layer.alert(message,
          {icon: number,title:"提示",area: '360px;',zIndex: layer.zIndex //重点1
        ,success: function(layero){
            layer.setTop(layero); //重点2
        }},
          function(index){
                   if(okCallback!=null) okCallback();
                   if(code=="9003"||token==null||udid==null){
                   			session();
                   			//登录
												sessionStorage.removeItem('token');
									      sessionStorage.removeItem('udid');
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

function  myConfirm(message,okCallback,cancleCallback,closeCallback){

 layer.confirm(message,{
      /*icon: 3,*/
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
      beforeSend: function(xhr){
        //xhr.setRequestHeader('Access-Control-Request-Headers','x-requested-with');
        //xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
        //xhr.setRequestHeader('Access-Control-Allow-Headers','DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type');
        //xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      }
      ,
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

/*
 * ajax提交
*参数说明：
*
right
 */

function submitForAjax(tag,url, successCb, errorCb, data){

	if (arguments.length == 4) {
    var submitmethod="post";
	}else{
		var submitmethod="get";
	}

	$.ajax({
			url: url,
			data: data,
			method:submitmethod,
			dataType: 'json',

			//发送前
			beforeSend: function(XMLHttpRequest){
			//ShowLoading();
			 $(".topbar").after("<div class='loading-bg'></div><div class='loadingimg'><img src='images/loading-2.gif'><div>数据加载中，请稍后...</div></div>");
			},
			success: function(data) {
				successCb(data);
			},
			complete: function(XMLHttpRequest, textStatus){
			 //HideLoading();
		    setTimeout(function(){
		     $(".loading-bg").fadeOut("fast");
		     $(".loadingimg").fadeOut("fast");
		    }
		    ,5000);
		   /* $("#" + tag).attr("datapage-url",url);
		    setTimeout(function(){
		     $(".loadingbg").remove();
		      $(".loadingimg").remove();
		    }
		    ,200);*/
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//$("#" + tag).html("装载应用时发生异常：" +"对象："+ XMLHttpRequest.status +"错误信息"+XMLHttpRequest.readyState+"错误提示"+textStatus);
			}
		})




}

/*
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
	  var timeformat=$(this).attr("data-date");
		$(this).jeDate({
	  	format:timeformat,
	})
});


/*
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
            myAlert(textStatus,2,null);
        },
        complete: function() {
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

//判断遮罩层id
var loadbgId = $('.loading-bg').attr('loading-id');
var loadimgId = $('.loadingimg').attr('loading-id');
//遮罩层开启
function loadbgOpen(){

	loadbgId = $('.loading-bg').attr('loading-id');
	loadimgId = $('.loadingimg').attr('loading-id');
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

	if(bg==(+loadbgId-1)&&img==(+loadimgId-1)){
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
/*
 * 自定义窗口 @param html 要显示的html （定义成模板） @param titlle 标题 @param closeCallback
 * 右上角关闭回调（×） @param size 窗口尺寸[400px ,200px] @param btnText 按钮文字 @param Callback
 * 按钮回调[function,function]
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
        content : html,// 要获取的内容
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
//显示数据条数样式
		//pageSize 数据条数  allTarget 所有的 数据条数的jquery目标对象(样式大清洗) target(给当前值添加样式)
		//例如 ：pageCo(pageSize,$('.bod-con-se3-page'),$('#pageSize p'));
    function pageCo(pageSize,allTarget,target){
    	if(pageSize<=10){
        	allTarget.removeClass('pagColor')
        	target.eq('1').addClass('pagColor')
        }else if(50>=pageSize>10){
        	allTarget.removeClass('pagColor')
        	target.eq('3').addClass('pagColor')
        }else if(100>=pageSize>50){
        	allTarget.removeClass('pagColor')
        	target.eq('5').addClass('pagColor')
        }
    }
  //保留小数点后两位四舍五入
function roundFun(value, n) {
  return Math.round(value*Math.pow(10,n))/Math.pow(10,n);
}
//自定义ajax提交=======

/*
*获取JSON长度
*@param jsonData 要判断的JSON对象
*/
function getJsonLength(jsonData){
    var jsonLength = 0;
    for(var item in jsonData){
        jsonLength++;
    }
    return jsonLength;
};

/*
* 函数：数组去重
* 用法：arr.unique
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
//刷新删除session里的状态值
		function session(){

			//工资发布记录
	    	sessionStorage.removeItem('selectYear');
	    	//发布详情
	    	sessionStorage.removeItem('searchContent');
	    	sessionStorage.removeItem('pageSize');
			//审批
	    	//统计
	    	sessionStorage.removeItem('stud_name');
	    	sessionStorage.removeItem('pay');
	    	sessionStorage.removeItem('clas_id');
	    	sessionStorage.removeItem('order_id');
	    	//收费列表
	    	sessionStorage.removeItem('currentPageNum');
	    	sessionStorage.removeItem('pageSize');
	    	sessionStorage.removeItem('starttime');
	    	sessionStorage.removeItem('endtime');
	    	sessionStorage.removeItem('status');
	    	sessionStorage.removeItem('item');
	    	sessionStorage.removeItem('clas_id');
	    	sessionStorage.removeItem('grade_id');
	    	//通知
	    	sessionStorage.removeItem('content');
	    	sessionStorage.removeItem('Name');
	    	//食谱日期
            sessionStorage.removeItem("nowCookDate");
	    }
	//点击获取验证码
	//获取短信验证码
	//点击状态  60s后可再次点击
		//btn获取验证码的点击事件按钮    admin:管理员Select框
	function Dx(btn,admin){
		var	TimeState = true;
		$(btn).click(function(){
			if(TimeState){
				var phone;
				var phoneF = $(admin).val();
				phone=phoneF.split(',')[1];
				getData(
				 	domain+'/esb/sendValidCodeWX',
					{"token":token,"udid":udid,"version":0,"phone":phone},
			     	function successfn(d){
			     		if(d.code == "200" && d.success == true){
			     			myAlert('短信发送成功',1,null);
			     			var countDown=30000;
							 var Time=setInterval(function(){
								countDown=countDown-1000;
				                 if(countDown>=1000){
				                	 $(btn).text(countDown/1000+"s后可发送").css({"color":"#fff","background":"#aaa9b1"});
				                	 TimeState = false;
								 }else{
									  $(btn).text("获取短信验证码").css({"color":"","background":""});
									clearInterval(Time);
									TimeState = true;
								 }
				   			 }, 1000);
			     		}else{
			     			var code = d.code;
			     			myAlert(d.message,2,code);
			     		};
			     	},'GET'
				);
			}
		});
	}
	//是否回执开关
	//checkSwitch true/false状态  //after  在input后插入的div //sure 	确定的时候的样式  //target 目标id
function Formswitch(After,sure,target){
	for(var i=0;i<$(target).length;i++){
		$(target)[i].checkSwitch = false;
	}
		if(After){
			$('input[type=checkbox]').after(After)
		}
		$('input[type=checkbox]').prop('checked',checkSwitch);
		$(target).click(function(){
			this.checkSwitch = !this.checkSwitch;
			$(this).prev('input[type=checkbox]').prop('checked',this.checkSwitch);
			this.checkSwitch?$(this).addClass(sure):$(this).removeClass(sure);
			return this.checkSwitch
		})

}
//修改职教科目
function modify(){
	var mapKC = JSON.parse(sessionStorage.getItem('mapKC'));
	console.log(mapKC)
	getData(
			 	domain+'/esb/api/org/getCoursByOrgId',
				{"token":token,"udid":udid,"version":0,"org_id":org_id},
		     	function successfn(d){
		     	if(d.code == "200" && d.success == true){
		     			var sub= [];
		     			var data = d.data;
		     			var html = '<div class="gui-form-item" style="padding-top: 30px;" id="Sub">'+
						    		'<label class="gui-form-label">职教科目  : </label>'+
						    		'<div class="gui-inline">'+
						    		'<div class="gui-input-block" style="border:none;">';
						    
						   var subje = JSON.parse(sessionStorage.getItem('sub'));
						    console.log(subje)
		     			data.forEach(function(e){
		     				/*if(mapKC.length){
		     					for(var i=0;i<mapKC.length;i++){
			     					if(mapKC[i].course_name==e.cour_name){
			     							html +=
						      			'<input type="checkbox" name="like[write]" title="'+e.cour_name+'" checked value="'+e.cour_id+'" style="display:none">'+
						      			'<div class="layui-unselect layui-form-checkbox SubCheck layui-form-checked" lay-skin=""><span>'+e.cour_name+'</span><i class="fa fa-check" style="margin-top: 4px;"></i></div>';
			     					}else{
			     						html +=
						      			'<input type="checkbox" name="like[write]" title="'+e.cour_name+'" checked value="'+e.cour_id+'" style="display:none">'+
						      			'<div class="layui-unselect layui-form-checkbox SubCheck" lay-skin=""><span>'+e.cour_name+'</span><i class="fa fa-check" style="margin-top: 4px;"></i></div>';
			     					}
			     				}
		     				}else*/ /*if(subje){
		     					//debugger
		     					for(var i=0;i<subje.length;i++){
			     					if(subje[i].name==e.cour_name){
			     						console.log(1)
			     							html +=
			     							'<input type="checkbox" name="like[write]" title="'+e.cour_name+'" value="'+e.cour_id+'" checked=checked>'+
						      			//'<input type="checkbox" checked name="like[write]" title="'+e.cour_name+'"  value="'+e.cour_id+'" >'+
						      			'<div class="layui-unselect layui-form-checkbox SubCheck layui-form-checked" lay-skin=""><span>'+e.cour_name+'</span><i class="fa fa-check" style="margin-top: 4px;"></i></div>';
			     					}else{
			     						console.log(1)
			     						html +=
						      			'<input type="checkbox" name="like[write]" title="'+e.cour_name+'" value="'+e.cour_id+'" >'+
						      			'<div class="layui-unselect layui-form-checkbox SubCheck" lay-skin=""><span>'+e.cour_name+'</span><i class="fa fa-check" style="margin-top: 4px;"></i></div>';
			     					}

			     				}
	     					}else{*/
	     					html +=
					      			'<input type="checkbox" name="like[write]" title="'+e.cour_name+'" checked value="'+e.cour_id+'" style="display:none">'+
					      			'<div class="layui-unselect layui-form-checkbox SubCheck '+e.cour_id+'" lay-skin=""><span>'+e.cour_name+'</span><i class="fa fa-check" style="margin-top: 4px;"></i></div>';

		     				//}

//		     				html +=
//					      			'<input type="checkbox" name="like[write]" title="'+e.cour_name+'" checked value="'+e.cour_id+'" style="display:none">'+
//					      			'<div class="layui-unselect layui-form-checkbox SubCheck" lay-skin=""><span>'+e.cour_name+'</span><i class="fa fa-check" style="margin-top: 4px;"></i></div>';
		     			})
		     			html+='</div></div></div>';
		     			//console.log(html)
		     			mydialogForm(html,"设置职教科目",null,['620px', '350px'], ["保存", "取消"],[function(y){
		     				//渲染开关 (保持加载最新的缓存数据)

		     				var subid = [];
		     				var subChecked=$("input[name='like[write]']").val();
		     				for(var i=0;i<$("input[name='like[write]']").length;i++){
		     					var subObj= {
		     						"id":'',
		     						"name":''
		     					};
		     					if($("input[name='like[write]']").eq(i).is(":checked")){
		     						var subChecked=$("input[name='like[write]']").eq(i).val();
		     						var subCheckedName=$("input[name='like[write]']").eq(i).attr('title');
		     						subObj.id = subChecked;
		     						subObj.name = subCheckedName;
		     						subid.push(subChecked);
		     						sub.push(subObj);
		     					}
		     				}
		     				subid=subid.join(',')
		     				getData(
							 	domain+'/esb/api/tech/updateTechCour',
								{"token":token,"udid":udid,"version":0,"org_id":org_id,"tech_id":Tech_id,"cour_ids":subid},
						     	function successfn(d){
						     		if(d.code == "200" && d.success == true){
						     			myAlert('科目设置完成',1,null);
						     			sessionStorage.setItem('sub',JSON.stringify(sub));
						     			//检查页面是否有$('#tochangeCourses')元素
						     			if($('#tochangeCourses').length>0){
					     					var changeStr ='<option value="">请选择学科</option>';
												var changesub = JSON.parse(sessionStorage.getItem('sub'));
												for(var i=0;i<changesub.length;i++){
													changeStr+='<option value="'+changesub[i].id+'">'+changesub[i].name+'</option>'
												};
												$('#tochangeCourses').html(changeStr);
						     			}
						     		}else{
						     			var code = d.code;
						     			myAlert(d.message,2,code);
						     		};
						     	},'GET'
							);
							layer.close(y);
		     			}])

		     			Formswitch("","layui-form-checked",".SubCheck");

		     			data.forEach(function(e){

		     				if(subje){
		     					
		     					for(var i=0;i<subje.length;i++){
			     					if(subje[i].name==e.cour_name){
			     						$("input[title="+e.cour_name+"]").prop('checked',"true");
			     						$("input[title="+e.cour_name+"]").next('div').addClass('layui-form-checked')
			     					}

			     				}
		     					}
		     			})
		     		}else{
		     			var code = d.code;
		     			myAlert(d.message,2,code);
		     		};
		     	},'GET'
			);
}
//图片加载不出来替换error图片
function nofind(){
	var img=event.srcElement;
	img.src="http://yun.5tree.cn/shijiwxy/weixin/images/error.gif";
	img.onerror=null; //控制不要一直跳动
}
//返回上一页
function goBack(){
	$('#back').click(function(){
		history.go(-1);
	})

}

//图片预览   放大缩小   拖拽
	function preview(){
		var Imgwidth = '';
		var maxWidth = '';
		//预览图片 (图片到浏览器边时的top/left值)
		var postop = '';
		var posleft = '';
		//浏览器的宽高
		var Height = $(window).height();
		var Width = $(window).width();
		//图片预览
		$('#imgBox').on('click','.Img',function(){

			$('.imgBox_pic img').css("max-width",'none');
			var src = $(this).attr('src');
			$('.imgBox_pic img').attr('src',src);
			$('.imgBox_pic img').css("max-height",Height);
			$('.imgBox_wrap').show();
			vertical();
			Imgwidth = $('.imgBox_pic img').css("width");
			Imgwidth=parseInt(Imgwidth.slice(0,-2));
			maxWidth= Imgwidth*4;
		});
		//兼容的滚轮缩小放大图片
		$("#newUserPopup").on("mousewheel DOMMouseScroll", function (e) {
		    var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) ||  // chrome & ie
		                (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1));              // firefox
		    if (delta > 0) {
		        // 向上滚
		        Imgwidth =  Imgwidth+80;
		        if(Imgwidth>maxWidth){
		        	Imgwidth=maxWidth;
		        }
		        var maxheight = $('.imgBox_pic img').css("max-height");
		        var height = $('.imgBox_pic img').css("height")
		        vertical();
		        if(height=maxheight){
		        	$('.imgBox_pic img').css("max-height",'');
		        }
		    } else if (delta < 0) {
		        // 向下滚
		        Imgwidth =  Imgwidth-80;
		        if(Imgwidth<100){
		        	Imgwidth=100;
		        }
		    };
		    $('.imgBox_pic img').css("width",Imgwidth);
		   	vertical();
		});
		//图片垂直水平居中函数
		function vertical(){
		 	var pictop = $('.imgBox_pic').css("height");
			var picleft = $('.imgBox_pic').css("width");
			pictop=parseInt(pictop.slice(0,-2))/2;
			picleft=parseInt(picleft.slice(0,-2))/2;
			postop = Height/2-pictop;
			posleft = Width/2-picleft;
			$('.imgBox_pic').css("top",postop);
			$('.imgBox_pic').css("left",posleft);
		}
		//点击拖动图片
		drag('.imgBox_pic')
		function drag(target){
			//1.元素上按下
			$(target).mousedown(function(ev){
				ev.preventDefault();
				var disX = ev.clientX-$(target).offset().left;
				var disY = ev.clientY-$(target).offset().top;
				//2.移动
				$(document).mousemove(function(ev){
					var l = ev.clientX-disX;
					var t = ev.clientY-disY;
					$(target).css("left",l+'px');
					$(target).css("top",t+'px');
				})
				//3.抬起
				$(document).mouseup(function(){
					$(document).unbind('mousemove');
					$(document).unbind('mouseup');
				})
			})
		}
		//点击关闭
		$('#close').click(function(){
			$('.imgBox_wrap').hide();
			Imgwidth='';
			$('.imgBox_pic img').css("width",'');
			$('.imgBox_pic').css("left",'');
			$('.imgBox_pic').css("top",'');
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
	fileCount = 8,
	// 添加的文件总大小
	fileSize = 0,
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
	// WebUploader实例
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
		swf: domain+'/shijiwxy/web/webuploader-0.1.5/Uploader.swf',
		// 文件接收服务端。
		server:domain+'/shijiwxy/admin/fileupload.htm',
		fileNumLimit:8,
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick: '#imgPicker',
		formData: {"token":token,"udid":udid,"org_id":org_id},
		// 只允许选择图片文件。
		accept: {
			title: 'Images',
			extensions: 'gif,jpg,jpeg,bmp,png',
			mimeTypes: 'image/jpg,image/jpeg,image/png' //修改这行
		},
		/* supportTransition = (function () {
		    var s = document.createElement('p').style,
		            r = 'transition' in s ||
		            'WebkitTransition' in s ||
		            'MozTransition' in s ||
		            'msTransition' in s ||
		            'OTransition' in s;
		    s = null;
		    return r;
		})(),*/
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
					console.info(file.rotation);
					break;

				case 2:
					file.rotation -= 90;
					break;
			}

			if(supportTransition) {
				deg = 'rotate(' + file.rotation + 'deg)';
				console.info($li.find('p.imgWrap'))
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
		imgId.push(res.id)
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

	$("#btn-imgupload").on('click', function() {
		if($(this).hasClass('disabled')) {
			return false;
		}
		uploader.upload();
		// if (state === 'ready') {
		//     uploader.upload();
		// } else if (state === 'paused') {
		//     uploader.upload();
		// } else if (state === 'uploading') {
		//     uploader.stop();
		// }
	});

}
//范围选择框（第一级展开函数）
function FIropen(){
	 $('body').off("click",'.gradelis');
	 $('body').on("click",'.gradelis',function (e) {




        //$('.gradelis').click(function (e) {
        if ($(e.target).is("label") || $(e.target).is("input")) {
            var obj = $(this).find("input");
            var type = obj.attr("data-id");

            if ($(obj).is(":checked")) {
                $("input[name='" + type + "']").prop("checked", "true");
                $("input[data-gid='" + type + "']").prop("checked", "true");
               // localAllClass[type].changed=true;
            } else {
                $("input[name='" + type + "']").removeAttr("checked");
                $("input[data-gid='" + type + "']").removeAttr("checked");
            };

            //刘后添加
            if($(this).find("input").is(":checked")){
                $(this).next('ul').find("input").prop("checked",true)
            }else{
                $(this).next('ul').find("input").prop("checked",false)
            }

        } else {
            if ($(this).hasClass("off")) {
                $(this).removeClass("off");
                if ($(this).next('ul').length) {
                    $(this).children('.fa-caret-right').removeClass('toDown');
                    $(this).next('ul').hide();
                    this.arrow = false;
                }

            } else {
                $(this).addClass("off");
                //刘后添加
                if($(this).find("input").is(":checked")){
                	$(this).next('ul').find("input").prop("checked",true)
                }else{
                    $(this).next('ul').find("input").prop("checked",false)
				}

                if ($(this).next('ul').length) {
                    $(this).children('.fa-caret-right').addClass('toDown');
                    $(this).next('ul').show();
                    this.arrow = true;
                };
            };
        };

         $(".dialog-classList .checkedboxp input[checked=checked]").prop("checked",true);

    });
}
//点击获取学生展开列表
function Studopen(){

	 //点击学生列表
    $('body').on('click',".studlis", function (event) {
    
        var $this = $(this).find('input');
        var classid = $this.attr("name");
        var gid = $this.attr("data-gnum");


        if ($this.is(':checked')) {

            $this.prop("checked", false);
            $("input[data-id=" + classid + "]").prop("checked", false);
            $(".checkedboxp input[data-gid=" + gid + "]").prop("checked", false);

        } else {
            $this.prop("checked", true);

            //如果点击的数量==
            if ($("input[name=" + classid + "]").length == $("input[name=" + classid + "]:checked").length) {
                $("input[data-id=" + classid + "]").prop("checked", true);

            };
             if($("input[name='" + gid + "']").length==$("input[name='" + gid + "']:checked").length){
                $("input[data-gid='" + gid + "']").prop("checked", "true");
            };
        };

    });
	 //获取学生展开列表
	 $('body').on("click",'.classlis',function (e) {

        // $('.classlis').click(function (e) {
        var ite = $(this);
        var clas_id = ite.attr('id');
        var grade_id = ite.find("input").attr("data-gid");
        var grade_num = ite.find("input").attr("data-gnum");


        if ($(e.target).is("label") || $(e.target).is("input")) {
            var obj = $(this).find("input");
            var type = obj.attr("data-id");

            if ($(obj).is(":checked")) {
                $("input[name='" + type + "']").prop("checked", "true");
                if($("input[name='" + grade_num + "']").length==$("input[name='" + grade_num + "']:checked").length){
                    $("input[data-id='" + grade_id + "']").prop("checked", "true");
                    //localAllClass[grade_id]["changed"]=true;
                };

            } else {
                $("input[name='" + type + "']").removeAttr("checked");

                //取消年级全选
                $("input[data-id='" + grade_id + "']").removeAttr("checked");
                //grade_id
            };
        }else {

            //判断是否选中
            if (ite.find("input").is(":checked")) {
                var checkedhtml = "checked=checked";
            } else {
                var checkedhtml = "";
            };
            //班级展开管理
            if (!ite.hasClass("off")) {
                //如果本地包含数据则从本地读取，否则从服务器获取
                if(getJsonLength(localAllClass[ite.find("input").attr("name")]["class"][clas_id]["student"])==0){
                    //获取班级学生
                    getData(domain + "/esb/api/student/getStudentsByCid", {"token": token, "udid": udid, "version": 0, "clas_id": clas_id},
                        function (d) {
                            if (d.code == 200 && d.success == true) {
                                if (d.data.length) {
                                    var data = d.data;
                                    var str = '<ul class="thir">';
                                    data.forEach(function (e) {
                                        str += '<li class="studlis" id=' + e.stud_id + '>' +
                                            '<i class="fa fa-user"></i>' +
                                            '<span>' + e.stud_name + '</span>' +
                                            '<input type="checkbox" name="' + clas_id + '"  data-gid="' + grade_id + '"  ' + checkedhtml + '   data-gnum="' + grade_num+ '"  class="checkedboxp" style="right:-10px">' +
                                            '</li>';

                                        localAllClass[ite.find("input").attr("name")]["class"][clas_id]["student"][e.stud_id] = {name: e.stud_name};
                                    });
                                    str += '</ul>';
                                    ite.after(str);
                                }

                                /*} else {
                                    alert('没有学生信息')
                                }*/
                            } else {
                            	var code = d.code;
                            	myAlert('学生信息加载失败',2,code);
                                $('#layer').modal('hide');
                            }
                        }, "GET");
            	};
                ite.next('ul').show();
                ite.children('.fa-caret-right').addClass('toDown');
                ite.addClass("off");
            } else {
                ite.removeClass("off");
                ite.next('ul').hide();
                ite.children('.fa-caret-right').removeClass('toDown');
            };
        };

    });
}
//回到顶部
	//gd:内容所在的元素
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
 * 翻页,写在获取列表类型接口回调中
 * 其中 必须使用以下类名及ID名称  .recordPages ---页码类名   .pagesMiddle --- 页码省略号类名  #prevPage --- 上一页按钮ID #nextPage --- 下一页按钮ID
 * @param {number} dataCount - 后台返回总条数
 * @param {number} pageSize - 每页显示条数
 * @param {number} pageNum - 当前页数
 */
function paging(dataCount,pageSize,pageNum){
	$(".recordPages").remove();//移除页码
	$(".pagesMiddle").remove();//移除...

	var pages = Math.ceil(dataCount/pageSize); //根据返回数据和每页显示多少条决定一共多少页
	var pagesHtml = '';
	var pageHtml = function (index){
		return '<li title="' + (index + 1) + '" class="gui-page-item recordPages" data-id="' + (index + 1) + '">' + (index + 1) + '</li>'
	}, midHtml = '<li title="..." class="gui-page-item pagesMiddle" >...</li>';
	var eachPages = function (start,end){
		for (var i = start;i < end;i++){
			pagesHtml += pageHtml(i);
		}
	}
	if(pages < 10){//总页数小于10,直接罗列1-9页码
		eachPages(0,pages);
	}else{
		eachPages(0,2);
		if(pageNum > 4 && pageNum < pages - 4){
			pagesHtml += midHtml;
			eachPages(pageNum-2,pageNum+2);
			pagesHtml += midHtml;
			eachPages(pages - 2 ,pages);
		}else if(pageNum < 6){
			eachPages(2,5);
			pagesHtml += midHtml;
			eachPages(pages - 2, pages);
		}else if(pageNum > pages-6){
			pagesHtml += midHtml;
			eachPages(pages - 6,pages);
		}
	}

	$("#prevPage").after(pagesHtml);//把页码列表添加到上一页后面
	$(".recordPages").each(function (){ //遍历所有页面改变当前页样式
		$(this).removeClass("nowPage");
		if($(this).text() == pageNum){
			$(this).addClass("nowPage");
		}
	})

	//按页数切换,上一页,下一页,首页,尾页等(请在所用之处再绑定点击事件,方便调你的翻页请求) 详情 请参考 #9页面代码
	// $("#prevPage").on("click", function () {
	// 	var prevPage = (+$(".nowPage").attr("data-id") - 1);
	// 	if (prevPage < 1) {
	// 		return false;
	// 	} else {
	// 		getPagesinfo(port, prevPage, pageSize);
	// 	}
	// })
	// $("#nextPage").on("click", function () {
	// 	var nextPage = (+$(".nowPage").attr("data-id") + 1);
	// 	if (nextPage > allNums) {
	// 		return false;
	// 	} else {
	// 		getPagesinfo(port, nextPage, pageSize);
	// 	}
	// })
	// $(".recordPages").on("click", function () {
	// 	var nowPage = +$(this).attr("data-id");
	// 	if ($(this).hasClass("nowPage")) {
	// 		return false;
	// 	} else {
	// 		$(".recordPages").each(function () {
	// 			$(this).removeClass("nowPage");
	// 		})
	// 		$(this).addClass("nowPage");
	// 		getPagesinfo(port, nowPage, pageSize);
	// 	}
	// })
}


//处理url地址获取图片的路径
function changeImgUrl(url){
    var imgName = url.split(".")[0];
    imgName = domain+"/esb/res/pic/" + Math.floor(+imgName / 10000) + "/" + Math.floor(+imgName / 100) + "/" + url;
    return imgName;
}



/*
* 函数：图片大图浏览
* 使用场景：通知详情/作业详情/校园通知/博客
* 使用方法：initPhotoSwipeFromDOM('.my-gallery'); zepto对象
*
*/
var initPhotoSwipeFromDOM = function (gallerySelector) {

    var parseThumbnailElements = function (el) {
        var thumbElements = el.childNodes,
            numNodes = thumbElements.length,
            items = [],
            figureEl,
            linkEl,
            size,
            item;

        for (var i = 0; i < numNodes; i++) {
            figureEl = thumbElements[i]; // <figure> element
            if (figureEl.nodeType !== 1) {
                continue;
            }
            linkEl = figureEl.children[0]; // <a> element
            size = linkEl.getAttribute('data-size').split('x');
            item = {
                src: linkEl.getAttribute('href'),
                w: parseInt(size[0], 10),
                h: parseInt(size[1], 10)
            };

            // console.info(item);
            if (figureEl.children.length > 1) {
                item.title = figureEl.children[1].innerHTML;
            }
            if (linkEl.children.length > 0) {
                item.msrc = linkEl.children[0].getAttribute('src');
            }
            item.el = figureEl; // save link to element for getThumbBoundsFn
            items.push(item);
        }
        return items;
    };

    // find nearest parent element
    var closest = function closest(el, fn) {
        return el && (fn(el) ? el : closest(el.parentNode, fn));
    };

    var onThumbnailsClick = function (e) {
        e = e || window.event;
        e.preventDefault ? e.preventDefault() : e.returnValue = false;
        var eTarget = e.target || e.srcElement;
        // find root element of slide
        var clickedListItem = closest(eTarget, function (el) {
            return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
        });
        if (!clickedListItem) {
            return;
        }
        var clickedGallery = clickedListItem.parentNode,
            childNodes = clickedListItem.parentNode.childNodes,
            numChildNodes = childNodes.length,
            nodeIndex = 0,
            index;
        for (var i = 0; i < numChildNodes; i++) {
            if (childNodes[i].nodeType !== 1) {
                continue;
            }
            if (childNodes[i] === clickedListItem) {
                index = nodeIndex;
                break;
            }
            nodeIndex++;
        }
        if (index >= 0) {
            openPhotoSwipe(index, clickedGallery);
        }
        return false;
    };

    // parse picture index and gallery index from URL (#&pid=1&gid=2)
    var photoswipeParseHash = function () {
        var hash = window.location.hash.substring(1),
            params = {};
        if (hash.length < 5) {
            return params;
        }
        var vars = hash.split('&');
        for (var i = 0; i < vars.length; i++) {
            if (!vars[i]) {
                continue;
            }
            var pair = vars[i].split('=');
            if (pair.length < 2) {
                continue;
            }
            params[pair[0]] = pair[1];
        }
        if (params.gid) {
            params.gid = parseInt(params.gid, 10);
        }
        return params;
    };

    var openPhotoSwipe = function (index, galleryElement, disableAnimation, fromURL) {
        var pswpElement = document.querySelectorAll('.pswp')[0],
            gallery,
            options,
            items;
        items = parseThumbnailElements(galleryElement);
        options = {
            galleryUID: galleryElement.getAttribute('data-pswp-uid'),
            getThumbBoundsFn: function (index) {
                var thumbnail = items[index].el.getElementsByTagName('img')[0], // find thumbnail
                    pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
                    rect = thumbnail.getBoundingClientRect();
                return {
                    x: rect.left,
                    y: rect.top + pageYScroll,
                    w: rect.width
                };
            },
            isClickableElement: function (el) {
                return true;
            }

        };
        if (fromURL) {
            if (options.galleryPIDs) {
                for (var j = 0; j < items.length; j++) {
                    if (items[j].pid == index) {
                        options.index = j;
                        break;
                    }
                }
            } else {
                options.index = parseInt(index, 10) - 1;
            }
        } else {
            options.index = parseInt(index, 10);
        }
        if (isNaN(options.index)) {
            return;
        }
        if (disableAnimation) {
            options.showAnimationDuration = 0;
        }
        gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items, options);
        gallery.init();
    };

    var galleryElements = document.querySelectorAll(gallerySelector);
    for (var i = 0, l = galleryElements.length; i < l; i++) {
        galleryElements[i].setAttribute('data-pswp-uid', i + 1);
        galleryElements[i].onclick = onThumbnailsClick;
    }
    var hashData = photoswipeParseHash();
    if (hashData.pid && hashData.gid) {
        openPhotoSwipe(hashData.pid, galleryElements[hashData.gid - 1], true, true);
    }
};

/*
 * 函数：获取照片的原始尺寸
 * 参数为dom对象
 * 返回宽高
 */

function getNaturalSize(Domlement) {
    var natureSize = {};
    //检测图片的宽度
    if (typeof Domlement.naturalWidth !== "undefined") {
        natureSize.width = Domlement.naturalWidth;
        natureSize.height = Domlement.naturalHeight;
    } else {
        var img = new Image();
        img.src = Domlement.src;
        natureSize.width = img.width;
        natureSize.height = img.height;
    }
    return natureSize;
}

/*
 *函数：判断图片是否完成并且获取原始尺寸
 */
function getWH(obj) {
    var natural = getNaturalSize(obj);
    $(obj).show();
    if ($(obj).next().length > 0) {
        $(obj).next().hide();
    }
    $(obj).parent().attr("data-size", natural.width + "x" + natural.height);
}
