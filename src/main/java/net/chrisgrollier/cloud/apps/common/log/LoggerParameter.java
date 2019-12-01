package net.chrisgrollier.cloud.apps.common.log;

import org.apache.commons.lang.StringUtils;

/**
 * Logger parameters
 */
public enum LoggerParameter {

    SERVICE("service"),

    CONTEXT("context"),

    LOG_TYPE("logType"),

    DURATION("duration");

    private String name;

    LoggerParameter(String name) {
        this.name = name;
    }

    public static LoggerParameter fromString(final String value) {
        for (LoggerParameter item : LoggerParameter.values()) {
            if (StringUtils.equals(item.name, value)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
