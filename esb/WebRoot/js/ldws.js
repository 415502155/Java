function formCode(code){
	var rt = code;
	if(code=="SignatureMismatch"){
		rt = "密码不匹配";
	}
	if(code=="NoSuchAccount"){
		rt = "用户不存在";
	}
	return rt;
}

$().ready(function() {	
	/*var h = $(window).height()-50;
	h && $('#page-content').height(h-245);
	$('#login-container form').css("margin-top",(h-455)/2);
	
	$(window).resize(function(){	
		var h = $(window).height()-50;
		h && $('#page-content').height(h-245);
		$('#login-container form').css("margin-top",(h-455)/2);
	});*/
	
		$("input[name=j_username]").focus(function(){
			$(this).parent().addClass("focused");
		}).blur(function(){
			$(this).parent().removeClass("focused");
		}).keyup(function(){
			if($(this).attr("value").length > 0&&!$(this).parent().hasClass('populated')||$(this).attr("value").length == 0&&$(this).parent().hasClass('populated')){
				$(this).parent().toggleClass('populated');
			}
		}).focus();
		$("input[name=j_password]").focus(function(){
			$(this).parent().addClass("focused");
		}).blur(function(){
			$(this).parent().removeClass("focused");
		}).keyup(function(){
			if($(this).attr("value").length > 0){
				$(this).parent().addClass("populated");
			}
			else{
				$(this).parent().removeClass("populated");
			}
		}).keydown(function(event){
			switch (event.keyCode) {
				case 13:
				    login();
				    break;
			}
		});
		$('#login_submit').click(function(){
			login();
		});		
		function login(){
			var form = $('form[name=loginform]');	
			if(form.valid()){
				if(!!$("#forced").attr("checked")){
					form.submit();
				}else{
					var name=$('input[name=j_username]').val();
					var password=$('input[name=j_password]').val();
					var formtype=$('input[name=X-Form-type]').val();
					console.log('formtype===============',formtype);
					$.ajax({ 
						type: "POST",
						url: ctx+"/LoginProcess",
						data: "j_username="+name+"&j_password="+password+"&X-Form-type=form",
						success: function(data){
							if(data.success){
								window.location.href=ctx+data.url;
							}else{								
								$('#formerror').html(formCode(data.code));
								$('#formerror').css('display','block');
							}
				    	}
					});
				}
			}
		}
		$("form[name=loginform]").validate({
		    rules: {		    	
		        j_username: {
		            required: true
		        },
		        j_password: {
		            required: true
		        }
		    },
		    messages: {
		    	j_username: {
		            required: '用户名不能为空'
		        },
		        j_password: {
		            required: '密码不能为空'
		        }
		    }
		});
	});