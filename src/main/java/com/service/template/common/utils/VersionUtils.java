package com.service.template.common.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import static java.lang.System.getenv;

@Slf4j
@UtilityClass
public class VersionUtils {

    public static String getAppVersion() {
        final String appVersion = getenv("APP_VERSION");
        log.info("App version :: {}", appVersion);
        return ObjectUtils.isEmpty(appVersion) ? "unknown" : appVersion;
    }
}

