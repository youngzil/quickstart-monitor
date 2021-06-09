Hlog+Pinpoint
Pinpoint is an open source APM (Application Performance Management) tool for large-scale distributed systems written in Java. 


https://github.com/naver/pinpoint
官方安装手册：https://naver.github.io/pinpoint/quickstart.html



pinpoint是java的apm工具，可以监控系统性能，分析访问情况，可以适用于分布式系统，同时对spring boot有原生支持。公司刚好使用的是spring boot框架进行开发的，pinpoint可以拿来一用。pinpoint在github上有标准的安装文档，但是还是会遇到很多问题，现记录下自己安装过程和遗留问题。


Pinpoint是一款对Java编写的大规模分布式系统的APM工具，有些人也喜欢称呼这类工具为调用链系统、分布式跟踪系统。我们知道，前端向后台发起一个查询请求，后台服务可能要调用多个服务，每个服务可能又会调用其它服务，最终将结果返回，汇总到页面上。如果某个环节发生异常，工程师很难准确定位这个问题到底是由哪个服务调用造成的，Pinpoint等相关工具的作用就是追踪每个请求的完整调用链路，收集调用链路上每个服务的性能数据，方便工程师能够快速定位问题。



jdk7 --- Java运行环境
hbase-1.0 --- 数据库，用来存储监控信息
tomcat8.0 --- Web服务器
pinpoint-collector.war --- pp的控制器
pinpoint-web.war --- pp展示页面
pp-collector.init --- 用来快速启动pp-col，不要也可以
pp-web.init --- 用来快速启动pp-web，不要也可以


架构
HBase (用于存储数据)收集到的数据存到HBase中
Pinpoint Collector (信息的收集者，部署在tomcat中)收集各种性能数据
Pinpoint Web (提供WEB_UI界面，部署在tomcat中)将收集到的数据显示成WEB网页形式 
Pinpoint Agent (附加到 java 应用来做采样)和自己运行的应用关联起来的探针 


Pinpoint Agent（Send Profile Data（UDP/TCP + Thrift））---》Pinpoint Collector (HBase Write) ---》HBase Storage--->Pinpoint Web(Hbase Read,从HBase读取数据展示)


启动
java -javaagent:/aoho/auth_compose/pinpoint-bootstrap-1.6.0.jar -Dpinpoint.agentId=aoho-consumer -Dpinpoint.applicationName=aoho-consumer -jar id_generator/snowflake-id-generate-1.0-SNAPSHOT.jar




详解
https://juejin.im/post/5a0579e6f265da4326524f0f
https://juejin.im/entry/5a0e576ef265da43322724a2
https://blog.csdn.net/heyeqingquan/article/details/74456591



安装
https://www.jianshu.com/p/6d7ce28bb74d
https://blog.csdn.net/ZYQDuron/article/details/80238326
https://blog.csdn.net/wh211212/article/details/80437696























