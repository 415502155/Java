/**
 * 代销商修改
 */
$(function() {
    /**
     * 初始化该页面的所有下拉列表数据
     */
    getDealerTypeList($("#dealer_type"),dealerType);
    getSystemInfoList($("#province_id"),provinceId,cityId);
    $("#province_id").bind("change", function() {
        var provinceId = $(this).val();
        getCityInfoListByProvinceId($("#city_id"), provinceId);
    });
    getIdTypeList($("#id_type_id"), idTypeId);
    getBankCodeList($("#bank_id"), bankId);
    initDealerPrivilege($("#gameInfoCkxs"),dpArr);
    
    $('#DealerinfoForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            dealer_name: {
                validators: {
                    notEmpty: {
                        message: '代销商名称不能为空'
                    }
                }
            }
        }
    });

    /**
     * 修改
     */
    $("#DealerinfoButton").bind("click", function() {
        $('#DealerinfoForm').bootstrapValidator('validate')
                .on('error.form.bv', function(e) {
                   return;
                }).on('success.form.bv',function(e){
//                    alert($("#DealerinfoForm").serialize()+"--------");
                    var dealerPrivilegeArr = [];
                    $("#gameInfoCkxs").find("input:checkbox:checked").each(function(i,o){
                        var game_id = (o.id.split("_"))[1];
                        var spInput = $(this).parent().parent().next();
                        var service_proportion = spInput.val().trim();
                        var game_permit = spInput.next().val().trim();
                        dealerPrivilegeArr.push({"game_id":game_id,"service_proportion":service_proportion,"game_permit":game_permit});
                    });
                    var dealerPrivilegeStr = JSON.stringify(dealerPrivilegeArr);
//                    alert("dealerPrivilegeStr: "+dealerPrivilegeStr);
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/dealerInfo/modify?dealerPrivilegeStr="+dealerPrivilegeStr,
                        data: $("#DealerinfoForm").serialize(),
                        success: function(data) {
                            alert(data.result);
                            if("修改成功" == data.result){
                                window.location.href = contextPath + "/dealerInfo/select";
                            }
                        },
                        error: function(e) {
                            alert("网络异常，请稍后重试！");
                        }
                    });
                });
    });
});
