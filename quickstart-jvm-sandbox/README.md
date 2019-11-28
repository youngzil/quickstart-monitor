https://github.com/alibaba/jvm-sandbox



1、查询进程PID
ps -ef|grep Clock

2、启动沙箱
sh sandbox.sh -p 37310

3、查看模块
sh sandbox.sh -p 37310 -l

4、修复clock#checkState()方法
sh sandbox.sh -p 37310 -d 'broken-clock-tinker/repairCheckState'

5、卸载沙箱
sh sandbox.sh -p 37511 -S




JVM SandBox 的技术原理与应用分析
https://www.infoq.cn/article/TSY4lGjvSfwEuXEBW*Gp




参考
http://attanwu.com/post/28/
https://yq.aliyun.com/articles/530091




