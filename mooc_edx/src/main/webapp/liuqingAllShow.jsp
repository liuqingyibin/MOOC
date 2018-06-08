<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"

"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="/js/echarts.js"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript">

        var menuName = [];
        var menuValue = [];
        $(function() {
            $.ajax({

                type: "GET",//请求方式为get（有post和get）
                async: false,//不管
                url: "http://localhost:8080/work/liuqing.do",//请求接口地址
                dataType: "jsonp",//我们用的式jsonp的一种方式，为了解决ajax跨域访问
                jsonp:"callback",//固定请求写法
                jsonpCallback : "jsonpCallback",//固定后端返回参数写法
                success: function(data) {
//                    alert("1");
                    for(var i=0;i<data.menu1.length;i++){
                        menuName[i]=data.menu1[i];
                    }
                    for(var i=0;i<data.menu2.length;i++){
                        menuName[i+data.menu1.length]=data.menu2[i];
                    }
                    for(var i=0;i<data.menu3.length;i++){
                        menuName[i+data.menu2.length+data.menu1.length]=data.menu3[i];
                    }
                    for(var i=0;i<data.value1.length;i++){
                        menuValue[i]=data.value1[i];
                    }
                    for(var i=0;i<data.value2.length;i++){
                        menuValue[i+data.value1.length]=data.value2[i];
                    }
                    for(var i=0;i<data.value3.length;i++){
                        menuValue[i+data.value1.length+data.value2.length]=data.value3[i];
                    }
//                    alert(menuName);
//                    alert(menuValue);
//                    alert(data.value1[3]);

                    var myChart = echarts.init(document.getElementById('main'));

//
//
//                    alert(menuName);
//                    alert(menuValue);
                    option = {
                        title: {
                            x: 'center',
                            text: '学生学习效果评价模型各项评分展示',
                            subtext: '柳青祎斌制作',
                            link: 'http://echarts.baidu.com/doc/example.html'
                        },
                        tooltip: {
                            trigger: 'item'
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                dataView: {show: true, readOnly: false},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        calculable: true,
                        grid: {
                            borderWidth: 0,
                            y: 80,
                            y2: 60
                        },
                        xAxis: [
                            {
                                type: 'category',
                                show: false,
//                data: ['Line', 'Bar', 'Scatter', 'K', 'Pie', 'Radar', 'Chord', 'Force', 'Map', 'Gauge', 'Funnel']
                                data : menuName
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                show: false
                            }
                        ],
                        series: [
                            {
                                name: '学生学习效果评价模型各项评分展示',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        color: function(params) {
                                            // build a color map as your need.
                                            var colorList = [
                                                '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                                '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                                '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                            ];
                                            return colorList[params.dataIndex]
                                        },
                                        label: {
                                            show: true,
                                            position: 'top',
                                            formatter: '{b}\n{c}'
                                        }
                                    }
                                },
//                data: [12,21,10,4,12,5,6,5,25,23,7],
                                data : menuValue,
                                markPoint: {
                                    tooltip: {
                                        trigger: 'item',
                                        backgroundColor: 'rgba(0,0,0,0)',
                                        formatter: function(params){
                                            return '<img src="'
                                                    + params.data.symbol.replace('image://', '')
                                                    + '"/>';
                                        }
                                    },
                                    data: [
                                        {xAxis:0, y: 350, name:'Line', symbolSize:20, symbol: 'image://../asset/ico/折线图.png'},
                                        {xAxis:1, y: 350, name:'Bar', symbolSize:20, symbol: 'image://../asset/ico/柱状图.png'},
                                        {xAxis:2, y: 350, name:'Scatter', symbolSize:20, symbol: 'image://../asset/ico/散点图.png'},
                                        {xAxis:3, y: 350, name:'K', symbolSize:20, symbol: 'image://../asset/ico/K线图.png'},
                                        {xAxis:4, y: 350, name:'Pie', symbolSize:20, symbol: 'image://../asset/ico/饼状图.png'},
                                        {xAxis:5, y: 350, name:'Radar', symbolSize:20, symbol: 'image://../asset/ico/雷达图.png'},
                                        {xAxis:6, y: 350, name:'Chord', symbolSize:20, symbol: 'image://../asset/ico/和弦图.png'},
                                        {xAxis:7, y: 350, name:'Force', symbolSize:20, symbol: 'image://../asset/ico/力导向图.png'},
                                        {xAxis:8, y: 350, name:'Map', symbolSize:20, symbol: 'image://../asset/ico/地图.png'},
                                        {xAxis:9, y: 350, name:'Gauge', symbolSize:20, symbol: 'image://../asset/ico/仪表盘.png'},
                                        {xAxis:10, y: 350, name:'Funnel', symbolSize:20, symbol: 'image://../asset/ico/漏斗图.png'},
                                    ]
                                }
                            }
                        ]
                    };

                    myChart.setOption(option);

                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest + "," + textStatus + "," + errorThrown);
                }
            });
        });;



    </script>

</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 100%;height: 700px;"></div>

</body>
</html>