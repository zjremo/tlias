package net.jrz.tlias.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(1) // 切面类执行顺序，前置通知是数字越小越先执行，后置通知是数字越大越先执行
public class RecordTimeAspect {

    // 切入点表达式一共有两种，一种是基于execution的表达式，一种是基于annotation的表达式
    // 切入点方法，公共的切入点表达式
    @Pointcut("execution(* net.jrz.tlias.service.*.*(..))")
    public void pt(){}

    // 前置通知，被动执行前
    @Before("pt()")
    public void before(JoinPoint joinPoint){
        log.info("In {}, before ... ", getClass().getSimpleName());
    }

    // 当加上Around后，此时为环绕通知，表示此时方法被完全接管，如果要执行原来的方法，需要执行pjp.proceed方法来主动调用
    // 一般切片表达式基于接口来进行描述，不要基于实现类
    /*
    * Around下获取连接点信息只能使用ProceedingJoinPoint, 其他四种类型只能使用JoinPoint，它是ProceedingJoinPoint的父类型
    * */
    @Around("execution(* net.jrz.tlias.service.*.page(java.lang.Integer, java.lang.Integer))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("In {}, around before ...", getClass().getSimpleName());

        // 执行原始方法
        String className = pjp.getTarget().getClass().getName(); // 获取目标类名
        Signature signature = pjp.getSignature(); // 获取对目标方法的签名
        String methodName = signature.getName(); // 获取目标方法名
        Object[] args = pjp.getArgs(); // 获取目标方法运行参数
        Object result = pjp.proceed(); // 获取执行结果

        log.info("In {}, around after ... ", getClass().getSimpleName());
        return result;
   }

   // 后置通知
   @After("pt()")
   public void after(JoinPoint joinPoint){
       log.info("In {}, after ... ", getClass().getSimpleName());
   }

   // 返回后通知(程序在正常执行的情况下执行的后置通知)
   @AfterReturning("pt()")
   public void afterReturning(JoinPoint joinPoint){
        log.info("In {}, afterReturning ... ", getClass().getSimpleName());
   }

   @AfterThrowing("pt()")
   public void afterThrowing(JoinPoint joinPoint){
        log.info("In {}, afterThrowing ... ", getClass().getSimpleName());
   }

}
