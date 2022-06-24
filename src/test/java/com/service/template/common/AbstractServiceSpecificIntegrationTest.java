package com.service.template.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.template.common.configuration.AppConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@ContextConfiguration(initializers = {

})
public abstract class AbstractServiceSpecificIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected AppConfiguration appConfiguration;

    @Override
    public void clearDatabase() {
        log.info("Cleaning database");
        //jdbcTemplate.execute("DELETE FROM template_user");
    }
}
