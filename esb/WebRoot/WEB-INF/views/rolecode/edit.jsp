<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
	li{ display:inline !important;}
</style>

<script type="text/javascript">
function sub(){
	var url = '';
	var data = {};
	if(null==$("#name").val()||""==$("#name").val()){
		$("#check_name").html("名称不能为空！");
	  	$("#check_name").css("color","red");
		return false;
	}else{
		$("#check_name").html("");
	}
	if((null!=$("#fun_url").val()&&""!=$("#fun_url").val())&&(null==$("#fun_version").val()||""==$("#fun_version").val())){
		$("#check_fun_version").html("版本号不能为空！");
	  	$("#check_fun_version").css("color","red");
		return false;
	}else{
		$("#check_fun_version").html("");
	}
	if((""!=$("#fun_id").val()&&null!=$("#fun_id").val())){
		if(""==$("#fun_url").val()||null==$("#fun_url").val()){//功能
			$("#check_fun_url").html("功能地址不能为空！");
		  	$("#check_fun_url").css("color","red");
			return false;
		}
	}
	if((""!=$("#fun_url").val()&&null!=$("#fun_url").val())){//功能
		$("#category").val($("#fcategory").val());
		//上级模块
		if(null==$(".selector").val()||""==$(".selector").val()){
			$("#check_module_id").html("请选择上级模块！");
		  	$("#check_module_id").css("color","red");
			return false;
		}
		//功能名称[编辑]
		if(null!=$("#oldName").val()&&""!=$("#oldName").val()&&
		   null!=$("#oldVersion").val()&&""!=$("#oldVersion").val()){
			if($("#oldVersion").val()!=$("#fun_version").val() || $("#oldName").val()!=$("#name").val()){
				url = "../function/checkName";
				data = {name:$("#name").val(),version:$("#fun_version").val(),type:"function"};
			}else{
				url = '';
			}
		//功能名称[新增]
		}else{
			url = "../function/checkName";
			data = {name:$("#name").val(),version:$("#fun_version").val(),type:"function"};
		}
		//功能范围
		if($("input[type=checkbox][name=grade_number]:checked").length==0){
			$("#check_grade").html("至少选择一个适用范围！");
		  	$("#check_grade").css("color","red");
			return false;
		}else{
			$("#check_grade").html("");
		}
		//功能状态
		$("#fun_status").val($("input:radio[name=funStatus]:checked").val());
		//功能上级
		$("#mod_id").val($("#module_id").val());
	}else{//模块
		$("#category").val($("#mcategory").val());
		//模块名称[编辑]
		if(null!=$("#oldName").val()&&""!=$("#oldName").val()){
			if($("#oldName").val()!=$("#name").val()){
				url = "../function/checkName";
				data = {name:$("#name").val(),type:"module",version:""};
			}else{
				url = '';
			}
		//模块名称[新增]
		}else{
			url = "../function/checkName";
			data = {name:$("#name").val(),type:"module",version:""};
		}
	}
	if(url!=''){//模块
		$.ajax({
			type:"POST",
			url:url,
			data: data,
			success:function(data){
				if(data.code==200){
					$("#check_name").html("可以使用!");
					$("#check_name").css("color","green");
					$("#form").submit();
				}else if(data.code==203){
					$("#check_name").html("功能名称和版本号已存在！");
					$("#check_name").css("color","red");
					return false;
				}else if(data.code==205){
					$("#check_name").html("模块名称已存在！");
					$("#check_name").css("color","red");
					return false;
				}else{
					console.error("名称校验返回结果异常");
					return false;
				}
			}
		});
	}else{
		$("#form").submit();
	}
}
$(".selector").change(function(){
	$("#check_module_id").html("");
});
</script>

<div class="pageContent">
	<form id="form" method="post" action="../function/${action }" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" id="mod_id" name="mod_id" value="${mod_id }"/>
			<input type="hidden" id="fun_id" name="fun_id" value="${func.fun_id }"/>
			<input type="hidden" id="showModule" name="showModule" value="${showModule }"/>
			<input type="hidden" id="showFunction" name="showFunction" value="${showFunction }"/>
			<input type="hidden" id="oldName" name="oldName" value="${func.fun_name }"/>
			<input type="hidden" id="oldVersion" name="oldVersion" value="${func.fun_version }"/>
			<input type="hidden" id="oldVersion" name="oldVersion" value="${func.fun_version }"/>
			<input type="hidden" name="category" id="category"/>
			<div style="clear: both; padding: 5px;">
				<label>名称：</label>
				<input name="fname" id="name" style="width:60%" class="required" type="text" size="30" value="${func.fun_name }"/>
				<span id="check_name"></span>
			</div>
			<div class="unit">
				<label>LOGO图片：</label>
                <!--  <img id="logo_preview" name="" src="${func.logo}" >-->
                <label>${func.logo}</label><br><br>
                <input style="margin-left: 136px;" id="logo_upload" name="logo_upload" type="file" />
            </div>
			<div id="url_div" style="clear: both; padding: 5px;">
				<label>地址：</label>
				<textarea name="fun_url" id="fun_url" class="url" style="width:60%;height:50px">${func.fun_url }</textarea>
				<span id="check_fun_url"></span>
			</div>
			<div id="mod_div">
				<div style="clear: both; padding: 5px;">
					<label>分类分组：</label>
					<select id="mcategory" class="selector" style="width:61%">
						<c:forEach var="c" items="${category}">
							<option value="${c.value }" <c:if test="${module.category==c.value}">selected="selected"</c:if>>${c.key}</option>
						</c:forEach>
					</select>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>模块编码：</label>
					<input name="mod_code" id="mod_code" style="width:60%" class="required" type="text" size="30" value="${module.mod_code }"/>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>模块顺序：</label>
					<input name="mod_order" id="mod_order" style="width:60%" class="required number" type="text" size="30" value="${module.mod_order }"/>
				</div>
			</div>
			<div id="fun_div" style="display:none">
				<div style="clear: both; padding: 5px;">
					<label>分类分组：</label>
					<select id="fcategory" class="selector" style="width:61%">
						<c:forEach var="c" items="${category}">
							<option value="${c.value }" <c:if test="${func.category==c.value}">selected="selected"</c:if>>${c.key}</option>
						</c:forEach>
					</select>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>功能编码：</label>
					<input name="fun_code" id="fun_code" style="width:60%" class="sjwy" type="text" size="30" value="${func.fun_code }"/>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>功能顺序：</label>
					<input name="fun_order" id="fun_order" style="width:60%" class="sjwy number" type="text" size="30" value="${func.fun_order }"/>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>当前版本：</label>
					<input name="fun_version" id="fun_version" style="width:60%;" class="sjwy" type="text" size="30" value="${func.fun_version }"/>
					<span id="check_fun_version"></span>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>上级名称：</label>
					<select name="module_id" id="module_id" class="selector" style="width:61%">
						<option value=""></option>
						<c:forEach var="module" items="${modules}">
							<option value="${module.mod_id}" <c:if test="${mod_id==module.mod_id}">selected="selected"</c:if>>${module.mod_name}</option>
						</c:forEach>	
					</select>
					<span id="check_module_id"></span>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>当前状态：</label>
					<input id="fun_status" name="fun_status" type="hidden" value="${func.fun_status }"/>
					<label><input name="funStatus" value="1" type="radio" <c:if test="${func.fun_status==1||func.fun_status==null}">checked="true"</c:if>/>正常</label>
					<label><input name="funStatus" value="2" type="radio" <c:if test="${func.fun_status==2}">checked="true"</c:if>/>维护</label>
					<label><input name="funStatus" value="3" type="radio" <c:if test="${func.fun_status==3}">checked="true"</c:if>/>过期</label>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>适用范围：</label>
					<input type="hidden" id="gradeNumber" value="${gradeNumber }">
					<div class="page unitBox" style="overflow: auto;height:auto;width:60%;border:solid 1px #CCC;background:#FFF;max-height: 285px">
						<ul class="tree treeFolder treeCheck collapse">
							<li><a tname="grade_number" id="gn1" tvalue="1">学校</a> 
								<ul>
									<li><a tname="grade_number" id="gn10" tvalue="10">幼儿园</a>
										<ul>
											<li><a style="color:orange" tname="grade_number" id="gn11" tvalue="11">小小班</a></li>    
											<li><a tname="grade_number" id="gn12" tvalue="12">小班</a></li>    
											<li><a tname="grade_number" id="gn13" tvalue="13">中班</a></li>    
											<li><a tname="grade_number" id="gn14" tvalue="14">大班</a></li>    
											<li><a style="color:orange" tname="grade_number" id="gn15" tvalue="15">学前班</a></li>   
										</ul>
									</li>
									<li><a tname="grade_number" id="gn20" tvalue="20">小学</a>
										<ul>
											<li><a tname="grade_number" id="gn21" tvalue="21">一年级</a></li>    
											<li><a tname="grade_number" id="gn22" tvalue="22">二年级</a></li>    
											<li><a tname="grade_number" id="gn23" tvalue="23">三年级</a></li>    
											<li><a tname="grade_number" id="gn24" tvalue="24">四年级</a></li>    
											<li><a tname="grade_number" id="gn25" tvalue="25">五年级</a></li>    
											<li><a tname="grade_number" id="gn26" tvalue="26">六年级</a></li>    
										</ul> 
									</li>
									<li><a tname="grade_number" id="gn30" tvalue="30">初中</a>   
										<ul>    
											<li><a tname="grade_number" id="gn31" tvalue="31">六年级(仅供部分学校使用)</a></li>    
											<li><a tname="grade_number" id="gn32" tvalue="32">七年级</a></li>    
											<li><a tname="grade_number" id="gn33" tvalue="33">八年级</a></li>    
											<li><a tname="grade_number" id="gn34" tvalue="34">九年级</a></li>    
										</ul> 
									</li> 
									<li><a tname="grade_number" id="gn40" tvalue="40">高中</a>   
										<ul>    
											<li><a tname="grade_number" id="gn41" tvalue="41">高一</a></li>    
											<li><a tname="grade_number" id="gn42" tvalue="42">高二</a></li>    
											<li><a tname="grade_number" id="gn43" tvalue="43">高三</a></li>    
										</ul> 
									</li> 
								</ul>
							</li>
							<li><a tname="grade_number" id="gn2" tvalue="2">培训机构</a></li>
							<li><a tname="grade_number" id="gn3" tvalue="3">教育局</a></li>
						</ul>
					</div>
					<div><span style="float:left;color:red;margin-left:130px" id="check_grade"></span></div>
				</div>
			</div>
			<div style="clear: both; padding: 5px;">
				<label>备注：</label>
				<textarea name="note" style="width:60%;height:50px">${func.fun_note }</textarea>
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
<script type="text/javascript">
	$(document).ready(function(){
		if($("#showModule").val()=="true"){
			$("#url_div").hide();
			$("#fun_div").hide();
			$("#fun_div .required").attr("class","sjwy");
			$("#mod_div").show();
			$("#mod_div .sjwy").attr("class","required");
			$("#fun_url").removeClass("required").removeClass("error");
		}else if($("#showFunction").val()=="true"){
			$("#fun_div").show();
			$("#fun_div .sjwy").attr("class","required");
			$("#mod_div").hide();
			$("#mod_div .required").attr("class","sjwy");
			$("#fun_url").addClass("required");
		}else{
			$("#fun_url").blur(function(){
				var url = $("#fun_url").val();
				if(""!=url&&null!=url){
					$("#fun_url").addClass("required");
					$("#fun_div").show();
					$("#fun_div .sjwy").attr("class",$("#fun_div .sjwy").attr("class").replace("sjwy","required"));
					$("#mod_div").hide();
					$("#mod_div .required").attr("class",$("#mod_div .required").attr("class").replace("required","sjwy"));
				}else{
					$("#fun_url").removeClass("required").removeClass("error");
					$("#fun_div").hide();
					$("#fun_div .required").attr("class",$("#fun_div .required").attr("class").replace("required","sjwy"));
					$("#mod_div").show();
					$("#mod_div .sjwy").attr("class",$("#mod_div .sjwy").attr("class").replace("sjwy","required"))
				}
			});
		}
		var count = 1;
		var gradeNumberRender=setInterval(function(){
			if(null!=$("#gradeNumber").val()&&""!=$("#gradeNumber").val()){
				clearInterval(gradeNumberRender);
				var gradeNumber = $("#gradeNumber").val().split(",");
				$(".first_expandable").trigger("click");
				$(".expandable").trigger("click");
				$(".last_expandable").trigger("click");
				for (var i = 0; i < gradeNumber.length; i++) {
					$("#gn"+gradeNumber[i]).trigger("click");
				}
				$(".checked").unbind("click").bind("click",function(){
					alertMsg.warn("适用范围只能扩充，不能缩减！");
					//alert("适用范围只能扩充，不能缩减！")
				})
			}else{
				count++;
			}
			if(count>500){
				clearInterval(gradeNumberRender);
			}
		},10);
		

		$("#logo_upload").click(function() {
		    $("#logo_upload").on("change", function() {
		        var objUrl = getObjectURL(this.files[0]); //获取图片的路径，该路径不是图片在本地的路径
		        if (objUrl) {
		            $("#logo_preview").attr("src", objUrl); //将图片路径存入src中，显示出图片
		        }
		    });
		});

		$("#bg_upload").click(function() {
		    $("#bg_upload").on("change", function() {
		    	$("#bg_preview").empty();
		    	for (var i=0;i < this.files.length; i++) {
		    		var objUrl = getObjectURL(this.files[i]); //获取图片的路径，该路径不是图片在本地的路径
			        if (objUrl) {
			            $("#bg_preview").append("<img name=\"\" src=\""+objUrl+"\" />"); //将图片路径存入src中，显示出图片
			        }
		    	}
		    });
		});

	});
	

	// 图片预览用
	function getObjectURL(file) {
	    var url = null;
	    if (window.createObjectURL != undefined) { // basic
	        url = window.createObjectURL(file);
	    } else if (window.URL != undefined) { // mozilla(firefox)
	        url = window.URL.createObjectURL(file);
	    } else if (window.webkitURL != undefined) { // webkit or chrome
	        url = window.webkitURL.createObjectURL(file);
	    }
	    return url;
	}
	
</script>
