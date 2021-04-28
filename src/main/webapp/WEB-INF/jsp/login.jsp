<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en" class="no-js">
    <head>
        <meta charset="utf-8">
        <base href="<%=basePath%>">
        <title>Fullscreen Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
        <link rel="stylesheet" href="assets/css/reset.css">
        <link rel="stylesheet" href="assets/css/supersized.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <script>
            $(function () {
                $("#loginForm").validate({
                    rules: {
                        username: "required",
                        password: {
                            rangelength: [3, 15],
                            required: true
                        }
                    },
                    errorPlacement: function (error, element) {	//错误信息位置设置方法
                        error.appendTo(element.parent().next());//这里的element是录入数据的对象,parent父元素，next()下一个
                    },
                    success: function (label) {
                        label.addClass("error");
                    },
                    messages: {
                        username: "用户名不能为空",
                        password: {
                            rangelength: "密码长度在{0}~{1}之间",
                            required: "密码不能为空"
                        }
                    }
                });
            })
        </script>

    </head>

    <body>
    <shiro:notAuthenticated>
        <div class="page-container">
            <h1>Login</h1>

                    <form action="<%=basePath%>user/login" method="post" id="loginForm">
                        <input type="text" name="username" class="username" placeholder="Username">
                        <input type="password" name="password" class="password" placeholder="Password">
                        <button type="submit">Sign me in</button>
                        <div class="error"><span class="error">${loginMsg}</span></div>
                    </form>
        </div>
        <div align="center"><a href="page/register"  target="_blank" class="btn btn-success">Free registration</a>  <a href=""  class="btn btn-danger">Forget the password</a></div>
    </shiro:notAuthenticated>
    <shiro:user>
        <div id="loginForm">
            <div>
                <div style="font-size: 20px">You are already logged in,<a href="user/info"><shiro:principal property="username"/></a></div>
                <div style="font-size: 18px"><a href="user/logout" >cancellation</a></div>
            </div>
        </div>

    </shiro:user>
    <!-- Javascript -->
    <script src="assets/js/jquery-1.8.2.min.js"></script>
    <script src="assets/js/supersized.3.2.7.min.js"></script>
    <script src="assets/js/supersized-init.js"></script>
    <script src="assets/js/scripts.js"></script>


    </body>

</html>

