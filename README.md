项目地址
https://github.com/youngzil/quickstart-monitor






可以使用zabbix，skywalking等监控

Zabbix
Nagios
Ganglia
Zenoss Core 宙斯
Open-falcon

Monit
Munin
Cacti
Observium
Collectd
.Argus
anglia
Grafana


Datadog
2. Ruxit
3. Takipi
4. Rollbar
5. Sensu
6. ELK stack
7. Graphite


OpenQRM

Icinga本来是作为Nagios的fork，但最近被重写为Icinga 2，


Zipkin
ELK


https://blog.csdn.net/jessise_zhan/article/details/80129915



Hlog+Pinpoint




市面上的APM（Application Performance Management）理论模型大多都是借鉴（borrow）Google Dapper论文，本文重点关注以下几种APM组件：

Zipkin  由Twitter公司开源，开放源代码分布式的跟踪系统，用于收集服务的定时数据，以解决微服务架构中的延迟问题，包括数据的收集、存储、查找和展现。
PinpointPinpoint是一款对Java编写的大规模分布式系统的APM工具，由韩国人开源的分布式跟踪组件。
Skywalking国产的优秀APM组件，是一个对JAVA分布式应用程序集群的业务运行情况进行追踪、告警和分析的系统。

其他类似的组件还有美团点评的CAT，淘宝的鹰眼EgleEye。


上面列出的几种组件，其中Zipkin是严格按照Google Dapper论文实现的，下面介绍下其中涉及的基本概念。

Span基本工作单元，一次链路调用(可以是RPC，DB等没有特定的限制)创建一个span，通过一个64位ID标识它，uuid较为方便，span中还有其他的数据，例如描述信息，时间戳，key-value对的(Annotation)tag信息，parent-id等,其中parent-id可以表示span调用链路来源。

Trace:类似于树结构的Span集合，表示一条调用链路，存在唯一标识。比如你运行的分布式大数据存储一次Trace就由你的一次请求组成。

Annotation: 注解,用来记录请求特定事件相关信息(例如时间)，通常包含四个注解信息：  (1) cs：Client Start,表示客户端发起请求
 (2) sr：Server Receive,表示服务端收到请求
 (3) ss：Server Send,表示服务端完成处理，并将结果发送给客户端
 (4) cr：Client Received,表示客户端获取到服务端返回信息






















[Zipkin官网](https://zipkin.io/)  
[Zipkin文档](https://zipkin.io/pages/quickstart.html)  
[Zipkin Github](https://github.com/openzipkin/zipkin)  

[Istio Zipkin](https://istio.io/latest/docs/tasks/observability/distributed-tracing/zipkin/)  
[Zipkin Tracing Integration](https://www.instana.com/supported-technologies/zipkin-apm-integration/)  
[Distributed Systems Tracing with Zipkin](https://blog.twitter.com/engineering/en_us/a/2012/distributed-systems-tracing-with-zipkin.html)  
[ZIPKIN TUTORIAL: GET STARTED EASILY WITH DISTRIBUTED TRACING](https://www.scalyr.com/blog/zipkin-tutorial-distributed-tracing/)  
[Distributed Tracing with Spring Cloud Sleuth and Spring Cloud Zipkin](https://spring.io/blog/2016/02/15/distributed-tracing-with-spring-cloud-sleuth-and-spring-cloud-zipkin)  

[Zipkin快速开始](https://segmentfault.com/a/1190000012342007)  
[Zipkin介绍和使用](https://www.jianshu.com/p/2fcbc8bba1c1)  
[一文搞懂基于zipkin的分布式追踪系统原理与实现](https://juejin.cn/post/6844903761438048269)  
[zipkin教程简单介绍及环境搭建（一）](https://mykite.github.io/2017/04/21/zipkin%E6%95%99%E7%A8%8B%E7%AE%80%E5%8D%95%E4%BB%8B%E7%BB%8D%E5%8F%8A%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA%EF%BC%88%E4%B8%80%EF%BC%89/)  
[使用zipkin做分布式链路监控](https://blog.csdn.net/u013815546/article/details/73086975)  


Zipkin is a distributed tracing system

Zipkin is a distributed tracing system. It helps gather timing data needed to troubleshoot latency problems in service architectures. Features include both the collection and lookup of this data.
Zipkin 是一个分布式追踪系统。它有助于收集对服务架构中的延迟问题进行故障排除所需的计时数据。功能包括收集和查找这些数据。


Zipkin是一个分布式链路跟踪系统，可以采集时序数据来协助定位延迟等相关问题。

Zipkin 为一个分布式的调用链跟踪系统( distributed tracing system ) ,设计来源于 google dapper paper






