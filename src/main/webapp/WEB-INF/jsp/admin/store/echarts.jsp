<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="<%=basePath%>">
    <title>图表</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/echarts.min.js"></script>
</head>
<body>
<div style="height: 150px"></div>
<div class="container">
    <div class="row">
        <div class="col-lg-6">
            <div class="input-group">
                <input type="text" class="form-control" id="storeId" name="storeId" value="" placeholder="请输入要查询的店铺号...">
                <span class="input-group-btn">
            <button class="btn btn-info" type="button" onclick="search()"><span class="glyphicon glyphicon-search"></span> 查询</button>
          </span>
            </div><!-- /input-group -->
        </div><!-- /.col-lg-6 -->
    </div>
    <div style="height: 20px"></div>
    <div class="row">
        <div class="col-lg-6">
           <div id="main" style="width: 800px;height:450px;"></div>
        </div>
    </div>
</div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<script type="text/javascript">

    function search(){
        var storeId = $("#storeId").val();
        var re = /^[0-9]+.?[0-9]*/;
        if (!re.test(storeId)) {
            alert("请输入数字");
            return false;
        }
        alert(storeId);
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            //数据加载完之前先显示一段简单的loading动画
            myChart.showLoading();
            var names=[];    //横坐标数组（实际用来盛放X轴坐标值）
            var values=[];    //纵坐标数组（实际用来盛放Y坐标值）
            $.ajax({
                type : "post",
                async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url : "/admin/store/storeMonthNum/"+storeId,    //请求发送到dataActiont处
                data : {},
                dataType : "json",        //返回数据形式为json
                success : function(result) {
                    //请求成功时执行该函数内容，result即为服务器返回的json对象
                    if (result) {
                        for(var i=0;i<result.length;i++){
                            names.push(result[i].name);
                            values.push(result[i].value);
                        }
                        myChart.hideLoading();    //隐藏加载动画
                        myChart.setOption({        //加载数据图表
                            title:{
                                text:'店铺月销量'
                            },
                            tooltip: {},
                            legend: {
                                data:['销售量']
                            },
                            xAxis: {
                                data: names,
                                name:'单位：月'
                            },
                            yAxis: {
                                type: 'value',
                                name:'单位：件'
                            },
                            series: [{
                                // 根据名字对应到相应的系列
                                name: '销售量',//薪资 series not exists. Legend data should be same with series name or data name.
                                type: 'bar',
                                data: values
                            }]
                        });
                    }
                },
                error : function(errorMsg) {
                    //请求失败时执行该函数
                    alert("图表请求数据失败!");
                    myChart.hideLoading();
                }
            });//end ajax
    }


</script>

</body>
</html>