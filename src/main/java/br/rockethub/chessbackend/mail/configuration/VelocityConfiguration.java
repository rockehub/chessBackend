package br.rockethub.chessbackend.mail.configuration;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.spring.VelocityEngineFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VelocityConfiguration {

    @Bean
    public VelocityEngine velocityEngine() throws Exception {
        VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
        factory.setResourceLoaderPath("classpath:/templates/velocity/");
        factory.afterPropertiesSet();
        return factory.getObject();
    }


}
