/**
 * 获取省系统的下拉列表数据
 */
function getSystemInfoList(selectObj,provinceId,cityId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/dealerInfo/selectSystemInfoList",
        success: function(data) {
            if(data != null && "" != data && data.length > 0){
                for(var i in data){
                    if(provinceId == data[i].province_id){
                        selectObj.append("<option value='"+data[i].province_id+"' selected>"+data[i].province_name+"</option>");
                        getCityInfoListByProvinceId($("#city_id"), provinceId,cityId);
                    }else{
                        if(i == 0){
                            selectObj.append("<option value='"+data[i].province_id+"' selected>"+data[i].province_name+"</option>");
                            getCityInfoListByProvinceId($("#city_id"), data[i].province_id);
                        }else{
                            selectObj.append("<option value='"+data[i].province_id+"'>"+data[i].province_name+"</option>");
                        }
                        
                    }
                    
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}