
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
        <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>支付结果</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <link rel="shortcut icon" href="/favicon.ico">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <!--ui包-->
        <link rel="stylesheet" href="/shijiwxy/weixin/css/sm.css">
        <link rel="stylesheet" href="/shijiwxy/weixin/css/layout.css">
        <style>
                .list-block ul {
                    border-bottom: none;
                }

                li {
                    border-bottom: 1px solid #e7e7e7
                }

                .list-block .item-inner {
                    border: none
                }
        </style>
        </head>
        <body>
                <div class="page-group">
                    <div class="page page-current content native-scroll">

                        <div class="weui-msg">
                            <div class="weui-msg__icon-area" id="tipIcon">
                                <div class="info-icon">
                                    <i class="icon icon-check"></i>
                                </div>
                            </div>
                            <div class="weui-msg__text-area">
                                <h2 class="weui-msg__title" id="tipTitle">支付成功</h2>
                                <span class="weui-msg__desc" id="payqueryId">交易单号
                                    ${result.data.queryId}</span>
                            </div>
                            <!--报名列表-->
                            <div class="list-block" id="allClass">
                                <ul>
                                    <li class="item-content">
                                        <div class="item-inner">
                                            <div class="item-title">支付金额</div>
                                            <div class="item-after failedTip_text" id="paymoney" >${result.data.money}</div>
                                        </div>
                                    </li>
                                    <li class="item-content">
                                        <div class="item-inner">
                                            <div class="item-title">商品名称</div>
                                            <div class="item-after successTip_text" id="payitem">${result.data.item}</div>
                                        </div>
                                    </li>
                                    <li class="item-content">
                                        <div class="item-inner">
                                            <div class="item-title">交易时间</div>
                                            <div class="item-after successTip_text" id="paytime">${result.data.time}</div>
                                        </div>
                                    </li>
                                    <div class="item-after successTip_text" id="payorgid" style="display:none">${result.data.org_id}</div>
                                    <div class="item-after successTip_text" id="payopenid" style="display:none">${result.data.openid}</div>
                                </ul>
                            </div>
                            <!--报名列表 ENd-->
                            <div class="content-block">
                              <!--   <a href="/shijiwxy/weixin/html/parent/payOnline/index.html" class="button button-fill" id="toPay">返回<span></span></a> -->
                                <a href="/shijiwxy/weixin/html/parent/login/index.html?org_id=${result.data.org_id}&openid=${result.data.openid}" class="button button-fill external"
                                    id="toBind" style="margin-top:1rem">返回主页</a>
                            </div>
                        </div>
                    </div>
                </div>
        </body>
</html>
<script src='/shijiwxy/weixin/js/zepto.min.js'></script>
<script src='/shijiwxy/weixin/js/sm.min.js'></script>
<script src='/shijiwxy/weixin/js/config.js'></script>
<script>
$(function(){
        var base = null;
        //如果从通过微信消息模板登录
        if(sessionStorage.baseUser==undefined || sessionStorage.baseUser== null){

        var nowOrg_id=$("#payorgid").text();
        var nowOpenid=$("#payopenid").text();

        var parameter={
        openid:nowOpenid,
        identity:0,
        org_id:nowOrg_id,
        version:3
        }

        getData(domainName+"/esb/api/wxredirect",parameter,function(res){
            if(res.code==200 && res.success==true){
                    sessionStorage.baseUser=JSON.stringify(res.data);
                    base =JSON.parse(sessionStorage.baseUser);
                    sessionStorage.nowChild="${result.data.stud_id}"
            }else{
                    $.alert(res.message);
            }
        });

        };

       sessionStorage.payed=true;

       //存储到本地
      if($("#queryId").text()==""||$("#payopenid").text()==""){
            $("#payqueryId").text(sessionStorage.payqueryId);
            $("#paymoney").text(sessionStorage.paymoney);
            $("#payitem").text(sessionStorage.payitem);
            $("#paytime").text(sessionStorage.paytime);
            $("#payorgid").text(sessionStorage.payorgid);
            $("#payopenid").text(sessionStorage.payopenid);
            $("#toBind").attr("href","/shijiwxy/weixin/html/parent/login/index.html?org_id="+sessionStorage.payorgid+"&openid="+sessionStorage.payopenid);
	    }else{
	        sessionStorage.payqueryId=$("#payqueryId").text();
	        sessionStorage.paymoney=$("#paymoney").text();
	        sessionStorage.payitem=$("#payitem").text();
	        sessionStorage.paytime=$("#paytime").text();
	        sessionStorage.payorgid=$("#payorgid").text();
	        sessionStorage.payopenid=$("#payopenid").text();
	    }


});
</script>
