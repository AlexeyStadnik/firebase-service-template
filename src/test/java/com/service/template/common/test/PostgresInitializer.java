package com.service.template.common.test;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final int POSTGRES_PORT = 5432;

    public static final String POSTGRES_DB = "template";
    public static final String POSTGRES_USER = "template";
    public static final String POSTGRES_PASSWORD = "template";

    public static final GenericContainer POSTGRES =
            new GenericContainer("postgres:10.6-alpine")
                    .withEnv("POSTGRES_DB", POSTGRES_DB)
                    .withEnv("POSTGRES_USER", POSTGRES_USER)
                    .withEnv("POSTGRES_PASSWORD", POSTGRES_PASSWORD)
                    .waitingFor(Wait.forListeningPort())
                    .withExposedPorts(POSTGRES_PORT);

    static {
        POSTGRES.start();
    }

    public static String getHost() {
        return POSTGRES.getContainerIpAddress();
    }

    public static int getPort() {
        return POSTGRES.getMappedPort(POSTGRES_PORT);
    }

    public static String getUrl() {
        return "jdbc:postgresql:" + UriComponentsBuilder.newInstance()
                .host(getHost()).port(getPort()).path(POSTGRES_DB)
                .toUriString();
    }

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        applyProperties(applicationContext);
    }

    private void applyProperties(final ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url:" + getUrl(),
                "spring.datasource.username:" + POSTGRES_USER,
                "spring.datasource.password:" + POSTGRES_PASSWORD
        ).applyTo(applicationContext);
    }

}
