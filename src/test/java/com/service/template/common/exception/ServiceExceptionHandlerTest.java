package com.service.template.common.exception;

import com.service.template.common.AbstractServiceSpecificIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ServiceExceptionHandlerTest extends AbstractServiceSpecificIntegrationTest {

    @Test
    public void fieldRequired() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tests/test-endpoint-bad-request"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorCode", is("required")));
    }

    @Test
    public void requiredRequestParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tests/test-endpoint-required-param"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Required request 'requiredParam' parameter is not present")));
    }
}
