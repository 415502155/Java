<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$("#right_name").blur(function(){
	if(""!=$("#right_name").val()&&null!=$("#right_name").val()){
		$.ajax({
			  type:"POST",
			  url:"../right/checkRightName",
			  data: {
				  name:$("#right_name").val(),
				  id:"0"
			  },
			  success:function(data){
				  if(data.code==200){
					  $("#check_result").html("可以使用!");
					  $("#check_result").css("color","green");
				  }else{
					  $("#check_result").html("权限名已存在！");
					  $("#check_result").css("color","red");
				  }
			  }
		});
	}
});
function sub(){
	if($("#check_result").html()=="权限名已存在！"){
		return false;
	}
	$("#form").submit();
}
</script>
<div class="pageContent">
	<form id="form" method="post" action="../role/rightEdit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input name="rid" type="hidden" value="${right.right_id }"/>
			<div style="clear: both; padding: 5px;">
				<label>权限名称：</label>
				<input name="right_name" id="right_name" class="required" type="text" size="30" value="${right.right_name }"/>
				<span id="check_result"></span>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>权限编码：</label>
				<input name="right_code" id="right_name" class="required" type="text" size="30" value="${right.right_code }"/>
				<span id="check_result"></span>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>权限备注：</label>
				<input name="right_note" type="text" size="30" value="${right.right_note }"/>
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