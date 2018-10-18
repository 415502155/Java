/**
 * 终端修改
 */
$(function() {
    /**
     * 初始化该页面的所有下拉列表数据
     */
    getDealerInfoList($("#dealer_id"),dealerId);
    getCityInfoListByProvinceId($("#city_id"),provinceId,cityId,districtId);
    getSoftwareTypeList($("#software_id"),softwareId);
    $("#dealer_id").bind("change", function() {
        var dealerId = $(this).val();
        $.ajax({
            type: "POST",
            data:{"dealerId":dealerId},
            url: contextPath + "/tmninfo/selectDealerInfoById",
            success: function(data) {
                if (data != null && "" != data) {
                    var province_id = data.province_id;
                    var city_id = data.city_id;
                    var dealer_name = data.dealer_name;
                    var dealer_address = data.dealer_address;
                    var dealer_phone = data.dealer_phone;
                    var owner_name = data.owner_name;
                    var owner_phone = data.owner_phone;
                    var link_name = data.link_name;
                    var link_phone = data.link_phone;
                    getCityInfoListByProvinceId($("#city_id"),province_id,city_id);
                    $("#city_id").bind("change",function(e){
                        var cityId = $(this).val();
                        getDistrictInfoListByProvinceIdAndCityId($("#district_id"),province_id,cityId);
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
    });
    
    $('#TmninfoForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            terminal_serial_no: {
                validators: {
                    notEmpty: {
                        message: '投注机序号不能为空'
                    }
                }
            }
        }
    });

    /**
     * 修改
     */
    $("#edit_button").bind("click", function() {
        $('#TmninfoForm').bootstrapValidator('validate')
                .on('error.form.bv', function(e) {
                   return;
                }).on('success.form.bv',function(e){
//                    alert($("#TmninfoForm").serialize()+"--------");
//                    var dealerPrivilegeArr = [];
//                    $("#gameInfoCkxs").find("input:checkbox:checked").each(function(i,o){
//                        var game_id = (o.id.split("_"))[1];
//                        var service_proportion = $(this).parent().parent().next().val().trim();
//                        dealerPrivilegeArr.push({"game_id":game_id,"service_proportion":service_proportion});
//                    });
//                    var dealerPrivilegeStr = JSON.stringify(dealerPrivilegeArr);
//                    alert("dealerPrivilegeStr: "+dealerPrivilegeStr);
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/tmninfo/update",
                        data: $("#TmninfoForm").serialize(),
                        success: function(data) {
                            alert(data.result);
                            if("修改成功" == data.result){
                                window.location.href = contextPath + "/tmninfo/selectAll";
                            }
                        },
                        error: function(e) {
                            alert("网络异常，请稍后重试！");
                        }
                    });
                });

    });
});
