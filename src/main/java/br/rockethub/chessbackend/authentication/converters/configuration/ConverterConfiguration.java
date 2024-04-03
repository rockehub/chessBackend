package br.rockethub.chessbackend.authentication.converters.configuration;

import br.rockethub.chessbackend.authentication.converters.UserToResponseUserDataConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(org.springframework.format.FormatterRegistry registry) {
        registry.addConverter(new UserToResponseUserDataConverter());
    }
}
