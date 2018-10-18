/**
 * 代销商注册
 */
$(function() {
    /**
     * 初始化该页面的所有下拉列表数据
     */
    getDealerTypeList($("#dealer_type"));
    getSystemInfoList($("#province_id"));
    $("#province_id").bind("change", function() {
        var provinceId = $(this).val();
        getCityInfoListByProvinceId($("#city_id"), provinceId);
    });
    getIdTypeList($("#id_type_id"));
    getBankCodeList($("#bank_id"));
    initDealerPrivilege($("#gameInfoCkxs"));
//    initDealerPrivilege($("#gameInfoCkxs"),$("#serviceProportionInput"));
    /**
     * 校验
     */
//    $("#dealer_id").bind("blur",function(){
//        var val = $(this).val();
//        var span = $(this).parent().next().find("span").get(0);
//        if("" == val.trim()){
//            addErrorStyle($(this).parent().parent());
//            span.innerHTML = "代销商编号不能为空";
//        }else{
//            removeErrorStyle($(this).parent().parent());
//            span.innerHTML = "";
//        }
//    });
    $('#DealerinfoForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            dealer_id: {
                validators: {
                    notEmpty: {
                        message: '代销商编号不能为空'
                    }
                }
            },
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
     * 注册
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
//                    alert($("#DealerinfoForm").serialize());
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/dealerInfo/add?dealerPrivilegeStr="+dealerPrivilegeStr,
                        data: $("#DealerinfoForm").serialize(),
                        success: function(data) {
                            alert(data.result);
                            if("注册成功" == data.result){
                                window.location.reload();
                            }
                        },
                        error: function(e) {
                            alert("网络异常，请稍后重试！");
                        }
                    });
                });

    });
});












