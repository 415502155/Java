
<div class="pageContent">
	<form method="post" action="../app/edit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="appId" value="${app.a_id}"/>
		<div class="pageFormContent" layoutH="62">
			<div style="clear: both; padding: 5px;">
				<label>name：</label>
				<input name="name" class="required" type="text" size="30" value="${app.name}"/>
			</div>
			<div style="clear: both;padding: 5px;">
				<label>key：</label>
				<input name="key" class="required" type="text" size="30" value="${app.key}"/>
			</div>
			<div style="clear: both;padding: 5px;">
				<label>sessionkey：</label>
				<input name="sessionkey" type="text" size="30" value="${app.sessionkey}"/>(不填写默认为：JSESSIONID)
			</div>
			<div style="clear: both;padding: 5px;">
				<label>index：</label>
				<input name="index" class="required" type="text" size="100" value="${app.index}"/>
			</div>
			<div style="clear: both;padding: 5px;">
				<label>login：</label>
				<input name="login" class="required" type="text" size="100" value="${app.login}"/>
			</div>
			<div style="clear: both;padding: 5px;">
				<label>loginProcess：</label>
				<input name="loginProcess" class="required" type="text" size="100" value="${app.loginProcess}"/>
			</div>
			<div style="clear: both;padding: 5px;">
				<label>logout：</label>
				<input name="logout" class="required" type="text" size="100" value="${app.logout}"/>
			</div>
			<div style="clear: both;padding: 5px;">
				<label>setSession：</label>
				<input name="setSession" class="required" type="text" size="100" value="${app.setSession}"/>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">发布</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
