package org.quickstart.monitor.prometheus;

import io.prometheus.client.Counter;

public class CounterTest {

    static final Counter requests = Counter.build().name("requests_total").help("Total requests.").register();

    public static void main(String[] args) {

    }

    static void processRequest() {
        requests.inc();
        // Your code here.
    }
}
