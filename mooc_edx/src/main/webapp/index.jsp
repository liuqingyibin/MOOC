<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"

"http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="/js/echarts.js"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 100%;height: 700px;;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    option = {
        tooltip : {
            trigger: 'axis'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        legend: {
            data:['成立机构数量','平均注册资金']
        },
        xAxis : [
            {
                type : 'category',
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis : [
            {
                type : 'value',
                name : '成立数量',
                axisLabel : {
                    formatter: '{value} 个'
                }
            },
            {
                type : 'value',
                name : '资金',
                axisLabel : {
                    formatter: '{value} 万元'
                }
            }
        ],
        series : [

            {
                name:'成立数量',
                type:'bar',
                data:[1, 2, 1, 2, 1, 2, 3, 2, 3, 2, 2, 3]
            },

            {
                name:'平均注册资金',
                type:'line',
                yAxisIndex: 1,
                data:[100, 200, 350, 250, 250, 150, 350, 250, 100, 100, 200, 300]
            }
        ]
    };




    myChart.setOption(option);



</script>
</body>
</html>