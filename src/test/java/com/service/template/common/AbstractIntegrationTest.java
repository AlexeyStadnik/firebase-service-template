package com.service.template.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.template.common.test.MinioInitializer;
import com.service.template.common.test.PostgresInitializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {
        PostgresInitializer.class,
        MinioInitializer.class
})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @After
    public abstract void clearDatabase();

}
