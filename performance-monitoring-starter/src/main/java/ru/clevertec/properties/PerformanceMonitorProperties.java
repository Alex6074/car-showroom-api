package ru.clevertec.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "monitor.performance")
public class PerformanceMonitorProperties {
    private boolean enabled = true;
    private long threshold = 0;
}