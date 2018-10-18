/**
 * 获取二级城市的下拉列表数据
 */
function getCityInfoList(selectObj, city_id) {
    $.ajax({
        type: "POST",
        url: contextPath + "/dealerInfo/selectCityInfoList",
        success: function(data) {
            if (data != null && "" != data && data.length > 0) {
                for (var i in data) {
                    if (city_id == data.city_id) {
                        selectObj.append("<option name='" + data[i].province_id + "' value='" + data[i].city_id + "' selected>" + data[i].city_name + "</option>");
                    } else {
                        selectObj.append("<option name='" + data[i].province_id + "' value='" + data[i].city_id + "'>" + data[i].city_name + "</option>");
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
function getCityInfoListByProvinceId1(selectObj, provinceId, cityId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/dealerInfo/selectCityInfoListByProvinceId",
        data: {"provinceId": provinceId},
        success: function(data) {
            selectObj.empty();
            if (data != null && "" != data && data.length > 0) {
                for (var i in data) {
                    if (cityId == data[i].city_id) {
                        selectObj.append("<option value='" + data[i].city_id + "' selected>" + data[i].city_name + "</option>");
                    } else {
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

/**
 * 根据省系统Id获取二级城市的下拉列表数据，并根据城市编号查出对应的三级区县信息，放入区县列表中
 */
function getCityInfoListByProvinceId(selectObj, provinceId, cityId, districtId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/dealerInfo/selectCityInfoListByProvinceId",
        data: {"provinceId": provinceId},
        success: function(data) {
            selectObj.empty();
            if (data != null && "" != data && data.length > 0) {
                for (var i in data) {
                    if (cityId == data[i].city_id) {
                        selectObj.append("<option value='" + data[i].city_id + "' selected>" + data[i].city_name + "</option>");
                        getDistrictInfoListByProvinceIdAndCityId($("#district_id"), provinceId, cityId, districtId);
                    } else {
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