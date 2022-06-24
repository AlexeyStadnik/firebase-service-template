package com.service.template.common.security;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN"),
    ANONYMOUS("ANONYMOUS"),
    FIREBASE("FIREBASE"),
    ANALYTICS_SERVICE("ANALYTICS_SERVICE");

    private final String value;

    UserRole(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static UserRole fromValue(final String text) {
        for (final UserRole role : UserRole.values()) {
            if (String.valueOf(role.value).equals(text)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + text + "'");
    }
}
