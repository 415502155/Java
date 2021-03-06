<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>新增玩法</title>
        <script type="text/javascript" src="<c:url value='/js/common/jquery-1.8.3.min.js'/> "></script>
        <script>
            $(function() {
                $("#add_button").click(function() {
                    $.ajax({
                        cache: true,
                        type: "POST",
                        url: "<%=request.getContextPath()%>/gameplayinfo/add",
                        data: $('#gamePlayInfoAddForm').serialize(),
                        async: false,
                        error: function(request) {
                            alert("Connection error");
                        },
                        success: function(data) {
                            if (data.result === "success") {
                                alert(data.msg);
                                window.location.href = "<%=request.getContextPath()%>/gameplayinfo/list";
                            } else {
                                alert(data.msg);
                            }
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h1>add game play info!!!</h1>
        <form id="gamePlayInfoAddForm" method="post">
            游戏编号<input type="text" id="game_id" name="game_id"/><br/>
            玩法编号<input type="text" id="play_id" name="play_id"/><br/>
            总期号<input type="text" id="draw_id" name="draw_id"/><br/>
            玩法名称<input type="text" id="play_name" name="play_name"/><br/>
            玩法类型<input type="text" id="play_type" name="play_type"/><br/>
            单注金额<input type="text" id="stakes_price" name="stakes_price"/><br/>
            单注最大倍数<input type="text" id="max_multiple" name="max_multiple"/><br/>
            投注号码个数<input type="text" id="bet_no_num" name="bet_no_num"/><br/>
            最小号码<input type="text" id="bet_min_no" name="bet_min_no"/><br/>
            最大号码<input type="text" id="bet_max_no" name="bet_max_no"/><br/>
            蓝球个数<input type="text" id="blue_no_num" name="blue_no_num"/><br/>
            最小蓝球<input type="text" id="blue_min_no" name="blue_min_no"/><br/>
            最大蓝球<input type="text" id="blue_max_no" name="blue_max_no"/><br/>
            号码可重复<input type="text" id="no_repeat" name="no_repeat"/><br/>
            号码有序<input type="text" id="no_order" name="no_order"/><br/>
            符号个数<input type="text" id="sign_num" name="sign_num"/><br/>
            奖金和比例<input type="text" id="prize_proportion" name="prize_proportion"/><br/>
            工作状态<input type="text" id="work_status" name="work_status"/><br/>
            <input type="button" id="add_button" value="增加"/>
        </form>
    </body>
</html>
