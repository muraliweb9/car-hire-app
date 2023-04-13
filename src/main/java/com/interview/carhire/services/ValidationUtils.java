package com.interview.carhire.services;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtils {

    public static void validateAvailable(String location, String startDate, String endDate) {

        if (StringUtils.isBlank(location)) {
            throw new BookingServiceException("Location (location) cannot be blank");
        }

        if (StringUtils.isBlank(startDate)) {
            throw new BookingServiceException("Start date (startDate) cannot be blank");
        }

        if (StringUtils.isBlank(endDate)) {
            throw new BookingServiceException("End date (endDate) cannot be blank");
        }

    }
}
