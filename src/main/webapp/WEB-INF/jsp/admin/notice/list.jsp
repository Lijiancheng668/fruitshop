<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>公告列表</title>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
</head>
<script type="text/javascript">

    $(function (){
        $('[data-toggle="popover"]').each(function () {
            var element = $(this);
            var buyerId = element.attr('value');
            $.ajax({
                type:"GET",
                url:"admin/order/buyer/"+buyerId,
                success : function(result){
                    if(result.code == 200){
                        element.popover({
                            trigger: 'manual',
                            placement: 'right', //top, bottom, left or right
                            title: "买家:"+result.data.username,
                            html: 'true',
                            content: "来自:"+result.data.location
                        }).on("mouseenter", function () {
                            var _this = this;
                            $(this).popover("show");
                            $(this).siblings(".popover").on("mouseleave", function () {
                                $(_this).popover('hide');
                            });
                        }).on("mouseleave", function () {
                            var _this = this;
                            setTimeout(function () {
                                if (!$(".popover:hover").length) {
                                    $(_this).popover("hide")
                                }
                            }, 100);
                        });
                    }
                },
                dataType : "json"
            });

        });
    });

    function deleteOrder(orderId) {
        if (confirm("确认删除订单吗?")) {
            location.href = "<%=basePath%>admin/order/deletion/" + orderId;
        }
    }

    function postOrder(orderId) {
        if (confirm("确认发货吗?")) {
            location.href = "<%=basePath%>admin/order/post/" + orderId;
        }
    }
    
    function toEditOrder(orderId) {
        location.href = "<%=basePath%>admin/order/toUpdate/" + orderId;
    }

</script>
<body>
<div class="container" style="border: 1px solid #CCCCCC;height: 1000px;">

    <div style="height:100px;border-bottom: 1px solid #CCCCCC;padding-top:30px;margin-bottom: 10px">
        <h2 class="h2">公告列表</h2>
    </div>

    <table class="table table-hover">
        <caption>公告列表</caption>
        <thead>
        <tr>
            <th>序号</th>
            <th>公告标题</th>
            <th>公告描述</th>
            <th>公告状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allNotices}" var="notice" varStatus="vs">
            <tr>
                <td>${notice.id}</td>
                <td>${notice.title}</td>
                <td>${notice.content}</td>
                <td>${notice.state}</td>
                <td>
                    <button type="button" class="btn btn-xs btn-info" onclick="toEditOrder('${orderCustom.order.orderId}')" >
                        修改公告
                    </button>
                    <button type="button" class="btn btn-xs btn-danger" onclick="deleteOrder('${orderCustom.order.orderId}')">
                        删除公告
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

</body>
</html>
