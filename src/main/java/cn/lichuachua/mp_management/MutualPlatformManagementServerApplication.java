package cn.lichuachua.mp_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 李歘歘
 * 运行类
 */
@EnableSwagger2
@SpringBootApplication
public class MutualPlatformManagementServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(MutualPlatformManagementServerApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
