package cn.lcdiao.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("cn.lcdiao.springboot.dao")
public class SpringbootApplication extends SpringBootServletInitializer {

    //TODO 修改配置文件里的redis host

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    /**为打war包而设置*/
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringbootApplication.class);
    }
}
