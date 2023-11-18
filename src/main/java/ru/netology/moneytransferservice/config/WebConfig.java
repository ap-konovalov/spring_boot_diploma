package ru.netology.moneytransferservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${transfer.path}")
    private String transferPath;

    @Value("${confirm_operation.path}")
    private String confirmOperationPath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(transferPath).allowedMethods(HttpMethod.POST.name());
        registry.addMapping(confirmOperationPath).allowedMethods(HttpMethod.POST.name());
    }
}
