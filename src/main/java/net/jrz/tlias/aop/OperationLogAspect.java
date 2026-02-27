package net.jrz.tlias.aop;

import net.jrz.tlias.annotation.LogOperation;
import net.jrz.tlias.mapper.OperateLogMapper;
import net.jrz.tlias.pojo.OperateLog;
import net.jrz.tlias.utils.CurrentHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    OperateLogMapper operateLogMapper;
    // 其实可以直接写@Around("@annotation(log)")，但是这个可读性较差，还是建议指定注解类型，然后将注解作为实例返回
    // @Around("@annotation(log)") 中的 log 不是注解全类名，而是通知方法中参数的 变量名。Spring AOP 通过这个变量名将目标方法上的 @LogOperation 注解实例绑定到参数 LogOperation log 上，从而可以在通知中访问注解的属性
    @Around("@annotation(net.jrz.tlias.annotation.LogOperation) && @annotation(log)")
    public Object around(ProceedingJoinPoint joinPoint, LogOperation log) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 记录结束时间
        long endTime = System.currentTimeMillis();

        // 耗时
        long costTime = endTime - startTime;

        // 构建日志对象
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(getCurrentUserId());
        operateLog.setOperateTime(LocalDateTime.now());
        operateLog.setClassName(joinPoint.getTarget().getClass().getCanonicalName());
        operateLog.setMethodName(joinPoint.getSignature().getName());
        operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        operateLog.setReturnValue(result.toString());
        operateLog.setCostTime(costTime);

        // 插入日志对象到数据库
        operateLogMapper.insert(operateLog);
        return result;
    }

    public int getCurrentUserId(){
        return CurrentHolder.getCurrentId();
    }


}
