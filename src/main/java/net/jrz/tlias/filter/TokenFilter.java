package net.jrz.tlias.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.jrz.tlias.utils.JwtUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = {"/*"})
public class TokenFilter implements Filter {
    // 释放web服务器等资源后执行
    @Override
    public void destroy() {
        System.out.println("destroy ... ");
        Filter.super.destroy();
    }

    // 拦截请求后执行
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. 获取请求url
        String url = request.getRequestURL().toString();

        // 2. 判断url是否是登录请求，是的话直接放行
        if (url.contains("login")){
            log.info("登录请求，直接放行");
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 计算令牌
        String jwt = request.getHeader("token");

        // 4. 判断令牌是否存在，如果不存在，返回错误结果
        if (!StringUtils.hasText(jwt)){
            log.info("获取的jwt令牌为空，返回错误结果");
            // 响应码在HttpServletResonse里面找
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 5. 解析token，如果解析失败，返回错误结果(未登录)
        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e){
            e.printStackTrace(System.out);
            log.info("解析令牌失败，返回错误结果");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 6. 放行
        log.info("令牌合法，放行");
        filterChain.doFilter(request, response);

        log.info("你可以在这里执行放行之后的逻辑 ...");
    }

    // 初始化web服务器后执行
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init ...");
        Filter.super.init(filterConfig);
    }
}
