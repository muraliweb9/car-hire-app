package com.interview.carhire.services;

import com.interview.carhire.data.Booking;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DateUtilsTest {

    @Test
    public void test1() {
        LocalDate reqStartDate = DataUtils.parse("2023-03-21");
        LocalDate reqEndDate = DataUtils.parse("2023-03-28");
        LocalDate bookingStartDate = DataUtils.parse("2023-03-21");
        LocalDate bookingEndDate = DataUtils.parse("2023-03-25");
        Booking booking = Booking.builder().startDate(bookingStartDate).endDate(bookingEndDate).build();

        assertEquals(true, DataUtils.isBookingInside(reqStartDate, reqEndDate, booking));
        assertEquals(false, DataUtils.isBookingOutside(reqStartDate, reqEndDate, booking));

    }

    @Test
    public void test2() {
        LocalDate reqStartDate = DataUtils.parse("2023-04-21");
        LocalDate reqEndDate = DataUtils.parse("2023-04-28");
        LocalDate bookingStartDate = DataUtils.parse("2023-03-21");
        LocalDate bookingEndDate = DataUtils.parse("2023-03-25");
        Booking booking = Booking.builder().startDate(bookingStartDate).endDate(bookingEndDate).build();

        assertEquals(false, DataUtils.isBookingInside(reqStartDate, reqEndDate, booking));
        assertEquals(true, DataUtils.isBookingOutside(reqStartDate, reqEndDate, booking));

    }

    @Test
    public void test3() {
        LocalDate reqStartDate = DataUtils.parse("2023-03-21");
        LocalDate reqEndDate = DataUtils.parse("2023-03-28");
        LocalDate bookingStartDate = DataUtils.parse("2023-03-22");
        LocalDate bookingEndDate = DataUtils.parse("2023-03-25");
        Booking booking = Booking.builder().startDate(bookingStartDate).endDate(bookingEndDate).build();

        assertEquals(true, DataUtils.isBookingInside(reqStartDate, reqEndDate, booking));
        assertEquals(false, DataUtils.isBookingOutside(reqStartDate, reqEndDate, booking));

    }

    @Test
    public void test4() {
        LocalDate reqStartDate = DataUtils.parse("2023-03-22");
        LocalDate reqEndDate = DataUtils.parse("2023-03-28");
        LocalDate bookingStartDate = DataUtils.parse("2023-03-21");
        LocalDate bookingEndDate = DataUtils.parse("2023-03-25");
        Booking booking = Booking.builder().startDate(bookingStartDate).endDate(bookingEndDate).build();

        assertEquals(true, DataUtils.isBookingInside(reqStartDate, reqEndDate, booking));
        assertEquals(false, DataUtils.isBookingOutside(reqStartDate, reqEndDate, booking));

    }
}
