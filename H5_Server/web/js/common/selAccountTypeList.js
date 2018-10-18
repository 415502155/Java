/**
 * 获取账户类型的下拉列表数据
 */
function getAccountTypeList(selectObj, accTypeId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/accountInfo/selectAccountTypeList",
        success: function(data) {
            if(data != null && "" != data && data.length > 0){
                for(var i in data){
                    if(accTypeId == data[i].account_type_id){
                        selectObj.append("<option value='"+data[i].account_type_id+"' selected>"+data[i].account_type_name+"</option>");
                    }else{
                        selectObj.append("<option value='"+data[i].account_type_id+"'>"+data[i].account_type_name+"</option>");
                    }
                    
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}