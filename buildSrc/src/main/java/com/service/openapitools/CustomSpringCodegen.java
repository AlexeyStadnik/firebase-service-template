package com.service.openapitools;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenParameter;
import org.openapitools.codegen.CodegenProperty;
import org.openapitools.codegen.languages.SpringCodegen;

import java.io.IOException;
import java.util.Set;

public class CustomSpringCodegen extends SpringCodegen {
    public CustomSpringCodegen() {
        embeddedTemplateDir = templateDir = "CustomJavaSpring";
    }

    @Override
    public String getName() {
        return "custom-spring";
    }


    @Override
    public CodegenParameter fromParameter(final Parameter parameter, final Set<String> imports) {
        final CustomCodegenParameter result;

        try {
            final CodegenParameter codegenParameter = super.fromParameter(parameter, imports);
            final ObjectMapper objectMapper = new ObjectMapper().configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            final String s = objectMapper.writeValueAsString(codegenParameter);
            result = objectMapper.readValue(s, CustomCodegenParameter.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (StringUtils.isNotBlank(result.dataFormat)) {
            final String[] formats = result.dataFormat.toLowerCase().split(",");
            for (final String format : formats) {
                populateFormat(result, format);
            }
        }
        return result;
    }


    @Override
    public CodegenProperty fromProperty(final String name, final Schema p) {
        final CustomCodegenProperty result;

        try {
            final CodegenProperty codegenProperty = super.fromProperty(name, p);
            final ObjectMapper objectMapper = new ObjectMapper().configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            final String s = objectMapper.writeValueAsString(codegenProperty);
            result = objectMapper.readValue(s, CustomCodegenProperty.class);
            result.setExclusiveMaximum(codegenProperty.getIExclusiveMaximum());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (StringUtils.isNotBlank(result.dataFormat)) {
            final String[] formats = result.dataFormat.toLowerCase().split(",");
            for (final String format : formats) {
                populateFormat(result, format);

            }
        }
        return result;
    }

    private void populateFormat(final CustomFormatValidation result, final String format) {
        switch (format.trim()) {
            case "not-blank":
                result.setNotBlank(true);
                break;
            case "trimmed":
                result.setTrimmed(true);
                break;
            case "lower-case":
                result.setLowerCase(true);
                break;
            case "email":
                result.setEmail(true);
                break;
            case "currency":
                result.setCurrency(true);
                break;
            case "url":
                result.setUrl(true);
                break;
            case "escapeJson":
                result.setEscapeJson(true);
                break;
            case "phone-number":
                result.setPhoneNumber(true);
                break;
            default:
                //do nothing
        }
    }

}
