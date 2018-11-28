
<div class="pageContent">
	<form method="post" action="../notice/add" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div style="clear: both; padding: 5px;">
				<label>通知标题：</label>
				<input name="title" class="required" type="text" size="30" value=""/>
			</div>
			<div style="clear: both;padding: 5px;">
				<label>通知内容：</label>
				<textarea name="content" cols="70" rows="8" style="float: left;"></textarea>
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
