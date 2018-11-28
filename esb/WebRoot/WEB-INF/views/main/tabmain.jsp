<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div style="clear:both;">
	<h2>用户统计</h2>
	<div style="clear:both;"></div>
	<div style="width:110px;float: left; margin: 20px 50px 20px 50px;">
		<img alt="当前用户量" src="${ctx}/images/user.png">
		<div style="text-align: center;padding: 10px">当前用户量:5</div>
	</div>
	<div style="width:110px;float: left; margin: 20px 50px 20px 50px;">
		<img alt="上月新增用户" src="${ctx}/images/user.png">
		<div style="text-align: center; padding: 10px">上月新增用户:5</div>
	</div>
	<div style="clear:both;"></div>
</div>
<div style="clear:both;">
	<h2>搜索统计</h2>
	<div style="clear:both;"></div>
	<div style="width:500px;float: left; margin-top: 20px; margin-left: 50px;">
		<table id="entryCount" style="display:none;width:500px; height:400px;">
				<caption>搜索分类词条统计</caption>
				<thead>
					<tr>
						<td></td>
						<th>查询量</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entry" items="${entrys}">
						<tr>
							<th>${entry.title}</th>
							<td>${entry.click}</td>
						</tr>
					</c:forEach>
				</tbody>
		</table>
	</div>
	<div style="width:500px;float: left; margin-top: 20px;margin-left: 50px;">
		<table id="keywordCount" style="display:none;width:500px; height:400px;">
			<caption>搜索关键字统计</caption>
			<thead>
				<tr>
					<td></td>
					<th>查询量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="keyword" items="${keywords}">
					<tr>
						<th>${keyword.title}</th>
						<td>${keyword.click}</td>
					</tr>
				</c:forEach>	
			</tbody>
		</table>
	</div>
	
	<div style="clear:both;"></div>
</div>

