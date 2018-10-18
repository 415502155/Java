/**
 * 班级操作函数
 */
$(function(){
	//初始化按钮组
	initBtns();
	//查询所有班级列表
	classUtils.searchClass();

});

/**
 * 初始化页面按钮
 */
function initBtns(){
	//初始化添加操作
	$("#add_class").off().on("click",function(){
		//添加班级
		classUtils.addClass();
	});
	//初始化查询操作
	$("#search_class").off().on("click",function(){
		classUtils.searchClass();
	});
}

/**
 * 班级操作函数
 */
var classUtils = {
	/**
	 * 添加班级操作
	 */
	addClass:function(){
		var $class = getClassForm();
		//添加操作
		if($class){
			$.ajax({
				type : "POST",
				url :"/class/addClass",
				dataType:"JSON",  
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify($class),//将对象序列化成JSON字符串  
				success : function(data) {
					if(data){
						//清空输入框
						$("input[name='class_name']").val("");
						classUtils.searchClass();
						alert("添加成功");
					}else{
						alert("添加失败，请重新添加");
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("添加失败，请重新添加");
				}
			});
		}
	},
	/**
	 * 查询所有班级
	 */
	searchClass:function(){
		$.ajax({
			type : "GET",
			url :"/class/searchAllClass",
			dataType:"JSON",  
			data: {},
			success : function(data) {
				classUtils.cloneStudentList(data);
				//查询未添加班级的学生
				classUtils.searchNoCheckedClassStudent(data);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("查询失败，请联系管理员");
			}
		});
	},
	/**
	 * 克隆当前班级列表
	 * @param data
	 */
	cloneStudentList:function(data){
		//克隆前先移除列表
		$("#class_list_template").nextAll().remove();
		//判断当前是否有学生
		if(data.length > 0){
			$.each(data,function(index,item){
				var $class_clone = $("#class_list_template").clone();
				$class_clone.show();
				$class_clone.attr("id","class_"+item.id);
				$class_clone.find("span:eq(0)").text(item.id);
				$class_clone.find("span:eq(1)").text(item.name);
				$("#class_list_container").append($class_clone);
			});
			//编辑和删除按钮绑定事件
			$(".edit").off().on("click",function(){
				classUtils.editClass(this);
			});
			$(".delete").off().on("click",function(){
				classUtils.deleteClass(this);
			});
		}else{
			alert("当前没有班级，请先添加班级");
		}
	},
	/**
	 * 修改班级名称
	 */
	editClass:function(obj){
		//获取当前点击所在行的班级id
		var $c_Id = $(obj).parent().attr("id").replace("class_","");
		//班级名称置为可编辑状态
		var $class_name = $(obj).prev().text();
		$(obj).prev().remove();
		$(obj).before("<input type='text' name='class_temp' value='"+$class_name+"'/>");
		$(obj).val("保存").off().one("click",function(){
			var $class = new Object();
			$class.id = $c_Id;
			$class.name = $(obj).prev().val();
			$.ajax({
				type : "POST",
				url :"/class/editClass",
				dataType:"JSON",  
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify($class),
				success : function(data) {
					alert("修改成功");
					classUtils.searchClass();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("修改失败，请联系管理员");
				}
			});
		});
	},
	/**
	 * 删除班级
	 */
	deleteClass:function(obj){
		//获取当前点击所在行的班级id
		var $c_Id = $(obj).parent().attr("id").replace("class_","");;
		$.ajax({
			type : "GET",
			url :"/class/deleteClass",
			dataType:"JSON",  
			data: {
				cId:$c_Id
			},
			success : function(data) {
				if(data){
					alert("删除成功");
					classUtils.searchClass();
				}else{
					alert("当前班级下存在关联学生，请勿删除");
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("删除失败，请联系管理员");
			}
		});
	},
	/**
	 * 查询所有未添加班级的学生列表
	 */
	searchNoCheckedClassStudent:function(classList){
		$.ajax({
			type : "GET",
			url :"/class/searchNoCheckedClassStudent",
			dataType:"JSON",  
			data: {},
			success : function(data) {
				//克隆列表
				classUtils.cloneNoCheckedClassStudent(data,classList);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("查询未添加班级的学生列表失败，请联系管理员");
			}
		});
	},
	/**
	 * 克隆当前学生列表
	 * @param data
	 */
	cloneNoCheckedClassStudent:function(data,classList){
		$("#student_list_template").nextAll().remove();
		//判断当前是否有学生
		if(data.length > 0){
			$.each(data,function(index,item){
				var $student_clone = $("#student_list_template").clone();
				$student_clone.show();
				$student_clone.attr("id","student_"+item.id);
				$student_clone.find("span:eq(0)").text(item.id);
				$student_clone.find("span:eq(1)").text(item.name);
				$student_clone.find("select").attr("id","check_class_"+item.id)
				$("#student_list_container").append($student_clone);
				$.each(classList,function(i,it){
					$("#check_class_"+item.id).append("<option value='"+ it.id +"'>"+ it.name +"</option>");
				});
			});
			//保存按钮绑定事件
			$(".add_class").off().on("click",function(){
				var $student = new Object();
				//获取所在行的行号
				var $stu_Id = $(this).prev().attr("id").replace("check_class_","");
				//所选的班级班号
				$student.id = $stu_Id;
				$student.cId = $("#check_class_"+$stu_Id +" option:selected").val();
				
				$.ajax({
					type : "POST",
					url :"/class/updateStudentClass",
					dataType:"JSON",  
					contentType: "application/json;charset=utf-8",
					data: JSON.stringify($student),
					success : function(data) {
						alert("修改成功");
						//重新加载未保存班级的学生列表
						classUtils.searchNoCheckedClassStudent(classList);
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						alert("保存失败，请联系管理员");
					}
				});
			});
			
		}
	},
}
/**
 * 添加班级封装班级对象
 */
function getClassForm(){
	var $class_name = $("input[name='class_name']").val();
	if($class_name != "" && $class_name != null && $class_name != undefined){
		var $class = new Object();
		$class.name = $class_name;
		return $class;
	}else{
		alert("请输入班级名称");
		return false;
	}
}

/**
 * 跳转班级操作
 */
function returnStudentHandle(){
	window.location.href="/student/index";
}


