<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"

"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>ECharts</title>
    <meta charset="utf-8">
    <script src="/js/echarts.js"></script>
    <script src="http://code.jquery.com/jquery-1.4.1.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 100%;height: 700px;;"></div>
<script type="text/javascript" charset="UTF-8">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));


    var dataStyle = {
        normal: {
            label: {show:false},
            labelLine: {show:false}
        }
    };
    var placeHolderStyle = {
        normal : {
            color: 'rgba(0,0,0,0)',
            label: {show:false},
            labelLine: {show:false}
        },
        emphasis : {
            color: 'rgba(0,0,0,0)'
        }
    };


        $.ajax({
            type : "get",
            async : false,
            url : "http://123.206.205.246:8080/mooc/user/liuqingOverShow.do",
            dataType : "jsonp",
            jsonp : "callback",
            scriptCharset : 'utf-8',
            jsonpCallback : "jsonpCallback",
            success: function (data) {
                alert("进入了ajax");

                var asses = [];
                var name = [];
                var value = [];


                for (var i = 0; i < data.Name.length; i++) {
                    name[i] = data.Name[i];
//                    name.push(data.Name[i]);
                }
                for (var i = 0; i < data.Value.length; i++) {
                    value[i] = data.Value[i];
                }
                for (var i = 0; i < data.asses.length; i++) {
                    asses[i] = data.asses[i];
                }
                alert("成功了" + name[2]);

                option = {
                    title: {
                        text: '你幸福吗？',
                        subtext: 'From ExcelHome',
                        sublink: 'http://e.weibo.com/1341556070/AhQXtjbqh',
                        x: 'center',
                        y: 'center',
                        itemGap: 20,
                        textStyle: {
                            color: 'rgba(30,144,255,0.8)',
                            fontFamily: '微软雅黑',
                            fontSize: 35,
                            fontWeight: 'bolder'
                        }
                    },
                    tooltip: {
                        show: true,
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: document.getElementById('main').offsetWidth / 2,
                        y: 45,
                        itemGap: 12,
                        data: ['68%的人表示过的不错', '29%的人表示生活压力很大', '3%的人表示“我姓曾”']
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
                    series: [
                        {
                            name: '1',
                            type: 'pie',
                            clockWise: false,
                            radius: [125, 150],
                            itemStyle: dataStyle,
                            data: [
                                {
                                    value: 68,
                                    name: '68%的人表示过的不错'
                                },
                                {
                                    value: 32,
                                    name: 'invisible',
                                    itemStyle: placeHolderStyle
                                }
                            ]
                        },
                        {
                            name: '2',
                            type: 'pie',
                            clockWise: false,
                            radius: [100, 125],
                            itemStyle: dataStyle,
                            data: [
                                {
                                    value: 29,
                                    name: '29%的人表示生活压力很大'
                                },
                                {
                                    value: 71,
                                    name: 'invisible',
                                    itemStyle: placeHolderStyle
                                }
                            ]
                        },
                        {
                            name: '3',
                            type: 'pie',
                            clockWise: false,
                            radius: [75, 100],
                            itemStyle: dataStyle,
                            data: [
                                {
                                    value: 3,
                                    name: '3%的人表示“我姓曾”'
                                },
                                {
                                    value: 97,
                                    name: 'invisible',
                                    itemStyle: placeHolderStyle
                                }
                            ]
                        }
                    ]
                };
                alert("1");
                alert(name[1]);
                myChart.setOption(option);


            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("失败了" + asses[2]);
                alert(XMLHttpRequest + "," + textStatus + "," + errorThrown);
            }

    });
    alert("12");
</script>
</body>
</html>