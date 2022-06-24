package com.service.template.common.utils;

import com.service.template.common.exception.ServiceException;
import com.service.template.common.security.UserDetailsImpl;
import com.service.template.common.security.authentication.TokenAuthentication;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@UtilityClass
public class ContextHolderUtils {

    @NotNull
    public static Optional<TokenAuthentication> currentUserOpt() {
        final SecurityContext context = SecurityContextHolder.getContext();
        Optional<TokenAuthentication> result;
        if (context != null) {
            final Authentication auth = context.getAuthentication();
            if (auth == null) {
                result = Optional.empty();
            } else if (auth instanceof TokenAuthentication) {
                result = Optional.of((TokenAuthentication) auth);
            } else {
                throw new IllegalArgumentException(
                        "Unknown user authentication type: " + auth.getClass());
            }
        } else {
            result = Optional.empty();
        }
        return result;
    }

    @NotNull
    public static TokenAuthentication currentUser() {
        return currentUserOpt().orElseThrow(() -> ServiceException.builder()
                .message("Authentication required")
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build());
    }

    public static String getUserId() {
        final UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getDetails();

        return userDetails.getUserId();
    }

    @NotNull
    public static String currentUserId() {
        return currentUser().getName();
    }

    @NotNull
    public static Optional<String> currentUserIdOpt() {
        return currentUserOpt().map(TokenAuthentication::getName);
    }

}
