package com.service.template.common.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Pattern;

@UtilityClass
public final class PathUtils {

    private static final Pattern PATH_VARIABLES_PATTERN = Pattern.compile("\\{\\S+?}");

    public static String generatePath(
            final String baseUrl,
            final Object... pathVariables
    ) {
        final LinkedList replacements = new LinkedList<>(Arrays.asList(pathVariables));
        return PATH_VARIABLES_PATTERN.matcher(baseUrl).replaceAll(matchResult -> String.valueOf(replacements.poll()));
    }

}
