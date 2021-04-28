<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
    <script src="js/base-loading.js"></script>

    <style type="text/css">
        td {
            max-width: 100px;
            overflow: hidden;
        }

        #searchBook {
            padding-top: 50px;
        }
    </style>

    <script type="text/javascript">

        function editBook(fruitId) {
            location.href = "<%=basePath%>admin/book/echo?fruitId=" + fruitId;
        }

        function changeBookShelf(fruitId, isShelf) {
            if (isShelf == 1) {
                if (confirm("下架水果?")) {
                    location.href = "<%=basePath%>admin/book/shelf?fruitId=" + fruitId + "&isShelf=0";
                }
            }
            if (isShelf == 0) {
                if (confirm("上架水果?")) {
                    location.href = "<%=basePath%>admin/book/shelf?fruitId=" + fruitId + "&isShelf=1";
                }
            }
        }

        function deleteBook(fruitId) {
            if (confirm("删除该水果?")) {
                location.href = "<%=basePath%>admin/book/deletion/"+fruitId;
            }
        }

    </script>
</head>

<body>
<div class="container" style="border: 1px solid #CCCCCC;">
    <div id="searchBook" style="height:100px;border-bottom: 1px solid #CCCCCC;">
        <form action="admin/book/list" class="form-inline" role="form" method="get">
            <div class="form-group">
                <input type="text" class="form-control" id="name" name="keywords" value="${keywords}" placeholder="请输入要搜索的水果名城">
            </div>

            <button type="submit" class="btn btn-default">
                <span class="glyphicon glyphicon-search"></span> 水果检索
            </button>
        </form>
    </div>

    <table class="table table-hover">
        <caption>水果列表</caption>
        <thead>
        <tr>
            <th>序号</th>
            <th>水果名</th>
            <th>图片</th>
            <th>产地</th>
            <th>现价</th>
            <th>定价</th>
            <th>状态</th>
            <th>库存</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookPageInfo.list}" var="book" varStatus="vs">
            <tr>
                <td>${bookPageInfo.startRow + vs.count-1}</td>
                <td>${book.name}</td>
                <td>
                    <img src="${book.imageUrl}" style="width: 50%">
                </td>
                <td>${book.place}</td>
                <td class="red">￥${book.price}</td>
                <td class="red">￥${book.marketPrice}</td>
                <td class="red">${book.isShelf == 1 ?"上架中":"已下架"}</td>
                <td>${book.storeMount}</td>
                <td>
                    <button type="button" class="btn btn-xs btn-info" onclick="editBook(${book.fruitId})">
                        <span class="glyphicon glyphicon-pencil"></span> 编辑
                    </button>
                    <button type="button" class="btn btn-xs btn btn-success"
                            onclick="changeBookShelf(${book.fruitId},${book.isShelf})">
                            ${book.isShelf == 1 ?"<span class='glyphicon glyphicon-arrow-down'></span> 下架":"<span class='glyphicon glyphicon-arrow-up'></span> 上架"}
                    </button>
                    <button type="button" class="btn btn-xs btn-danger" onclick="deleteBook(${book.fruitId})">
                        <span class="glyphicon glyphicon-pencil"></span> 删除
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <ul class="pagination pagination-lg">

        <c:if test="${bookPageInfo.isFirstPage}">
            <li class="disabled"><a href="javascript:void(0);">&laquo;</a></li>
        </c:if>

        <c:if test="${!bookPageInfo.isFirstPage}">
            <li>
                <a href="admin/book/list?page=${bookPageInfo.prePage}">&laquo;</a>
            </li>
        </c:if>
        <c:forEach
                begin="${bookPageInfo.pageNum < 6 ? 1 :bookPageInfo.pageNum-5}"
                end="${bookPageInfo.pages<6?bookPageInfo.pages:(bookPageInfo.pageNum < 6 ? 6 :bookPageInfo.pageNum) }"
                var="current">
            <li
                    class="${(current == bookPageInfo.pageNum) ? 'active':''}">
                <a href="admin/book/list?keywords=${keywords}&page=${current}">
                        ${current}
                </a>
            </li>
        </c:forEach>

        <c:if test="${bookPageInfo.isLastPage}">
            <li class="disabled"><a href="javascript:void(0);">&raquo;</a></li>
        </c:if>

        <c:if test="${!bookPageInfo.isLastPage}">
            <li><a href="admin/book/list?keywords=${keywords}&page=${bookPageInfo.nextPage}">&raquo;</a>
            </li>
        </c:if>

        <li>
            <a href="admin/book/list?keywords=${keywords}&page=${bookPageInfo.pages}">末页</a>
        </li>

        <li><a href="javascript:void(0);">共${bookPageInfo.pages}页</a></li>
        <li><a href="javascript:void(0);">共${bookPageInfo.total}件</a></li>
    </ul>
</div>

</body>
</html>
