<jsp:include page="../student/head.jsp"></jsp:include>
<%--
  Created by IntelliJ IDEA.
  User: drpeng
  Date: 2018/5/14
  Time: 下午2:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- 引入 echarts.js -->
    <script src="/js/echarts.js"></script>
</head>
<body>

<ul id="myTab" class="nav nav-tabs">
    <li class="active">
        <a href="#home" data-toggle="tab">课程列表</a>
    </li>
    <li><a href="#ios" data-toggle="tab">课程图表</a></li>
</ul>

<div id="myTabContent" class="tab-content">
    <div class="tab-pane fade in active" id="home">diyiye    </div>

    <div class="tab-pane fade" id="ios">但是骄傲过把昆明老师
        <div id="main" style="width:900px;height:400px;"></div>
    </div>

</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    $.get('avg.json', function (data) {
        var sizeValue = '55%';
        var symbolSize = 5;
        option = {
            legend: {},
            tooltip: {},
            toolbox: {
                left: 'center',
                feature: {
                    dataZoom: {}
                }
            },
            grid: [
                {right: sizeValue, bottom: sizeValue},
                {left: sizeValue, bottom: sizeValue},
                {right: sizeValue, top: sizeValue},
                {left: sizeValue, top: sizeValue}
            ],
            xAxis: [
                {type: 'value', gridIndex: 0, name: 'nevents', axisLabel: {rotate: 50, interval: 0}},
                {type: 'value', gridIndex: 1, name: 'nplay_videos', boundaryGap: false, axisLabel: {rotate: 50, interval: 0}},
                {type: 'value', gridIndex: 2, name: 'nchapters', axisLabel: {rotate: 50, interval: 0}},
                {type: 'value', gridIndex: 3, name: 'nforum_posts', axisLabel: {rotate: 50, interval: 0}}
            ],
            yAxis: [
                {type: 'value', gridIndex: 0, name: 'grade'},
                {type: 'value', gridIndex: 1, name: 'grade'},
                {type: 'value', gridIndex: 2, name: 'grade'},
                {type: 'value', gridIndex: 3, name: 'grade'}
            ],
            dataset: {
                dimensions: [
                    'grade',
                    'nevents',
                    'nplay_videos',
                    'nchapters',
                    'nform_posts'
                ],
                source: data
            },
            series: [
                {
                    type: 'scatter',
                    symbolSize: symbolSize,
                    xAxisIndex: 0,
                    yAxisIndex: 0,
                    encode: {
                        x: 'nevents',
                        y: 'grade',
                        tooltip: [0, 1, 2, 3, 4]
                    }
                },
                {
                    type: 'scatter',
                    symbolSize: symbolSize,
                    xAxisIndex: 1,
                    yAxisIndex: 1,
                    encode: {
                        x: 'nplay_videos',
                        y: 'grade',
                        tooltip: [0, 1, 2, 3, 4]
                    }
                },
                {
                    type: 'scatter',
                    symbolSize: symbolSize,
                    xAxisIndex: 2,
                    yAxisIndex: 2,
                    encode: {
                        x: 'nchapters',
                        y: 'grade',
                        tooltip: [0, 1, 2, 3, 4]
                    }
                },
                {
                    type: 'scatter',
                    symbolSize: symbolSize,
                    xAxisIndex: 3,
                    yAxisIndex: 3,
                    encode: {
                        x: 'grade',
                        y: 'nforum_posts',
                        tooltip: [0, 1, 2, 3, 4]
                    }
                }
            ]
        };

        myChart.setOption(option);
    });

</script>


<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    模态框（Modal）标题
                </h4>
            </div>
            <div class="modal-body">
                在这里添加一些文本
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script>
    //点击详情显示模态框
    $(function(){
        // dom加载完毕
        var $m_btn = $('.btn-sm');
        var $modal = $('#myModal');
        $m_btn.on('click', function(){
            $modal.modal({backdrop: 'static'});
        });
        // 测试 bootstrap 居中
        $modal.on('show.bs.modal', function(){
            var $this = $(this);
            var $modal_dialog = $this.find('.modal-dialog');
            // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
            $this.css('display', 'block');
            $modal_dialog.css({'margin-top': Math.max(0, ($(window).height() - $modal_dialog.height()) / 2) });
        });

    });
</script>

</body>
</html>
