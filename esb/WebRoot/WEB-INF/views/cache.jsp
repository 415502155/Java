<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>更新缓存</title>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<script src="resource/jquery-1.7.2.min.js" type="text/javascript" charset="utf-8"></script>

</head>

<body>
	<div id="main">
		<div class="page-header-border " id="page-header">
			<div id="inner-page-header">
				<span class="home-icon-container">
					更新缓存
				</span>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="outer-frame">		    
			  <table id="cachelist">
				  <tr>
				  	<th>缓存名称</th>
				  	<th>操作</th>
				  	<th>版本号</th>
				  	<th>缓存大小</th>
				  	<th>添加时间</th>
				  </tr>
				  <c:forEach var="cache" items="${list}">
				  <tr>
				  	<td>${cache.cacheName}</td> 
				  	<td><a href=reloadCache?type=${cache.cacheKey}>更新缓存</a></td> 
				  	<td>版本号:${cache.version}</td>
				  	<td>${cache.cacheSize}</td>
				  	<td>${cache.updateTimeStr}</td>
			  	  </tr>
			  	  </c:forEach>
			  </table>
		</div>
	</div>
</body>
</html>