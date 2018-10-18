<%-- 
    Document   : newjsp1
    Created on : 2014-8-7, 14:50:17
    Author     : chenliping
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Stylesheets -->
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <!-- Font awesome icon -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/font-awesome.css">
        <!-- Date picker -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap-datetimepicker.min.css">
        <!-- Main stylesheet -->
        <link href="<%=request.getContextPath()%>/css/style/style.css" rel="stylesheet">
    </head>
    <body>
        <div class="content">
            <h5>Datepicker</h5>
            <div id="datetimepicker1" class="col-lg-4">
                <input data-format="yyyy-MM-dd hh:mm:ss" type="text" class="form-control dtpicker">
                <span class="add-on">
                    <i data-time-icon="icon-time" data-date-icon="icon-calendar" class="btn btn-info btn-lg"></i>
                </span>
            </div>
            <hr />
            <h5>Timepicker</h5>
            <div id="datetimepicker2" class="input-append">
                <input data-format="hh:mm:ss" class="form-control dtpicker" type="text">
                <span class="add-on">
                    <i data-time-icon="icon-time" data-date-icon="icon-calendar" class="btn btn-info btn-lg"></i>
                </span>
            </div>
        </div>

        <!-- JS -->
        <script src="<%=request.getContextPath()%>/js/test/jquery.js"></script> 
        <script src="<%=request.getContextPath()%>/js/test/bootstrap.js"></script> 
        <script src="<%=request.getContextPath()%>/js/test/jquery-ui-1.9.2.custom.min.js"></script> 
        <script src="<%=request.getContextPath()%>/js/test/fullcalendar.min.js"></script> 
        
        <script src="<%=request.getContextPath()%>/js/test/bootstrap-datetimepicker.min.js"></script>  
        <script src="<%=request.getContextPath()%>/js/test/custom.js"></script> 
    </body>
</html>
