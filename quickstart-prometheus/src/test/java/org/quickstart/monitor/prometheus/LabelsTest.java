package org.quickstart.monitor.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class LabelsTest {

    static final Counter requests2 = Counter.build()//
        .name("my_library_requests_total")//
        .help("Total requests.")//
        .labelNames("path")//
        .register();

    static final Counter requests = Counter.build()//
        .name("my_library_requests_total")//
        .help("Total requests.")//
        .labelNames("method")//
        .register();

    void processGetRequest() {
        requests.labels("get").inc();
        // Your code here.
    }


    static final Gauge activeTransactions = Gauge.build()//
        .name("my_library_transactions_active")//
        .help("Active transactions.")//
        .register();

    void processThatCalculates(String key) {
        activeTransactions.inc();
        try {
            // Perform work.
        } finally{
            activeTransactions.dec();
        }
    }


    static final Counter calculationsCounter = Counter.build()
        .name("my_library_calculations_total").help("Total calls.")
        .labelNames("key").register();

    void processThatCalculates3(String key) {
        calculationsCounter.labels(key).inc();
        // Run calculations.
    }

}
