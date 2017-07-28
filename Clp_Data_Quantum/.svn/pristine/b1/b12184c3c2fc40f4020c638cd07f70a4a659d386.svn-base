/**
 * 从缓存中获取软件类型列表数据
 */
function getSoftwareTypeList(selectObj, softwareId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/tmninfo/selectSoftwareTypeList",
        success: function(data) {
            if(data != null && "" != data && data.length > 0){
                for(var i in data){
                    if(softwareId == data[i].software_id){
                        selectObj.append("<option value='"+data[i].software_id+"' selected>"+data[i].software_name+"</option>");
                    }else{
                        selectObj.append("<option value='"+data[i].software_id+"'>"+data[i].software_name+"</option>");
                    }
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}