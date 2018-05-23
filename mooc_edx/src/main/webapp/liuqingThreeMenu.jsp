<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"

"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="js/echarts.js"></script>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript">

        var menuName1 = [];
        var menuValue1 = [];
        var menuName2 = [];
        var menuValue2 = [];
        var menuName3 = [];
        var menuValue3 = [];

        $(function() {
            $.ajax({

                url: "http://localhost:8080/work/liuqing.do",
                contentType: 'application/json',
                dataType: "json",
                type: "post",
                success: function(data) {
                    for (var i = 0; i < data.menu1.length; i++) {
                        menuName1[i] = data.menu1[i];
                    }
                    for (var i = 0; i < data.menu2.length; i++) {
                        menuName2[i] = data.menu2[i];
                    }
                    for (var i = 0; i < data.menu3.length; i++) {
                        menuName3[i] = data.menu3[i];
                    }
                    for (var i = 0; i < data.value1.length; i++) {
                        menuValue1[i] = data.value1[i];
                    }
                    for (var i = 0; i < data.value2.length; i++) {
                        menuValue2[i] = data.value2[i];
                    }
                    for (var i = 0; i < data.value3.length; i++) {
                        menuValue3[i] = data.value3[i];
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
                            text: '学生各项综合展示',
                            subtext: '柳青祎斌'
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            x: 'center',
                            data:['学习过程与方法','学习态度与情感','学习效果与能力']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                mark: {show: true},
                                dataView: {show: true, readOnly: false},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        calculable: true,
                        polar: [
                            {
                                indicator: (function () {
                                    var res = [];
                                    for (var i = 0; i <menuName1.length; i++) {
                                        res.push({text: menuName1[i], max: 100});
                                    }
                                    return res;
                                })(),
                                center: ['25%', 200],
                                radius: 80
                            },
                            {
                                indicator: (function () {
                                    var res = [];
                                    for (var i = 0 ; i <menuName2.length; i++) {
                                        res.push({text: menuName2[i], max: 100});
                                    }
                                    return res;
                                })(),
                                radius: 80
                            },
                            {
                                indicator: (function () {
                                    var res = [];
                                    for (var i = 0; i <menuName3.length; i++) {
                                        res.push({text: menuName3[i], max: 100});
                                    }
                                    return res;
                                })(),
                                center: ['75%', 200],
                                radius: 80
                            }
                        ],
                        series: [
                            {
                                type: 'radar',
                                tooltip: {
                                    trigger: 'item'
                                },
                                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                data: [
                                    {
                                        value: menuValue1,
                                        name: '学习过程与方法'
                                    }
                                ]
                            },
                            {
                                type: 'radar',
                                polarIndex: 1,
                                data: [
                                    {
                                        value: menuValue2,
                                        name: '学习态度与情感'
                                    }
//
                                ]
                            },
                            {
                                type: 'radar',
                                polarIndex: 2,
                                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                data: [
                                    {
                                        name: '学习效果与能力',
//                                        value: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 75.6, 82.2, 48.7, 18.8, 6.0, 2.3],
                                        value: menuValue3
                                    }
//                                    ,
//                                    {
//                                        name: '蒸发量',
//                                        value: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 35.6, 62.2, 32.6, 20.0, 6.4, 3.3]
//                                    }
                                ]
                            }
                        ]
                    };

                    myChart.setOption(option);

                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest + "," + textStatus + "," + errorThrown);
                }
            });
        });



    </script>

</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 100%;height: 700px;;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例



</script>
</body>
</html>