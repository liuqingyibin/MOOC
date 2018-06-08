<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>学生</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="Modern Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template,
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel='stylesheet' type='text/css' />
    <!-- Custom CSS -->
    <link href="css/style.css" rel='stylesheet' type='text/css' />
    <!-- Graph CSS -->
    <link href="css/lines.css" rel='stylesheet' type='text/css' />
    <link href="css/font-awesome.css" rel="stylesheet">
    <!-- jQuery -->
    <script src="js/jquery.min.js"></script>
    <!----webfonts--->
    <link href='http://fonts.useso.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'>
    <!---//webfonts--->
    <!-- Nav CSS -->
    <link href="css/custom.css" rel="stylesheet">
    <!-- Metis Menu Plugin JavaScript -->
    <script src="js/metisMenu.min.js"></script>
    <script src="js/custom.js"></script>
    <!-- Graph JavaScript -->
    <script src="js/d3.v3.js"></script>
    <script src="js/rickshaw.js"></script>
    <!-- E charts -->
    <script src="js/echarts.min.js"></script>
</head>
<body>
<div id="wrapper">
    <!-- Navigation -->
    <nav class="top1 navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="https://www.edx.org/course?course=all">MOOCStatistic</a>
        </div>
        <!-- /.navbar-header -->
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-comments-o"></i><span class="badge">4</span></a>
                <ul class="dropdown-menu">
                    <li class="dropdown-menu-header">
                        <strong>Messages</strong>
                        <div class="progress thin">
                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                <span class="sr-only">40% Complete (success)</span>
                            </div>
                        </div>
                    </li>
                    <li class="avatar">
                        <a href="#">
                            <img src="images/1.png" alt=""/>
                            <div>New message</div>
                            <small>1 minute ago</small>
                            <span class="label label-info">NEW</span>
                        </a>
                    </li>
                    <li class="dropdown-menu-footer text-center">
                        <a href="#">View all messages</a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle avatar" data-toggle="dropdown"><img src="images/1.png"><span class="badge">9</span></a>
                <ul class="dropdown-menu">
                    <li class="dropdown-menu-header text-center">
                        <strong>Account</strong>
                    </li>
                    <li class="m_2"><a href="#"><i class="fa fa-envelope-o"></i> Messages <span class="label label-success">42</span></a></li>
                    <li class="m_2"><a href="#"><i class="fa fa-tasks"></i> Tasks <span class="label label-danger">42</span></a></li>
                    <li><a href="#"><i class="fa fa-comments"></i> Comments <span class="label label-warning">42</span></a></li>
                    <li class="dropdown-menu-header text-center">
                        <strong>Settings</strong>
                    </li>
                    <li class="m_2"><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                    <li class="m_2"><a href="#"><i class="fa fa-wrench"></i> Settings</a></li>
                    <li class="divider"></li>
                    <li class="m_2"><a href="#"><i class="fa fa-shield"></i> Lock Profile</a></li>
                    <li class="m_2"><a href="#"><i class="fa fa-lock"></i> Logout</a></li>
                </ul>
            </li>
        </ul>
        <form class="navbar-form navbar-right">
            <input type="text" class="form-control" value="Search..." onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Search...';}">
        </form>
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li>
                        <a href="student.jsp"><i class="fa fa-dashboard fa-fw nav_icon"></i>首页</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-laptop nav_icon"></i>学习效果评价<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="student11.jsp">综合评价</a>
                            </li>
                            <li>
                                <a href="student12.jsp">各项评价成绩展示</a>
                            </li>
                            <li>
                                <a href="student13.jsp">各项评价排名展示</a>
                            </li>
                            <!-- <li>
                                <a href="lesson.jsp">学习效果与能力</a>
                            </li> -->
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="student2.jsp"><i class="fa fa-indent nav_icon"></i>学习成绩预测</a>
                    </li>
                    <li>
                        <a href="student3.jsp"><i class="fa fa-envelope nav_icon"></i>学习能力评估</a>
                    </li>
                    <li>
                        <a href="student4.jsp"><i class="fa fa-check-square-o nav_icon"></i>学习课程推荐</a>
                    </li>
                    <li>
                        <a href="info.jsp"><i class="fa fa-flask nav_icon"></i>我的信息</a>
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>
    <div id="page-wrapper">
        <div class="graphs">
            <div id="main" style="width: 100%;height: 700px;;"></div>

            <div class="copy">
                <p>Copyright &copy; 2018.Company name All rights reserved.</p>
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->
<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
        
        var myChart = echarts.init(document.getElementById('main'));
         // 显示标题，图例和空的坐标轴
        option = {
            title : {
                text: '各项评价成绩展示',
                subtext: '成绩展示'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                x : 'center',
                data:[]
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            polar : [
                {
                    indicator : [

                    ],
                    center : ['25%',200],
                    radius : 80
                },
                {
                    indicator : [

                    ],
                    radius : 80
                },
                {
                    indicator : [

                    ],
                    center : ['75%', 200],
                    radius : 80
                }
            ],
            series : [
                {
                    type: 'radar',
                    tooltip : {
                        trigger: 'item'
                    },
                    itemStyle: {normal: {areaStyle: {type: 'default'}}},
                    data : [
                        {
                            value : [],
                            name : ''
                        }
                    ]
                },
                {
                    type: 'radar',
                    polarIndex : 1,
                    data : [
                        {
                            value : [],
                            name : ''
                        }
                    ]
                },
                {
                    type: 'radar',
                    polarIndex : 2,
                    itemStyle: {normal: {areaStyle: {type: 'default'}}},
                    data : [
                        {
                            name : '',
                            value : [],
                        }
                    ]
                }
            ]
        };

         myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
         
         var asses=[];
         var name3=[];
         var Value3=[];
         var Value1=[];
         var Value2=[];
         var name2=[];
         var name1=[];
         var Name=[];

         $.ajax({
         type : "get",
         async : false,
         url : "http://123.206.205.246:8080/mooc/work/liuqingGradeAll.do",
         dataType : "jsonp",
         jsonp : "callback",
         jsonpCallback : "jsonpCallback",
         success : function(result) {
             //请求成功时执行该函数内容，result即为服务器返回的json对象
             if (result) {
                    for(var i=0;i<result.asses.length;i++){
                       asses.push(result.asses[i]);
                     }
                     for(var i=0;i<result.name3.length;i++){
                       name3.push(result.name3[i]);
                     }
                     for(var i=0;i<result.name2.length;i++){
                       name2.push(result.name2[i]);
                     }
                 for(var i=0;i<result.name1.length;i++){
                     name1.push(result.name1[i]);
                 }
                 for(var i=0;i<result.Name.length;i++){
                     Name.push(result.Name[i]);
                 }
                 for(var i=0;i<result.Value3.length;i++){
                     Value3.push(result.Value3[i]);
                 }
                  for(var i=0;i<result.Value2.length;i++){
                     Value2.push(result.Value2[i]);
                 }
                  for(var i=0;i<result.Value1.length;i++){
                     Value1.push(result.Value1[i]);
                 }

                 var polar1=[];
                 var polar2=[];
                 var polar3=[];
                 for(var i=0;i<name1.length;i++){
                     polar1.push({text:name1[i],max:100});
                 }
                 for(var i=0;i<name2.length;i++){
                     polar2.push({text:name2[i],max:100});
                 }
                 for(var i=0;i<name3.length;i++){
                     polar3.push({text:name3[i],max:100});
                 }
                    option.legend.data=Name;

                    option.polar[0].indicator=polar1;
                    option.polar[1].indicator=polar2;
                    option.polar[2].indicator=polar3;

                    option.series[0].data[0].value=Value1;
                    option.series[0].data[0].name=Name[0];
                    option.series[1].data[0].value=Value2;
                    option.series[1].data[0].name=Name[1];
                    option.series[2].data[0].value=Value3;
                    option.series[2].data[0].name=Name[2];

                    myChart.hideLoading();    //隐藏加载动画
                    myChart.setOption(option);
                    
             }
         
        },
         error : function(errorMsg) {
             //请求失败时执行该函数
         alert("图表请求数据失败!");
         myChart.hideLoading();
         }
    })  
    </script>
</body>
</html>

