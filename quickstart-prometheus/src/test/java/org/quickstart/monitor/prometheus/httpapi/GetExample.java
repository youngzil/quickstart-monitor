/**
 * 项目名称：quickstart-unirest
 * 文件名：GetExample.java
 * 版本信息：
 * 日期：2017年11月6日
 * Copyright yangzl Corporation 2017
 * 版权所有 *
 */
package org.quickstart.monitor.prometheus.httpapi;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * GetExample
 *
 * @author：youngzil@163.com
 * @2017年11月6日 上午10:45:56
 * @since 1.0
 */
public class GetExample {

    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    // private static String url = "http://localhost:9090/api/v1/query";
    // private static String promQL = "sum by(topic) (hermes_partition_not_assign_exception_count_total{exception=\"PARTITION_NOT_ASSIGN\"})";
    private static String url = "http://prometheus.kube-monitoring.wke-office.test.wacai.info/api/v1/query";
    // private static String query_range = "http://prometheus.kube-monitoring.wke-office.test.wacai.info/api/v1/query_range?query=up&start=2020-03-01T20:10:30.781Z&end=2020-03-01T20:11:00.781Z&step=15s";
    private static String query_range = "http://prometheus.kube-monitoring.wke-office.test.wacai.info/api/v1/query_range";
    private static String promQL =
        "(sum by (kubernetes_name)(rate(http_server_requests_seconds_sum[1m])))/(sum by (kubernetes_name)(rate(http_server_requests_seconds_count[1m])))";

    @Test
    public void testQuery() throws UnirestException {
        String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
        // String url = "http://localhost:9090/api/v1/query";
        // String promQL = "sum by(topic) (hermes_partition_not_assign_exception_count_total{exception=\"PARTITION_NOT_ASSIGN\"})";
        String url = "http://prometheus.kube-monitoring.wke-office.test.wacai.info/api/v1/query";
        // String promQL =
        //     "(sum by (kubernetes_name)(rate(http_server_requests_seconds_sum[1m])))/(sum by (kubernetes_name)(rate(http_server_requests_seconds_count[1m])))";
        String promQL = "rate(http_server_requests_seconds_sum[1m])";
        // Unirest.get("http://httpbin.org/{method}").routeParam("method", "get").queryString("name", "Mark").asJson();

        // HttpResponse<String> response = Unirest.get(url).header("User-Agent", USER_AGENT).header("X-Login-User","hermes").queryString("query",promQL).asString();
        HttpResponse<String> response = Unirest.get(url).queryString("query", promQL).asString();
        String body = response.getBody();
        System.out.println("body=" + body);
        // 我获取数据是Response.json中的，所以我定义了一个Response.class
        QueryResponse test = JSON.parseObject(body,QueryResponse.class);
        System.out.println(test);
    }

    @Test
    public void testQueryRange() throws UnirestException, UnsupportedEncodingException {
        String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

        String promQL = "rate(http_server_requests_seconds_sum[1m])";
            // "(sum by (kubernetes_name)(rate(http_server_requests_seconds_sum[1m])))/(sum by (kubernetes_name)(rate(http_server_requests_seconds_count[1m])))";
        String query_range2 =
            // "http://prometheus.kube-monitoring.wke-office.test.wacai.info/api/v1/query_range?query=\'(sum by (kubernetes_name)(rate(http_server_requests_seconds_sum[1m])))/(sum by (kubernetes_name)(rate(http_server_requests_seconds_count[1m])))\'&start=2022-04-25T00:00:30.781Z&end=2022-04-25T08:45:00.781Z&step=15s";
            "http://prometheus.kube-monitoring.wke-office.test.wacai.info/api/v1/query_range?query="+ URLEncoder.encode(promQL, "UTF-8")+"&start=2022-04-25T08:00:30.781Z&end=2022-04-25T08:05:00.781Z&step=15s";
        HttpResponse<String> response2 = Unirest.get(query_range2).asString();
        String body2 = response2.getBody();
        System.out.println("body2=" + body2);


        String query_range = "http://prometheus.kube-monitoring.wke-office.test.wacai.info/api/v1/query_range";

        // Unirest.get("http://httpbin.org/{method}").routeParam("method", "get").queryString("name", "Mark").asJson();

        HttpResponse<String> response = Unirest.get(query_range).queryString("query", promQL).queryString("start", "2022-04-25T00:00:30.781Z")
            .queryString("end", "2022-04-25T08:45:00.781Z").queryString("step", "30s").asString();
        String body = response.getBody();
        System.out.println("body=" + body);

        // 我获取数据是Response.json中的，所以我定义了一个Response.class
        QueryRangeResponse test = JSON.parseObject(body,QueryRangeResponse.class);
        System.out.println(test);
    }

}
