package org.gxstar.randomdata.config;

import org.gxstar.randomdata.grpc.client.GrpcClientConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = "org.gxstar.randomdata")
@Import(GrpcClientConfiguration.class)
public class AppConfig {

    @Bean
    public ClientHttpRequestFactory httpRequestFactory(
            @Value("${rest.client.timeout-millis.read:300}") final Integer readTimeoutMillis,
            @Value("${rest.client.timeout-millis.connection:300}")final Integer connectionTimeoutMillis) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectionTimeoutMillis);
        factory.setReadTimeout(readTimeoutMillis);
        return factory;
    }

    @Bean("restClientWithTimeouts")
    @Primary
    public RestClient restClientWithTimeouts(ClientHttpRequestFactory factory) {
        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }
}
