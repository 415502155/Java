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
<style>
<!--
dd input[type=text] { width:200px}
label input[type=checkbox] {margin-right:5px}
dd select { margin-right:5px;}
.clear { clear:both}
.inputAll {height:21px;clear:both; padding-top:5px}
-->


.upload-wrap {
	position: relative; width: 80px; height: 80px; line-height: 80px; overflow: hidden; margin-right: 5px; border: 1px dashed #cacbcc; display: inline-block; text-align: center;
	background:url('data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIzMCIgaGVpZ2h0PSIzMCIgdmlld0JveD0iMCAwIDUwIDUwIj4KICAgIDxwYXRoIGZpbGw9IiM5OTkiIGQ9Ik0gMTcgNSBDIDE2LjE4MTI0NiA1IDE1LjYwMjYzNiA1LjQ5ODg1MDggMTUuMTg3NSA1Ljk2ODc1IEMgMTQuNzcyMzY0IDYuNDM4NjQ5MiAxNC40MzkxODQgNi45Njc1NTg4IDE0LjEyNSA3LjQ2ODc1IEMgMTMuODEwODE2IDcuOTY5OTQxMiAxMy41MjU2MzIgOC40MzcxMzYgMTMuMjgxMjUgOC43MTg3NSBDIDEzLjA5Nzk2NCA4LjkyOTk2MDUgMTIuOTc4NCA4Ljk4NjkwMTcgMTIuOTY4NzUgOSBMIDMgOSBDIDEuMzU0OTM3MiA5IDAgMTAuMzU0OTM3IDAgMTIgTCAwIDM5IEMgMCA0MC42NDUwNjMgMS4zNTQ5MzcyIDQyIDMgNDIgTCA0NyA0MiBDIDQ4LjY0NTA2MyA0MiA1MCA0MC42NDUwNjMgNTAgMzkgTCA1MCAxMiBDIDUwIDEwLjM1NDkzNyA0OC42NDUwNjMgOSA0NyA5IEwgMzcuMDMxMjUgOSBDIDM3LjAzNDQ2NyA5LjAwNDM2NjEgMzcuMDIxNjQyIDkgMzcgOSBDIDM2Ljk2NzgwOCA4Ljk3NzY4NDEgMzYuODc3NTk3IDguOTAxNzk5MSAzNi43MTg3NSA4LjcxODc1IEMgMzYuNDc0MzY4IDguNDM3MTM2IDM2LjE4OTE4NCA3Ljk2OTk0MTIgMzUuODc1IDcuNDY4NzUgQyAzNS41NjA4MTYgNi45Njc1NTg4IDM1LjIyNzYzNiA2LjQzODY0OTIgMzQuODEyNSA1Ljk2ODc1IEMgMzQuMzk3MzY0IDUuNDk4ODUwOCAzMy44MTg3NTQgNSAzMyA1IEwgMTcgNSB6IE0gMTcgNyBMIDMzIDcgQyAzMi45MzkyNSA3IDMzLjA2NTA0IDcuMDAxMTUgMzMuMzEyNSA3LjI4MTI1IEMgMzMuNTU5OTU3IDcuNTYxMzUwOCAzMy44NDM1NTkgOC4wMzI0NDEyIDM0LjE1NjI1IDguNTMxMjUgQyAzNC40Njg5NDEgOS4wMzAwNTg4IDM0LjgxMjI4OSA5LjU2Mjg2NCAzNS4yMTg3NSAxMC4wMzEyNSBDIDM1LjYyNTIxMSAxMC40OTk2MzYgMzYuMTc4OTI3IDExIDM3IDExIEwgNDcgMTEgQyA0Ny41NjI5MzcgMTEgNDggMTEuNDM3MDYzIDQ4IDEyIEwgNDggMzkgQyA0OCAzOS41NjI5MzcgNDcuNTYyOTM3IDQwIDQ3IDQwIEwgMyA0MCBDIDIuNDM3MDYyOCA0MCAyIDM5LjU2MjkzNyAyIDM5IEwgMiAxMiBDIDIgMTEuNDM3MDYzIDIuNDM3MDYyOCAxMSAzIDExIEwgMTMgMTEgQyAxMy44MjEwNzMgMTEgMTQuMzc0Nzg5IDEwLjQ5OTYzNiAxNC43ODEyNSAxMC4wMzEyNSBDIDE1LjE4NzcxMSA5LjU2Mjg2NCAxNS41MzEwNTkgOS4wMzAwNTg4IDE1Ljg0Mzc1IDguNTMxMjUgQyAxNi4xNTY0NDEgOC4wMzI0NDEyIDE2LjQ0MDA0MyA3LjU2MTM1MDggMTYuNjg3NSA3LjI4MTI1IEMgMTYuOTM0OTU3IDcuMDAxMTQ5MiAxNy4wNjA3NTQgNyAxNyA3IHogTSAyNSAxMSBDIDE3LjgzMjE0MyAxMSAxMiAxNi44MzIxNDMgMTIgMjQgQyAxMiAzMS4xNjc4NTcgMTcuODMyMTQzIDM3IDI1IDM3IEMgMzIuMTY3ODU3IDM3IDM4IDMxLjE2Nzg1NyAzOCAyNCBDIDM4IDE2LjgzMjE0MyAzMi4xNjc4NTcgMTEgMjUgMTEgeiBNIDI1IDEzIEMgMzEuMDg2OTc3IDEzIDM2IDE3LjkxMzAyMyAzNiAyNCBDIDM2IDMwLjA4Njk3NyAzMS4wODY5NzcgMzUgMjUgMzUgQyAxOC45MTMwMjMgMzUgMTQgMzAuMDg2OTc3IDE0IDI0IEMgMTQgMTcuOTEzMDIzIDE4LjkxMzAyMyAxMyAyNSAxMyB6IE0gOCAxNCBDIDYuODk2IDE0IDYgMTQuODk2IDYgMTYgQyA2IDE3LjEwNCA2Ljg5NiAxOCA4IDE4IEwgOSAxOCBDIDEwLjEwNCAxOCAxMSAxNy4xMDQgMTEgMTYgQyAxMSAxNC44OTYgMTAuMTA0IDE0IDkgMTQgTCA4IDE0IHogTSAyNSAxNSBDIDIwLjA0MTI4MiAxNSAxNiAxOS4wNDEyODIgMTYgMjQgQyAxNiAyOC45NTg3MTggMjAuMDQxMjgyIDMzIDI1IDMzIEMgMjkuOTU4NzE4IDMzIDM0IDI4Ljk1ODcxOCAzNCAyNCBDIDM0IDE5LjA0MTI4MiAyOS45NTg3MTggMTUgMjUgMTUgeiBNIDI1IDE3IEMgMjguODc3ODM4IDE3IDMyIDIwLjEyMjE2MiAzMiAyNCBDIDMyIDI3Ljg3NzgzOCAyOC44Nzc4MzggMzEgMjUgMzEgQyAyMS4xMjIxNjIgMzEgMTggMjcuODc3ODM4IDE4IDI0IEMgMTggMjAuMTIyMTYyIDIxLjEyMjE2MiAxNyAyNSAxNyB6IiBjb2xvcj0iIzAwMCIgb3ZlcmZsb3c9InZpc2libGUiIGVuYWJsZS1iYWNrZ3JvdW5kPSJhY2N1bXVsYXRlIiBmb250LWZhbWlseT0iQml0c3RyZWFtIFZlcmEgU2FucyI+PC9wYXRoPgo8L3N2Zz4=') center center no-repeat;
}
.upload-wrap .thumbnail, .upload-preview .thumbnail {display:table-cell; vertical-align: middle; width: 80px; height: 80px; text-align: center; overflow: hidden; z-index: 100;}
.upload-wrap .thumbnail .edit-icon { content:' '; display: block; position:absolute; top: 5px; right:5px; width:20px; height:20px; border-radius: 3px; opacity: 0.5; filter:alpha(opacity=50); background:url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAzMiAzMiI+CiAgICA8cGF0aCBzdHlsZT0idGV4dC1pbmRlbnQ6MDt0ZXh0LWFsaWduOnN0YXJ0O2xpbmUtaGVpZ2h0Om5vcm1hbDt0ZXh0LXRyYW5zZm9ybTpub25lO2Jsb2NrLXByb2dyZXNzaW9uOnRiOy1pbmtzY2FwZS1mb250LXNwZWNpZmljYXRpb246Qml0c3RyZWFtIFZlcmEgU2FucyIgZD0iTSAyMy45MDYyNSAzLjk2ODc1IEMgMjIuODU5Mjg2IDMuOTY4NzUgMjEuODEzMTc4IDQuMzc0MzIxNSAyMSA1LjE4NzUgTCA1LjQwNjI1IDIwLjc4MTI1IEwgNS4xODc1IDIxIEwgNS4xMjUgMjEuMzEyNSBMIDQuMDMxMjUgMjYuODEyNSBMIDMuNzE4NzUgMjguMjgxMjUgTCA1LjE4NzUgMjcuOTY4NzUgTCAxMC42ODc1IDI2Ljg3NSBMIDExIDI2LjgxMjUgTCAxMS4yMTg3NSAyNi41OTM3NSBMIDI2LjgxMjUgMTEgQyAyOC40Mzg4NTcgOS4zNzM2NDMgMjguNDM4ODU3IDYuODEzODU3IDI2LjgxMjUgNS4xODc1IEMgMjUuOTk5MzIyIDQuMzc0MzIxNSAyNC45NTMyMTQgMy45Njg3NSAyMy45MDYyNSAzLjk2ODc1IHogTSAyMy45MDYyNSA1Ljg3NSBDIDI0LjQwOTI4NiA1Ljg3NSAyNC45MTk0MjggNi4xMDY5Mjg1IDI1LjQwNjI1IDYuNTkzNzUgQyAyNi4zNzk4OTMgNy41NjczOTMgMjYuMzc5ODkzIDguNjIwMTA3IDI1LjQwNjI1IDkuNTkzNzUgTCAyNC42ODc1IDEwLjI4MTI1IEwgMjEuNzE4NzUgNy4zMTI1IEwgMjIuNDA2MjUgNi41OTM3NSBDIDIyLjg5MzA3MiA2LjEwNjkyODUgMjMuNDAzMjE0IDUuODc1IDIzLjkwNjI1IDUuODc1IHogTSAyMC4zMTI1IDguNzE4NzUgTCAyMy4yODEyNSAxMS42ODc1IEwgMTEuMTg3NSAyMy43ODEyNSBDIDEwLjUzMzE0MiAyMi41MDA2NTkgOS40OTkzNDE1IDIxLjQ2Njg1OCA4LjIxODc1IDIwLjgxMjUgTCAyMC4zMTI1IDguNzE4NzUgeiBNIDYuOTM3NSAyMi40Mzc1IEMgOC4xMzY1ODQyIDIyLjkyMzM5MyA5LjA3NjYwNjcgMjMuODYzNDE2IDkuNTYyNSAyNS4wNjI1IEwgNi4yODEyNSAyNS43MTg3NSBMIDYuOTM3NSAyMi40Mzc1IHoiIGNvbG9yPSIjMDAwIiBvdmVyZmxvdz0idmlzaWJsZSIgZm9udC1mYW1pbHk9IkJpdHN0cmVhbSBWZXJhIFNhbnMiPjwvcGF0aD4KPC9zdmc+') center center no-repeat #fff;}
.upload-wrap input[type=file], .upload-wrap button {position: absolute; top:0; left:0; font-size: 0; width: 100%; height: 100%; outline: 0; opacity: 0; filter: alpha(opacity=0); cursor: pointer;}

.upload-preview {display: inline-block;}
.upload-preview .thumbnail {margin: 0 3px;}
.upload-preview .thumbnail .del-icon, .upload-wrap .del-icon { content:' '; display: block; position:absolute; top: 5px; right:5px; width:20px; height:20px; border-radius: 3px; opacity: 0.4; filter:alpha(opacity=40); background:url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAzMiAzMiI+CiAgICA8cGF0aCBzdHlsZT0idGV4dC1pbmRlbnQ6MDt0ZXh0LWFsaWduOnN0YXJ0O2xpbmUtaGVpZ2h0Om5vcm1hbDt0ZXh0LXRyYW5zZm9ybTpub25lO2Jsb2NrLXByb2dyZXNzaW9uOnRiOy1pbmtzY2FwZS1mb250LXNwZWNpZmljYXRpb246Qml0c3RyZWFtIFZlcmEgU2FucyIgZD0iTSA3LjIxODc1IDUuNzgxMjUgTCA1Ljc4MTI1IDcuMjE4NzUgTCAxNC41NjI1IDE2IEwgNS43ODEyNSAyNC43ODEyNSBMIDcuMjE4NzUgMjYuMjE4NzUgTCAxNiAxNy40Mzc1IEwgMjQuNzgxMjUgMjYuMjE4NzUgTCAyNi4yMTg3NSAyNC43ODEyNSBMIDE3LjQzNzUgMTYgTCAyNi4yMTg3NSA3LjIxODc1IEwgMjQuNzgxMjUgNS43ODEyNSBMIDE2IDE0LjU2MjUgTCA3LjIxODc1IDUuNzgxMjUgeiIgY29sb3I9IiMwMDAiIG92ZXJmbG93PSJ2aXNpYmxlIiBmb250LWZhbWlseT0iQml0c3RyZWFtIFZlcmEgU2FucyI+PC9wYXRoPgo8L3N2Zz4=') center center no-repeat #fff;}
.upload-preview .thumbnail .del-icon:hover, .upload-wrap .del-icon:hover {opacity: 0.7; filter:alpha(opacity=70);}

.upload-preview img {width:80px;height:80px;margin-right:5px;border: 1px dashed #cacbcc;}
</style>

<div class="pageContent" style="margin-top: -13px">
    <form id="addOrgForm" name="addOrgForm" method="post" action="<%=path%>/manage/organization/addOrganization" enctype="multipart/form-data">
        <div class="pageFormContent" style="clear: both; padding:5px; overflow:auto; height:810px">
            <input type="hidden" name="org_id" id="org_id" value="">
            <dl>
                <dt>机构中文名称：</dt>
                <dd><input name="org_name_cn" id="org_name_cn" class="required" type="text" size="100" value="" /></dd>
            </dl>
            <dl>
                <dt>机构中文简称：</dt>
                <dd><input name="org_name_s_cn" id="org_name_s_cn" class="" type="text" size="100" value="" /></dd>
            </dl>
            <dl>
                <dt>机构英文名称：</dt>
                <dd><input name="org_name_en" id="org_name_en" class="" type="text" size="30" value="" /></dd>
            </dl>

            <dl>
                <dt>机构英文简称：</dt>
                <dd><input name="org_name_s_en" id="org_name_s_en" class="" type="text" size="30" value="" /></dd>
            </dl>
            <div class="unit">
				<label>机构Logo图片：</label>
                <img id="org_logo_preview" name="" src="" width="72" height="72">
                <input id="org_logo_upload" name="org_logo_upload" accept="image/*" type="file" />
            </div>
            <div class="unit">
				<label>机构欢迎图：</label>
                <img id="org_welcome_preview" name="" src="" >
                <input id="org_welcome_upload" name="org_welcome_upload" type="file" />
            </div>
            <div class="unit">
                <div class="unit">
				    <label>机构背景图片：</label>
				    <ul id="org_bg_preview" class="upload-preview"></ul>
				    <div class="upload-wrap" rel="#org_bg_preview">
				        <input type="file" id="org_bg_upload" title="上传多个文件" name="org_bg_upload" accept="image/*" multiple="multiple" style="left: 0px;">
				    </div>
				</div>
            </div>
            <dl class="nowrap">
                <dt>所在区域：</dt>
                <input type="hidden" id="area" name="area" value="">
                <dd id="area_select">
                    <select class="prov" style="width:207px"></select>
                    <select class="city" disabled="disabled" style="width:207px"></select>
                    <select class="dist" disabled="disabled" style="width:207px"></select>
                </dd>
            </dl>
            <div class="clear"></div>
            <div>
                <dt>机构类型：</dt>
                <label><input type="radio" name="type" value="0" checked="checked" />学校</label>
                <label><input type="radio" name="type" value="2" />培训机构</label>
                <label><input type="radio" name="type" value="3" />教育局</label>
            </div>
			<input type="hidden" name="grade4AddOrg" id="grade4AddOrg" value="">
            <div class="clear"></div>
            <div id="grade4SchoolType" style="padding-left:130px;">
				<div id="grade4Kindergarten_div" style="margin-top: 10px">
					<b><span style="float:left">幼儿园：</span></b>
	                <label><input type="checkbox" name="" value="小小班" />小小班</label>
	                <label><input type="checkbox" name="" value="小班" />小班</label>
	                <label><input type="checkbox" name="" value="中班" />中班</label>
	                <label><input type="checkbox" name="" value="大班" />大班</label>
	                <label><input type="checkbox" name="" value="学前班" />学前班</label>
				</div>
				<div class="clear"></div>
				<div id="grade4PrimarySchool_div" style="margin-top: 10px">
					<b><span style="float:left">小&nbsp;&nbsp;&nbsp;&thinsp;学：</span></b>
					<label><input type="checkbox" name="" value="一年级" />一年级</label>
	                <label><input type="checkbox" name="" value="二年级" />二年级</label>
	                <label><input type="checkbox" name="" value="三年级" />三年级</label>
	                <label><input type="checkbox" name="" value="四年级" />四年级</label>
	                <label><input type="checkbox" name="" value="五年级" />五年级</label>
	                <label><input type="checkbox" name="" value="六年级" />六年级</label>
				</div>
                <div class="clear"></div>
				<div id="grade4MiddleSchool_div" style="margin-top: 10px">
					<b><span style="float:left">初&nbsp;&nbsp;&nbsp;&thinsp;中：</span></b>
					<label style="width:250px"><input type="checkbox" name="" value="六年级（仅供部分学校使用）"/>六年级（仅供部分学校使用）</label>
					<label><input type="checkbox" name="" value="七年级" />七年级</label>
	                <label><input type="checkbox" name="" value="八年级" />八年级</label>
	                <label><input type="checkbox" name="" value="九年级" />九年级</label>
				</div>

                <div class="clear"></div>
				<div id="grade4HighSchool_div" style="margin-top: 10px">
					<b><span style="float:left">高&nbsp;&nbsp;&nbsp;&thinsp;中：</span></b>
					<label><input type="checkbox" name="" value="高一" />高一</label>
	                <label><input type="checkbox" name="" value="高二" />高二</label>
	                <label><input type="checkbox" name="" value="高三" />高三</label>
				</div>
            </div>
            <div class="clear"></div>
            <dl>
                <dt>机构地址：</dt>
                <dd><input name="address" id="address" class="" type="text" size="200" value="" /></dd>
            </dl>
            <dl>
                <dt>邮政编码：</dt>
                <dd><input name="postcode" id="postcode" class="" type="text" size="6" value="" /></dd>
            </dl>
 			<dl>
                <label>机构UI包：</label>
                <dd>
                	<select style="width:207px" name="css">
		                <c:forEach var="ui" items="${uiList}">
							<option value="${ui.value}">${ui.key}</option>                		
						</c:forEach>
                	</select>
                </dd>
            </dl>
            <div style="float: left;display: block;width: 400px;height: 21px;margin: 0;padding: 5px 0;position: relative;">
                <div>
	                <dt>菜单排列方式：</dt>
	                <label><input type="radio" name="layout" value="2" checked="checked"/>树形菜单</label>
	                <label><input type="radio" name="layout" value="1" />平铺格子</label>
	            </div>
            </div>
            <dl>
                <dt>主管联系人：</dt>
                <dd><input name="contact" id="contact" class="" type="text" size="10" value="" /></dd>
            </dl>

            <dl>
                <dt>手机号码：</dt>
                <dd><input name="contact_mobile" id="contact_mobile" class="" type="text" size="11"/></dd>
            </dl>
            <dl>
                <dt>机构网址：</dt>
                <dd><input name="website" id="website" class="" type="text" size="50" value="" /></dd>
            </dl>
            <dl>
                <dt>版权信息：</dt>
                <dd><input name="copyright_info" id="copyright_info" type="text" size="100"  /></dd>
            </dl>
            <dl>
                <dt>公安备案：</dt>
                <dd><input name="police_record" id="police_record" type="text" size="100"  /></dd>
            </dl>
            <dl>
                <dt>公安备案地址：</dt>
                <dd><input name="police_record_url" id="police_record_url" type="text" size="100"  /></dd>
            </dl>
            <dl>
                <dt>ICP备案：</dt>
                <dd><input name="ICP_record" id="ICP_record" type="text" size="100" /></dd>
            </dl>
            <dl>
                <dt>ICP备案地址：</dt>
                <dd><input name="ICP_record_url" id="ICP_record_url" type="text" size="100" /></dd>
            </dl>
            <dl>
                <dt>技术支持：</dt>
                <dd><input name="support" id="support" type="text" size="100" /></dd>
            </dl>
            <dl>
                <dt>提示信息：</dt>
                <dd><input name="info" id="info" type="text" size="100" /></dd>
            </dl>
            <dl class="nowrap">
                <dt>备注：</dt>
                <dd><textarea name="remark" id="remark" cols="80" rows="2" class="textInput"></textarea></dd>
            </dl>
            <dl class="nowrap">
                <dt>学区设置：</dt>
                <dd>
					<div class="grid" style="clear: both; width:600px" id="org_campus_div">
						<table class="list nowrap " width="600px" id="org_campus_table" name="org_campus_table">
		                    <thead>
		                        <tr>
		                        	<td></td>
		                            <td>校区名</td>
		                            <td>校区地址</td>
		                            <td>操作</td>
		                        </tr>
		                    </thead>
		                    <tr>
		                    	<td>主校区：</td>
		                        <td><input type="text" style=" width:150px" placeholder="主校区" name="main_campus_name"></td>
		                        <td><input type="text" style=" width:150px" name="main_campus_address"></td>
		                        <td><input type="button" onclick="addBranchCampus();" value="增加分校区" /></td>
		                    </tr>
		                </table>
		            </div>
				</dd>
            </dl>
            <div class="clear"></div>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent"><button type="button" onclick="doAddOrg();">保存</button></div>
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
	$(document).ready(function() {
		// 设置地区默认天津
		$("#area_select").citySelect({prov:"天津",nodata:"none"});
		$("#area").val("");
		
		// 为单选添加选中事件
		$("input[type=radio][name=type]").change(function() {
	        if (this.value == "0") {
	            $("#grade4SchoolType").show();
	            $("#org_campus_div").show();
	        } else if (this.value == "2") {
	            $("#grade4SchoolType").hide();
	            $("#org_campus_div").show();
	        } else if (this.value == "3") {
	        	$("#grade4SchoolType").hide();
	        	$("#org_campus_div").hide();
	        }
	    });

		$("#org_logo_upload").click(function() {
		    $("#org_logo_upload").on("change", function() {
		        var objUrl = getObjectURL(this.files[0]); //获取图片的路径，该路径不是图片在本地的路径
		        if (objUrl) {
		            $("#org_logo_preview").attr("src", objUrl); //将图片路径存入src中，显示出图片
		        }
		    });
		});

		$("#org_welcome_upload").click(function() {
		    $("#org_welcome_upload").on("change", function() {
		        var objUrl = getObjectURL(this.files[0]); //获取图片的路径，该路径不是图片在本地的路径
		        if (objUrl) {
		            $("#org_welcome_preview").attr("src", objUrl); //将图片路径存入src中，显示出图片
		        }
		    });
		});
		
		$("#org_bg_upload").click(function() {
		    $("#org_bg_upload").on("change", function() {
		    	console.log(this.files.length);
		    	$("#org_bg_preview").empty();
		    	for (var i=0;i < this.files.length; i++) {
		    		var objUrl = getObjectURL(this.files[i]); //获取图片的路径，该路径不是图片在本地的路径
			        if (objUrl) {
			            $("#org_bg_preview").append("<img name=\"\" src=\""+objUrl+"\" />"); //将图片路径存入src中，显示出图片
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
	
	// 增加一行分校区
	function addBranchCampus() {
		$("#org_campus_table").append("<tr><td></td><td><input type=\"text\" style=\"width:150px\" value=\"\" name=\"campus_name\"></td><td><input type=\"text\" style=\"width:150px\" name=\"campus_address\"></td><td><input type=\"button\" value=\"删除\" onclick=\"deleteCampusRow(this);\" ></td></tr>");
	}
	
	// 删除一行分校区
	function deleteCampusRow(row) {
		$(row).parent().parent().remove();
	}
	
	// 进行新增组织操作
	function doAddOrg() {
		//var thiz = this;
		var ajaxbg = $("#background,#progressBar");
		ajaxbg.show();
		$("#submit").html("提交中").attr("disabled", true);
		setTimeout(function(){
			if (!$("#addOrgForm").valid()) {
				$("#submit").html("保存").removeAttr("disabled");
				ajaxbg.hide();
				return false;
			}
			
			var orgType = $("input[type=radio][name=type]:checked").val();
			if ("0" == orgType) {
				var grade4OrgStr = "";
				// 在选中机构类型是学校的情况下，获取勾选了哪些年级
				$("#grade4Kindergarten_div input[type=checkbox]").each(function (i, n) {
					if ($(this).prop("checked")) {
						grade4OrgStr += (this.value + "_");
					}
				});
				
				$("#grade4PrimarySchool_div input[type=checkbox]").each(function (i, n) {
					if ($(this).prop("checked")) {
						grade4OrgStr += (this.value + "_");
					}
				});
				
				$("#grade4MiddleSchool_div input[type=checkbox]").each(function (i, n) {
					if ($(this).prop("checked")) {
						grade4OrgStr += (this.value + "_");
					}
				});
				
				$("#grade4HighSchool_div input[type=checkbox]").each(function (i, n) {
					if ($(this).prop("checked")) {
						grade4OrgStr += (this.value + "_");
					}
				});
				
				if (grade4OrgStr.length > 0) {
					grade4OrgStr = grade4OrgStr.substring(0, grade4OrgStr.length-1);
				}
				console.log(grade4OrgStr);
				$("#grade4AddOrg").val(grade4OrgStr);
			} else {
				$("#grade4AddOrg").val("");
			}
			
			// 获取选择的地区
			var areaStr = $("#area_select > .prov").val() + "_" + $("#area_select > .city").val() + "_" + $("#area_select > .dist").val();
			$("#area").val(areaStr);
			
			$("#addOrgForm").ajaxSubmit({
				url: "<%=path%>/manage/organization/addOrganization",
				type: "POST",
				async: false,
				success: function(data) {
					$("#submit").html("保存").removeAttr("disabled");
					ajaxbg.hide();
					dialogAjaxDone(data);
				},
				error: function(data) {
					$("#submit").html("保存").removeAttr("disabled");
					ajaxbg.hide();
					alertMsg.warn('您提交的数据有误，请检查后重新提交！');
				}
			});
		}, 1);
	}
</script>
