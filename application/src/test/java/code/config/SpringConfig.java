package code.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(SpringConfig.TEST_PROFILE)
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "code")
@PropertySource(value = "classpath:/config/application-test.yml")
public class SpringConfig {
    public static final String TEST_PROFILE = "test";

}
