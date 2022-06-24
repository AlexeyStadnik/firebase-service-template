package com.service.openapitools;

public interface CustomFormatValidation {

    void setNotBlank(boolean notBlank);

    void setLowerCase(boolean lowerCase);

    void setEmail(boolean email);

    void setTrimmed(boolean trimmed);

    void setCurrency(boolean currency);

    void setUrl(boolean url);

    void setEscapeJson(boolean escapeJson);

    void setPhoneNumber(boolean phoneNumber);
}
