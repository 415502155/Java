/**
 * 学生操作函数
 */
$(function(){
	//初始化按钮组
	initBtns();
	//初始化学生列表
	studentUtils.searchStudent(0);//0为查询所有学生列表
	//初始化班级下拉框
	studentUtils.initClassCheckBox();
});

/**
 * 初始化页面按钮
 */
function initBtns(){
	$("#add_student").off().on("click",function(){
		studentUtils.addStudent();
	});
	//根据班级查询学生
	$("#search_student_class").off().on("click",function(){
		var $cId = $("#check_class_search option:selected").val();
		studentUtils.searchStudent($cId);
	});
	//根据姓名查询学生
	$("#search_student_name").off().on("click",function(){
		studentUtils.searchStudentByName();
	});
	
}
var studentUtils = {
	/**
	 * 添加学生
	 */
	addStudent:function(){
		var student = getStudentForm();
		//添加学生
		$.ajax({
			type : "POST",
			url :"/student/addStudent",
			dataType:"JSON",  
			contentType: "application/json;charset=utf-8",
			data: JSON.stringify(student),//将对象序列化成JSON字符串  
			success : function(data) {
				if(data){
					alert("添加成功");
					//清空文本框的内容
					$("input[name='student_name']").val("");
					studentUtils.searchStudent(0);
				}else{
					alert("添加失败，请重新添加");
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("添加失败，请重新添加");
			}
		});
	},
	/**
	 * 查询学生
	 */
	searchStudent:function(cId){
		var url = "/student/searchAllStudent";
		$.ajax({
			type : "GET",
			url :url,
			dataType:"JSON",  
			data: {
				cId:cId
			},
			success : function(data) {
				studentUtils.cloneStudentList(data);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("查询失败，请联系管理员");
			}
		});
	},
	/**
	 * 克隆当前学生列表
	 * @param data
	 */
	cloneStudentList:function(data){
		$("#student_list_template").nextAll().remove();
		//判断当前是否有学生
		if(data.length > 0){
			$.each(data,function(index,item){
				var $student_clone = $("#student_list_template").clone();
				$student_clone.show();
				$student_clone.attr("id","student_"+item.id);
				$student_clone.find("span:eq(0)").text(item.id);
				$student_clone.find("span:eq(1)").text(item.name);
				if(item.cId != 0){
					$student_clone.find("span:eq(2)").text(item.className);					
				}else{
					$student_clone.find("span:eq(2)").text("未选择班级");					
				}
				$("#student_list_container").append($student_clone);
			});
			//编辑和删除按钮绑定事件
			$(".edit").off().on("click",function(){
				studentUtils.editStudent(this);
			});
			$(".delete").off().on("click",function(){
				studentUtils.deleteStudent(this);
			});
		}else{
			alert("当前没有学生");
		}
	},
	initClassCheckBox:function(){
		$.ajax({
			type : "GET",
			url :"/student/getClassList",
			dataType:"JSON",  
			data: {},
			success : function(data) {
				//渲染下拉框列表
				console.log(data);
				if(data.length > 0){
					$.each(data,function(index,item){
						$("#check_class").append("<option value='"+ item.id +"'>"+ item.name +"</option>");
						$("#check_class_search").append("<option value='"+ item.id +"'>"+ item.name +"</option>");
					});
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("班级下拉框列表初始化失败，请联系管理员");
			}
		});
	},
	/**
	 * 编辑学生姓名
	 */
	editStudent:function(obj){
		//获取当前点击所在行的学生id
		var $s_Id = $(obj).parent().attr("id").replace("student_","");
		//学生名称置为可编辑状态
		var $student_name = $(obj).prev().prev().text();
		$(obj).prev().prev().remove();
		$(obj).prev().before("<input type='text' name='student_temp' value='"+$student_name+"'/>");
		$(obj).val("保存").off().one("click",function(){
			var $student = new Object();
			$student.id = $s_Id;
			$student.name = $(obj).prev().prev().val();
			$.ajax({
				type : "POST",
				url :"/student/editStudent",
				dataType:"JSON",  
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify($student),
				success : function(data) {
					alert("修改成功");
					studentUtils.searchStudent(0);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("修改失败，请联系管理员");
				}
			});
		});
	},
	/**
	 * 删除学生
	 */
	deleteStudent:function(obj){
		//获取当前点击所在行的班级id
		var $s_Id = $(obj).parent().attr("id").replace("student_","");
		$.ajax({
			type : "GET",
			url :"/student/deleteStudent",
			dataType:"JSON",  
			data: {
				sId:$s_Id
			},
			success : function(data) {
				alert("删除成功");
				studentUtils.searchStudent(0);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("删除失败，请联系管理员");
			}
		});
	},
	/**
	 * 根据姓名查询学生
	 */
	searchStudentByName:function(){
		//获取文本框中的内容
		var $student_name = $("input[name='search_student_name']").val();
		$.ajax({
			type : "GET",
			url :"/student/searchStudentByName",
			dataType:"JSON",  
			data: {
				name:$student_name
			},
			success : function(data) {
				studentUtils.cloneStudentList(data);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("查询失败，请联系管理员");
			}
		});
	}
}
/**
 * 获取封装的学生对象
 */
function getStudentForm(){
	var student = new Object();
	student.name = $("input[name='student_name']").val();
	student.cId = $("#check_class option:selected").val();
	return student;
}
/**
 * 跳转班级操作
 */
function returnClassHandle(){
	window.location.href="/class/index";
}


