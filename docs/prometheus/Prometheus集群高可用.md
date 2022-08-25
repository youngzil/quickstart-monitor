



Prometheus高可用部署

1. 基本HA：解决：服务可用性
2. 基本HA + 远程存储：解决：监控数据持久化，同时能够确保Promthues Server的可迁移性
3. 基本HA + 远程存储 + 联邦集群：解决：水平扩展



[Prometheus高可用部署](https://www.prometheus.wang/ha/prometheus-and-high-availability.html)
[Prometheus联邦机制实现分布式](https://blog.csdn.net/YYC1503/article/details/103194719)

## 基本HA

解决：服务可用性

但是这种方法存在明显的弊端：
1. 无法扩展 
2. 数据可能不一致

由于Promthues的Pull机制的设计，为了确保Promthues服务的可用性，用户只需要部署多套Prometheus Server实例，并且采集相同的Exporter目标即可。

基本的HA模式只能确保Promthues服务的可用性问题，但是不解决Prometheus Server之间的数据一致性问题以及持久化问题(数据丢失后无法恢复)，也无法进行动态的扩展。因此这种部署方式适合监控规模不大，Promthues Server也不会频繁发生迁移的情况，并且只需要保存短周期监控数据的场景。


## 基本HA + 远程存储

解决：监控数据持久化，同时能够确保Promthues Server的可迁移性

相比第一种方法、这种架构有以下优点：
1. 可以保证数据一致 
2. 数据可长期存储 
3. server可以灵活迁移

在基本HA模式的基础上通过添加Remote Storage存储支持，将监控数据保存在第三方存储服务上。


在解决了Promthues服务可用性的基础上，同时确保了数据的持久化，当Promthues Server发生宕机或者数据丢失的情况下，可以快速的恢复。 同时Promthues Server可能很好的进行迁移。因此，该方案适用于用户监控规模不大，但是希望能够将监控数据持久化，同时能够确保Promthues Server的可迁移性的场景。


## 基本HA + 远程存储 + 联邦集群

解决：水平扩展

这种架构有以下优点：

1、资料能够被持久性保持在第三方存储系统中

2、能够依据不同任务进行层级划分

3、server可以灵活迁移

4、serverA和serverB可以用前面提到的方法进行高可用扩展

当然伴随而来的就是：

1、单一资料中心带来的单点(ServerC压力较大)

2、分层带来的配置复杂，维护成本较高



当单台Promthues Server无法处理大量的采集任务时，用户可以考虑基于Prometheus联邦集群的方式将监控采集任务划分到不同的Promthues实例当中即在任务级别功能分区。


这种部署方式一般适用于两种场景：

场景一：单数据中心 + 大量的采集任务

这种场景下Promthues的性能瓶颈主要在于大量的采集任务，因此用户需要利用Prometheus联邦集群的特性，将不同类型的采集任务划分到不同的Promthues子服务中，从而实现功能分区。例如一个Promthues Server负责采集基础设施相关的监控指标，另外一个Prometheus Server负责采集应用监控指标。再有上层Prometheus Server实现对数据的汇聚。

场景二：多数据中心

这种模式也适合与多数据中心的情况，当Promthues Server无法直接与数据中心中的Exporter进行通讯时，在每一个数据中部署一个单独的Promthues Server负责当前数据中心的采集任务是一个不错的方式。这样可以避免用户进行大量的网络配置，只需要确保主Promthues Server实例能够与当前数据中心的Prometheus Server通讯即可。 中心Promthues Server负责实现对多数据中心数据的聚合。


