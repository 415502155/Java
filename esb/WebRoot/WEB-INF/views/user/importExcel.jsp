﻿<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="pageContent">
    <form action="../user/importExcel" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);" enctype="multipart/form-data" method="post">
        <div class="pageFormContent" layoutH="58">
        	<div class="unit">
        		<label>所属组织：</label>
				<select class="combox" name="org_id">
					<option value="0">暂无</option>
					<c:forEach items="${orgs}" var="org">									
						<option value="${org.org_id}" <c:if test="${org.org_id==role.org_id}" > selected="selected" </c:if>>${org.org_name_cn}</option>									
					</c:forEach>
				</select>
        	</div>
            <div class="unit">
                            <label>选择Excel文件：</label>
                                <input type="file" name="file_stu"/>
            </div>
                    <span style="color: #0000FF;margin-left: 80px;">备注：可以上传Microsoft Excel 2003格式的文件（文件名为*.xls），文件大小小于1M。</span>                   
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
</div>