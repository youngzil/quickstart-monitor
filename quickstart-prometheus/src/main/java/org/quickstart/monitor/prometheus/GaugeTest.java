package org.quickstart.monitor.prometheus;

import io.prometheus.client.Gauge;

public class GaugeTest {

    static final Gauge inprogressRequests = Gauge.build() //
        .name("inprogress_requests") //
        .help("Inprogress requests.") //
        .register();

    void processRequest() {
        inprogressRequests.inc();
        // Your code here.
        inprogressRequests.dec();
    }

}
