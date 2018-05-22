<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2018/5/13
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<jsp:include page="../student/head.jsp"></jsp:include>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="/js/echarts.js"></script>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<%--<div id="main" style="width: 1000px;height:400px;float: left"></div>--%>
<%--<div id="main2" style="width: 1000px;height:400px;"></div>--%>
<div id="allCourse" style="width: 600px;height:400px;float: right">
    选课总人数，考试总人数，取得证书总人数
</div>

<script type="text/javascript">
    var myChart = echarts.init(document.getElementById("allCourse"));
    var courseDatas = new Array();
    var x = new Array();

    $(function () {
        $.getJSON("../json/allCourseNumberOfStudents.json",function (data) {
            $.each(data,function (itemIndex, dataItem) {
                x.push(itemIndex);
                var courseData = new Array();
                courseData.push(dataItem["选课总人数"]);
                courseData.push(dataItem["考试总人数"]);
                courseData.push(dataItem["取得证书人数"]);
                courseDatas.push(courseData);
            })
            option = {
                title: {
                    text: '各类型人数分布',
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: ['选课总人数', '考试总人数','取得证书人数']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01]
                },
                xAxis: {
                    type: 'category',
                    data: x
                },
                series: [
                    {
                        name: '选课总人数',
                        type: 'bar',
                        data:[courseDatas[0][0], courseDatas[1][0]]
                    },
                    {
                        name: '考试总人数',
                        type: 'bar',
                        data: [courseDatas[0][1], courseDatas[1][1]]
                    },
                    {
                        name: '取得证书人数',
                        type: 'bar',
                        data: [courseDatas[0][2], courseDatas[1][2]]
                    }
                ]
            };
            myChart.setOption(option);
        })
    });

</script>

</body>
</html>