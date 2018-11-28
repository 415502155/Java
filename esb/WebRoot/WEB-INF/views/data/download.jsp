<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="clear: both; padding: 5px;">
	<label>请选择要下载的学校：</label>
	<input type="hidden" id="orgs" value='${orgs }'>
	<input type="hidden" id="type" value='${type }'>
	<div class="page unitBox" style="overflow: auto;height:500px;width:600px;border:solid 1px #CCC;background:#FFF;">
		<ul class="tree treeFolder treeCheck collapse">
			<li><a tname="org_ids" id="all" tvalue="">全部</a> 
				<!-- <ul>
					<li><a tname="org_ids" id="gn10" tvalue="10">幼儿园</a>
						<ul>
							<li><a style="color:orange" tname="org_ids" id="gn11" tvalue="11">小小班</a></li>    
							<li><a tname="org_ids" id="gn12" tvalue="12">小班</a></li>    
							<li><a tname="org_ids" id="gn13" tvalue="13">中班</a></li>    
							<li><a tname="org_ids" id="gn14" tvalue="14">大班</a></li>    
							<li><a tname="org_ids" id="gn15" tvalue="15">学前班</a></li>   
						</ul>
					</li>
				</ul> -->
			</li>
		</ul>
	</div>
	<div><span style="float:left;color:red;margin-left:130px" id="check_grade"></span></div>
	<div class="formBar">
		<ul>
			<li><div class="button"><a id="dd" href = ""><div class="buttonContent" style="width: 200px;"><button class="buttonContent" id="subBtn" type="button" style="width: 205px;">下载</button></div></a></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	var orgs = JSON.parse($("#orgs").val());
	var obj = {};
	for(var i = 0; i < orgs.length; i++){
		if(null==obj[orgs[i]["area"]]||undefined==obj[orgs[i]["area"]]||''==obj[orgs[i]["area"]]){
			obj[orgs[i]["area"]]=[];
		}
		obj[orgs[i]["area"]].push(orgs[i]);
	}
	var ul = "<ul>";
	for(var key in obj){
		var ids = [];
		var list = obj[key];
		var li = "";
		for(var j=0; j<list.length;j++){
			li += '<li><a tname="org_ids" tvalue="'+list[j]["org_id"]+'">'+list[j]["org_name_cn"]+'</a></li>';
			ids.push(list[j]["org_id"]);
		}
		ul += '<li><a tname="org_ids" tvalue="'+ids.join(',')+'">'+key.replaceAll("_null",'')+'</a>'+
					'<ul>'+
						li+
					'</ul>'+
				'</li>';
	}	
	ul += '</ul>';	
	$("#all").after(ul);
	setTimeout(function() {
		$($(".end_expandable")[0]).trigger("click");
	}, 1)
	
	$("#subBtn").click(download);
	
	function download(){
	    obj = $('input[name="org_ids"]');
	    check_val = [];
	    obj.each(function(i){
	      if($(this).is(":checked")){    
	      	if($(this).val()!="on"){
	          check_val.push($(this).val());
	      	}        
	      }
	    });
	    isPillbox=check_val.toString();//已字符串形式获取
	    $("#dd").attr("href","${ctx}/manage/data/download?type="+$("#type").val()+"&ids="+isPillbox);
	    setTimeout(function(){
		    $("#dd").trigger("click");
	    },1);
	}
</script>
