项目地址
https://github.com/youngzil/quickstart-monitor



Greys是一个JVM进程执行过程中的异常诊断工具。 在不中断程序执行的情况下轻松完成JVM相关问题排查工作。
和HouseMD一样，Greys-Anatomy取名同名美剧“实习医生格蕾”，目的是向前辈致敬。代码编写的时候参考了BTrace和HouseMD两个前辈的思路。


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







































