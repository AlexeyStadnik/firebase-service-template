package com.service.template.common.controller;

import com.service.template.common.exception.DefaultErrorCodeConstants;
import com.service.template.common.exception.FieldError;
import com.service.template.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TestController {

    @RequestMapping(value = "/tests/test-endpoint-bad-request", method = GET)
    public TestData testEndpointBadRequest() {
        throw ServiceException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .error(new FieldError("field1", "messge1", DefaultErrorCodeConstants.REQUIRED))
                .message("message1")
                .build();
    }

    @RequestMapping(value = "/tests/test-endpoint-required-param", method = GET)
    public ResponseEntity<Void> testEndpointMethod(@RequestParam final int requiredParam) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static class TestData {
        private String id;
        private String owner;
    }
}
