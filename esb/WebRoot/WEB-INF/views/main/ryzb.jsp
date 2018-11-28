<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">    
 //遍历被选中CheckBox元素的集合 得到Value值 
$(document).ready(function() {
// put all your jQuery goodness in here.
	$('.treetable .syncorg').click(function(){
		var $this = $(this);
		
		var url = "../user/syncschoolorg?&sid="+$this.attr("rel");
		var param = {};
		  $.pdialog.open(url,"syncschoolsub","对应用户中心",{
			  height:600, 
			  width:780,
		      max:false,
		      mask:true,
		      mixable:false,
		      minable:false,
		      resizable:false,
		      drawable:true,
		      fresh:true,
		      close:"function",
		      param:param
		  });
	});
}); 
 
</script>
<div class="pageHeader">
	<h2>入园准备</h2>
</div>
<div class="pageContent">
<form method="post" action="../main/ryzb" class="pageForm required-validate" onsubmit="return iframeCallback(this)">
	<div class="pageFormContent" layoutH="158">
	<div>
		<fieldset>
		<table>
			<tr>
				<td>
					标题：
				</td>	
				<td>
					<input class="required" name="title" type="text" value="${pojo.title }" />
				</td>					
			</tr>
			
			<tr>
				<td>
					封面图片：
				</td>
				<td>
					<div style="float: left; line-height: 40px"><input id="ryzbcover" name="cover" type="text" size="30" value="${pojo.cover }"/></div>							
					<div style="float: left;"><input id="ryzbcoverFileInput" type="file" name="image" uploaderOption="{
								swf:'${pageContext.request.contextPath}/uploadify/scripts/uploadify.swf',
								uploader:'${pageContext.request.contextPath}/manage/yzjs/upload',
								buttonText:'请选择文件',
								fileSizeLimit:'200KB',
								fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
								fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
								auto:true,
								multi:true,
								onUploadSuccess:function(obj,response,result){
									$('#ryzbcover').val(response);
								}
							}" />
					</div>
					<div style="clear: both;"></div>
				</td>
			</tr>
			
		</table>
		<table>
			<tr>
				<td></td>
				<td>
						<div class="pageFormContent">
							<div class="unit">
								<textarea class="editor" name="content" rows="20" cols="100"
									upLinkUrl="${pageContext.request.contextPath}/manage/upload/vidio" upLinkExt="zip,rar,txt" 
									upImgUrl="${pageContext.request.contextPath}/manage/upload/image" upImgExt="jpg,jpeg,gif,png" 
									upFlashUrl="${pageContext.request.contextPath}/manage/upload/vidio" upFlashExt="swf"
									upMediaUrl="${pageContext.request.contextPath}/manage/upload/vidio" upMediaExt:"avi">
									${pojo.content }
								</textarea>
							</div>						
						</div>
				</td>						
			</tr>
		</table>
		</fieldset>
	</div>
</div>
<div class="formBar">
	<ul>
		<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
		<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
	</ul>
</div>
</form>
</div>