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
  <title>图表</title>
  <base href="<%=basePath%>">
  <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
  <script src="https://cdn.bootcss.com/echarts/4.6.0/echarts.min.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 800px;height:450px;"></div>
<script type="text/javascript">
  $(document).ready(function(){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    //数据加载完之前先显示一段简单的loading动画
    myChart.showLoading();
    var names=[];    //横坐标数组（实际用来盛放X轴坐标值）
    var values=[];    //纵坐标数组（实际用来盛放Y坐标值）
    $.ajax({
      type : "post",
      async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
      url : "/admin/store/storeMonthNum/1",    //请求发送到dataActiont处
      data : {},
      dataType : "json",        //返回数据形式为json
      success : function(result) {
        //请求成功时执行该函数内容，result即为服务器返回的json对象
        myChart.hideLoading();    //隐藏加载动画
        myChart.setOption({        //加载数据图表
          tooltip: { //提示框组件
            trigger: 'item', //触发类型,'item'数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。 'axis'坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用。
            triggerOn: "mousemove", //提示框触发的条件,'mousemove'鼠标移动时触发。'click'鼠标点击时触发。'mousemove|click'同时鼠标移动和点击时触发。'none'不在 'mousemove' 或 'click' 时触发
            showContent: true,  //是否显示提示框浮层
            alwaysShowContent: true,  //是否永远显示提示框内容
          },
          title:{
            text:'某店铺月销量'
          },
          series : [
            {
              name: '月销量(单位：件)',
              type: 'pie',
              radius : '55%',
              center: ['50%', '60%'],
              data: result,

              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                  color:function(params) {
                    //自定义颜色
                    var colorList = [
                      '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                      '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                      '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                    ];
                    return colorList[params.dataIndex]
                  }
                }
              }
            }
          ]
        });
      },
      error : function(errorMsg) {
        //请求失败时执行该函数
        alert("图表请求数据失败!");
        myChart.hideLoading();
      }
    });//end ajax
  });
</script>

</body>
</html>