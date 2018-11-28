<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="pageContent">
	<form method="post" action="../user/sendMessageAction" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>内容标题：</label>
				<input name="txtNum" class="required" type="text" size="30" value="12月15日:"/>
			</p>
			<p>
				<label>内容：</label>
				<textarea class="editor" name="content" rows="6" cols="40">通知:</textarea>
			</p>
			<input type="hidden" name="receiverid" value="${receiverid}"/>	
			<input type="hidden" name="sid" value="${sid}"/>			
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">发送</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
