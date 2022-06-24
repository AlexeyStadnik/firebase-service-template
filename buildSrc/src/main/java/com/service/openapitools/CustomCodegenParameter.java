package com.service.openapitools;

import org.openapitools.codegen.CodegenParameter;

import java.util.Objects;

public class CustomCodegenParameter extends CodegenParameter implements CustomFormatValidation {
    private boolean notBlank;
    private boolean lowerCase;
    private boolean email;
    private boolean trimmed;
    private boolean currency;
    private boolean url;
    private boolean escapeJson;
    private boolean phoneNumber;

    public boolean isNotBlank() {
        return notBlank;
    }

    public void setNotBlank(final boolean notBlank) {
        this.notBlank = notBlank;
    }

    public boolean isLowerCase() {
        return lowerCase;
    }

    public void setLowerCase(final boolean lowerCase) {
        this.lowerCase = lowerCase;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(final boolean email) {
        this.email = email;
    }

    public boolean isTrimmed() {
        return trimmed;
    }

    public void setTrimmed(final boolean trimmed) {
        this.trimmed = trimmed;
    }

    public boolean isCurrency() {
        return currency;
    }

    public void setCurrency(final boolean currency) {
        this.currency = currency;
    }

    public boolean isUrl() {
        return url;
    }

    public void setUrl(final boolean url) {
        this.url = url;
    }

    public boolean isEscapeJson() {
        return escapeJson;
    }

    public void setEscapeJson(final boolean escapeJson) {
        this.escapeJson = escapeJson;
    }

    public boolean isPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final boolean phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomCodegenParameter)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final CustomCodegenParameter that = (CustomCodegenParameter) o;
        return notBlank == that.notBlank
                && lowerCase == that.lowerCase
                && email == that.email
                && trimmed == that.trimmed
                && currency == that.currency
                && url == that.url
                && escapeJson == that.escapeJson
                && phoneNumber == that.phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), notBlank, lowerCase, email, trimmed, currency, url, escapeJson,
                phoneNumber);
    }
}
