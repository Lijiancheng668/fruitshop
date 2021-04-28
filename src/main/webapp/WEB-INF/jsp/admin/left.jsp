<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
</head>
<style>
    #left-menu {
        font-size: 13px;
        line-height: 18px;
        color: #fff;
    }

    #left-menu-main {
    }

    .left-menu-main-list {
        background-color: grey;
    }

    .panel-body li:hover {
        background-color: #222222;
    }

    .panel-body a {
        display: inline-block;
        color: #fff;
        font-size: 20px;
        margin-top: 10px;
        text-decoration: none;
    }
    .btn{
        width: 193px;
    }
    .panel-heading {
        padding: 20px 15px;
    }
    .panel-title a{
        text-decoration: none;
    }
    .btn-primary{
        margin-top: 20px;
    }
</style>
<body style="background-color: yellowgreen;">

<div class="container" id="left-menu" style="padding-right:20px ;">

    <div class="row" id="center_header" style="height: 100px;border-bottom: 1px solid #CCCCCC;">
        <img src="images/logo.png">
    </div>

    <div class="row" id="left-menu-main">

        <shiro:hasAnyRoles name="admin,user-manager">
            <div class="btn btn-primary">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapseOne">
                            <span class="glyphicon glyphicon-user"></span> 用户管理
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <li>
                                <a href="admin/user/list" target="centerFrame">用户列表</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </shiro:hasAnyRoles>

        <shiro:hasAnyRoles name="admin">
            <div class="btn btn-primary">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapseTwo">
                            <span class="glyphicon glyphicon-cloud"></span> 云店铺
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <li>
                                <a href="admin/store/list" target="centerFrame">店铺列表</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </shiro:hasAnyRoles>

        <shiro:hasAnyRoles name="admin">
            <div class="btn btn-primary">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a  data-toggle="collapse" data-parent="#accordion"
                           href="#collapseThree">
                            <span class="glyphicon glyphicon-cloud"></span> 通知公告
                        </a>
                    </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <li>
                                <a href="admin/notice/list" target="centerFrame">公告列表</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="btn btn-primary">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a  data-toggle="collapse" data-parent="#accordion"
                           href="#collapseFive">
                            <span class="glyphicon glyphicon-list-alt"></span> 报表统计
                        </a>
                    </h4>
                </div>
                <div id="collapseFive" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <li>
                                <a href="admin/store/storeHello" target="centerFrame">访问量</a>
                            </li>
                            <li>
                                <a href="admin/store/storeMonth" target="centerFrame">销售量</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </shiro:hasAnyRoles>

        <c:if test="${sessionScope.loginStore != null }">
            <shiro:hasPermission name="store-manage">
                <div class="btn btn-primary">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a  data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                <span class="glyphicon glyphicon-asterisk"></span> 我的店铺
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul>

                                <li>
                                    <a href="admin/store/${sessionScope.loginStore.storeId}" target="centerFrame">店铺信息</a>
                                </li>
                                <li>
                                    <a href="admin/book/list?page=1" target="centerFrame">我的水果</a>
                                </li>
                                <li>
                                    <a href="admin/book/toAddition" target="centerFrame">水果上新</a>
                                </li>
                                <li>
                                    <a href="admin/order/list" target="centerFrame">订单管理</a>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>
            </shiro:hasPermission>
        </c:if>

        <shiro:hasPermission name="personal-center">
            <div class="btn btn-primary">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapseSix">
                            <span class="glyphicon glyphicon-user"></span> 个人中心
                        </a>
                    </h4>
                </div>
                <div id="collapseSix" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <li>
                                <a href="admin/user/echo/${sessionScope.loginUser.userId}" target="centerFrame">我的信息</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </shiro:hasPermission>

        <div class="btn btn-primary">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion"
                       href="#collapseSeven">
                        <span class="glyphicon glyphicon-home"></span> 前往
                    </a>
                </h4>
            </div>
            <div id="collapseSeven" class="panel-collapse collapse">
                <div class="panel-body">
                    <ul>
                        <li>
                            <a href="index" target="_blank">前台首页</a>
                        </li>
                        <li>
                            <a href="admin/center" target="centerFrame">后台首页</a>
                        </li>
                        <li>
                            <a href="admin/user/logout" target="_top">注销</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
