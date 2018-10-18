/**
 * 终端列表页面 js
 */
$(function() {
    /**
     * 初始化地市列表
     */
    getCityInfoList($("#city_id"));
    /**
     * 根据条件查询列表数据
     */
    $("#search_button").bind("click", function(e) {
        var cityId = $("#city_id").val().trim();
        var tmnId = $("#terminal_id").val().trim();
        if ("" == cityId || "" == tmnId) {
            alert("查询条件：地市或终端机编号不能为空！");
            return;
        }
        var dealerId = $("#dealer_id").val().trim();
        window.location.href = contextPath + "/tmninfo/selectByCondition?cityId="+cityId+"&tmnId="+tmnId+"&dealerId="+dealerId;
    });

    /**
     * 注册按钮
     */
    $("#add_button").bind("click", function(e) {
//        window.open(contextPath + "/tmninfo/2add");
       window.location.href=contextPath + "/tmninfo/2add";
    });
});

/**
 * 根据终端机编号查询修改相关的特权列表
 */
function editPrivilege(tmnId) {
    window.location.href=contextPath + "/tmninfo/2modifyPrivilege?tmnId=" + tmnId;
}

/**
 * 根据终端机编号修改终端信息
 */
function edit(tmnId) {
    window.location.href=contextPath + "/tmninfo/2modify?tmnId=" + tmnId;
//    window.open(contextPath + "/tmninfo/2modify?tmnId=" + tmnId);
}