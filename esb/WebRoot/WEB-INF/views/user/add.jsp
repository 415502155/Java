﻿<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$("#login_name").blur(function(){
	if(""!=$("#login_name").val()&&null!=$("#login_name").val()){
		$.ajax({
			  type:"POST",
			  url:"../user/checkName",
			  data: {
				  name:$("#login_name").val(),
				  uid:"0"
			  },
			  success:function(data){
				  if(data.code==200){
					  $("#check_result").html("可以使用!");
					  $("#check_result").css("color","green");
				  }else{
					  $("#check_result").html("登录名已存在！");
					  $("#check_result").css("color","red");
				  }
			  }
		})
	}
})
$("#pwd").blur(function(){
	if($("#rePwd").val()!=""&&$("#pwd").val()!=$("#rePwd").val()){
		$("#rePwd_result").html("两次密码不一致！");
	}else{
		$("#rePwd_result").html("");
	}
})
$("#rePwd").blur(function(){
	if($("#pwd").val()!=""&&$("#rePwd").val()!=$("#pwd").val()){
		$("#rePwd_result").html("两次密码不一致！");
	}else{
		$("#rePwd_result").html("");
	}
})
function sub(){
	if($("#check_result").html()=="登录名已存在！"){
		return false;
	}
	if($("#rePwd_result").html()=="两次密码不一致！"){
		return false;
	}
	if($("input[type=checkbox][name=roleIds]:checked").length==0){
		$("#check_role").html("至少选择一个角色！");
		return false;
	}else{
		$("#check_role").html("");
	}
	$("#form").submit();
}
</script>

<div class="pageContent">
	<form id="form" method="post" action="../user/add" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">
			<div style="clear: both; padding: 5px;">
				<label>登&thinsp;&thinsp;&thinsp;录&thinsp;&thinsp;&thinsp;名：</label>
				<input id="login_name" name="login_name" class="required" type="text" size="30" value=""/>
				<span id="check_result"></span>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>登录密码：</label>
				<input id="pwd" name="login_pass" class="required" type="password" size="30" value=""/>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>确认密码：</label>
				<input id="rePwd" name="repass" type="password" class="required" size="30" value=""/>
				<span id="rePwd_result" style="color:red"></span>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>所属组织：</label>
				<select class="combox" name="org_id">
					<option value="0">暂无</option>
					<c:forEach items="${orgs}" var="org">									
						<option value="${org.org_id}">${org.org_name_cn}</option>									
					</c:forEach>
				</select>
			</div>				
			<div style="clear: both; padding: 5px;">
				<label>用户编号：</label>
				<label><input name="user_idnumber" type="text" size="30" value=""></label>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>用户邮箱：</label>
				<input name="user_mail" class="required email" type="text" size="30" value=""/>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>手机号码：</label>
				<input name="user_mobile" class="required number" type="text" size="30" value=""/>
			</div>			
			<div style="clear: both; padding: 5px;">
				<label>用户角色：</label>
				<div style="margin-left: 130px">
					<c:forEach items="${roles}" var="role">
						<label><input type="checkbox" name="roleIds" value="${role.role_id}">${role.role_name}</label>
					</c:forEach>
				</div>
				<div><span style="float:left;color:red;margin-left:130px" id="check_role"></span></div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button onclick="sub()" type="button">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>