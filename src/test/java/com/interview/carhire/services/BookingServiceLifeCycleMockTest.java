package com.interview.carhire.services;

import com.interview.carhire.data.Car;
import com.interview.carhire.repository.CarRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit 5 constructor does not need to be public
 */
// One Test instance i.e. BookingServiceLifeCycleMockTest for ALL tests
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// Default for Junit5 and normal Junit4 behaviour. New Test instance i.e. BookingServiceLifeCycleMockTest for EACH test
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
// Run tests as per @Order(1) annotation on the tests
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceLifeCycleMockTest {


    BookingServiceLifeCycleMockTest() {
        System.out.println("new BookingServiceLifeCycleMockTest() is created ....");
    }
    /**
     * DOES NOT need to be static because test IS annotated with
     * @TestInstance(TestInstance.Lifecycle.PER_CLASS)
     */
    @BeforeAll
    private void forAllTests() {
        System.out.println("Running @BeforeAll");
    }

    @BeforeEach
    private void forEachTest() {
        System.out.println("Running @BeforeEach");
    }

    @Test
    @Order(1)
    public void test1() {
        CarRepository mock = Mockito.mock(CarRepository.class);
        Mockito.when(mock.findAll()).thenReturn(
                Arrays.asList(Car.builder().make("Porsche").build(),
                        Car.builder().make("VW").build(),
                        Car.builder().make("Ford").build(),
                        Car.builder().make("Lincoln").build())
        );

        BookingService service = new BookingService(mock, null, null, null, null);
        assertEquals(4L, StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        service.cars(),
                        Spliterator.ORDERED)
                , false).count());
    }

    @Test
    @Order(2)
    public void test2() {
        CarRepository mock = Mockito.mock(CarRepository.class);
        Mockito.when(mock.findAll()).thenReturn(
                Arrays.asList(Car.builder().make("Porsche").build(),
                        Car.builder().make("VW").build(),
                        Car.builder().make("Lincoln").build())
        );

        BookingService service = new BookingService(mock, null, null, null, null);
        assertEquals(3L, StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        service.cars(),
                        Spliterator.ORDERED)
                , false).count());
    }
}
