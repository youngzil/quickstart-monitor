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

/**
 * GetExample
 *
 * @author：youngzil@163.com
 * @2017年11月6日 上午10:45:56
 * @since 1.0
 */
public class GetExample {

    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    private static String url = "http://localhost:9090/api/v1/query";
    private static String promQL = "sum by(topic) (hermes_partition_not_assign_exception_count_total{exception=\"PARTITION_NOT_ASSIGN\"})";


    public static void main(String[] args) throws UnirestException {
        // Unirest.get("http://httpbin.org/{method}").routeParam("method", "get").queryString("name", "Mark").asJson();

        HttpResponse<String> response = Unirest.get(url).header("User-Agent", USER_AGENT).queryString("query",promQL).asString();
        String body = response.getBody();

        // 我获取数据是Response.json中的，所以我定义了一个Response.class
        Response test = JSON.parseObject(body,Response.class);
        System.out.println(test);
    }

}
