package net.jrz.tlias.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.jrz.tlias.utils.JwtUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    // 目标资源方法执行前执行 返回true放行 返回false不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求url
        String url = request.getRequestURL().toString();

        // 2. 判断请求url中是否包含login，如果包含，说明是登录操作进行放行
        if (url.contains("login")){
            log.info("登录请求，进行放行");
            return true;
        }

        // 3. 计算令牌
        String jwt = request.getHeader("token");

        // 4. 判断令牌是否存在，如果不存在，返回错误结果
        if (!StringUtils.hasText(jwt)){
            log.info("获取的jwt令牌为空，返回错误结果");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 5. 解析token，如果解析失败，返回错误结果(未登录)
        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.info("解析令牌失败，返回错误结果");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 6. 放行
        log.info("令牌合法，放行");
        return true;
    }

    // 目标资源方法执行后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler .... ");
    }

    // 视图渲染完毕后执行 这个是前后端不分离时采用，但是现在一般都是前后端分离，所以其没啥用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("afterCompletion .... ");
    }
}
