<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>用户列表</title>
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">        
        <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style/style.css" >    
    </head>
    <body>
        <div class="content">
            <div class="mainbar">
                <div class="matter">
                    <div class="container">
                        <!-- Table -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="widget">
                                    <div class="widget-head">
                                        <div class="pull-left">系统用户列表</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>  
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="widget-content">
                                        <table class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>用户名</th>
                                                    <th>地市</th>
                                                    <th>真实姓名</th>
                                                    <th>注册时间</th>
                                                    <th>部门</th>
                                                    <th>角色</th>
                                                    <th>状态</th>
                                                    <th>操作</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${page.rows}" var="user" >
                                                    <tr>
                                                        <th>${user.user_name}</th>
                                                        <th>${user.city_id}</th>
                                                        <th>${user.real_name}</th>
                                                        <th><fmt:formatDate value="${user.regist_date}" type="both"/></th>
<!--                                                        <th>${user.department_id}</th>-->
                                                         <th>${user.department_name}</th>
                                                        <th>${user.role_name}</th>
                                                        <th>${user.work_status=="1"?"启用":"不启用"}</th>
                                                        <th><a href="">查询权限</a> &nbsp;&nbsp;<a href="">修改权限</a> &nbsp;&nbsp;<a href="">修改用户信息</a></th>
                                                    </tr>
                                                </c:forEach>                                                                                                                       
                                            </tbody>
                                        </table>

                                        <div class="widget-foot">
                                            <ul class="pagination pull-right">
                                                <li><a href="#">Prev</a></li>
                                                <li><a href="#">1</a></li>
                                                <li><a href="#">2</a></li>
                                                <li><a href="#">3</a></li>
                                                <li><a href="#">4</a></li>
                                                <li><a href="#">Next</a></li>
                                            </ul>
                                            <div class="clearfix"></div> 
                                        </div>
                                    </div>
                                </div>                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/test/bootstrap-datetimepicker.js"></script>
<!--        <script language="javascript">
            $(function() {
                $("#userbody").find("th[name='datetimepicker']").each(function(i, o) {
                    $(o).datetimepicker({
                        format: 'yyyy-mm-dd hh:ii'
                    });
                });
            });
        </script>-->
        <script language="javascript">
            $(function() {
                $("#add_button").click(function() {
                    window.location.href = "<%=request.getContextPath()%>/user/2add";
                });
            });
        </script>
    </body>
</html>
