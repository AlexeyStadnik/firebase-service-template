package com.service.template.common.configuration;

import com.service.template.common.exception.ServiceException;
import com.service.template.common.utils.VersionUtils;
import io.sentry.Sentry;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;

@Configuration
@Profile({"!test"})
@AllArgsConstructor
public class SentryConfiguration {

    private final AppConfiguration appConfiguration;

    @PostConstruct
    public void init() {
        if (ObjectUtils.isEmpty(appConfiguration.getSentry().getDsn())) {
            return;
        }

        Sentry.init(options -> {
            options.setDsn(appConfiguration.getSentry().getDsn());
            options.setRelease(VersionUtils.getAppVersion());
            options.setEnvironment(appConfiguration.getSentry().getEnvironment());
            options.setBeforeSend((event, hint) -> {
                // Ignoring exception related to 4xx client errors
                if (event.getThrowable() instanceof ServiceException) {
                    final ServiceException serviceException = (ServiceException) event.getThrowable();
                    if (serviceException.getHttpStatus().is4xxClientError()) {
                        return null;
                    }
                }
                return event;
            });
        });
    }
}
