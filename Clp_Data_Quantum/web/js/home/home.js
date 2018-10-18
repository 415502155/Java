/**
 * 控制主页面左边导航栏js操作
 */
$(function() {
    //默认打开时显示系统用户管理页面
    $("#rightContent").attr({"src": contextPath + "/systemInfo/getSystemInfo"});
//    $("#rightContent").attr({"src": contextPath + "/dealerInfo/2add"});
//抽奖规则
    $("#gameluckyRule").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gameluckyRule/2select"});
    });
//游戏玩法
    $("#gameplayinfo").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gameplayinfo/2select"});
    });
//游戏信息
    $("#gameinfo").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gameinfo/list"});
//        $("#rightContent").attr({"src": contextPath + "/gameinfo/list", "height": "1100px"});
    });
//开奖次数
    $("#gameMultiOpen").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gameMultiOpen/2select"});
    });
//资金就分配
    $("#gameFundsPropor").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gameFundsPropor/2select"});
    });
//游戏奖级
    $("#gameClassInfo").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gameClassInfo/2select"});
    });
//代销商信息管理
    $("#dealerInfoSel").click(function() {
        $("#rightContent").attr({"src": contextPath + "/dealerInfo/select"});
    });
//代销商注册
    $("#dealerInfoAdd").click(function() {
        $("#rightContent").attr({"src": contextPath + "/dealerInfo/2add"});
    });
//终端信息
    $("#tmninfoSel").click(function() {
        $("#rightContent").attr({"src": contextPath + "/tmninfo/selectAll"});
    });
    $("#tmnRegister").click(function() {
        $("#rightContent").attr({"src": contextPath + "/tmninfo/2add"});
    });
//系统用户管理
    $("#sysuserSelect").click(function() {
        $("#rightContent").attr({"src": contextPath + "/user/list"});
    });
    $("#sysuserAdd").click(function() {
        $("#rightContent").attr({"src": contextPath + "/user/2add"});
    });
    $("#sysuserModifyPwd").click(function() {
        $("#rightContent").attr({"src": contextPath + "/user/2modifypwd"});
    });
//    期信息
    $("#drawinfoAdd").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gamedraw/2add"});
    });
    $("#currentDrawSaleTime").click(function() {
        $("#rightContent").attr({"src": contextPath + "/saleTimeEdit/2saleTime"});
    });
    $("#drawinfoSelect").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gamedraw/2select"});
    });
    $("#kdrawinfoSelect").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gamedraw/2kselect"});
    });
    //开户
    $("#openAccount").click(function() {
        $("#rightContent").attr({"src": contextPath + "/accountInfo/2add"});
    });
//    开奖
    $("#drawlucky").click(function() {
        $("#rightContent").attr({"src": contextPath + "/drawlucky/2select"});
    });
    //游戏规则文件生成
    $("#gameRuleFileMake").click(function() {
        $("#rightContent").attr({"src": contextPath + "/gameRule/2makeFilePage"});
    });
    //彩票公告文件生成
    $("#ticketBulletinFileMake").click(function() {
        $("#rightContent").attr({"src": contextPath + "/ticketBulletin/2makeFilePage"});
    });
    //缓存数据同步
    $("#synehcachedata").click(function() {
        $("#rightContent").attr({"src": contextPath + "/synehcache/2dataSynPage"});
    });


    //每次点击左边菜单时执行 
    $("a").click(function() {
        var iframeObj = document.getElementById("rightContent");
        setTimeout(function() {
            iframeAutoFit(iframeObj);
        }, 400);
    });

});
//iframe嵌套页面时执行
function iFrameLoad(iframeObj) {
    setTimeout(function() {
        iframeAutoFit(iframeObj);
    }, 100);
}
//调节iframe高度
function iframeAutoFit(iframeObj) {
    var leftHeight = $("#nav").height();
    if (!iframeObj)
        return;
//    iframeObj.height = (iframeObj.Document ? iframeObj.Document.body.scrollHeight : iframeObj.contentDocument.body.offsetHeight);
    if (iframeObj.contentDocument && iframeObj.contentDocument.body) { //如果用户的浏览器是NetScape 
        iframeObj.height = iframeObj.contentDocument.body.offsetHeight;
    } else if (iframeObj.Document && iframeObj.Document.body.scrollHeight) {     //如果用户的浏览器是IE 
        iframeObj.height = iframeObj.Document.body.scrollHeight;
    }
    if (iframeObj.height < 790) {
        iframeObj.height = leftHeight;
    }
}