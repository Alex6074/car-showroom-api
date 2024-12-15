package ru.clevertec.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.clevertec.annotation.MonitorPerformance;
import ru.clevertec.properties.PerformanceMonitorProperties;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PerformanceMonitorAspect {
    private final PerformanceMonitorProperties properties;

    @Around("@annotation(monitorPerformance)")
    public Object monitorMethodExecution(ProceedingJoinPoint joinPoint, MonitorPerformance monitorPerformance) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        if (executionTime >= properties.getThreshold()) {
            log.info("Method [{}] executed in [{}] ms.", joinPoint.getSignature(), executionTime);
        }

        return result;
    }
}
