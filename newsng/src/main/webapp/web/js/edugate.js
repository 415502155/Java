/**
 * 处理navTab上的查询, 会重新载入当前navTab
 * @param {Object} form
 */
function navTabSearch(form, navTabId){
	var $form = $(form);	
	getHTML($(form).attr("action"),function (r) {
        $("#loadbox").html(r);
    },$form.serializeArray());
	
	return false;
}

/**
 * 普通ajax表单提交
 * @param {Object} form
 * @param {Object} callback
 * @param {String} confirmMsg 提示确认信息
 */
function validateCallback(form, callback, confirmMsg) {
	
	var $form = $(form);

	if (!$form.valid()) {
		return false;
	}
	
	var _submitFn = function(){
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || ajaxDone,
			error: function(){}
		});
	}
	
	if (confirmMsg) {
		myConfirm(confirmMsg,function(){
			_submitFn();
		})
	} else {
		_submitFn();
	}
	
	return false;
}
	
/**
 * 带文件上传的ajax表单提交
 * @param {Object} form
 * @param {Object} callback
 */
function iframeCallback(form, callback){
	var $form = $(form), $iframe = $("#callbackframe");
	if(!$form.valid()) {return false;}

	if ($iframe.size() == 0) {
		$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>").appendTo("body");
	}
	if(!form.ajax) {
		$form.append('<input type="hidden" name="ajax" value="1" />');
	}
	form.target = "callbackframe";
	
	_iframeResponse($iframe[0], callback || DWZ.ajaxDone);
}
function _iframeResponse(iframe, callback){
	var $iframe = $(iframe), $document = $(document);
	
	$document.trigger("ajaxStart");
	
	$iframe.bind("load", function(event){
		$iframe.unbind("load");
		$document.trigger("ajaxStop");
		
		if (iframe.src == "javascript:'%3Chtml%3E%3C/html%3E';" || // For Safari
			iframe.src == "javascript:'<html></html>';") { // For FF, IE
			return;
		}

		var doc = iframe.contentDocument || iframe.document;

		// fixing Opera 9.26,10.00
		if (doc.readyState && doc.readyState != 'complete') return; 
		// fixing Opera 9.64
		if (doc.body && doc.body.innerHTML == "false") return;
	   
		var response;
		
		if (doc.XMLDocument) {
			// response is a xml document Internet Explorer property
			response = doc.XMLDocument;
		} else if (doc.body){
			try{
				response = $iframe.contents().find("body").text();
				response = jQuery.parseJSON(response);
			} catch (e){ // response is html document or plain text
				response = doc.body.innerHTML;
			}
		} else {
			// response is a xml document
			response = doc;
		}
		
		callback(response);
	});
}

function ajaxDone(json){
	myAlert(json.message,"1")
}


/**
 * 处理navTab中的分页和排序
 * targetType: navTab 或 dialog
 * rel: 可选 用于局部刷新div id号
 * data: pagerForm参数 {pageNum:"n", numPerPage:"n", orderField:"xxx", orderDirection:""}
 * callback: 加载完成回调函数
 */
function eduPageBreak(options){
	var op = $.extend({ targetType:"navTab", rel:"", data:{pageNum:"", numPerPage:"", orderField:"", orderDirection:""}, callback:null}, options);
	var $parent = $("#loadbox");	
	var form = _getPagerForm($parent, op.data);
	var params = $(form).serializeArray();
	getHTML($(form).attr("action"),function (r) {
        $("#loadbox").html(r);
    },params);
	
}
	
/**
 * 
 * @param {Object} args {pageNum:"",numPerPage:"",orderField:"",orderDirection:""}
 * @param String formId 分页表单选择器，非必填项默认值是 "pagerForm"
 */
function _getPagerForm($parent, args) {
	var form = $("#pagerForm", $parent).get(0);
	var EDU={
		pageInfo: {pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}
	}
	if (form) {
		if (args["pageNum"]) form[EDU.pageInfo.pageNum].value = args["pageNum"];
		if (args["numPerPage"]) form[EDU.pageInfo.numPerPage].value = args["numPerPage"];
		if (args["orderField"]) form[EDU.pageInfo.orderField].value = args["orderField"];
		if (args["orderDirection"] && form[EDU.pageInfo.orderDirection]) form[DWZ.pageInfo.orderDirection].value = args["orderDirection"];
	}
	return form;
}

//加载不同应用页面
function getHTML(url,okCallback,data,ajaxmethod) {
    var tempbg = $('.loading-bg').attr('loading-id');
    var tempimg = $('.loadingimg').attr('loading-id');
    $.ajax({
        url : url,
        data : data,
        async : true,
        type:ajaxmethod,
        /*  dataType:'json',*/
        beforeSend:function(){
            //$.showPreloader(tipText);
            loadbgOpen()
        },
        success : function(result,textStatus,jqXHR) {
            if(jqXHR.status==200){
                //回调
                okCallback(result);
            };
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus)
        },
        complete: function() {
            loadbgOver(tempbg,tempimg);
            
        },
    });
};
