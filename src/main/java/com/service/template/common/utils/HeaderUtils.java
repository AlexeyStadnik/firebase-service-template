package com.service.template.common.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

import static org.springframework.http.HttpHeaders.LOCATION;

@UtilityClass
public class HeaderUtils {

    @SneakyThrows
    public static HttpHeaders generateLocationHeader(final String baseUrl, final Object... pathVariables) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(LOCATION, PathUtils.generatePath(baseUrl, pathVariables));
        return headers;
    }

}
