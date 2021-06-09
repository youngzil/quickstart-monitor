#FROM prom/prometheus
#RUN rm -rf /etc/localtime
#RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime


FROM centos:7
ADD prometheus-2.27.1.linux-amd64.tar.gz /

RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    mv /prometheus-2.27.1.linux-amd64 /prometheus

WORKDIR /prometheus
#ENTRYPOINT [ "/prometheus/prometheus" ]
CMD        [ "--config.file=/prometheus/prometheus.yml", \
             "--storage.tsdb.path=/prometheus/data", \
             "--web.console.libraries=/prometheus/console_libraries", \
             "--web.enable-lifecycle", \
             "--web.console.templates=/prometheus/consoles" ]



#生成镜像
#docker build -t myprometheus:1 .

# 创建持久化目录
#mkdir -p /Users/lengfeng/prometheus/data


# 挂载目录启动
#docker run -d --restart always -p 9090:9090 --name prometheus -v /Users/lengfeng/software/prometheus-2.27.1.darwin-amd64/prometheus.yml:/etc/prometheus/prometheus.yml -v /Users/lengfeng/prometheus/data:/prometheus myprometheus:1
