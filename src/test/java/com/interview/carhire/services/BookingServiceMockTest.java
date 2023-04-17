package com.interview.carhire.services;

import com.interview.carhire.data.Car;
import com.interview.carhire.repository.CarRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit 5 constructor does not need to be public
 */
class BookingServiceMockTest {

    BookingServiceMockTest() {
        System.out.println("new BookingServiceMockTest() is created ....");
    }

    /**
     * DOES need to be static because test is NOT annotated with
     * @TestInstance(TestInstance.Lifecycle.PER_CLASS)
     */
    @BeforeAll
    private static void beforeAll1() {
        System.out.println("Running @BeforeAll1");
    }

    @BeforeAll
    private static void beforeAll2() {
        System.out.println("Running @BeforeAll2");
    }

    @BeforeEach
    private void beforeEach() {
        System.out.println("Running @BeforeEach");
    }

    @AfterAll
    private static void afterAll() {
        System.out.println("Running @AfterAll");
    }

    @AfterEach
    private void afterEach() {
        System.out.println("Running @AfterEach");
    }

    @Test
    public void test1() {
        CarRepository mock = Mockito.mock(CarRepository.class);
        Mockito.when(mock.findAll()).thenReturn(
                Arrays.asList(Car.builder().make("Porsche").build(),
                        Car.builder().make("VW").build(),
                        Car.builder().make("Ford").build(),
                        Car.builder().make("Lincoln").build())
        );

        BookingService service = new BookingService(mock, null, null, null);
        assertEquals(4L, StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        service.cars(),
                        Spliterator.ORDERED)
                , false).count());
    }

    @Test
    public void test2() {
        CarRepository mock = Mockito.mock(CarRepository.class);
        Mockito.when(mock.findAll()).thenReturn(
                Arrays.asList(Car.builder().make("Porsche").build(),
                        Car.builder().make("VW").build(),
                        Car.builder().make("Lincoln").build())
        );

        BookingService service = new BookingService(mock, null, null, null);
        assertEquals(3L, StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        service.cars(),
                        Spliterator.ORDERED)
                , false).count());
    }
}
