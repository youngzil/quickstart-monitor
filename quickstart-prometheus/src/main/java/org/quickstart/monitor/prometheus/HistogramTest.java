package org.quickstart.monitor.prometheus;

import io.prometheus.client.Histogram;

public class HistogramTest {

    static final Histogram requestLatency = Histogram.build()//
        .name("requests_latency_seconds")//
        .help("Request latency in seconds.")//
        .register();

    void processRequest() {
        Histogram.Timer requestTimer = requestLatency.startTimer();
        try {
            // Your code here.
        } finally {
            requestTimer.observeDuration();
        }
    }

    static final Histogram requestLatency2 = Histogram.build()//
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
