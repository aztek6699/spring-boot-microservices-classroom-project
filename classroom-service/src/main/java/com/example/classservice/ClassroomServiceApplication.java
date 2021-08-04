package com.example.classservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ClassroomServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassroomServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClientBuilder() {

        HttpClient client = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
				.responseTimeout(Duration.ofSeconds(10));



        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(client));
    }

    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
