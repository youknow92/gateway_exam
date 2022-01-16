package com.example.gateway_exam.config.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ApiGatewayFilterFactory extends AbstractGatewayFilterFactory<ApiGatewayFilterFactory.Config> {

    public ApiGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("ApiFilter baseMessage>>>>>> {}", config.getBaseMessage());
            if (config.isPreLogger()) {
                log.info("ApiFilter Start>>>>>> {}", exchange.getRequest());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("ApiFilter End>>>>>>" + exchange.getResponse());
                }
            }));
        };
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

}
