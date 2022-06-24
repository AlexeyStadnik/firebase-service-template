package com.service.template.common.test;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class MinioInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final int MINIO_PORT = 9000;
    private static final String ACCESS_KEY = "template";
    private static final String SECRET_KEY = "template";
    private static final String BUCKET_NAME = "bucket";

    public static final GenericContainer MINIO = new GenericContainer("minio/minio:RELEASE.2021-04-22T15-44-28Z")
                .withEnv("MINIO_ACCESS_KEY", ACCESS_KEY)
                .withEnv("MINIO_SECRET_KEY", SECRET_KEY)
                .withCommand("server /data")
                .waitingFor(Wait.forListeningPort())
                .withExposedPorts(MINIO_PORT);

    static {
        MINIO.start();
    }

    public static String getHost() {
        return MINIO.getHost();
    }

    public static int getPort() {
        return MINIO.getMappedPort(MINIO_PORT);
    }

    public static String getUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("http").host(getHost()).port(getPort())
                .toUriString();
    }

    private void createBuckets() {
        final var s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(getUrl(), "us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
                .withPathStyleAccessEnabled(true)
                .build();
        s3Client.createBucket(BUCKET_NAME);
        s3Client.shutdown();
    }

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        createBuckets();
        applyProperties(applicationContext);
    }

    private void applyProperties(final ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "app.aws.s3config.bucket-name:" + BUCKET_NAME,
                "app.aws.access-key:" + ACCESS_KEY,
                "app.aws.s3config.endpoint-url:" + getUrl(),
                "app.aws.secret-key:" + SECRET_KEY
        ).applyTo(applicationContext);
    }
}
