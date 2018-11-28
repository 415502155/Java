﻿﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
%>
<c:set var="path" value="<%=path%>"></c:set>

<script type="text/javascript" src="<%=path%>/js/cityselect/jquery.cityselect.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>

<div class="pageContent" style="margin-top: -13px">
    <form id="addOrgForm" name="addOrgForm" method="post" action="<%=path%>/no/importOrg">
        <div class="pageFormContent" style="clear: both; padding:5px; overflow:auto; height:110px">
            <dl style="margin-left: 15%;margin-top: 35px">
                <dt>区县名称：</dt>
                <dd><input name="district" id="district" class="required" type="text" style="width:200px" value="" /></dd>
            </dl>
            <div class="clear"></div>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent"><button type="button" onclick="doAddOrg();">开始导入</button></div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent"><button type="button" class="close">取消</button></div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>

<script type='text/javascript'>
	// 进行新增组织操作
	function doAddOrg() {
		if (!$("#addOrgForm").valid()) {
			return false;
		}
		
		$("#addOrgForm").ajaxSubmit({
			url: "<%=path%>/no/importOrg",
			type: "POST",
			async: false,
			success: function(data) {
				dialogAjaxDone(data);
			},
			error: function(data) {
				myAlert("cztip","操作提示","网络异常！",null);
			}
		});
	}
</script>
