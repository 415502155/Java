/**
 * 开户js
 */
$(function() {
    /**
     * 初始化日期控件
     */
    $('.form_datetime').datetimepicker({
        language: 'zh',
        todayBtn: 1,
        autoclose: true,
        todayHighlight: 1,
        forceParse: 0
    });

    /**
     * 初始化下拉列表数据
     */
    getAccountTypeList($("#account_type"));
    getBankCodeList($("#bank_id"));

    /**
     * 表单校验
     */
    $('#accountInfoForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            account_id: {
                validators: {
                    notEmpty: {
                        message: '账户编号不能为空'
                    }
                }
            }
        }
    });

    /**
     * 开户
     */
    $("#add_button").bind("click", function() {
        $('#accountInfoForm').bootstrapValidator('validate')
                .on('error.form.bv', function(e) {
                    return;
                }).on('success.form.bv', function(e) {
            $.ajax({
                type: "POST",
                url: contextPath + "/accountInfo/add",
                data: $("#accountInfoForm").serialize(),
                success: function(data) {
                    alert(data.result);
                    if ("开户成功" == data.result) {
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