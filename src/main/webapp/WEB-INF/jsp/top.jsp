<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<style>
    #gongao{width:500px;height:30px;overflow:hidden;line-height:30px;font-size:13px;font-family:'宋体';color: black;text-shadow: -1px -1px 0px #e6e600,-2px -2px 0px #e6e600,
    -3px -3px 0px #e6e600,1px 1px 0px #bfbf00,2px 2px 0px #bfbf00,3px 3px 0px #bfbf00;font-weight:bold; position: absolute;right: 550px;top: 10px;}
    #gongao #scroll_begin, #gongao{display:inline}
</style>
<!-- js字体左右移动-->
<script type="text/javascript">
    function ScrollImgLeft(){
        var speed=50;  //设置调用时间
        var scroll_div = document.getElementById("scroll_div");  //获取left需要移动的idv

        function Marquee(){
            if(scroll_div.scrollLeft>300)
                scroll_div.scrollLeft=0;
            else
                scroll_div.scrollLeft++;
        }
        var MyMar=setInterval(Marquee,speed);  //每多少秒调用一次该函数
        scroll_div.οnmοuseοver=function() {clearInterval(MyMar);}  //鼠标移动停止移动事件
        scroll_div.οnmοuseοut=function() {MyMar=setInterval(Marquee,speed);}  //离开运行移动
    }

</script>
<html>
<body>
<nav class="navbar">
<div id="header">
    <div id="header_inner">
<%--          <div class="nihong" id="scroll_begin" align="center">欢迎来到绿叶水果商城</div>--%>
    <div id="gongao">
        <div style="width:500px;height:30px;margin:0 auto;white-space: nowrap;overflow:hidden;" id="scroll_div" class="scroll_div">
            <div id="scroll_begin">
                欢迎来到绿叶水果商城，闻着香，吃着甜，物美还价廉-甜田果园你生活中必不可少的水果王国。&nbsp;&nbsp;&nbsp;&nbsp;
                欢迎来到绿叶水果商城，闻着香，吃着甜，物美还价廉-甜田果园你生活中必不可少的水果王国。&nbsp;&nbsp;&nbsp;&nbsp;
                欢迎来到绿叶水果商城，闻着香，吃着甜，物美还价廉-甜田果园你生活中必不可少的水果王国。&nbsp;&nbsp;&nbsp;&nbsp;
            </div>

        </div>
        <script type="text/javascript">setInterval(ScrollImgLeft(),1000);</script>
    </div>
        <ul id="header_ul">
            <li class="header_li">
                <shiro:user>
                    <div class="btn-group">
                        <button type="button" class="btn btn-default dropdown-toggle mybtn"
                                data-toggle="dropdown">
                            <span class="glyphicon glyphicon-user"></span>
                            个人中心<span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="user/info">个人信息</a></li>
                            <shiro:hasPermission name="system">
                                <li><a href="admin">后台管理</a></li>
                            </shiro:hasPermission>
                            <li class="divider"></li>
                            <li><a href="user/logout">注销</a></li>

                        </ul>
                    </div>
                </shiro:user>
            </li>


            <li class="header_li">
                <a href="page/register">免费注册</a>
            </li>
            <li class="header_li">
                <c:if test="${sessionScope.loginUser == null}">
                    <a href="page/login">登录入口</a>
                </c:if>
                <c:if test="${sessionScope.loginUser != null } ">
                    <a href="user/info">${sessionScope.loginUser.username}</a>
                </c:if>
            </li>

            <li class="header_li">
                <c:if test="${sessionScope.loginUser != null }">
                    欢迎您，<a href="user/info">${sessionScope.loginUser.username}</a>
                </c:if>
            </li>
        </ul>
    </div>
</div>
</nav>
</body>
</html>
