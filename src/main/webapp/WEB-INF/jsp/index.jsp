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
    <title>${applicationScope.globalParameter.webName}</title>

    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="shortcut icon" href="img/icon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
    <style>
        .product_div {
            padding-top: 10px;
        }
    </style>
</head>

<body>
<jsp:include page="header.jsp"/>
<div class="container" style="margin-top:10px ;">
    <div class="row" style="height: 850px;">
        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 sort" style="width: 17.5%;height: 100%;padding: 0;">
            <div id="sort_header">
                <span>水果分类</span>
            </div>
            <ul id="sort_ul">
                <c:forEach items="${bookCategories}" var="bookCat">
                    <li class="sort_li">
                        <span class="glyphicon glyphicon-leaf"></span>
                        <a href="book/list?cateId=${bookCat.cateId}">${bookCat.name}</a>
                        <span class="sort_span">></span>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-lg-6 col-md-4 col-sm-6 col-xs-12" style="width: 65%;height: 100%;">
            <div id="myCarousel" class="carousel slide" data-ride="carousel">
                <!-- 轮播（Carousel）指标 -->
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0"
                        class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                    <li data-target="#myCarousel" data-slide-to="3"></li>
                    <li data-target="#myCarousel" data-slide-to="4"></li>
                    <li data-target="#myCarousel" data-slide-to="5"></li>
                    <li data-target="#myCarousel" data-slide-to="6"></li>
                    <li data-target="#myCarousel" data-slide-to="7"></li>
                    <li data-target="#myCarousel" data-slide-to="8"></li>
                </ol>
                <!-- 轮播（Carousel）项目 -->
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="img/item1.png"/>
                    </div>
                    <div class="item">
                        <img src="img/item2.png"/>
                    </div>
                    <div class="item">
                        <img src="img/item3.png"/>
                    </div>
                    <div class="item">
                        <img src="img/item4.png"/>
                    </div>
                    <div class="item">
                        <img src="img/item5.png"/>
                    </div>
                    <div class="item">
                        <img src="img/item6.png"/>
                    </div>
                    <div class="item">
                        <img src="img/item7.png"/>
                    </div>
                    <div class="item">
                        <img src="img/item8.png"/>
                    </div>
                </div>
                <!-- 轮播（Carousel）导航 -->
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            <div class="index_product_top">
                <span class="title">为你推荐</span>
                <div class="div_hr"></div>
            </div>
            <div class="product_div">
                <ul class="product_ul">

                    <c:forEach items="${fruitInfos}" begin="0" end="7" var="fruitInfo">

                        <li class="product_li">
                            <a href="book/info/${fruitInfo.fruitId}" class="img" target="_blank">
                                <img src="${fruitInfo.imageUrl}" class="img-rounded"/>
                            </a>
                            <p class="name">
                                <a href="book/info/${fruitInfo.fruitId}">${fruitInfo.name}</a>
                            </p>
                            <p class="author">${fruitInfo.storeId}号店铺</p>
                            <p class="price">
                                <span class="rob">￥${fruitInfo.price}</span>
                                <span class="oprice">￥${fruitInfo.marketPrice}</span>
                            </p>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12" style="width: 17.5%;height: 100%; padding: 0;">
            <div class="news">
                <p class="title">公告</p>
                <ul>
                    <c:forEach items="${notices}" var="notice">
                    <li>${notice.title}</li>
                        <li style="list-style-type:none"><span class="glyphicon glyphicon-hand-right"></span><span style="color: red">${notice.content}</span></li>
                    </c:forEach>
                </ul>
                <img style="width: 150px;height: 200px" src="images/leaf.gif">
            </div>
        </div>
    </div>

</div>
</div>

<div style="height: 3px; background-color: darkgray;"></div>

<jsp:include page="footer.jsp"/>
</body>

</html>