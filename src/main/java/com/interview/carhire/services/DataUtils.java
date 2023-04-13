package com.interview.carhire.services;

import com.interview.carhire.data.Booking;

import java.time.LocalDate;

public class DataUtils {

    // Parse string from YYYY-MM-dd e.g. 2023-03-20
    public static LocalDate parse(String dateString) {
        return LocalDate.parse(dateString);
    }

    public static boolean isBookingInside(LocalDate startDate, LocalDate endDate, Booking booking) {
        return !isBookingOutside(startDate, endDate, booking);
    }

    public static boolean isBookingOutside(LocalDate startDate, LocalDate endDate, Booking booking) {
        return isStartAndEndBefore(startDate, endDate, booking) || isStartAndEndAfter(startDate, endDate, booking);
    }

    public static boolean isStartAndEndBefore(LocalDate startDate, LocalDate endDate, Booking booking) {
        LocalDate bookingStartDate = booking.getStartDate();
        LocalDate bookingEndDate = booking.getEndDate();
        return startDate.isBefore(bookingStartDate) && endDate.isBefore(bookingStartDate);
    }

    public static boolean isStartAndEndAfter(LocalDate startDate, LocalDate endDate, Booking booking) {
        LocalDate bookingStartDate = booking.getStartDate();
        LocalDate bookingEndDate = booking.getEndDate();
        return startDate.isAfter(bookingEndDate) && endDate.isAfter(bookingEndDate);
    }
}
