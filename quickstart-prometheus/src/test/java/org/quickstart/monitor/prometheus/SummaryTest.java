package org.quickstart.monitor.prometheus;

import io.prometheus.client.Summary;

public class SummaryTest {

    static final Summary receivedBytes = Summary.build().name("requests_size_bytes").help("Request size in bytes.").register();
    static final Summary requestLatency = Summary.build().name("requests_latency_seconds").help("Request latency in seconds.").register();

    void processRequest() {
        Summary.Timer requestTimer = requestLatency.startTimer();

        int requestSize = 1000;
        try {
            // Your code here.
        } finally {
            receivedBytes.observe(requestSize);
            requestTimer.observeDuration();
        }
    }

    static final Summary requestLatency2 = Summary.build()//
        .quantile(0.5, 0.05)   // Add 50th percentile (= median) with 5% tolerated error
        .quantile(0.9, 0.01)   // Add 90th percentile with 1% tolerated error
        .name("requests_latency_seconds")//
        .help("Request latency in seconds.")//
        .register();

    void processRequest2() {
        requestLatency.time(new Runnable() {
            @Override
            public void run() {
                // Your code here.
            }
        });

        // Or the Java 8 lambda equivalent
        requestLatency2.time(() -> {
            // Your code here.
        });
    }

}
