//package com.example.gateway_exam.config.filter;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import reactor.core.publisher.Mono;
//
//import java.util.Map;
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class RouteConfiguration {
//
//    //    @Value("${routes.api}")
//    private final String apiHost = "http://localhost:9080";
//
//    @Bean
//    public RouteLocator apiRoutes(RouteLocatorBuilder builder) {
//
//        return builder.routes()
//                .route("rewrite_response_upper", r -> r.path("/api/**")
//                        .filters(f -> f
//                                .modifyResponseBody(Map.class, Map.class
//                                        , (exchange, s) -> {
//                                            String status = s.get("status").toString();
//                                            log.info(s.toString());
//                                            if (HttpStatus.NOT_FOUND.name().equals(status)) {
//                                                exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
//                                            }
//                                            s.put("message", "실패코드");
////                                            String data = s.get("data").toString();
//                                            return Mono.just(s);
//                                        })
//                                .rewritePath("/api/?(?<segment>.*)", "/$\\{segment}")
//                                )
//                        .uri(apiHost))
//                .build();
//    }
//
//}