package org.quickstart.monitor.prometheus.httpapi;

import com.alibaba.fastjson.JSONObject;

@lombok.Data
public class QueryRangeResponse {
    private String status;
    private Data data;

    @lombok.Data
    static class Data {
        private String resultType;
        private Result[] result;

    }

    @lombok.Data
    static class Result {
        private JSONObject metric;
        private String[][] values;
    }

}
