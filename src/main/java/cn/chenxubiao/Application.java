package cn.chenxubiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by chenxb on 17-3-30.
 */
@SpringBootApplication
@EntityScan(basePackages = {"cn.chenxubiao.account.domain",
        "cn.chenxubiao.message.domain",
        "cn.chenxubiao.user.domain",
        "cn.chenxubiao.picture.domain",
        "cn.chenxubiao.tag.domain",
        "cn.chenxubiao.neo4j.domain",
        "cn.chenxubiao.project.domain"})

@ServletComponentScan
@ComponentScan(basePackages = "cn.chenxubiao")
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = {"cn.chenxubiao.picture.repository","cn.chenxubiao.tag.repository","cn.chenxubiao.project.repository","cn.chenxubiao.user.repository"})
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
