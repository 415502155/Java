<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
	th,td{text-align: center !important;}
</style>


<div class="pageHeader">
</div>
<div class="pageContent j-resizeGrid">
	<table class="treetable" width="100%" layoutH="134">
		<thead>
			<tr>
				<th width="50" align="center">序号</th>
				<th width="100" align="center">数据名称</th>
				<th width="300" align="center">数据说明</th>
				<th width="100" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr class="hover">
				<td>1</td>
				<td>班级关注率统计</td>
				<td>【实时】以班级为单位，统计班级的学生关注率</td>
				<td>
					<div class="h">
						<a title="下载" href="${ctx}/manage/data/download?type=1" class="btnAttach"><span>下载</span></a>
					</div>
				</td>
			</tr>
			<tr class="hover">
				<td>2</td>
				<td>班级博客发布情况统计</td>
				<td>【实时】班级博客发布的详细情况汇总</td>
				<td>
					<div class="h">
						<a title="下载" href="${ctx}/manage/data/download?type=2" class="btnAttach"><span>下载</span></a>
					</div>
				</td>
			</tr>
			<tr class="hover">
				<td>3</td>
				<td>工资发布情况统计</td>
				<td>【实时】学校发布的工资明细的详细情况汇总</td>
				<td>
					<div class="h">
						<a title="下载" href="${ctx}/manage/data/download?type=3" class="btnAttach"><span>下载</span></a>
					</div>
				</td>
			</tr>
			<tr class="hover">
				<td>4</td>
				<td>校园通知发布情况统计</td>
				<td>【实时】学校发布的校园通知的详细情况汇总</td>
				<td>
					<div class="h">
						<a title="下载" href="${ctx}/manage/data/download?type=4" class="btnAttach"><span>下载</span></a>
					</div>
				</td>
			</tr>
			<tr class="hover">
				<td>5</td>
				<td>学校综合关注情况统计</td>
				<td>【实时】以学校为单位的学生、家长、教师关注人数及比例</td>
				<td>
					<div class="h">
						<a title="下载" href="${ctx}/manage/data/download?type=5" class="btnAttach"><span>下载</span></a>
					</div>
				</td>
			</tr>
			<tr class="hover">
				<td>6</td>
				<td>学校支付情况统计</td>
				<td>【实时】学校发起支付的详细情况的汇总</td>
				<td>
					<div class="h">
						<a title="下载" href="${ctx}/manage/data/download?type=6" class="btnAttach"><span>下载</span></a>
					</div>
				</td>
			</tr>
			<tr class="hover">
				<td>7</td>
				<td>学生家长关注详细情况查询</td>
				<td>【实时】按查询范围查看学生、家长关注的明细列表</td>
				<td>
					<div>
						<a title="下载" target="dialog" width="630" height="600" href="${ctx}/manage/data/goDownload?type=7" target="dialog" class="btnLook"><span>查看</span></a>
					</div>
				</td>
			</tr>
			<tr class="hover">
				<td>8</td>
				<td>教师关注详细情况查询</td>
				<td>【实时】按查询范围查看教师关注的明细列表</td>
				<td>
					<div>
						<a title="下载" target="dialog" width="630" height="600" href="${ctx}/manage/data/goDownload?type=8" target="dialog" class="btnLook"><span>查看</span></a>
					</div>
				</td>
			</tr>
			<tr class="hover">
				<td>9</td>
				<td>角色教师关注率统计</td>
				<td>【实时】角色教师的关注情况</td>
				<td>
					<div class="h">
						<a title="下载" href="${ctx}/manage/data/download?type=9" class="btnAttach"><span>下载</span></a>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>


<script type="text/javascript">
$(".goLook").hover(function(){
	$(this)[0].style.backgroundColor ="#d0d0d0";
},function(){
	$(this)[0].style.backgroundColor ="";
});
$(".hover").hover(function(){
	$(this)[0].style.backgroundColor ="#ededed";
},function(){
	$(this)[0].style.backgroundColor ="";
});

$(".userRange").each(function(i){
	var a = $(this).html();
	var b = a.replace("0","学校").replace("2","培训机构").replace("3","教育局");
	$(this).html(b);
});
</script>
