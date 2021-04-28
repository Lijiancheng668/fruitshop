<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<script type="text/javascript">
    $(function () {

        /**
         * 为导航栏绑定选择事件
         */
        $(".header-tabs li").removeClass("active");
        $("#${cateId==null?0:cateId}").addClass("active")


        //轮播图间隔时间
        $('.carousel').carousel({
            interval: 2000
        });

        /**
         * 为导航栏绑定选择事件
         */
        $(".nav-tabs li").click(function () {
            $(".nav-tabs li").removeClass("active");
            $(this).addClass("active");
        });
    });

    function submitSearchForm() {
        var keywords = $("#keywords").val();
        if("" == keywords.trim()){
            $("#keywords").val("java");
        }

        $("#searchForm").submit();
    }
</script>

<html>

<jsp:include page="top.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 logo">
            <img src="images/logo.png" alt="logo"/>
        </div>
        <div class="col-lg-6 col-md-6 col-xs-12 search">
            <form action="book/list" class="form-inline" id="searchForm" role="form" method="get">
                <div class="form-group">
                    <label class="sr-only" for="keywords">关键字</label>
                    <input type="text" class="form-control" id="keywords" name="keywords" value="${keywords}"size="55"
                           placeholder="请输入您想要买的水果名称">
                </div>
                <button type="button" onclick="submitSearchForm()" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span>
                    搜索
                </button>
            </form>
        </div>
        <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 search">
            <button type="button" onclick="window.open('cart/items')"
                    style="background-color: #ff2832;border-color: #ff2832" class="btn btn-info">
                <span class="glyphicon glyphicon-shopping-cart"></span>
                购物车
            </button>
            <button type="button" onclick="window.open('order/list')"
                    style="background-color: #ff2832;border-color: #ff2832" class="btn btn-info">
                <span class="glyphicon glyphicon-list-alt">&nbsp;</span>我的订单
            </button>
        </div>
    </div>
    <div class="row">
        <ul class="nav nav-tabs header-tabs">

            <li class="active" id="0"><a href=""><span class="glyphicon glyphicon-home"></span></a></li>
        </ul>
    </div>
</div>
<div style="height: 3px; background-color: darkgray;"></div>
</html>
