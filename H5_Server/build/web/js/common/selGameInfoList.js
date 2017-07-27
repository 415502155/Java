/**
 * 获取游戏名称下拉列表数据
 */
function getGameInfoList(selectObj) {
    $.ajax({
        type: "POST",
        url: contextPath + "/gameinfo/getGameinfoCache",
        success: function(data) {
            if (data !== null && "" !== data && data.length > 0) {
                for (var i in data) {
                    $("<option value=" + data[i]["game_id"] + ">" + data[i]["game_name"] + "</option>").appendTo(selectObj);
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}



//function initDealerPrivilege1(gameDiv,inputDiv) {
//    $.ajax({
//        type: "POST",
//        url: contextPath + "/gameinfo/getGameInfoSelect",
//        success: function(data) {
//            if (data !== null && "" !== data && data.length > 0) {
//                for (var i in data) {
//                    $("<div class='checkbox' style='margin-bottom: 10px'><label><input type='checkbox' name='game' id='game"+data[i]["game_id"]+"'>"
//                            +data[i]["game_name"]+"</label></div>").appendTo(gameDiv);
//                    $("<input type='text' class='form-control' style='margin-bottom: 2px' id='service_proportion"+data[i]["game_id"]+"' placeholder='代销商缴费'>").appendTo(inputDiv);
//                }
//            }
//        },
//        error: function(e) {
//            alert("网络异常，请稍后重试！");
//        }
//    });
//}

/**
 * 初始化代销商特权列表，以复选框的形式展现
 */
function initDealerPrivilege(gameDiv, dpArr) {
    $.ajax({
        type: "POST",
        url: contextPath + "/gameinfo/getGameinfoCache",
        success: function(data) {
            if (data !== null && "" !== data && data.length > 0) {
                for (var i in data) {
                    var flag = false;
                    for (var j in dpArr) {
                        if (dpArr[j].gameId == data[i]["game_id"]) {
                            flag = true;
                            $("<div class='checkbox col-md-2' style='margin-bottom: 10px'><label><input type='checkbox' name='game' id='game_" + data[i]["game_id"] + "' checked>"
                                    + data[i]["game_name"] + "</label></div>").appendTo(gameDiv);
                            $("<input type='text' class='form-control col-md-7' style='margin-bottom: 2px' id='service_proportion" + data[i]["game_id"] + "' placeholder='代销商缴费' value='"+dpArr[j].bd+"'>").appendTo(gameDiv);
                            $("<input type='text' class='form-control col-md-7' style='margin-bottom: 2px' id='game_permit" + data[i]["game_id"] + "' placeholder='游戏许可' value='"+dpArr[j].game_permit+"'>").appendTo(gameDiv);
                            break;
                        }
                    }
                    if (!flag) {
                        $("<div class='checkbox col-md-2' style='margin-bottom: 10px'><label><input type='checkbox' name='game' id='game_" + data[i]["game_id"] + "'>"
                                + data[i]["game_name"] + "</label></div>").appendTo(gameDiv);
                        $("<input type='text' class='form-control col-md-7' style='margin-bottom: 2px' id='service_proportion" + data[i]["game_id"] + "' placeholder='代销商缴费'>").appendTo(gameDiv);
                        $("<input type='text' class='form-control col-md-7' style='margin-bottom: 2px' id='game_permit" + data[i]["game_id"] + "' placeholder='游戏许可'>").appendTo(gameDiv);
                    }
                }
            }
        },
        error: function(e) {
            alert("网络异常，请稍后重试！");
        }
    });
}
