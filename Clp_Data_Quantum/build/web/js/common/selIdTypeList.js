/**
 * 获取证件类型的下拉列表数据
 */
function getIdTypeList(selectObj, idTypeId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/dealerInfo/selectIdTypeList",
        success: function(data) {
            if(data != null && "" != data && data.length > 0){
                for(var i in data){
                    if(idTypeId == data[i].id_type_id){
                        selectObj.append("<option value='"+data[i].id_type_id+"' selected>"+data[i].id_type_name+"</option>");
                    }else{
                        selectObj.append("<option value='"+data[i].id_type_id+"'>"+data[i].id_type_name+"</option>");
                    }
                    
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}