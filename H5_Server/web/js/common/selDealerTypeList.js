/**
 * 获取代销商类型的下拉列表数据
 */
function getDealerTypeList(selectObj, dealerType) {
    $.ajax({
        type: "POST",
        url: contextPath + "/dealerInfo/selectDealerTypeList",
        success: function(data) {
            if (data != null && "" != data && data.length > 0) {
                for (var i in data) {
                    if (dealerType == data[i].dealer_type) {
                        selectObj.append("<option value='" + data[i].dealer_type + "' selected>" + data[i].dealer_type_name + "</option>");
                    } else {
                        selectObj.append("<option value='" + data[i].dealer_type + "'>" + data[i].dealer_type_name + "</option>");
                    }
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}