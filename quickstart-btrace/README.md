https://github.com/btraceio/btrace




很早的时候，我们使用BTrace排查问题，在感叹BTrace的强大之余，也曾好几次将线上系统折腾挂掉。2012年淘宝的聚石写了HouseMD，将常用的几个Btrace脚本整合在一起形成一个独立风格的应用，但其核心代码用的是Scala，我们没这方面的编程维护经验，所以只好艳羡HouseMD的才思敏捷而无法在其上增加功能。

Greys是一个JVM进程执行过程中的异常诊断工具，可以在不中断程序执行的情况下轻松完成问题排查工作。

和HouseMD一样，Greys-Anatomy取名同名美剧“实习医生格蕾”，目的是向前辈致敬。代码编写的时候参考了BTrace和HouseMD两个前辈的思路。

常见的动态追踪工具有BTrace、HouseMD（该项目已经停止开发）、Greys-Anatomy（国人开发，个人开发者）、Byteman（JBoss出品），注意Java运行时追踪工具并不限于这几种，但是这几个是相对比较常用的，本文主要介绍BTrace。


sh btrace -o out.csv 70386 /Users/yangzl/git/quickstart-monitor/quickstart-btrace/target/test-classes/org/quickstart/btrace/Tracer.java


参考
https://blog.csdn.net/xingbear/article/details/78091851
https://blog.csdn.net/ZYC88888/article/details/81662671

