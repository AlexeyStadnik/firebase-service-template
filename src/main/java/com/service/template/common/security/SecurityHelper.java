package com.service.template.common.security;

import com.service.template.common.exception.ServiceException;
import com.service.template.common.utils.ContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component("securityHelper")
@RequiredArgsConstructor
public class SecurityHelper {


    public static final String YOU_CAN_ACCESS_ONLY_YOUR_RESOURCES
            = "You can access only your resources";


    public boolean matchCurrentUserId(final String userId) {
        if (!Objects.equals(userId, ContextHolderUtils.getUserId())) {
            throw ServiceException.builder()
                    .httpStatus(FORBIDDEN)
                    .message(YOU_CAN_ACCESS_ONLY_YOUR_RESOURCES)
                    .errorCode("userIdNotMatchesOneFromToken")
                    .build();
        }
        return true;
    }

}
