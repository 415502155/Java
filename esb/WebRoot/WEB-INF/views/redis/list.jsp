<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
	String path = request.getContextPath();
%>
<c:set var="path" value="<%=path%>"></c:set>


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
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>机构：</td>
				<td>
					<select class="combox" id="org_id">
						<option value="">所有机构</option>
						<c:forEach items="${orgs}" var="org">									
							<option value="${org.org_id}">${org.org_name_cn}</option>									
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	</div>
</div>

<div class="pageContent">
	<%-- <div>
		<li><a class="button" href="${ctx}/manage/redis/refresh" target="navTab"><span>刷新缓存</span></a></li>	
	</div> --%>
	<table class="table" width="100%" layoutH="134">
		<thead>
			<tr>
				<th width="50" align="center">选择</th>
				<th width="50" align="center">序号</th>
				<th width="100" align="center">缓存名称</th>
				<th width="100" align="center">刷新时间</th>				
				<th width="100" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
		<input type="hidden" value="1" target="sid_user" rel="4" class="selected"/>
		<c:forEach var="cache" items="${list}" varStatus="status">
			<tr target="sid_user">
				<td width="48"><input type="checkbox" /></td>
				<td width="46">${cache.cacheName}</td>
				<td width="100">${cache.cacheName}</td>
				<td width="100"><fmt:formatDate value="${cache.updateTime}" type="date" dateStyle="long"/></td>
				<td width="100">
					<div>
						<a title="刷新" target="ajaxTodo" href="${ctx}/manage/redis/refreshBykey?cacheKey=${cache.cacheKey}" class="btnEdit">刷新</a>
						<a id="${cache.cacheKey}" title="按机构刷新" class="btnView goLookBtn" onclick="refreshOrg('${cache.cacheKey}')"><span>按机构刷新</span></a>
					</div>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<script>

//$("#GROUPMEMBER").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#ORGTREE").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#ORGANIZATION").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#APP").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#PICTURE").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#RIGHT").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#ROLELABEL").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
//$("#TEACHERROLE").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
//$("#TECHRANGE").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#UCUSERS").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#UCUSER2ORGUSER").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#USERACCOUNT").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");
$("#WXCONFIG").removeClass("btnView").addClass("btnDel").removeAttr('onclick').attr("onClick","javascript:alertMsg.warn('暂未开放单机构刷新！')");


function refreshOrg(k){
	var org = $("#org_id").find("option:selected").text();
	var id = $("#org_id").val();
	var warning = '';
	if(k=='CLASSES'){
		warning = '<br/>建议先刷新年级缓存';
	}else if(k=='CLASSSTUDENT'){
		warning = '<br/>建议先刷新班级缓存再刷新学生缓存';
	}else if(k=='GRADE'){
		warning = '<br/>刷新完成后建议再刷新班级缓存以更新年级班级关系;';
	}else if(k=='STUDENT'){
		warning = '<br/>建议先刷新机构用户缓存';
	}else if(k=='PARENT'){
		warning = '<br/>建议先刷新机构用户缓存';
	}else if(k=='TEACHERMEMBER'){
		warning = '<br/>建议先刷新机构用户缓存';
	}
	if(null!=id&&''!=id){
		alertMsg.confirm("确定刷新"+org+"缓存？"+warning, {
			okCall: function(){
				$.ajax({
					type:'POST',
					url: "<%=path%>/manage/redis/refreshOrgBykey",
					data: {'org_id':id,'cacheKey':k},
			    	dataType: 'json',
					success:function(result){
						if(result.statusCode==200){
							alertMsg.correct('刷新成功！');
						}else{
							alertMsg.error('刷新失败！');
						}
					}
				});	
			}
		});
	}else{
		alertMsg.warn('请选择要刷新的机构！');
	}
}; 
</script>
