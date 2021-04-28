<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
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
    <link rel="stylesheet" href="css/book_list.css"/>
    <title>fruits</title>
</head>
<body>

<jsp:include page="header.jsp"/>
<div class="container">
    <div class="search_tab">
        <ul>
            <li>全部商品</li>
        </ul>
    </div>
    <div class="crumbs">
        <div>
            <a href="#">全部</a>
            <span>&gt;</span>
            <span class="search_word">${keywords}</span>
        </div>
        <span class="total_search_book_count">
					共<em class="red">${fruitPageInfo.total}</em>件水果
				</span>
    </div>
    <div class="search_result">
        <div class="shoplist">
            <ul class="shoplist_ul">

                <c:forEach items="${fruitPageInfo.list}" var="fruitInfo">
                    <li>
                        <a href="book/info/${fruitInfo.fruitId}" target="_blank" title="${fruitInfo.outline}">
                            <img src="${fruitInfo.imageUrl}" class="img-circle" alt="${fruitInfo.outline}" width="200px" height="200px"/>
                        </a>
                        <p class="name">
                            <a href="book/info/${fruitInfo.fruitId}" title="${fruitInfo.outline}" target="_blank">
                                    ${fruitInfo.outline}
                            </a>
                        </p>
                        <p class="price">
                            <span class="search_now_price">￥ ${fruitInfo.price}</span>
                            <span style="color: #C0C0C0;">原价：</span>
                            <span class="oprice">￥${fruitInfo.marketPrice}</span>
                        </p>
                        <p class="search_book_author">
                            <span>【${fruitInfo.storeId}号店铺】</span>
                        </p>
                        <p class="search_fruit_palce">
                            <span>产地:${fruitInfo.place}</span>
                        </p>
                        <p class="detail">
                                ${fruitInfo.detail}
                        </p>
                        <div class="shop_button">
                            <p class="bottom_p">
                                <a class="search_btn_cart" href="cart/addition?fruitId=${fruitInfo.fruitId}&storeId=${fruitInfo.storeId}&buyNum=1">加入购物车</a>
                            </p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <ul class="pagination pagination-lg">

            <%--
                上一页
            --%>
            <c:if test="${fruitPageInfo.isFirstPage}">
                <li class="disabled"><a href="javascript:void(0);"><span class="glyphicon glyphicon-chevron-left"></span></a></li>
            </c:if>

            <c:if test="${!fruitPageInfo.isFirstPage}">
                <li>
                    <a href="book/list?&cateId=${cateId}&page=${fruitPageInfo.prePage}"><span class="glyphicon glyphicon-chevron-left"></span></a>
                </li>
            </c:if>

            <c:forEach
                    begin="${fruitPageInfo.pageNum < 6 ? 1 :fruitPageInfo.pageNum-5}"
                    end="${fruitPageInfo.pages<6?fruitPageInfo.pages:(fruitPageInfo.pageNum < 6 ? 6 :fruitPageInfo.pageNum) }"
                    var="current">
                <li
                        class="${(current == fruitPageInfo.pageNum) ? 'active':''}">
                    <a href="book/list?&cateId=${cateId}&page=${current}">
                            ${current}
                    </a>
                </li>
            </c:forEach>
            <%--
                下一页
            --%>
            <c:if test="${fruitPageInfo.isLastPage}">
                <li class="disabled"><a href="javascript:void(0);"><span class="glyphicon glyphicon-chevron-right"/></a></li>
            </c:if>

            <c:if test="${!fruitPageInfo.isLastPage}">
                <li><a href="book/list?keywords=cateId=${cateId}&page=${fruitPageInfo.nextPage}"><span class="glyphicon glyphicon-chevron-right"></span></a>
                </li>
            </c:if>

            <li class="disabled"><a href="javascript:void(0);">共${fruitPageInfo.pages}页</a></li>
        </ul>
    </div>
</div>
</body>
</html>
