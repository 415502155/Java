/**
 * 根据省市获取三级区县的下拉列表数据
 */
function getDistrictInfoListByProvinceIdAndCityId(selectObj,province_id,city_id,district_id) {
    $.ajax({
        type: "POST",
        data:{"province_id":province_id,"city_id":city_id},
        url: contextPath + "/tmninfo/getDistrictInfoListByProvinceIdAndCityId",
        success: function(data) {
            selectObj.empty();
            if (data != null && "" != data && data.length > 0) {
                for (var i in data) {
                    if(district_id == data[i].district_id){
                        selectObj.append("<option value='" + data[i].district_id + "' selected>" + data[i].district_name + "</option>");
                    }else{
                        selectObj.append("<option value='" + data[i].district_id + "'>" + data[i].district_name + "</option>");
                    }
                    
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}

/**
 * 根据省系统Id获取二级城市的下拉列表数据
 */
function getCityInfoListByProvinceId(selectObj, provinceId,cityId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/dealerInfo/selectCityInfoListByProvinceId",
        data: {"provinceId": provinceId},
        success: function(data) {
            selectObj.empty();
            if (data != null && "" != data && data.length > 0) {
                for (var i in data) {
                    if(cityId == data[i].city_id){
                        selectObj.append("<option value='" + data[i].city_id + "' selected>" + data[i].city_name + "</option>");
                    }else{
                        selectObj.append("<option value='" + data[i].city_id + "'>" + data[i].city_name + "</option>");
                    }
                    
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}