package com.interview.carhire.services;

/**
 * Application specific Exception class
 */
public class BookingServiceException extends RuntimeException{
    public BookingServiceException(String message) {
        super(message);
    }
}
