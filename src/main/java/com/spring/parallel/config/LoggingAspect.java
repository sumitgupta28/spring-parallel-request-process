package com.spring.parallel.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.spring.parallel.*.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        Object result = null;
        try {
            stopWatch.start();
            result = joinPoint.proceed();
            stopWatch.stop();
        } finally {
            log.info("{} - {} took {} ms", joinPoint.getThis().getClass().getName(), joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
        }
        return result;
    }
}
