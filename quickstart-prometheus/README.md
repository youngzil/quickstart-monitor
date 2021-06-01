- [Prometheus介绍](#Prometheus介绍)
- [Prometheus安装](#Prometheus安装)
- [Prometheus的自定义查询语言PromQL](#Prometheus的自定义查询语言PromQL)


---------------------------------------------------------------------------------------------------------------------
## Prometheus介绍



[Prometheus官网](https://prometheus.io/)  
[Prometheus Github](https://github.com/prometheus/prometheus)  
[Java客户端](https://github.com/prometheus/client_java) ：Prometheus instrumentation library for JVM applications  
[Prometheus下载](https://prometheus.io/download/)  
[]()  
[]()  



From metrics to insight Power your metrics and alerting with a leading open-source monitoring solution.
从指标到洞察力 通过领先的指标为您的指标和警报提供支持 开源监控解决方案。








[Prometheus操作指南](https://github.com/yunlzheng/prometheus-book)  






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

docker run -d -p 9090:9090 --name prometheus -v /Users/lengfeng/software/prometheus-2.27.1.darwin-amd64/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus:v2.27.1



运行 docker start prometheus 启动服务
运行 docker stats prometheus 查看 prometheus 状态
运行 docker stop prometheus 停止服务


UI 页面跟上面一样都使用 IP：9090

启动完成后，可以通过http://localhost:9090访问Prometheus的UI界面





### 部署主机监控组件node_exporter

[node_exporter源码地址](https://github.com/prometheus/node_exporter)  
[node_exporter下载](https://github.com/prometheus/node_exporter/releases)  

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






---------------------------------------------------------------------------------------------------------------------
## Prometheus的自定义查询语言PromQL









---------------------------------------------------------------------------------------------------------------------




