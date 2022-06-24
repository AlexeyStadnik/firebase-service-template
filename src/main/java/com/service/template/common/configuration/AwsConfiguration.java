package com.service.template.common.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AwsConfiguration {

    private final AppConfiguration appConfiguration;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(
                        appConfiguration.getAws().getAccessKey(),
                        appConfiguration.getAws().getSecretKey()
                )
        );
    }


}
