package net.jrz.tlias.config;

import net.jrz.tlias.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器对象 /* 匹配的是单层路径，/**匹配的是任意层级路径 和.gitignore中的路径匹配很像
        // 这是spring mvc的路径匹配格式，与前面的servlet提供的过滤器中的servlet路径匹配格式不同
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }
}
