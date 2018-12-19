package com.plml.pplatform.aop;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Configuration
@Aspect
public class RestAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestAspect.class);

    private final MetricRegistry metricRegistry;

    public RestAspect(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object estimateMtrics(ProceedingJoinPoint joinPoint) throws Throwable {
        try (Timer.Context ignored = metricRegistry.timer(joinPoint.getSignature().getName() + ".timer").time()) {
            metricRegistry.counter(joinPoint.getSignature().getName() + ".counter").inc();
            Object obj = joinPoint.proceed();
            metricRegistry.counter(joinPoint.getSignature().getName() + ".success.counter").inc();
            return obj;
        }
    }

    @AfterReturning(pointcut="execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))"
            ,returning="result")
    public void afterLogin(JoinPoint joinPoint, Object result) throws Throwable {
        LOGGER.info(">>> user login to the platform: " + ((Authentication) result).getName());
        metricRegistry.counter(joinPoint.getSignature().getName() + ".counter").inc();
    }
}
