﻿﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	/*公共样式*/
     a:hover{text-decoration:none;color:#fff}
	.fl{float:left}
	.fr{float:right}
	.clearfix{zoom:1}
	.clearfix:after{
		content:'';
		clear:both;
		display:block
	}
	/*公共样式end*/
	.content{width:100%}
	.cont-left{width:25%}
	.cont-right{width:75%}
	.cont-left p,.machine-id,.machine-type,.machine-place,.other{line-height:50px;}
	.formBar{
		position: absolute;
	    bottom: 10px;
	    right: 0;
	    width: 100%;
	}
</style>
<form id="addMachine" name="" method="post" action="" enctype="multipart/form-data" style="position:relative;height:100%;padding:0 100px">
	<input id="orgId" name="org_id" type="hidden" value="${orgId}">
	<div class="content clearfix">
		<div class="cont-left fl">
			<p class="machine-id">考勤机ID</p>
			<p class="machine-type">考勤机类型</p>
			<p class="machine-place">存放地点</p>
			<p class="other">备注</p>
		</div>
		<div class="cont-right fr">
			<div class="machine-id"><input id="machine_id" name="serial_number" type="text" placeholder="请输入ID"></div>
			<div class="machine-type">
				<label><input type="radio" name="type" value="0" checked> 近程门禁 </label>
				<label><input type="radio" name="type" value="1"> 近程门禁(带照片) </label>
				<label><input type="radio" name="type" value="2"> 远程门禁 </label>
				<label><input type="radio" name="type" value="3"> 远程门禁(带照片) </label>
			</div>
			<div class="machine-place">
				<label><input type="radio" name="place" value="校门口" checked> 校门口 </label>
				<label><input type="radio" name="place" value="宿舍门口"> 宿舍门口 </label>
				<label><input type="radio" name="place" value="其他"> 其他 <input id="others" type="text" name="" placeholder="请输入" style="opacity:0.3" disabled></label>
			</div>
			<div class="other">
				<textarea id="machine_remarks" class="" name="remarks" cols="50" rows="10" Maxlength="50"></textarea>
			</div>
		</div>
	</div>
	<div class="formBar">
	    <ul>
	        <li>
	            <div class="buttonActive">
	                <div class="buttonContent"><button id="" type="button" onclick="addSubmit()" >添加</button></div>
	            </div>
	        </li>
	        <li>
	            <div class="button">
	                <div class="buttonContent"><button type="button" class="close closes">取消</button></div>
	            </div>
	        </li>
	    </ul>
	</div>
</form>

<script>
	$(function (){
		$(".dialog")[0].style.zIndex = "1";
		$("[name='place']").on("click",function (){
			if($("[name='place']:checked").val()!=="其他"){
				$("#others").prop("disabled",true).css("opacity","0.3");	
			}else{
				$("#others").removeAttr("disabled").css("opacity","1");
			}
		})
		
	})
	function addSubmit(){
		var orgId = $("#orgId").val();
		var serial_number = $("#machine_id").val(),type=$("[name='type']:checked").val(),place=$("[name='place']:checked").val(),remarks=$("#machine_remarks").val()
		if(serial_number !== ''&& type !== '' && place !== ''){
			//$("#addMachine").ajaxSubmit({
			$.ajax({
				url: "<%=path%>/manage/attnMachineManage/addAttnMachine",
				type: "POST",
				data :{
					org_id : orgId,
					serial_number:serial_number,
					type:type,
					place:place != "其他"? place : $("#others").val(),
					remarks:remarks
				} ,
				async: false,
				type:"post",
	 			dataType:"json",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data.code == 200 && data.success == true){
						alert("添加成功!");
						//关闭当前窗口和考勤机列表窗口
						$(".closes").trigger("click");
						//再次打开考勤机列表窗口
						$("[attn-id="+ sessionStorage.attn_id +"]").trigger("click");
						sessionStorage.removeItem("attn_id");
					}else{
						alert(data.message);
					}
					
				},
				error: function(data) {
					alert("网络异常！");
				}
			});
		}else{
			alert("考勤机ID,类型,存放地点不能为空");
		}
	}
	
	
</script>