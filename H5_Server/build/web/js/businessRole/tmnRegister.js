/**
 * 终端注册
 */
$(function() {
    /**
     * 初始化该页面的所有下拉列表数据
     */
    getDealerInfoList($("#dealer_id"));
    $("#dealer_id").bind("change", function() {
        var dealerId = $(this).val();
        getDealerInfoById(dealerId);
    });
    /**
     * 得到软件类型的下拉列表数据
     */
    getSoftwareTypeList($("#software_id"));

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
     * 注册
     */
    $("#add_button").bind("click", function() {
//        $('#TmninfoForm').bootstrapValidator('validate')
//                .on('error.form.bv', function(e) {
//                    return;
//                }).on('success.form.bv', function(e) {
//                    alert($("#TmninfoForm").serialize());
        var tmnId = $.trim($("#terminal_id").val());
        var reg = /^\d+$/;
        if (!tmnId.match(reg)) {
            alert("终端编号：只能输入数字");
            return;
        }
        if (tmnId.length != 4) {
            alert("终端编号：必须为4位");
            return;
        }
        $.ajax({
            type: "POST",
            url: contextPath + "/tmninfo/add",
            data: $("#TmninfoForm").serialize(),
            success: function(data) {
                alert(data.result);
                if ("注册成功" == data.result) {
                    var terminalId = data.terminalId;
                    window.location.href = contextPath + "/tmninfo/2privilegeAdd?terminalId=" + terminalId;
                }
            },
            error: function(e) {
                alert("网络异常，请稍后重试！");
            }
        });
//        });

    });
});

