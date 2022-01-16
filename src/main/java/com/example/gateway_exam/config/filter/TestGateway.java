//package com.example.gateway_exam.config.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.reactivestreams.Publisher;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.nio.channels.Channels;
//import java.nio.charset.StandardCharsets;
//
//@Slf4j
//@Component
//public class TestGateway implements GlobalFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        ServerHttpRequestDecorator serverHttpRequestDecorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
//            String requestBody;
//
//            @Override
//            public Flux<DataBuffer> getBody() {
//                return super.getBody().doOnNext(dataBuffer -> {
//                    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//                        Channels.newChannel(byteArrayOutputStream).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
//                        requestBody = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
////                        requestBody = IOUtils.toString(byteArrayOutputStream.toByteArray(), "UTF-8");
//                        log.info("Request Body {}",requestBody);
//                    } catch (IOException e) {
//                        log.error(requestBody, e);
//                    }
//                });
//            }
//        };
//        ServerHttpResponseDecorator serverHttpResponseDecorator = new ServerHttpResponseDecorator(
//                exchange.getResponse()) {
//            String responseBody = "";
//
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//
//                Mono<DataBuffer> buffer = Mono.from(body);
//                return super.writeWith(buffer.doOnNext(dataBuffer -> {
//                    long start = System.currentTimeMillis();
//                    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//                        Channels.newChannel(byteArrayOutputStream).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
//                        responseBody = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
////                        responseBody = IOUtils.toString(byteArrayOutputStream.toByteArray(), "UTF-8");
//                        log.info(responseBody);
//                    } catch (Exception e) {
//                        log.error(responseBody, e);
//                    }
//                    log.info("Time Taken to log response :{}", System.currentTimeMillis() - start);
//                }));
//            }
//        };
//
//        serverHttpResponseDecorator.writeWith()
//
//        return chain.filter(
//                exchange.mutate().request(serverHttpRequestDecorator).response(serverHttpResponseDecorator).build());
//    }
//
//}
