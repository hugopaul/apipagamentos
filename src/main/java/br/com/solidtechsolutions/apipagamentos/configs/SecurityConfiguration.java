package br.com.solidtechsolutions.apipagamentos.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://solidtechsolutions.com.br/")
                .allowedHeaders("*")
                .allowedMethods( "POST")
                .allowCredentials(true);
    }

    @Bean
    public Filter corsFilter() {
        return new CorsFilter();
    }

}
