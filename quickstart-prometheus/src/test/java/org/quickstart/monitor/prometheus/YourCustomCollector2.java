package org.quickstart.monitor.prometheus;

import io.prometheus.client.GaugeMetricFamily;
import io.prometheus.client.Collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Arrays;

class YourCustomCollector2 extends Collector {

    @Override
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> mfs = new ArrayList<MetricFamilySamples>();

        // With no labels.
        mfs.add(new GaugeMetricFamily("my_gauge_2", "help", 42));

        // With labels
        GaugeMetricFamily labeledGauge = new GaugeMetricFamily("my_other_gauge", "help", Arrays.asList("labelname"));
        labeledGauge.addMetric(Arrays.asList("foo"), 4);
        labeledGauge.addMetric(Arrays.asList("bar"), 5);
        mfs.add(labeledGauge);

        return mfs;
    }
}
