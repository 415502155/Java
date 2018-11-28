<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">    
 //遍历被选中CheckBox元素的集合 得到Value值 
$(document).ready(function() {
// put all your jQuery goodness in here.
	$('#sendmessage').click(function(){
		var oidStr=""; //定义一个字符串用来装值的集合    
		//jquery循环t2下的所有选中的复选框  
		  $("input:checked").each(function(i,a){  
		    //alert(a.value);  
		    if(a.value!='on'){
		    	oidStr +=a.value+',';  //拼接字符串 
		    }
		  });   
		
		var param = {};
		var jsessionid = '${sessionid}';
		var sid = '${sid}';
		var url = "../user/sendMessage?receiverid="+oidStr+"&jsessionid="+jsessionid+"&sid="+sid;
		console.log('oidStr==========',param);
		  $.pdialog.open(url,"proaddsub","发短信",{
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
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a title="发短信" id="sendmessage" href="javascript:;" class="add"><span>发短信</span></a></li>			
		</ul>
	</div>

	<ul class="tree treeFolder treeCheck" layoutH="30">
		<c:forEach var="group" items="${list}">
			<li><a>${group.pname}  <c:if test="${group.classname!=null}">  >  ${group.classname}</c:if> </a>
				<c:if test="${group.maps.size()>0}">				
				<ul>
					<c:forEach var="map" items="${group.maps}">
						<li><a tname="name" tvalue="${map.receivers}">${map.name}   tel:${map.tel} </a></li>
					</c:forEach>
				</ul>
				</c:if>
			</li>
		</c:forEach>
		
	</ul>
</div>