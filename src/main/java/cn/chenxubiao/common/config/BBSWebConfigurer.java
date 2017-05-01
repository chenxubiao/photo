package cn.chenxubiao.common.config;

import cn.chenxubiao.common.interceptor.AuthorityInterceptor;
import cn.chenxubiao.common.interceptor.BBSInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by chenxb on 17-3-30.
 */
@Configuration
public class BBSWebConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new BBSInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    // 跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }


}
