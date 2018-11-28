<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
%>
<c:set var="path" value="<%=path%>"></c:set>

<script type="text/javascript" src="<%=path%>/js/cityselect/jquery.cityselect.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
<style>
.clear { clear:both}

</style>

<div class="pageContent" style="margin-top: -13px">
    <form id="importUpgradeExcelForm" name="importUpgradeExcelForm" method="post" action="" enctype="multipart/form-data">
        <div class="pageFormContent" style="clear: both; padding:30px 0 100px 20px; overflow:auto;">
        	<p>请上传需要导入的EXCEL表格文件</p>
			<input type="file" id="uploadFile" onchange="checkType()" name="uploadFile">
            <div class="clear"></div>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent"><button type="button" onclick="doImprotExcel()">导入</button></div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent"><button type="button" class="close">取消</button></div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>

<script type='text/javascript'>
	function checkType(){
		 //得到上传文件的值
		 var fileName=document.getElementById("uploadFile").value;
		 //返回String对象中子字符串最后出现的位置.
		 var seat=fileName.lastIndexOf(".");
		 //返回位于String对象中指定位置的子字符串并转换为小写.
		 var extension=fileName.substring(seat).toLowerCase();
		 //判断允许上传的文件格式
		 //if(extension!=".jpg"&&extension!=".jpeg"&&extension!=".gif"&&extension!=".png"&&extension!=".bmp"){
		 //alert("不支持"+extension+"文件的上传!");
		 //return false;
		 //}else{
		 //return true;
		 //}
		 //var allowed=[".jpg",".gif",".png",".jpeg"];
		 var allowed=[".xls",".xlsm",".xltx",".xltm",".xlsb","xlam"];
		 for(var i=0;i<allowed.length;i++){
		   if(!(allowed[i]!=extension)){
		     return true;
		   }
		 }
		 alert("不支持"+extension+"格式,请上传正确的excel文件!");
		 $("#uploadFile").val('');//清空文件
		 throw new Error("不支持"+extension+"格式,请上传正确的excel文件!");
		 //return false;
	}
	
	function doImprotExcel() {
		var ajaxbg = $("#background,#progressBar");
		ajaxbg.show();
		$("#submit").html("提交中").attr("disabled", true);
		setTimeout(function(){
			if (!$("#importUpgradeExcelForm").valid()) {
				$("#submit").html("保存").removeAttr("disabled");
				ajaxbg.hide();
				
				return false;
			}
			
			$("#importUpgradeExcelForm").ajaxSubmit({
				url: "<%=path%>/manage/classUpgrade/doImportExcel",
				type: "POST",
				async: false,
				success: function(data) {
					$("#submit").html("保存").removeAttr("disabled");
					ajaxbg.hide();
					//dialogAjaxDone(data);
					if(data.success == true && data.code == 200){
						var resHtml = '',tableHtml='',tbodyHtml='',errMsg=''
							for(var i in data.data){
								//errMsg += i.replace(/[0-9]{3}\_/,'') + ':';
								errMsg = '';
								for(var k=0;k<data.data[i].length;k++){
									errMsg += data.data[i][k] + '\n'
								}
								tbodyHtml += '<tr>'+
												'<td>'+i.split("_")[0]+'</td>'+
												'<td>'+i.split("_")[1]+'</td>'+
												'<td><pre style="padding:10px;line-height:30px">'+errMsg+'</pre></td>'+
											'</tr>'
								
							}
							console.log(errMsg);
							tableHtml += '<table class="table msg-table" >'+
											'<thead>'+
												'<tr>'+
													'<th>学校ID</th>'+
													'<th>学校名称</th>'+
													'<th>反馈信息</th>'+
												'</tr>'+
											'</thead>'+
											'<tbody>'+
												'<tr>'+tbodyHtml+'</tr>'+
											'</tbody>'+
										'</table>'
							resHtml += '<div style="overflow:scroll;height:100%;width:100%;position:absolute;z-index:20000;background-color:#fff"  id="errorUpdateMsg">'+
										//'<pre style="text-align:left;padding:30px;line-height:30px">' + errMsg + '</pre>'+
										tableHtml + 
										'<button style="margin: 30px;display: block;padding: 10px 30px;background: none;border: none;background-color: #3f80ff;color: #fff;border-radius: 5px;" type="button" id="closeErrMsg">返回</button>'
										'</div>'
							
							
							$('[action="/esb/manage/classUpgrade/list"]').parents(".page").css({//防止导出成功后页面偏移bug
								"top":0,
								"left":0,
								"z-index":10001
							});
							$('[action="/esb/manage/classUpgrade/list"]').parents(".page").find("#pagerForm").after(resHtml);
							$('#closeErrMsg').on('click',function (){
								$($(".close")[$(".close").length-1]).trigger("click");
								$("#errorUpdateMsg").remove();
								//$(".pagination ul li.selected a").trigger("click");
								//$("#classSubmit").trigger("click");
							})
					}else{
						alert(data.message);
					}
					
					
				},
				error: function(data) {
					$("#submit").html("保存").removeAttr("disabled");
					ajaxbg.hide();
					//alertMsg.warn('您提交的数据有误，请检查后重新提交！');
				}
			});
		}, 1);
	}
	
</script>
