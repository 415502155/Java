/**
 * 获取银行编号的下拉列表数据
 */
function getBankCodeList(selectObj, bankId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/dealerInfo/selectBankCodeList",
        success: function(data) {
            if(data != null && "" != data && data.length > 0){
                for(var i in data){
                    if(bankId == data[i].bank_id){
                        selectObj.append("<option value='"+data[i].bank_id+"' selected>"+data[i].bank_name+"</option>");
                    }else{
                        selectObj.append("<option value='"+data[i].bank_id+"'>"+data[i].bank_name+"</option>");
                    }
                    
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}