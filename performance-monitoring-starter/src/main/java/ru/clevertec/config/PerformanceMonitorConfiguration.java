package ru.clevertec.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.aspect.PerformanceMonitorAspect;
import ru.clevertec.properties.PerformanceMonitorProperties;

@Configuration
@EnableConfigurationProperties(PerformanceMonitorProperties.class)
public class PerformanceMonitorConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "monitor.performance", name = "enabled", havingValue = "true", matchIfMissing = true)
    public PerformanceMonitorAspect performanceMonitorAspect(PerformanceMonitorProperties properties) {
        return new PerformanceMonitorAspect(properties);
    }
}