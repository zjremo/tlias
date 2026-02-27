package net.jrz.tlias.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(2)
public class MyAspect {

    @Before("net.jrz.tlias.aop.RecordTimeAspect.pt()")
    public void before(JoinPoint joinPoint){
        log.info("In {}, before ... ", getClass().getSimpleName());
    }

    @After("net.jrz.tlias.aop.RecordTimeAspect.pt()")
    public void after(JoinPoint joinPoint){
        log.info("In {}, after ... ", getClass().getSimpleName());
    }
}
