<%-- 
    Document   : FsTest
    Created on : 2014-9-17, 17:02:35
    Author     : YangRong 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>文件客户端测试</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap.css" >
        <link href="<%=request.getContextPath()%>/css/style/style.css" rel="stylesheet"> 
        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
    </head>
    <body>
        <br>
        <br>
        <div>
        <label for="pro_id" class="control-label text-right col-md-2 ">协议编号：</label>
        <div class="col-md-8">
            <input type="text" class="form-control" id="pro_id" name="pro_id" value=""  />
        </div>
        </div>
        <br>
        <div>
        <label for="retn" class="control-label text-right col-md-2 ">返回值：</label>
        <div class="col-md-3">
            <input type="text" class="form-control" id="retn" name="retn" value="" />
        </div>
        </div>
        <br>
        <input type="button" id="Button1" class="btn btn-primary" value="测试" onclick="submitTest()">
        <div>
        <br>
        <p>4103 tt: ticket_type   rt:raffle_times  pf;prize_file  gi:game_id  di:draw_id  kdi:kdraw_id  pnn:prize_no_num  pn:prize_no snn:special_no_num sn:special_no</p>
        <br>
        <p>4103?tt=1&rt=2&pf=1&gi=1&di=1&kdi=0&pnn=7&pn="010203040506"&snn=1&sn="07"</p>
        <br>
        <p>4104 ot:opentimes gi:game_id   di:draw_id    kdi:kdraw_id   pcn:prize_class_num pc:prize_class pm:prize_money </p>
        <br>
        <P>4104?ot=1&gi=1&di=1&kdi=0&pcn=2&pc0=1&pm0=2000&pc1=2&pm1=3000</P>
        </div>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
            function submitTest() {
                $("#retn").val("");
                $.ajax({
                    type: "POST",
                    url: contextPath + "/TestFs/" + $("#pro_id").val(),
                    success: function(data) {
                       $("#retn").val(data);

                    },
                    error: function(e) {
                        alert("网络异常，请稍后重试！");
                    }
                });
            }
        </script>

    </body>
</html>
