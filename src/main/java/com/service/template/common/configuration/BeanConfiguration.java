package com.service.template.common.configuration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

@Configuration
@AllArgsConstructor
public class BeanConfiguration {

    private final TaskExecutorBuilder taskExecutorBuilder;

    @Bean
    public Yaml yaml() {
        final LoaderOptions options = new LoaderOptions();
        options.setAllowDuplicateKeys(false);
        return new Yaml(options);
    }

    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        final HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        final HttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        restTemplate.getMessageConverters().add(formHttpMessageConverter);
        restTemplate.getMessageConverters().add(stringHttpMessageConverter);
        return restTemplate;
    }

    @Bean
    public ThreadPoolTaskExecutor defaultTaskExecutor(
            @Value("${app.executor-max-pool-size}") final int maxPoolSize) {
        return taskExecutorBuilder
                .maxPoolSize(maxPoolSize)
                .build();
    }
}
