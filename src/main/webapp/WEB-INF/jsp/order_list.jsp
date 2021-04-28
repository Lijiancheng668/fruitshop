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
    <title>订单结算</title>

    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/confirm.css"/>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
    <link rel="stylesheet" href="css/order_info.css"/>

    <script type="text/javascript">
        function deleteOrder(orderId) {
            var span = document.getElementById("title");
            span.innerHTML = "删除确认";
            var message = "确认删除订单吗?";
            dialogBox(message,
                function () {
                    console.log("confirmed");
                    location.href = "<%=basePath%>order/deletion/" + orderId;
                },
                function(){
                    console.log("canceled");
                    // do something
                }
            );
        }
        function confirmReceiving(orderId) {
            var span = document.getElementById("title");
            span.innerHTML = "确认收货";
            var message = "您确认收货吗?";
            dialogBox(message,
                function () {
                    console.log("confirmed");
                    location.href = "<%=basePath%>order/confirm/" + orderId;
                },
                function(){
                    console.log("canceled");
                    // do something
                }
            );
        }

        function dialogBox(message, yesCallback, noCallback){
            if(message){
                $('.dialog-message').html(message);
            }
            // 显示遮罩和对话框
            $('.wrap-dialog').removeClass("hide");
            // 确定按钮
            $('#confirm').click(function(){
                $('.wrap-dialog').addClass("hide");
                yesCallback();
            });
            // 取消按钮
            $('#cancel').click(function(){
                $('.wrap-dialog').addClass("hide");
                noCallback();
            });
        }

        function toPay(orderId) {
            location.href = "<%=basePath%>order/payPage/" + orderId;
        }

        <%--function deleteOrder(orderId) {--%>
        <%--    if (confirm("确认删除订单吗?")) {--%>
        <%--        location.href = "<%=basePath%>order/deletion/" + orderId;--%>
        <%--    }--%>
        <%--}--%>

        <%--function confirmReceiving(orderId) {--%>
        <%--    if(confirm("确认收货吗?")){--%>
        <%--        location.href = "<%=basePath%>order/confirm/" + orderId;--%>
        <%--    }--%>
        <%--}--%>

        function lookShipping() {
            alert("暂无物流信息!");
        }

    </script>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">

    <c:if test="${orderCustoms == null || empty orderCustoms}">
        <div class="row">
            <h1 class="h1">亲，您还没有已提交的订单，<a href="">再逛一逛吧!</a></h1>
            <img src="img/empty.png" alt="您暂时没有订单">
        </div>
    </c:if>

    <c:if test="${orderCustoms != null && !empty orderCustoms}">

    <div class="row">
        <h1 class="title order_title">订单列表</h1>
    </div>

    <div class="row" id="order_table_div">
        <table id="order_table" border="0" cellpadding="0" cellspacing="0">
            <thead>
            <tr id="table_head">
                <th width="10%" class="tcol1">订单编号</th>
                <th width="30%" class="tcol1">买了什么</th>
                <th width="10%">订单状态</th>
                <th width="10%">金额</th>
                <th width="15%">收货地址</th>
                <th width="25%">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orderCustoms}" var="orderCustom">
                <tr class="order_item" id="order_item${orderCustom.order.orderId}">

                    <td>
                        <span>${orderCustom.order.orderId}</span>
                    </td>
                    <td style="padding-left: 30px">
                        <c:forEach items="${orderCustom.orderDetails}" var="orderDetail">
                            <span>${orderDetail.mount}&nbsp;x</span>
                            <a href="book/info/${orderDetail.fruitId}" target="_blank" title="${orderDetail.fruitName}"><img src="${orderDetail.imageUrl}"
                                                                           width="15%"/></a>
                        </c:forEach>
                    </td>
                    <td>
                        <span>${orderCustom.order.statusString}</span>
                    </td>
                    <td>
                        <span class="red">￥${orderCustom.order.payment}</span>
                    </td>
                    <td>
                        <span>${orderCustom.orderShipping.receiverAddress}</span>
                    </td>
                    <td>
                        <c:if test="${orderCustom.order.status == 0}">
                            <button type="button" class="btn btn-info btn-xs" onclick="toPay('${orderCustom.order.orderId}')">
                                去支付
                            </button>
                            <button type="button" class="btn btn-xs btn-danger" onclick="deleteOrder('${orderCustom.order.orderId}')">
                                取消订单
                            </button>
                        </c:if>
                        <c:if test="${orderCustom.order.status >= 1}">
                            <c:if test="${orderCustom.order.status != 4}">
                                <button type="button" class="btn btn-xs btn-info" onclick="confirmReceiving('${orderCustom.order.orderId}')" >
                                    确认收货
                                </button>
                            </c:if>
                            <button type="button" class="btn btn-xs btn-danger" onclick="deleteOrder('${orderCustom.order.orderId}')">
                                删除订单
                            </button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </div>
</div>
</c:if>
<div class="wrap-dialog hide">
    <div class="dialog">
        <div class="dialog-header">
            <span class="dialog-title" id="title"></span>
        </div>
        <div class="dialog-body">
            <span class="dialog-message"></span>
        </div>
        <div class="dialog-footer">
            <input type="button" class="btn btn-success" id="confirm" value="确认" />
            <input type="button" class="btn btn-danger" id="cancel" value="取消" />
        </div>
    </div>
</div>
</body>
</html>
