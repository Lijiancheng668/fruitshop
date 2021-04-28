<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>权限不足</title>

    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
    <style>
        .fangsheng{
            /* 要把阴影与大小配合好，一般来说大小都是偏大时采用 */
            font-family: "Arial Rounded MT Bold", "Helvetica Rounded", Arial, sans-serif;
            text-transform: uppercase;/* 全开大写 */
            font-size: 92px;
            color: #f1ebe5;
            text-shadow: 0 8px 9px #c4b59d, 0px -2px 1px #fff;
            font-weight: bold;
            letter-spacing: -4px;
            background: linear-gradient(to bottom, #ece4d9 0%,#e9dfd1 100%);
        }
        .main{
            text-align: center; /*让div内部文字居中*/
            background-color: #fff;
            border-radius: 20px;
            width: 800px;
            height: 350px;
            margin: auto;
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
        }
    </style>
</head>
<html>
<body>
<jsp:include page="top.jsp"/>
<div class="main">
<h1 class="fangsheng" align="center">对不起，您没有访问权限！</h1>
</div>
</body>
</html>
