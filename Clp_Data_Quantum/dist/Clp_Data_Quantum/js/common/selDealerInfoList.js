/**
 * 获取代销商信息的下拉列表数据
 */
function getDealerInfoList(selectObj, dealerId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/tmninfo/selectDealerInfoList",
        success: function(data) {
            if (data != null && "" != data && data.length > 0) {
                for (var i in data) {
                    if (dealerId == data[i].dealer_id) {
                        selectObj.append("<option value='" + data[i].dealer_id + "' selected>" + data[i].dealer_name + "</option>");
                    } else {
                        if(i == 0){
                            selectObj.append("<option value='" + data[i].dealer_id + "' selected>" + data[i].dealer_name + "</option>");
                            getDealerInfoById(data[i].dealer_id);
                        }else{
                             selectObj.append("<option value='" + data[i].dealer_id + "'>" + data[i].dealer_name + "</option>");
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

function getDealerInfoById(dealerId) {
    $.ajax({
        type: "POST",
        data: {"dealerId": dealerId},
        url: contextPath + "/tmninfo/selectDealerInfoById",
        success: function(data) {
            if (data != null && "" != data) {
                var province_id = data.province_id;
                $("#province_id").val(province_id);
                var city_id = data.city_id;
                var dealer_name = data.dealer_name;
                var dealer_address = data.dealer_address;
                var dealer_phone = data.dealer_phone;
                var owner_name = data.owner_name;
                var owner_phone = data.owner_phone;
                var link_name = data.link_name;
                var link_phone = data.link_phone;
                getCityInfoListByProvinceId($("#city_id"), province_id, city_id);
                $("#city_id").bind("change", function(e) {
                    var cityId = $(this).val();
                    getDistrictInfoListByProvinceIdAndCityId($("#district_id"), province_id, cityId);
                });
                $("#station_name").val(dealer_name);
                $("#terminal_address").val(dealer_address);
                $("#station_phone").val(dealer_phone);
                $("#owner_name").val(owner_name);
                $("#owner_phone").val(owner_phone);
                $("#linkman").val(link_name);
                $("#linkman_phone").val(link_phone);
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}