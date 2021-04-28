<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
    <link rel="stylesheet" href="css/book_info.css"/>
    <title>水果详情</title>
    <style>
        .row {
            margin-right: -15px;
            margin-left: -15px;
            margin-top: 20px;
        }
    </style>
    <script type="application/javascript">
        $(function () {

            $(".book_message li").click(function () {
                $(".book_message li").removeClass("active");
                $(this).addClass("active");
                $(".nav_content").hide();
                var name = $(this).attr("id");
                $("#" + name + "_content").show();
            });

            $("#num_add").click(function () {
                var num = parseInt($("#buy_num").val());
                if (num < 10) {
                    $("#buy_num").val(num + 1);
                }
            });

            $("#num_sub").click(function () {
                var num = parseInt($("#buy_num").val());
                if (num > 1) {
                    $("#buy_num").val(num - 1);
                }
            });
        });

        function buyNow(bookId) {
            location.href =  "<%=basePath%>" + "order/info?bookId=" + bookId + "&buyNum=" + $("#buy_num").val();
        }

        function addCart(bookId) {
            location.href = "<%=basePath%>" + "cart/addition?bookId=" + bookId + "&buyNum=" + $("#buy_num").val();
        }
    </script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container">
    <div class="row" id="breadcrumb" style="margin-bottom:40px" ;>
        <a href="index" target="_blank">
            <b style="font-size: 20px">水果</b>
        </a>
        <span>&gt;</span>
        <a href="book/list?cateId=${fruitInfo.fruitCategoryId}" target="_blank" style="font-size: 20px">${fruitInfo.categoryName}</a>
        <span>&gt;</span>
        <b style="font-size: 20px">${fruitInfo.name}</b>
    </div>
    <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12" style="height: 400px;">
            <div>
                <a href="book/info/${fruitInfo.fruitId}">
                    <img src="${fruitInfo.imageUrl}" width="290px" height="290px"/>
                </a>
            </div>
        </div>
        <div class="col-lg-9 col-md-6 col-sm-12 col-xs-12">
            <div class="alert alert-success">
                <h1 style="font-weight: bold;">水果名称：${fruitInfo.name}</h1><br/>
                <h2><B>
                    <span>${fruitInfo.outline}</span>
                </B></h2>
            </div>
            <div class="jumbotron">
                ${FruitDesc.fruitDesc}
            </div>
            <div class="alert alert-danger">
                <p class="price_info">
                    <span class="rob" style="font-size: 30px;">￥${fruitInfo.price}</span>
                    <span>原价:</span>
                    <span class="oprice">￥${fruitInfo.marketPrice}</span>
                </p>
            </div>
    </div>
    </div>
</div>
</body>
</html>