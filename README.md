# MOOCStatistic
Statistic the user actions of MOOC.

* 登录网址：[MOOCStatistic signon](http://123.206.205.246/sign/signon.html)
* 注册网址：[MOOCStatistic signup](http://123.206.205.246/sign/signup.html)
* 展示网址：[MOOCStatistic datadisplay](http://123.206.205.246/datadisplay)

## 架构设计
### 蓝图
```
前端框架：Bootstrap
        ^
        |
        |
反向代理：NGINX
        ^
        |
        |
后端框架：Tomcat
```

### 技术点
1. 用户登录MOOCStatistic进行注册
2. 使用分布式网络爬虫爬去MOOC用户数据（Token由注册用户提供）
3. 数据处理（过滤、统计、分析、预测、推荐）
4. 根据后台使用E chart进行可视化展示
5. *GitHub进行协同开发、版本控制*

### 实现
* Mysql管理用户数据
* 数据交互使用json格式
* DNS解析域名至服务器
* NGINX反向代理实现负载均衡
* IP过滤防止恶意攻击
