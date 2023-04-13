package com.interview.carhire.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationUtilsTest {

    @Test()
    @org.junit.jupiter.api.Order(1)
    public void test1() {
        Exception exception = assertThrows(BookingServiceException.class, () -> {
            ValidationUtils.validateAvailable("", "", "");
        });
        String expectedMessage = "Location (location) cannot be blank";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test()
    @org.junit.jupiter.api.Order(2)
    public void test2() {
        Exception exception = assertThrows(BookingServiceException.class, () -> {
            ValidationUtils.validateAvailable("Toronto", "", "");
        });
        String expectedMessage = "Start date (startDate) cannot be blank";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test()
    @org.junit.jupiter.api.Order(3)
    public void test3() {
        Exception exception = assertThrows(BookingServiceException.class, () -> {
            ValidationUtils.validateAvailable("Toronto", "2023-03-21", "");
        });
        String expectedMessage = "End date (endDate) cannot be blank";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
