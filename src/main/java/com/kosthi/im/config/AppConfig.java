package com.kosthi.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 应用配置类
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2023/4/2 10:47
 */
@Configuration
public class AppConfig {
    /**
     * 微服务环境中，可以同时配置多个实例。
     * 带负载均衡(@LoadBalanced)的 RestTemplate 必须使用微服务名称发起请求，不能使用 ip:port
     * 不带负载均衡(@LoadBalanced)的 RestTemplate 不能使用微服务名称发起请求，只能使用 ip:port
     * <pre>
     *     默认使用 org.springframework.http.client.SimpleClientHttpRequestFactory 客户端，
     *     底层使用 jdk 标准的 API，即 java.net 包下的 API，如 java.net.HttpURLConnection 等。
     * </pre>
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        // 先设置 http 连接工厂的连接超时时间为 30 秒，读取超时时间为 120 秒
        // 请求发送后，对方数据量比较大，需要长时间处理数据时，读取超时时间需要设置的长一些，否则对方还未处理完，连接就超时了。
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30 * 1000);
        requestFactory.setReadTimeout(120 * 1000);
        // 将 RestTemplate 实例交由 Spring 容器管理
        return new RestTemplate(requestFactory);
    }

    /**
     * 切换 OkHttpClient 客户端
     * 设置 http 连接工厂，然后将 RestTemplate 实例交由 Spring 容器管理
     *
     * @return
     */
    @Bean
    public RestTemplate okRestTemplate() {
        // 先设置 http 连接工厂的连接超时时间为 30 秒，读取超时时间为 120 秒
        OkHttp3ClientHttpRequestFactory okRequestFactory = new OkHttp3ClientHttpRequestFactory();
        okRequestFactory.setConnectTimeout(30 * 1000);
        okRequestFactory.setReadTimeout(60 * 1000);
        return new RestTemplate(okRequestFactory);
    }
}
