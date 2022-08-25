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
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GetExample
 *
 * @author：youngzil@163.com
 * @2017年11月6日 上午10:45:56
 * @since 1.0
 */
public class GetExample {

    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    // private static String query = "http://localhost:9090/api/v1/query";
    // private static String query_range = "http://localhost:9090/api/v1/query_range";
    private static String query = "http://prometheus.kube-monitoring.wke-office.test.wacai.info/api/v1/query";
    private static String query_range = "http://prometheus.kube-monitoring.wke-office.test.wacai.info/api/v1/query_range";

    private static String promQL =
        "(sum by (kubernetes_name)(rate(http_server_requests_seconds_sum[1m])))/(sum by (kubernetes_name)(rate(http_server_requests_seconds_count[1m])))";

    @Test
    public void testQuery() throws UnirestException {

        String url = query;

        // String promQL = "rate(http_server_requests_seconds_sum[1m])";
        String promQL = "count by (job) (kafka_topic_partitions)";
        // Unirest.get("http://httpbin.org/{method}").routeParam("method", "get").queryString("name", "Mark").asJson();

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String nowDateTime = dateTimeFormatter.format(zonedDateTime);
        System.out.println(nowDateTime);

        String bb = Unirest.get(url)//
            .queryString("query", promQL)//
            .queryString("time", nowDateTime) // 指定时间戳。可选参数，默认情况下使用Prometheus当前系统时间。
            .asString().getBody();
        System.out.println("body=" + bb);

        // HttpResponse<String> response = Unirest.get(url).header("User-Agent", USER_AGENT).header("X-Login-User","hermes").queryString("query",promQL).asString();
        HttpResponse<String> response = Unirest.get(url)//
            .queryString("query", promQL)//
            .queryString("time", "") // 指定时间戳。可选参数，默认情况下使用Prometheus当前系统时间。
            .asString();
        String body = response.getBody();
        System.out.println("body=" + body);

         response = Unirest.get(url)//
            .queryString("query", promQL)//
            // .queryString("time", "") // 指定时间戳。可选参数，默认情况下使用Prometheus当前系统时间。
            .asString();
         body = response.getBody();
        System.out.println("body=" + body);

        // 我获取数据是Response.json中的，所以我定义了一个Response.class
        QueryResponse queryResponse = JSON.parseObject(body, QueryResponse.class);

        /*Arrays.stream(queryResponse.getData().getResult()).map(result->{
            String lineName = result.getMetric().getString("job");
            String timestamp = result.getValue()[0];
            String value = result.getValue()[1];
        })*/

        System.out.println(queryResponse);
    }

    @Test
    public void testQueryRange() throws UnirestException, UnsupportedEncodingException {
        //String url = query_range + "?query=up&start=2020-03-01T20:10:30.781Z&end=2020-03-01T20:11:00.781Z&step=15s";
        String promQL = "sum by (topic) (rate(kafka_topic_partition_current_offset{topic=~\"red.alert\"}[3m]))";
        String url =
            //  query_range + "?query=\'(sum by (kubernetes_name)(rate(http_server_requests_seconds_sum[1m])))/(sum by (kubernetes_name)(rate(http_server_requests_seconds_count[1m])))\'&start=2022-04-25T00:00:30.781Z&end=2022-04-25T08:45:00.781Z&step=15s";
            query_range + "?query=" + URLEncoder.encode(promQL, "UTF-8") + "&start=2022-04-25T08:00:30.781Z&end=2022-04-25T08:05:00.781Z&step=15s";
        HttpResponse<String> response2 = Unirest.get(url).asString();
        String body2 = response2.getBody();
        // System.out.println("body2=" + body2);

        // Unirest.get("http://httpbin.org/{method}").routeParam("method", "get").queryString("name", "Mark").asJson();
        // yyyy-MM-dd'T'HH:mm:ss.SSSZ

        // 2022-04-26T08:00:30.781Z

        HttpResponse<String> response = Unirest.get(query_range)//
            .queryString("query", promQL)//
            // .queryString("start", "2022-04-27T09:00:30.781Z")//查询的数据就是8个小时后的数据，时间戳直接解析出来是8小时后的时间
            // .queryString("start", "2022-04-27T09:00:30.781+08:00")//查询的数据就是当前时间的，时间戳直接解析出来是当前时间
            .queryString("start", "1651021230.781")//和+08:00一样，查询的数据就是当前时间的，时间戳直接解析出来是当前时间
            // .queryString("end", "2022-04-27T19:00:30.781Z")//查询的数据就是8个小时后的数据，时间戳直接解析出来就对的
            // .queryString("start", "2022-04-27T09:00:30.781+0800")//会报错，不识别这种格式日期，invalid parameter \"start\": cannot parse \"2022-04-27T09:00:30.781+0800\" to a valid timestamp"
            .queryString("end", "2022-04-27T19:00:30.000Z")//
            .queryString("step", "15s")//
            .asString();
        String body = response.getBody();
        System.out.println("body=" + body);
        // 2022-04-27T15:56:30.000+0800
        // 2022-04-27T07:56:30.000+0800
        // 我获取数据是Response.json中的，所以我定义了一个Response.class
        QueryRangeResponse test = JSON.parseObject(body, QueryRangeResponse.class);
        System.out.println(test);
    }

    @Test
    public void testQuery2() throws UnirestException {

        String url = query;

        // String promQL = "rate(http_server_requests_seconds_sum[1m])";

        // Unirest.get("http://httpbin.org/{method}").routeParam("method", "get").queryString("name", "Mark").asJson();

        String promQL = "sum by (job) (kafka_topic_partitions)";
        String key = "job";

        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        List<ZonedDateTime> dateTimes = new ArrayList<>();
        dateTimes.add(zonedDateTime);
        dateTimes.add(zonedDateTime.with(LocalTime.MAX).minusDays(1));
        dateTimes.add(zonedDateTime.with(LocalTime.MAX).minusDays(2));
        dateTimes.add(zonedDateTime.with(LocalTime.MAX).minusDays(3));

        List<Map<String, Long>> topicCountListMap = getTopicNumMetric(promQL, key, dateTimes);
        System.out.println(topicCountListMap);

        promQL = "sum by (job) (kafka_topic_partition_current_offset)";

        List<Map<String, Long>> msgCountListMap = getTopicNumMetric(promQL, key, dateTimes);
        System.out.println(msgCountListMap);

        /*for (Map<String, Long> map : msgCountListMap) {
            System.out.println("-------------------------------------------------------------------------------------");
            // 正序
            // map.values().stream().sorted().collect(Collectors.toList()).forEach(System.out::println);
            // 逆序
            // map.values().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).forEach(System.out::println);

            List<Map.Entry<String, Long>> list = new ArrayList<>(map.entrySet());
            list.sort(Comparator.comparing(Map.Entry::getValue));
            Map<String, Long> map2 = new LinkedHashMap<>();
            for (Map.Entry<String, Long> entry : list) {
                map2.put(entry.getKey(), entry.getValue());
            }
            map2.forEach((o1, o2) -> System.out.println(o1 + ":" + o2));

        }*/

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<TopicMetric> topicMetrics = new ArrayList<>();
        for (int i = 0; i < dateTimes.size() - 1; i++) {
            TopicMetric topicMetric = new TopicMetric();
            topicMetric.setDate(dateTimes.get(i).toLocalDate().format(dateTimeFormatter));
             String topicCountDesc = topicCountListMap.get(i).entrySet().stream().map(entry-> entry.getKey() + ":" + entry.getValue()).collect(
                 Collectors.joining("\n"));
             String msgCountDesc=msgCountListMap.get(i).entrySet().stream().map(entry-> entry.getKey() + ":" + entry.getValue()).collect(
                 Collectors.joining("\n"));
            topicMetric.setTopicCountDesc(topicCountDesc);
            topicMetric.setMsgCountDesc(msgCountDesc);

            topicMetrics.add(topicMetric);
        }

        System.out.println(topicMetrics);


    }

    private List<Map<String, Long>> getTopicNumMetric(String promQL, String key, List<ZonedDateTime> dateTimeList) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        List<String> dateTimes = dateTimeList.stream().map(zonedDateTime -> dateTimeFormatter.format(zonedDateTime)).collect(Collectors.toList());

        List<Map<String, Long>> listMap = new ArrayList<>();

        Map<String, Long> source = getMap(promQL, key, dateTimes.get(0));
        Map<String, Long> target = getMap(promQL, key, dateTimes.get(1));
        Map<String, Long> targetMap = mapSub(source, target);
        listMap.add(targetMap);

        for (int i = 2; i < dateTimes.size(); i++) {
            source = target;
            target = getMap(promQL, key, dateTimes.get(i));
            targetMap = mapSub(source, target);
            listMap.add(targetMap);
        }

        return listMap;
    }

    private Map<String, Long> getMap(String promQL, String key, String dateTime) {

        String body = Unirest.get(query)//
            .queryString("query", promQL)//
            .queryString("time", dateTime) // 指定时间戳。可选参数，默认情况下使用Prometheus当前系统时间。
            .asString().getBody();
        QueryResponse queryResponseToday = JSON.parseObject(body, QueryResponse.class);

        Map<String, Long> mapToday = Arrays.stream(queryResponseToday.getData().getResult()).collect(
            Collectors.toMap(result -> result.getMetric().get(key).toString(), result -> Long.valueOf(result.getValue()[1]), (k1, k2) -> k1));

        return mapToday;
    }

    private Map<String, Long> mapSub(Map<String, Long> source, Map<String, Long> target) {
        return Stream.concat(source.entrySet().stream(), target.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (value1, value2) -> value1 - value2));
    }

    @Data
    public static class TopicMetric {
        private String date;
        private String topicCountDesc;
        private String msgCountDesc;
    }

}
