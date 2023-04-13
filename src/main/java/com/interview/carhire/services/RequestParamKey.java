package com.interview.carhire.services;

import java.util.Map;
import java.util.Optional;

public enum RequestParamKey {
    LOCATION("location"),
    START_DATE("start_date"),

    END_DATE("end_date"),

    FUEL("fuel");

    private String paramKey;

    RequestParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamKey() {
        return paramKey;
    }

    public static Optional<String> getIfPresent(Map<String, String> requestParams, RequestParamKey requestParamKey) {
        if (requestParams.containsKey(requestParamKey.paramKey)) {
            return Optional.of(requestParams.get(requestParamKey.paramKey));
        }
        return Optional.empty();
    }

}