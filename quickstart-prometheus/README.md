- [Prometheus介绍](#Prometheus介绍)
- [Prometheus安装](#Prometheus安装)
- [Prometheus的自定义查询语言PromQL](#Prometheus的自定义查询语言PromQL)
- [Prometheus指标类型](#Prometheus指标类型)
- [Prometheus原理](#Prometheus原理)
- [通过micrometer实时监控线程池的各项指标](#通过micrometer实时监控线程池的各项指标)
- [Prometheus HTTP API接口](#Prometheus-HTTP-API接口)




---------------------------------------------------------------------------------------------------------------------
## Prometheus介绍



[Prometheus官网](https://prometheus.io/)  
[Prometheus Github](https://github.com/prometheus/prometheus)  
[Java客户端](https://github.com/prometheus/client_java) ：Prometheus instrumentation library for JVM applications  
[Prometheus下载](https://prometheus.io/download/)  
[Prometheus官方文档](https://prometheus.io/docs/introduction/overview/)  
[prometheus函数文档](https://prometheus.io/docs/prometheus/latest/querying/functions/#increase)
[prometheus blog](https://promlabs.com/blog/)
[]()  



From metrics to insight Power your metrics and alerting with a leading open-source monitoring solution.
从指标到洞察力 通过领先的指标为您的指标和警报提供支持 开源监控解决方案。


Prometheus 是一个时序数据库。

数据模型
Prometheus 在底层将所有数据都按时间序列排序，





[Prometheus操作指南](https://yunlzheng.gitbook.io/prometheus-book)  

[Prometheus学习系列](https://www.jianshu.com/u/a1f163a32328)  
[Prometheus中文文档](https://hulining.gitbook.io/prometheus/)
[Prometheus 非官方中文手册](https://www.bookstack.cn/read/prometheus-manual/prometheus-querying-functions.md#delta())



三大套件
Server 主要负责数据采集和存储，提供PromQL查询语言的支持。
Alertmanager 警告管理器，用来进行报警。
Push Gateway 支持临时性Job主动推送指标的中间网关。

grafana 界面展示
Grafana是用于可视化大型测量数据的开源程序，它提供了强大和优雅的方式去创建、共享、浏览数据。


Prometheus 监控 Java 应用有两种方式：一种是使用官方提供的jar包，然后嵌入到应用中。这种方式一般都是新项目。我认为也是最合适的一种。不过这种情况一般是理想而已。而除了这种方式，第二种是prometheus的jmx_exporter。
我们就是用的第二种。使用jmx_exporter的方式来监控我们的java应用程序。我们的java应用基本上是使用tomcat作为服务器的。这种情况下有两种方式，一种是基于springboot的jar包启动方式，一种是直接下载tomcat软件之后，将应用打成war包部署的方式。

jmx_exporter的使用非常简单，但是如果不了解就会非常懵逼。jmx_exporter实际也是基于java的jmx通过暴露Mbean来做为代理，使用http的方式来给Prometheus进行指标采集。

config.yaml下载地址：https://github.com/prometheus/jmx_exporter/blob/master/example_configs/tomcat.yml
jar包下载地址：https://github.com/prometheus/jmx_exporter



https://www.cnblogs.com/chenqionghe/p/10494868.html
https://www.ibm.com/developerworks/cn/cloud/library/cl-lo-prometheus-getting-started-and-practice/index.html



---------------------------------------------------------------------------------------------------------------------
## Prometheus安装

[Prometheus下载](https://prometheus.io/download/)


启动DefaultExportsTest.main()

访问http://localhost:1234/metrics


下载Prometheus

在prometheus.yml配置文件中追加simpleclient的job
scrape_configs:
- job_name: "simpleclient"
  metrics_path: "/metrics"
  scrape_interval: 5s
  static_configs:
    - targets: ["localhost:1234"]


nohup  yourPath/prometheus >  yourLogPath/prometheus.stdout  2>&1 &
注：yourPath是prometheus可执行程序所在路径，yourLogPath是日志路径，prometheus默认会加载prometheus.yml文件进行初始化，默认端口为9090。


cd prometheus-2.27.1.darwin-amd64
mkdir -p logs
nohup ./prometheus >  logs/prometheus.stdout  2>&1 &



启动完成后，可以通过http://localhost:9090访问Prometheus的UI界面

热加载更新Prometheus
curl -X POST http://localhost:9090/-/reload




### Docker安装

docker pull prom/prometheus:v2.27.1

# 创建持久化目录
mkdir -p /Users/lengfeng/prometheus/data

docker run -d --restart always -e TZ=Asia/Shanghai -p 9090:9090 --name prometheus -v /Users/lengfeng/software/prometheus-2.27.1.darwin-amd64/prometheus.yml:/etc/prometheus/prometheus.yml -v /Users/lengfeng/prometheus/data:/prometheus prom/prometheus:v2.27.1


docker run -d --net=bridge -p 9090:9090 --name prometheus -v /Users/lengfeng/software/prometheus-2.27.1.darwin-amd64/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus:v2.27.1

[comment]: <> (docker run -d --restart always -e TZ=Asia/Shanghai --hostname prometheus --name prometheus-server -p 9090:9090 -v /data/prometheus/server/data:/prometheus -v /data/prometheus/server/conf/prometheus.yml:/etc/prometheus/prometheus.yml -u root prom/prometheus:v2.5.0)



把prometheus配置文件配置成宿主机的IP就好了


运行 docker start prometheus 启动服务
运行 docker stats prometheus 查看 prometheus 状态
运行 docker stop prometheus 停止服务


UI 页面跟上面一样都使用 IP：9090

启动完成后，可以通过http://localhost:9090访问Prometheus的UI界面


[INSTALLATION](https://prometheus.io/docs/prometheus/latest/installation/)






### 使用Dockerfile自定义构建镜像

[基于docker封装prometheus解决时区问题](https://cloud.tencent.com/developer/article/1684313) 测试最新版本不行  
[INSTALLATION](https://prometheus.io/docs/prometheus/latest/installation/)





### 部署主机监控组件node_exporter

[node_exporter源码地址](https://github.com/prometheus/node_exporter)  
[node_exporter下载](https://github.com/prometheus/node_exporter/releases)  
[node_exporter介绍分类](https://prometheus.io/docs/instrumenting/exporters/)  

在下载安装Prometheus之前我们先安装node_exporter插件，用于提供服务器监控的指标(比如：CPU、内存、磁盘、磁盘读写速率等指标)，是一个非常常用的Prometheus Client插件。

各节点主机使用主机网络模式部署主机监控组件node-exporter，官方不建议将其部署为Docker容器，因为该node_exporter设计用于监控主机系统。需要访问主机系统，而且通过容器的方式部署发现磁盘数据不太准确。二进制部署就去看项目文档吧

该node_exporter设计用于监控主机系统。不建议将其部署为 Docker 容器，因为它需要访问主机系统。

对于需要 Docker 部署的情况，必须使用一些额外的标志来允许node_exporter访问主机命名空间。

请注意，您要监视的任何非根挂载点都需要绑定挂载到容器中。

如果您启动容器以进行主机监控，请指定path.rootfs参数。此参数必须与主机根的绑定安装中的路径匹配。node_exporter 将 path.rootfs用作访问主机文件系统的前缀。



后台运行
nohup yourPath/node_exporter  > yourLogPath/node_exporter.stdout 2>&1 &

cd node_exporter-1.1.2.darwin-amd64
mkdir logs
nohup ./node_exporter  > logs/node_exporter.stdout 2>&1 &


或者Docker部署[没有部署起来]

docker run -d \
--net="host" \
--pid="host" \
-v "/:/host:ro,rslave" \
quay.io/prometheus/node-exporter:latest \
--path.rootfs=/host


docker run -d -p 9100:9100 \
-v "/proc:/host/proc:ro" \
-v "/sys:/host/sys:ro" \
-v "/:/rootfs:ro" \
--net="host" \
--restart=always \
--name node-exporter \
prom/node-exporter


访问http://localhost:9100/可以看到以下页面：

访问http://localhost:9100/metrics，可以看到当前node exporter获取到的当前主机的所有监控数据



在prometheus.yml配置文件中追加node_exporter的job，监控本机服务器
- job_name: 'node_exporter'
  static_configs:
  - targets: ['localhost:9100']

热加载更新Prometheus
curl -X POST http://localhost:9090/-/reload



[容器监控实践—node-exporter](https://segmentfault.com/a/1190000017959127)  
[Docker 安装node_exporter](https://www.kancloud.cn/willseecloud/prometheus/1904355)  
[最新版Prometheus+Grafana+node-exporter炫酷界面](https://cloud.tencent.com/developer/article/1595811)  



[Docker部署Prometheus+Grafana监控系统](https://segmentfault.com/a/1190000039671651)  
[Prometheus和Grafana安装部署](https://blog.csdn.net/skh2015java/article/details/102572363)  
[Prometheus-基础系列-(二)-安装与部署](https://zhuanlan.zhihu.com/p/117719823)  





[使用 prometheus jmx_exporter监控kafka](https://github.com/jianzhiunique/kafka-jmx-exporter)  
[prometheus jmx_exporter地址](https://github.com/prometheus/jmx_exporter)  



## Prometheus配置文件


[prometheus学习系列五： Prometheus配置文件](https://www.cnblogs.com/zhaojiedi1992/p/zhaojiedi_liunx_60_prometheus_config.html)  
[Prometheus配置文件prometheus.yml 四个模块详解](http://www.21yunwei.com/archives/7321)  



---------------------------------------------------------------------------------------------------------------------
## Prometheus的自定义查询语言PromQL

Prometheus 提供了一种名为 PromQL(Prometheus Query Language) 的功能查询语言，使用户可以实时选择和汇总时间序列数据。，表达式的结果可以在 Prometheus 的表达式浏览器中显示为图形和表格数据，也可以由外部系统通过 HTTP API 使用


### 查询时间序列

PromQL还支持用户根据时间序列的标签匹配模式来对时间序列进行过滤，目前主要支持两种匹配模式：完全匹配和正则匹配。

PromQL支持使用=和!=两种完全匹配模式：
1. 通过使用label=value可以选择那些标签满足表达式定义的时间序列；
2. 反之使用label!=value则可以根据标签匹配排除时间序列；

PromQL还可以支持使用正则表达式作为匹配条件，多个表达式之间使用|进行分离：
1. 使用label=~regx表示选择那些标签符合正则表达式定义的时间序列；
2. 反之使用label!~regx进行排除；

Prometheus中的所有正则表达式都使用 [RE2语法](https://github.com/google/re2/wiki/Syntax) 。

也就是标签查询  
如  
如果我们只需要查询所有http_requests_total时间序列中满足标签instance为localhost:9090的时间序列，则可以使用如下表达式：  
http_requests_total{instance="localhost:9090"}

反之使用instance!="localhost:9090"则可以排除这些时间序列：  
http_requests_total{instance!="localhost:9090"}

如果想查询多个环节下的时间序列序列可以使用如下表达式：  
http_requests_total{environment=~"staging|testing|development",method!="GET"}




### 范围查询

直接通过类似于PromQL表达式http_requests_total查询时间序列时，返回值中只会包含该时间序列中的最新的一个样本值，这样的返回结果我们称之为瞬时向量。而相应的这样的表达式称之为瞬时向量表达式。

而如果我们想过去一段时间范围内的样本数据时，我们则需要使用区间向量表达式。区间向量表达式和瞬时向量表达式之间的差异在于在区间向量表达式中我们需要定义时间选择的范围，时间范围通过时间范围选择器[]进行定义。

例如，通过以下表达式可以选择最近5分钟内的所有样本数据：  
http_requests_total{}[5m]

除了使用m表示分钟以外，PromQL的时间范围选择器支持其它时间单位：
s - 秒
m - 分钟
h - 小时
d - 天
w - 周
y - 年


瞬时向量表达式
区间向量表达式
在瞬时向量表达式或者区间向量表达式中，都是以当前时间为基准：

http_request_total{} # 瞬时向量表达式，选择当前最新的数据
http_request_total{}[5m] # 区间向量表达式，选择以当前时间为基准，5分钟内的数据




### 时间位移操作

如果我们想查询，5分钟前的瞬时样本数据，或昨天一天的区间内的样本数据呢? 这个时候我们就可以使用位移操作，位移操作的关键字为offset。

可以使用offset时间位移操作：
http_request_total{} offset 5m
http_request_total{}[1d] offset 1d




### 使用聚合操作

一般来说，如果描述样本特征的标签(label)在并非唯一的情况下，通过PromQL查询数据，会返回多条满足这些特征维度的时间序列。而PromQL提供的聚合操作可以用来对这些时间序列进行处理，形成一条新的时间序列：


# 查询系统所有http请求的总量
sum(http_request_total)

# 按照mode计算主机CPU的平均使用时间
avg(node_cpu) by (mode)

# 按照主机查询各个主机的CPU使用率
sum(sum(irate(node_cpu{mode!='idle'}[5m]))  / sum(irate(node_cpu[5m]))) by (instance)





### 计算速率
1. rate():该计算增加速率每秒，平均在整个提供时间窗口。【单位是1/s】rate只能用在counter的 metrics 类型上进行计算。
2. irate():只考虑最近两个样本所提供的时间窗口下的计算并忽略所有更早的。输出将比rate()更加尖锐【单位是1/s】
3. increase():这个函数完全等同于rate()除了它不将最终单位转换为“每秒”( 1/s)。相反，最终的输出单元是每个提供的时间窗口。Thus increase(foo[5m]) / (5 * 60) is 100% equivalent to rate(foo[5m]).【跟rate()比只有单位不同，是给定的时间单位】


[How Exactly Does PromQL Calculate Rates?](https://promlabs.com/blog/2021/01/29/how-exactly-does-promql-calculate-rates)
[prometheus的rate与irate内部是如何计算的](https://zhangguanzhang.github.io/2020/07/30/prometheus-rate-and-irate/)



### 计算差值
1. delta():delta(v range-vector)函数，计算一个范围向量v的第一个元素和最后一个元素之间的差值。delta函数返回值类型只能是gauges。
2. idelta()：idelta(v range-vector)函数，输入一个范围向量，返回key: value = 度量指标： 每最后两个样本值差值。

delta(v range-vector)函数，计算一个范围向量v的第一个元素和最后一个元素之间的差值。返回值：key:value=度量指标：差值

下面这个表达式例子，返回过去两小时的CPU温度差：
delta(cpu_temp_celsius{host=”zeus”}[2h])

delta函数返回值类型只能是gauges。






### 在HTTP API中使用PromQL






参考
[探索PromQL](https://yunlzheng.gitbook.io/prometheus-book/parti-prometheus-ji-chu/promql)  
[Prometheus-基础系列-(四)-PromQL语句实践-2](https://zhuanlan.zhihu.com/p/121104877)  
[Prometheus查询语句官方文档](https://prometheus.io/docs/prometheus/latest/querying/basics/)  


[Prometheus学习笔记（7）PromQL玩法入门](https://www.cnblogs.com/linuxk/p/12054401.html)  
[使用PromQL查询监控数据](https://yunlzheng.gitbook.io/prometheus-book/parti-prometheus-ji-chu/quickstart/prometheus-quick-start/promql_quickstart)  
[第05期：Prometheus 数据查询（一）](https://segmentfault.com/a/1190000023741116)  
[]()  
[]()  


---------------------------------------------------------------------------------------------------------------------
## Prometheus指标类型


Prometheus定义了4种不同的指标类型(metric type)：Counter（计数器）、Gauge（仪表盘）、Histogram（直方图）、Summary（摘要）。


Counter：只增不减的计数器
Gauge：可增可减的仪表盘




1. 计数器(Counter)
是一个单调递增的数字，只能加，不能减，也可以归0.
可以使用计数器来表示已服务请求，已完成任务或错误的数量。

2. Gauge
可任意上升和下降。
通常用于测量值，例如温度或当前的内存使用量，还用于可能上升和下降的“计数”，例如并发请求数。

3. 直方图（Histogram）
将计数放到一个一个的桶中。
[直方图讲解](https://cloud.tencent.com/developer/article/1495303)

累积直方图:
Prometheus 的 histogram 是一种累积直方图，与上面的区间划分方式是有差别的，它的划分方式如下：还假设每个 bucket 的宽度是 0.2s，那么第一个 bucket 表示响应时间小于等于 0.2s 的请求数量，第二个 bucket 表示响应时间小于等于 0.4s 的请求数量，以此类推。也就是说，每一个 bucket 的样本包含了之前所有 bucket 的样本，所以叫累积直方图。


4. 摘要（Summary）
类似于直方图，就是加入了百分位。


Enumeration
Info



[](https://github.com/prometheus/client_java#Instrumenting)  
[METRIC TYPES](https://prometheus.io/docs/concepts/metric_types/)  
[](https://prometheus.io/docs/practices/instrumentation/#counter-vs.-gauge,-summary-vs.-histogram)  
[Prometheus概念](https://xujiyou.work/%E4%BA%91%E5%8E%9F%E7%94%9F/Prometheus/Prometheus%E6%A6%82%E5%BF%B5.html)  
[Prometheus指标和标签命名](https://blog.csdn.net/xtayfjpk/article/details/103446842)  



[Prometheus基础应用笔记](https://coconutmilktaro.top/2019/Prometheus%E5%9F%BA%E7%A1%80%E5%BA%94%E7%94%A8%E7%AC%94%E8%AE%B0/)  
[一篇文章带你理解和使用Prometheus的指标](https://frezc.github.io/2019/08/03/prometheus-metrics/)  




---------------------------------------------------------------------------------------------------------------------

## Prometheus原理



每一个指标就是一个Collector.MetricFamilySamples对象



SpringBoot2中监控路径/actuator/prometheus对应的处理类
PrometheusScrapeEndpoint




[Prometheus架构原理及监控告警配置实现](https://www.modb.pro/db/45956)  
[Prometheus 原理和源码分析](https://www.infoq.cn/article/prometheus-theory-source-code)  
[Prometheus原理简介](https://zhuanlan.zhihu.com/p/126513347)  
[]()  
[]()  
[]()  




[自定义Exporter示例代码](https://github.com/ogibayashi/kafka-topic-exporter)  
[Prometheus系列--Exporter原理](https://zhuanlan.zhihu.com/p/273229856)  
[使用Java自定义Exporter](https://yunlzheng.gitbook.io/prometheus-book/part-ii-prometheus-jin-jie/exporter/custom_exporter_with_java)
[Exporter对http返回格式的要求见官方文档](https://prometheus.io/docs/instrumenting/exposition_formats/#text-based-format)  
[深入探索Prometheus Exporter](https://www.jianshu.com/p/dc4dbb497559)  
[]()  
[]()  
[]()  


---------------------------------------------------------------------------------------------------------------------
## 通过micrometer实时监控线程池的各项指标



[Java线程池监控预警实现](https://www.jianshu.com/p/3001431f1b0a)  
[通过micrometer实时监控线程池的各项指标](https://www.cnblogs.com/throwable/p/10708351.html)  

[使用Prometheus监控接口的响应时间](https://github.com/hellorocky/blog/blob/master/prometheus/2.%E4%BD%BF%E7%94%A8Prometheus%E7%9B%91%E6%8E%A7%E6%8E%A5%E5%8F%A3%E7%9A%84%E5%93%8D%E5%BA%94%E6%97%B6%E9%97%B4.md)  




## Prometheus HTTP API接口

[Prometheus 查询语言 PromQL 使用说明](https://jimmysong.io/kubernetes-handbook/practice/promql.html)  
[官方HTTP API接口文档](https://prometheus.io/docs/prometheus/latest/querying/api/)  

