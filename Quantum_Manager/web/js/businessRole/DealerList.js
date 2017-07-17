/**
 * 代销商列表页面 js
 */
$(function(){
    /**
     * 根据条件查询列表数据
     */
    $("#search_button").bind("click",function(e){
         alert("查询！");       
    });
    
    /**
     * 注册按钮
     */
    $("#add_button").bind("click",function(e){
        window.location.href=contextPath+"/dealerInfo/2add";
         //  window.open(contextPath+"/dealerInfo/2add");
    });
});

/**
 * 根据代销商编号查询对应的游戏特权信息
 */
function dealerPrivilege(dealerId){
    window.showModalDialog(contextPath+"/dealerInfo/dealerPrivilegeListPage",dealerId,"dialogWidth=800px;dialogHeight=600px");
}

/**
 * 根据代销商编号修改代销商信息
 */
function edit(dealerId){
     //window.open(contextPath+"/dealerInfo/2modify?dealerId="+dealerId);
     window.location=contextPath+"/dealerInfo/2modify?dealerId="+dealerId;
}