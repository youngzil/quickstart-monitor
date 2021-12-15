package org.quickstart.monitor.prometheus.httpapi;

@lombok.Data
public class Data {
    private String resultType;
    private MetricValue[] result;

}
