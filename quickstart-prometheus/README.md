https://prometheus.io/
https://github.com/prometheus/prometheus

https://github.com/prometheus/client_java
https://github.com/prometheus/client_java


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




