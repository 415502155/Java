﻿<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.input{
	width:240px
}
.selects{
	width:220px
}
.classes{
   appearance:none;
   -moz-appearance:none;
   -webkit-appearance:none;
   background-color: #fff;
   /*设置箭头*/
   background: url("") no-repeat scroll right center transparent;
   padding-right: 14px;
	width:82px
}
</style>

<div class="pageContent">
	<form id="form" method="post" action="../user/edit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" id="user_id" name="user_id" value="${user.user_id }"/>
			<input type="hidden" id="obj" value='${obj }'/>
			<input type="hidden" id="identity" value="${user.identity }"/>
			<input type="hidden" id="scampus" value="${cam_id }"/>
			<input type="hidden" id="sgrade" value="${student.grade_id }"/>
			<input type="hidden" id="sclasses" value="${student.clas_id }"/>
			<input type="hidden" id="delparentId" name="delparentId" value=""/>
			<input type="hidden" id="parentsInfo" name="parentsInfo" value=""/>
			<div style="clear: both; padding: 5px;">
				<label>登&thinsp;&thinsp;&thinsp;录&thinsp;&thinsp;&thinsp;名：</label>
				<input name="loginname" id="login_name" class="<c:if test="${user.identity != 2}" >required</c:if> input" type="text" size="30" value="${user.user_loginname}" readonly="readonly"/>
				<span id="check_result"></span>
			</div>
			<div style="clear: both; padding: 5px;position: relative;">
			    <c:choose>
			        <c:when test="${user.identity == 0}">
			            <label>家长姓名：</label>
			            <input name="parent_name" class="input" type="text" size="30" value="${parent.parent_name}" required>
			        </c:when>
			        <c:when test="${user.identity == 1}">
						<label>教师姓名：</label> 
						<input name="tech_name" class="input" type="text" size="30" value="${teacher.tech_name}" required>
					</c:when>
			        <c:otherwise>
			            <label>学生姓名：</label><input name="stud_name" class="input" type="text" size="30" value="${student.stud_name}" required> 
			        </c:otherwise>
			    </c:choose>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
				<input type="radio" name="gender" value="0" checked="checked">男
				<input type="radio" name="gender" value="1" <c:if test="${'1'==sex}" > checked="checked" </c:if>>女
			</div>
			<div style="clear: both; padding: 5px;">
				<label>所属组织：</label>
				<select  name="org_id" disabled="disabled">
					<option value="0">暂无</option>
					<c:forEach items="${orgs}" var="org">									
						<option value="${org.org_id}" <c:if test="${org.org_id==user.org_id}" >selected="selected"</c:if>>${org.org_name_cn}</option>									
					</c:forEach>
				</select>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>登录密码：</label>
				<input name="user_loginpass" type="text" size="30" value="" placeholder="需要修改时填写" class="input"/>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>连&thinsp;&thinsp;&thinsp;枝&thinsp;&thinsp;&thinsp;号：</label>
				<input name="user_number" type="text" size="30" value="${user.user_number}" class="input">
			</div>
			<div style="clear: both; padding: 5px;">
				<label>用户证件号码：</label>
				<input name="user_idnumber" type="text" size="30" value="${user.user_idnumber}" class="input">
			</div>
			<div style="clear: both; padding: 5px;">
				<label>用户邮箱：</label>
				<input name="user_mail" class="email input" type="text" size="30" value="${user.user_mail}"/>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>手机号码：</label>
				<input name="user_mobile" class='<c:if test="${user.identity != 2}" >required</c:if> number input' type="text" size="30" value="${user.user_mobile}"/>
			</div>
			<div id="students">
				<div style="clear: both; padding: 5px;">
					<label>年级班级：</label>
					<span id="student_classinfo">
					<select class="classes" name="campus" id="campus"></select>
					<select class="classes" name="grade" id="grade"></select>
					<select class="classes" name="classes" id="classes"></select>
					</span>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>家长信息：</label>
				</div>
				<div style="clear: both; padding: 5px;">
					<div class="tabsContent" style="height:160px;">
						<div class="tabs" currentindex="0" eventtype="click" style="width:100%">
							<div class="tabsHeader">
								<div class="tabsHeaderContent">
									<ul>
										<c:forEach items="${parents}" var="parent" varStatus="i">		
											<c:choose>
												<c:when test="${'0'==parent.relation}"><li id="li${parent.parent_id }" <c:if test="${i.index==0}"> class="selected" </c:if>><a href="javascript:;"><span>父亲</span></a></li></c:when>
												<c:when test="${'1'==parent.relation}"><li id="li${parent.parent_id }" <c:if test="${i.index==0}"> class="selected" </c:if>><a href="javascript:;"><span>母亲</span></a></li></c:when>
												<c:when test="${'2'==parent.relation}"><li id="li${parent.parent_id }" <c:if test="${i.index==0}"> class="selected" </c:if>><a href="javascript:;"><span>爷爷</span></a></li></c:when>
												<c:when test="${'3'==parent.relation}"><li id="li${parent.parent_id }" <c:if test="${i.index==0}"> class="selected" </c:if>><a href="javascript:;"><span>奶奶</span></a></li></c:when>
												<c:when test="${'4'==parent.relation}"><li id="li${parent.parent_id }" <c:if test="${i.index==0}"> class="selected" </c:if>><a href="javascript:;"><span>外公</span></a></li></c:when>
												<c:when test="${'5'==parent.relation}"><li id="li${parent.parent_id }" <c:if test="${i.index==0}"> class="selected" </c:if>><a href="javascript:;"><span>外婆</span></a></li></c:when>
												<c:when test="${'6'==parent.relation}"><li id="li${parent.parent_id }" <c:if test="${i.index==0}"> class="selected" </c:if>><a href="javascript:;"><span>其他</span></a></li></c:when>
												<c:otherwise><li id="li${parent.parent_id }" <c:if test="${i.index==0}"> class="selected" </c:if>><a href="javascript:;"><span>家长</span></a></li></c:otherwise>
											</c:choose>
										</c:forEach>
										<li class="" id="addParent"><a href="javascript:;"><span id="cl">+</span></a></li>
									</ul>
								</div>
							</div>
							<div class="tabsContent" style="height:120px;" id="parents">
								<c:forEach items="${parents}" var="parent"  varStatus="var">		
									<div inited="1000" style="display: block;" class="parents"  id="div${parent.parent_id }">
										<div style="clear: both; padding: 5px;">
								            <label>家长姓名：</label>
								            <input id="pname${parent.parent_id  }" class="input" type="text" size="30" value="${parent.parent_name}">
								            <input id="pid${parent.parent_id  }" type="hidden" value="${parent.parent_id}">
								            <button style="float:right" id="pdelete${parent.parent_id }" class="deleteParent" type="button">X</button>
										</div>
										<div style="clear: both; padding: 5px;">
											<label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
											<input value="0" type="radio" <c:if test="${'0'==parent.sex}" > checked="checked" </c:if> name="pgender${parent.parent_id }">男
											<input value="1" type="radio" <c:if test="${'1'==parent.sex}" > checked="checked" </c:if> name="pgender${parent.parent_id }">女
										</div>
										<div style="clear: both; padding: 5px;">
											<label>亲子关系：</label>
											<select class="combox selects" id="prelation${parent.parent_id }">
												<option value="0" <c:if test="${'0'==parent.relation}" > selected="selected" </c:if>>父亲</option>
												<option value="1" <c:if test="${'1'==parent.relation}" > selected="selected" </c:if>>母亲</option>
												<option value="2" <c:if test="${'2'==parent.relation}" > selected="selected" </c:if>>爷爷</option>
												<option value="3" <c:if test="${'3'==parent.relation}" > selected="selected" </c:if>>奶奶</option>
												<option value="4" <c:if test="${'4'==parent.relation}" > selected="selected" </c:if>>外公</option>
												<option value="5" <c:if test="${'5'==parent.relation}" > selected="selected" </c:if>>外婆</option>
												<option value="6" <c:if test="${'6'==parent.relation}" > selected="selected" </c:if>>其他</option>
											</select>
										</div>
										<div style="clear: both; padding: 5px;" class="toolBar">
											<label>手机号码：</label>
											<input id="oldmobile${parent.parent_id }" type="hidden" value="${parent.mobile}"/>
											<input id="pmobile${parent.parent_id }" name="user_mobile" class='required number input' type="text" size="30" value="${parent.mobile}" onkeyup="value=value.replace(/[^\d]/g,'')"/>
											<select class="combox" id="pmobiletype${parent.parent_id }">
												<option value="0" <c:if test="${'0'==parent.mobile_type}" > selected="selected" </c:if>>移动</option>
												<option value="1" <c:if test="${'1'==parent.mobile_type}" > selected="selected" </c:if>>联通</option>
												<option value="2" <c:if test="${'2'==parent.mobile_type}" > selected="selected" </c:if>>电信</option>
											</select>
										</div>
									</div>
								</c:forEach>
							</div>
							<div class="tabsFooter">
								<div class="tabsFooterContent"></div>
							</div>
						</div>
					</div>
				</div>
				
				<div style="clear: both; padding: 5px;">
					<div class="tabsContent" style="height:120px;">
						<div class="tabs" currentindex="0" eventtype="click" style="width:100%">
							<div class="tabsHeader">
								<div class="tabsHeaderContent" id="cardHeader">
									<ul>
										<c:if test="${student.cards== null || fn:length(student.cards) == 0}">
											<li id="cardli1" class="card"><a href="javascript:;"><span>卡1</span></a></li>
										</c:if>
										<c:forEach items="${student.cards}" var="card" varStatus="i">	
											<li id="cardli${i.count}" class="card"><a href="javascript:;"><span>卡${i.count}</span></a></li>											
										</c:forEach>
										<li class="" id="addCard"><a href="javascript:;"><span id="cardcl">+</span></a></li>
									</ul>
								</div>
							</div>
							<div class="tabsContent" style="height:60px;" id="cards">
								<c:if test="${student.cards== null || fn:length(student.cards) == 0}">
									<div inited="1000" style="display: block;" class="cards"  id="carddiv1">
										<div style="clear: both; padding: 5px;">
								            <label>考勤卡号：</label>
								            <input id="cardno1" name="card_no" class="input" type="text" size="30" value="">
								            <button style="float:right" id="cdelete1" class="deleteCard" type="button">X</button>
										</div>										
									</div>
								</c:if>
								<c:forEach items="${student.cards}" var="card"  varStatus="var">		
									<div inited="1000" style="display: block;" class="cards"  id="carddiv${var.count}">
										<div style="clear: both; padding: 5px;position: relative;">
								            <label>考勤卡号：</label>
								            <input id="cardno${var.count}" name="card_no" class="input" type="text" size="30"  maxlength="10" minlength="10" value="${card.card_no}">
								            <button style="float:right" id="cdelete${var.count}" class="deleteCard" type="button">X</button>
										</div>										
									</div>
								</c:forEach>
							</div>
							<div class="tabsFooter">
								<div class="tabsFooterContent"></div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>

			<!--
			<c:if test="${user.identity == 1}" >
				<div style="clear: both; padding: 5px;">
					<label>用户角色：</label>
					<div style="margin-left: 130px">
						<c:forEach items="${roles}" var="role">
							<label><input type="checkbox" name="roleIds" value="${role.role_id}">${role.role_name}</label>
						</c:forEach>
					</div>
					<div><span style="float:left;color:red;margin-left:130px" id="check_role"></span></div>
				</div>
				<c:forEach items="${ownRoles}" var="ownRole">
					<input type="hidden" name="ownRoles" value="${ownRole.role_id}">
				</c:forEach>
			</c:if>

			-->
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button onclick="sub(this,dialogAjaxDone)" type="button">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">

//【注意】使用中当parent_id等于0或处于个位数时会引发错误
var counter = 0;
var cardcounter = ${fn:length(student.cards)};
cardcounter = cardcounter>0?cardcounter:1;

function sub(dialogAjaxDone){
	if($("#identity").val()==2){
		var msg = getParentsInfo();
		if(""==msg){
			if($("#campus").val()==undefined){
				alert("该学生已毕业不能编辑")
			}else{
				$("#form").submit();
			}
			
		}else{
			alertMsg.error(msg, {
				okCall: function(){
					
				}
			});
		}
	}else{
		$("#form").submit();
	}
}
debugger
if($("#identity").val()==2){
	$("#students").show();
	
	var classes = JSON.parse($("#obj").val());
	var campus = '';
	var grade = '';
	var clas = '';
	 
	var scam=$("#scampus").val()
	if(scam!==""){
		rendClasses(scam);
	}else{
		$("#campus,#grade,#classes").remove();
		$("#student_classinfo").html("<span style=\"color:red\">该学生已毕业！</span>")
	}
	
	
	$("#campus").change(function(){
		campusChange($(this).val());
	})
	$("#grade").change(function(){
		gradeChange($(this).val());
	})
	function rendClasses(id){
		for(var c in classes){
			if(campus!='name'){
				campus += "<option value='"+c+"'>"+classes[c]['name']+"</option>";
			}
		}
		$("#campus").empty().append(campus);
		$("#campus").val(id);
		campusChange(id);
	}
	
	function campusChange(id){
		grade = "";
		for(var g in classes[id]){
			if(g!='name'){
				grade += "<option value='"+g+"'>"+classes[id][g]['name']+"</option>";
			}
		}
		$("#grade").empty().append(grade);
		$("#grade").val($("#sgrade").val());
		gradeChange($("#sgrade").val());
	}
	
	function gradeChange(id){
		var camp_id = $("#campus").val();
		var clas = "";
		var first = "";
		var current = $("#sclasses").val();
		for(var c in classes[camp_id][id]){
			if(c!='name'){
				clas += "<option value='"+c+"'>"+classes[camp_id][id][c]+"</option>";
				if(""==first){
					first=c;
				}
				if(c==current){
					first=c;
				}
			}
		}
		$("#classes").empty().append(clas);
		$("#classes").val($("#sclasses").val());
		if(""==$("#classes[selected=selected]").text()){
			$("#classes").val(first);
		}
	}
	$("#cl").unbind("click").click(function(){
		counter++
		var li = '<li class="selected new" id="li'+counter+'" ><a href="javascript:;"><span>新家长</span></a></li>';
		var temp = '<div inited="1000" style="display: block;"  class="parents newdiv"  id="div'+counter+'">'+
						'<div style="clear: both; padding: 5px;">'+
				            '<label>家长姓名：</label>'+
				            '<input id="pname'+counter+'" class="input" type="text" size="30" value="${parent.parent_name}">'+
				            '<input id="pid'+counter+'" type="hidden" value="">'+
				            '<button style="float:right" id="pdelete'+counter+'" class="deleteParent" type="button">X</button>'+
						'</div>'+
						'<div style="clear: both; padding: 5px;">'+
							'<label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>'+
							'<input value="0" type="radio" <c:if test="${\'0\'==parent.sex}" > checked="checked" </c:if> name="pgender'+counter+'">男'+
							'<input value="1" type="radio" <c:if test="${\'1\'==parent.sex}" > checked="checked" </c:if> name="pgender'+counter+'">女'+
						'</div>'+
						'<div style="clear: both; padding: 5px;">'+
							'<label>亲子关系：</label>'+
							'<select class="combox selects" id="prelation'+counter+'">'+
								'<option value="0" <c:if test="${\'0\'==parent.relation}" > selected="selected" </c:if>>父亲</option>'+
								'<option value="1" <c:if test="${\'1\'==parent.relation}" > selected="selected" </c:if>>母亲</option>'+
								'<option value="2" <c:if test="${\'2\'==parent.relation}" > selected="selected" </c:if>>爷爷</option>'+
								'<option value="3" <c:if test="${\'3\'==parent.relation}" > selected="selected" </c:if>>奶奶</option>'+
								'<option value="4" <c:if test="${\'4\'==parent.relation}" > selected="selected" </c:if>>外公</option>'+
								'<option value="5" <c:if test="${\'5\'==parent.relation}" > selected="selected" </c:if>>外婆</option>'+
								'<option value="6" <c:if test="${\'6\'==parent.relation}" > selected="selected" </c:if>>其他</option>'+
							'</select>'+
						'</div>'+
						'<div style="clear: both; padding: 5px;" class="toolBar">'+
							'<label>手机号码：</label>'+
							'<select class="combox" id="pmobiletype'+counter+'">'+
								'<option value="0" <c:if test="${\'0\'==parent.mobile_type}" > selected="selected" </c:if>>移动</option>'+
								'<option value="1" <c:if test="${\'1\'==parent.mobile_type}" > selected="selected" </c:if>>联通</option>'+
								'<option value="2" <c:if test="${\'2\'==parent.mobile_type}" > selected="selected" </c:if>>电信</option>'+
							'</select>'+
							'<input id="oldmobile'+counter+'" type="hidden" value=""/>'+
							'<input id="pmobile'+counter+'" name="user_mobile" class=\'required number input\' type="text" size="30" value="${parent.mobile}" onkeyup="value=value.replace(\/[^\\d]\/g,\'\')"/>'+
						'</div>'+
					'</div>';
		$("#addParent").before(li);
		$("#parents").append(temp);
		bindFun();
		setTimeout(function(){
		console.info(1)
			$("#li"+counter).click()
		},1);
	});
	
	$("#cardcl").unbind("click").click(function(){
		cardcounter++
		var li = '<li id="cardli'+cardcounter+'" class="card"><a href="javascript:;"><span>卡'+cardcounter+'</span></a></li>';
		var temp = '<div inited="1000" style="display: block;" class="cards"  id="carddiv'+cardcounter+'">'+
						'<div style="clear: both; padding: 5px;">'+
					        '<label>考勤卡号：</label>'+
					        '<input id="cardno'+cardcounter+'" name="card_no" class="input" type="text" size="30" value="">'+
					        '<button style="float:right" id="cdelete'+cardcounter+'" class="deleteCard" type="button">X</button>'+
						'</div>'+										
					'</div>';
		$("#addCard").before(li);
		$("#cards").append(temp);
		bindFun();
		setTimeout(function(){
		console.info(1)
			$("#cardli"+cardcounter).click()
		},1);
	});
	
	
	function bindFun(){
		$(".new").unbind("click").bind("click",function(){
			$(".tabsHeaderContent li").removeClass("selected");
			$(this).addClass("selected");
			var lis = $(".tabsHeaderContent li");
			var divs = $(".parents");
			for(var i=0;i<lis.length;i++){
				$(divs[i]).hide();
				if($(lis[i]).hasClass("selected")){
					$(divs[i]).show();
				}
			}
		});
		$(".card").unbind("click").bind("click",function(){
			$("#cardHeader li").removeClass("selected");
			$(this).addClass("selected");
			var lis = $("#cardHeader li");
			var divs = $(".cards");
			for(var i=0;i<lis.length;i++){
				$(divs[i]).hide();
				if($(lis[i]).hasClass("selected")){
					$(divs[i]).show();
				}
			}
		});
		$(".deleteParent").unbind("click").click(function(){
			var id = $(this).attr("id").substr(7);
			if(""!=$("#pid"+id).val()){
				$("#delparentId").val($("#delparentId").val()+","+$("#pid"+id).val());
			}
			if($("#li"+id).prevAll().length!=0){
				$("#li"+id).prev().trigger("click");
			}else{
				$("#li"+id).next().trigger("click");	
			}
			$("#li"+id).remove();
			$("#div"+id).remove();
		});
		$(".deleteCard").unbind("click").click(function(){
			var id = $(this).attr("id").substr(7);			
			if($("#cardli"+id).prevAll().length!=0){
				$("#cardli"+id).prev().trigger("click");
			}else{
				$("#cardli"+id).next().trigger("click");	
			}
			$("#cardli"+id).remove();
			$("#carddiv"+id).remove();
		})
	} 
	bindFun();
	
	function getParentsInfo(){
		var hasName = true;
		var hasMobile = true;
		var obj = [];
		$(".parents").each(function(){
			var p = {};
			var id=$(this).attr("id").substr(3);
			if($(this).hasClass("newdiv")){
				p.id="";
			}else{
				p.id=id;
			}
			p.oldmobile=$("#oldmobile"+id).val();
			p.name=$("#pname"+id).val();
			p.gender = $("input[name='pgender"+id+"']:checked").val();
			p.relation = $("#prelation"+id).val();
			p.mobile = $("#pmobile"+id).val();
			p.mobiletype = $("#pmobiletype"+id).val();
			if((null==p.name||""==p.name)&&(null==p.mobile||""==p.mobile)&&(null==p.gender||""==p.gender)){
			}else{
				obj.push(p);
				if(null==p.name||""==p.name){
					hasName = false;
				}
				if(null==p.mobile||""==p.mobile){
					hasMobile = false;
				}
			}
		});
		$("#parentsInfo").val(JSON.stringify(obj));
		if(hasMobile&&hasName){
			return "";
		}else if(hasMobile){
			return "家长手机号不能为空";
		}else if(hasName){
			return "家长姓名不能为空";
		}else{
			return "家长姓名和手机号不能为空";
		}
	}

}else{
	$("#students").hide();
}

</script>