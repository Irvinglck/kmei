package com.lck.reverse.commons;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigHttpUtils {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
