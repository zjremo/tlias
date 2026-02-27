package net.jrz.tlias.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class MyAspect1 {

    // 前置通知
    @Before("@annotation(net.jrz.tlias.annotation.TestOperation)")
    public void before(){
        log.info("In {}, for logOperation, before ... ", getClass().getSimpleName());
    }

    // 后置通知
    @After("@annotation(net.jrz.tlias.annotation.TestOperation)")
    public void after(){
        log.info("In {}, for logOperation, after ... ", getClass().getSimpleName());
    }
}
