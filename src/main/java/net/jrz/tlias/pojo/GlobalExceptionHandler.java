package net.jrz.tlias.pojo;

import net.jrz.tlias.exception.DefaultException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理异常
    @ExceptionHandler
    public Result ex(Exception e){
        e.printStackTrace(System.out); // 打印堆栈中的异常信息
        // 捕获异常之后，响应一个标准的Result
        return Result.error("对不起，操作失败，请联系管理员");
    }

    @ExceptionHandler
    public Result defaultEx(DefaultException e){
        e.printStackTrace(System.out);
        return Result.error(e.getMessage());
    }
}
