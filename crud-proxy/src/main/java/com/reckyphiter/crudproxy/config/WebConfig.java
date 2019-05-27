package com.reckyphiter.crudproxy.config;

import com.reckyphiter.crudproxy.converter.StringToAbstractEntityConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Recky Phiter
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToAbstractEntityConverterFactory());
    }
}