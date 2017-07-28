   //判断是否为空
    function  isNull(id) {
        var textValue = $.trim($("#" + id + "").val());
        if (textValue === "" || textValue.length === 0) {
            return false;
        } else {
            return true;
        }
    }
    //验证长度
    function checkLen(id, len) {
        var textValue = $.trim($("#" + id + "").val());
        var textLen = textValue.length * 2;
        if (textLen > len) {
            return false;
        } else {
            return true;
        }
    }
    //判断是否为数值型
    function isNumber(id){
         var textValue = $("#" + id + "").val();
         if(isNaN(textValue)){
             return false;
         }else{
             return true;
         }
    }