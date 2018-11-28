<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
%>
<c:set var="path" value="<%=path%>"></c:set>

<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
<style>
.clear { clear:both}

</style>

<div class="pageContent" style="margin-top: -13px">
    <form id="classesDegradeForm" name="classesDegradeForm" method="post" action="" enctype="multipart/form-data">
    	<div class="pageFormContent" style="clear: both; padding:30px 0 100px 20px; overflow:auto;">
	        <input type="text" placeholder="请输入机构ID" value="" name="org_id"><br><br><br>
	        <input type="text" placeholder="请输入班级ID，当前年级ID，降级年级ID" name="degradeClassesInfo">
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent"><button type="button" onclick="doClassesDegrade()">降级</button></div>
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
	
	function doClassesDegrade() {
		var ajaxbg = $("#background,#progressBar");
		ajaxbg.show();
		$("#submit").html("提交中").attr("disabled", true);
		setTimeout(function(){
			if (!$("#classesDegradeForm").valid()) {
				$("#submit").html("保存").removeAttr("disabled");
				ajaxbg.hide();
				
				return false;
			}
			
			$("#classesDegradeForm").ajaxSubmit({
				url: "<%=path%>/manage/classUpgrade/classesDegrade",
				type: "POST",
				async: false,
				success: function(data) {
					$("#submit").html("保存").removeAttr("disabled");
					ajaxbg.hide();
					if(data.success == true && data.code == 200){
						var resHtml = '',tableHtml='',tbodyHtml='',errMsg=''
							for(var i=0;i<data.data.length;i++){
								//errMsg += i.replace(/[0-9]{3}\_/,'') + ':';
								errMsg += data.data[i]+"\n";
								
								
							}
							tbodyHtml += '<tr>'+
								'<td><pre style="padding:10px;line-height:30px">'+errMsg+'</pre></td>'+
							'</tr>'
							console.log(errMsg);
							tableHtml += '<table class="table msg-table" >'+
											'<thead>'+
												'<tr>'+
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
							
							
							$('#classesDegradeForm').parents(".page").css({//防止导出成功后页面偏移bug
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
						alert(data.data);
					}
				},
				error: function(data) {
					$("#submit").html("保存").removeAttr("disabled");
					ajaxbg.hide();
					alert(data);
				}
			});
		}, 1);
	}
	
</script>
