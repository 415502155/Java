<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$("#role_name").blur(function(){
	if(""!=$("#role_name").val()&&null!=$("#role_name").val()){
		$.ajax({
			  type:"POST",
			  url:"../role/checkRoleName",
			  data: {
				  name:$("#role_name").val(),
				  id:"0"
			  },
			  success:function(data){
				  if(data.code==200){
					  $("#check_result").html("可以使用!");
					  $("#check_result").css("color","green");
				  }else{
					  $("#check_result").html("角色名已存在！");
					  $("#check_result").css("color","red");
				  }
			  }
		});
	}
});
function sub(){
	if($("#check_result").html()=="角色名已存在！"){
		return false;
	}
	$("#form").submit();
}
</script>
<div class="pageContent">
	<form id="form" method="post" action="../role/add" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div style="clear: both; padding: 5px;">
				<label>角色名称：</label>
				<input name="role_name" id="role_name" class="required" type="text" size="30" value=""/>
				<span id="check_result"></span>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>角色备注：</label>
				<input name="role_note" type="text" size="30" value=""/>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>所属组织：</label>
				<select class="combox" name="org_id">
					<option value="0">暂无</option>
					<c:forEach items="${orgs}" var="org">									
						<option value="${org.org_id}" >${org.org_name_cn}</option>									
					</c:forEach>
				</select>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>角色备注：</label>
				<select class="combox" name="rl_id">
					<option value="0">暂无</option>
					<c:forEach items="${rls}" var="rl">									
						<option value="${rl.rl_id}" >${rl.rl_name}</option>									
					</c:forEach>
				</select>
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