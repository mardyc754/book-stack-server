package com.bookstack.bookstack.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LoggerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.bookstack.bookstack.controllers.*.*(..))")
    private void controllerMethod() {
    }

    @Before("controllerMethod()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing method: {} ", joinPoint.getSignature().getName());
    }

    @After("controllerMethod()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("End of {} method execution", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "controllerMethod()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) throws Throwable {
        logger.error("**********");
        logger.error("An exception has been thrown in: {}", joinPoint.getSignature().getName());
        logger.error("{}", ex.toString());
        logger.error("Cause: {}", ex.getCause().toString());
        logger.error("**********");
    }

}
