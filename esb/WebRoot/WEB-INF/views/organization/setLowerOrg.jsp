﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    .centent {
        width: 660px;
    }

    .cententl {
        float: left;
    }

    .btnAdd {
        float: left;
        padding: 40px 10px;
    }
</style>

<div class="pageContent">
    <form id="saveLowerOrgForm" name="editOrgForm" method="post" action="<%=path%>/manage/organization/editOrganzation"  enctype="multipart/form-data">
        <div class="pageFormContent" style="clear: both; padding:5px; overflow:auto; height:600px">

            <input type="hidden" name="org_id" id="org_id" value="${orgEntity.org_id}">
            <dl>
                <dt>机构中文名称：</dt>
                <dd><input name="org_name_cn" id="org_name_cn" readonly="readonly" type="text" size="100" value="${orgEntity.org_name_cn}" /></dd>
            </dl>

            <dl>
                <dt>机构中文简称：</dt>
                <dd><input name="org_name_s_cn" id="org_name_s_cn" readonly="readonly" type="text" size="100" value="${orgEntity.org_name_s_cn}" /></dd>
            </dl>

            <dl>
                <dt>所在区域：</dt>
                <input type="hidden" id="area" name="area" value="">
                <dd id="area_select">
                    <select class="prov"></select>
                    <select class="city" disabled="disabled"></select>
                    <select class="dist" disabled="disabled"></select>
                </dd>
            </dl>
            <div class="clear"></div>

            <div>
                <dt>机构类型：</dt>
                <label><input type="radio" name="orgType" value="0" />学校</label>
                <label><input type="radio" name="orgType" value="2" />培训机构</label>
                <label><input type="radio" name="orgType" value="3" />教育局</label>
                <input type="hidden" name="type" id="type" value="">
            </div>


            <div class="clear"></div>

            <dl class="nowrap">
                <label>备注：</label>
                <textarea name="remark" id="remark" cols="80" rows="2" class="textInput" readonly="readonly">${orgEntity.remark}</textarea>
            </dl>

            <div class="clear"></div>
            <div class="grid" style="clear: both; padding-left:130px; width:680px" id="lower_org_div">
            	<div class="centent">
			        <div class="cententl">
			            <select multiple="multiple" id="orgSelectFrom" style="width: 300px; height: 260px;">
			            	<c:forEach var="org" items="${allOrgList}">
			            		<option value="${org.org_id}">${org.org_name_cn}</option>
			            	</c:forEach>
			            </select>
			        </div>
			        <div class="btnAdd">
			            <input type="button" id="btnAdd" value=" > " /><br />
			            <input type="button" id="btnDel" value=" &lt; " /><br />
			            <input type="button" id="btnAddAll" value=">>" /><br />
			            <input type="button" id="btnDelAll" value="&lt;&lt;" />
			        </div>
			        <div>
			        	<input type="hidden" id="selectedLowerOrg" name="selectedLowerOrg" value="">
			            <select multiple="multiple" id="orgSelectTo" style="width: 300px; height: 260px;">
			            	<c:forEach var="org" items="${orgEntity.lowerOrgList}">
			            		<option value="${org.org_id}">${org.org_name_cn}</option>
			            	</c:forEach>
			            </select>
			        </div>
			    </div>
			</div>
	        <div class="formBar">
	            <ul>
	                <li>
	                    <div class="buttonActive">
	                        <div class="buttonContent"><button type="button" onclick="saveLowerOrg();">保存</button></div>
	                    </div>
	                </li>
	                <li>
	                    <div class="button">
	                        <div class="buttonContent"><button type="button" class="close">取消</button></div>
	                    </div>
	                </li>
	            </ul>
	        </div>
        </div>
    </form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		
		//移到右边
        $("#btnAdd").click(function () {
            //获取选中的选项，删除自己并追加给对方
            $("#orgSelectFrom option:selected").appendTo("#orgSelectTo");
        });
        //移到左边
        $("#btnDel").click(function () {
            //获取选中的选项，删除自己并追加给对方
            $("#orgSelectTo option:selected").appendTo("#orgSelectFrom");
        });
        //全部移到右边
        $("#btnAddAll").click(function () {
            //获取全部的选项,删除自己并追加给对方
            $("#orgSelectFrom option").appendTo("#orgSelectTo");
        });
        //全部移到左边
        $("#btnDelAll").click(function () {
            //获取全部的选项,删除自己并追加给对方
            $("#orgSelectTo option").appendTo("#orgSelectFrom");
        });
        //双击选项
        $("#orgSelectFrom").dblclick(function () { 
            //获取双击的选项,删除自己并追加给对方
            $("option:selected", this).appendTo("#orgSelectTo"); 
        });
        //双击选项
        $("#orgSelectTo").dblclick(function () {
            //获取双击的选项,删除自己并追加给对方
            $("option:selected", this).appendTo("#orgSelectFrom");
        });
		
		var flag = "${successFlag}";
		if ("success" == flag) {
			// 设置所在区域
			var areaArray = "${orgEntity.area}".split("_");
			if (areaArray[2] != "null") {
				$("#area_select").citySelect({prov:areaArray[0], city:areaArray[1], dist:areaArray[2]});
			} else {
				$("#area_select").citySelect({prov:areaArray[0], city:areaArray[1], nodata:"none"});
			}
			
			// 设置机构类型
			console.log(${orgEntity.type});
			var orgType = "${orgEntity.type}";
			if ("0" == orgType) {
	            $("input[type=radio][name=orgType]").eq(0).prop("checked", true);
			} else if ("2" == orgType) {
	            $("input[type=radio][name=orgType]").eq(1).prop("checked", true);
			} else if ("3" == orgType) {
	        	$("input[type=radio][name=orgType]").eq(2).prop("checked", true);
			}
			// 编辑时不能修改机构类型
			$("input[type=radio][name=orgType]").prop("disabled", true);
			$("#area_select > .prov").attr("disabled", true);
			$("#area_select > .city").attr("disabled", true);
			$("#area_select > .dist").attr("disabled", true);
			
		} else {
			alert("未查询到当前机构！");
		}
		
	});
	
	
	// 进行保存机构下级操作
	function saveLowerOrg() {
		// 获取选择的下级机构
		var str = "";
		$("#orgSelectTo option").each(function () {
			str += ($(this).val() + "_");
		});
		if (str.length > 0) {
			str = str.substring(0, str.length-1);
		}
		$("#selectedLowerOrg").val(str);;
		
		$("#saveLowerOrgForm").ajaxSubmit({
			url: "<%=path%>/manage/organization/saveLowerOrg",
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
