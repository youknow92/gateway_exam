package com.example.gateway_exam.config.filterfactory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

@Slf4j
@Component
public class ApiGatewayFilterFactory extends AbstractGatewayFilterFactory<ApiGatewayFilterFactory.Config> {

    private String getRequestBody(ServerHttpRequest request) {
        final ServerHttpRequest decorated = new ServerHttpRequestDecorator(request);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Flux<DataBuffer> requestBody = decorated.getBody();
        requestBody.map(dataBuffer -> {
            try {
                Channels.newChannel(byteArrayOutputStream)
                        .write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
            } catch (IOException e) {
                log.error("Unable to log input request due to an error", e);
            }
            return dataBuffer;
        });

        return byteArrayOutputStream.toString();
    }

//    private String getResponseBody(ServerHttpResponse response) {
//        final ServerHttpResponseDecorator decorated = new ServerHttpResponseDecorator(response);
//        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Flux<DataBuffer> requestBody = decorated.
//        requestBody.map(dataBuffer -> {
//            try {
//                Channels.newChannel(byteArrayOutputStream)
//                        .write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
//            } catch (IOException e) {
//                log.error("Unable to log input request due to an error", e);
//            }
//            return dataBuffer;
//        });
//
//        return byteArrayOutputStream.toString();
//    }

    public ApiGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return new OrderedGatewayFilter((exchange, chain)->{    //WebFlux를 활용하여 비동기 처리에서 request와 response를 가져올 수 있다.

            log.info("ApiFilter baseMessage>>>>>> {}", config.getBaseMessage());
            if (config.isPreLogger()) {
                log.info("ApiFilter Start>>>>>> {}", exchange.getRequest());
            }

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

//            //post filter 동작
//            return chain.filter(exchange).then(Mono.fromRunnable(()->{  //스프링5에서 지원하는 기능으로 비동기로 값을 전달할때 사용되는 객체
//                log.info("Custom POST filter : response id -> {}", response.getStatusCode());
//            }));
            return WebClient.create().get().uri("http://localhost:9080/api/callback").retrieve().bodyToMono
                    (String.class).flatMap(s -> {
                System.out.println(s);
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    if (config.isPostLogger()) {
                        log.info("ApiFilter End>>>>>>" + exchange.getResponse());
                    }
                }));
            });

        }, Ordered.LOWEST_PRECEDENCE); //필터의 우선순위
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

}
