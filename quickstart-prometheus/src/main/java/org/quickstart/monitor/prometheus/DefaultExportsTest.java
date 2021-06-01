package org.quickstart.monitor.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DefaultExportsTest {

    // 运行main函数，并且访问http://localhost:1234/metrics
    // 这里返回的是当前应用中JVM相关的监控指标，包括JVM中GC，Memory Pool，JMX, Classloading，以及线程数等监控统计信息。

    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultExports.initialize();

        // 当需要统计对某些特定方法调用次数的统计时
        final Counter requests = Counter.build().name("requests_total").help("Total requests.").register();
        requests.inc();

        // 可以通过Gauge统计函数中某个方法正在处理中的调用次数：
        final Gauge inprogressRequests = Gauge.build().name("inprogress_requests").help("Inprogress requests.").register();
        inprogressRequests.inc();
        inprogressRequests.inc();
        // Your code here.
        inprogressRequests.dec();

        // 自动统计请求的响应时间以及请求的数据量在Buckets下的分布情况。
        final Histogram receivedBytes = Histogram.build().name("requests_size_bytes").help("Request size in bytes.").register();
        final Histogram requestLatency = Histogram.build().name("requests_latency_seconds").help("Request latency in seconds.").register();

        Histogram.Timer requestTimer = requestLatency.startTimer();
        try {
            // Your code here.
            TimeUnit.SECONDS.sleep(2);
        } finally {
            requestTimer.observeDuration();
            int bodyBytes = 100;
            receivedBytes.observe(bodyBytes);
        }


        new HTTPServer(1234);

    }

}
