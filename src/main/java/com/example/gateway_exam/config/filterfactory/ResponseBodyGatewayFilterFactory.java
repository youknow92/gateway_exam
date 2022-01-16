package com.example.gateway_exam.config.filterfactory;

import com.example.gateway_exam.config.filter.ModifyResponseGatewayFilter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResponseBodyGatewayFilterFactory extends AbstractGatewayFilterFactory<ResponseBodyGatewayFilterFactory.Config> {

    public ResponseBodyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return new ModifyResponseGatewayFilter();
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

}
