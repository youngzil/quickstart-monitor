package org.quickstart.monitor.prometheus.httpapi;

@lombok.Data
public class MetricValue {
    private Metric metric;
    private Object[] value;
}
