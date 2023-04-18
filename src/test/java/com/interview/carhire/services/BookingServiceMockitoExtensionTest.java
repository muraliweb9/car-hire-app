package com.interview.carhire.services;

import com.interview.carhire.data.Location;
import com.interview.carhire.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookingServiceMockitoExtensionTest {

    @Mock
    private LocationRepository locationRepository;

    @BeforeEach
    public void init() {
        Mockito.when(locationRepository.findAll()).thenReturn(
                Arrays.asList(Location.builder().locationName("London").build(),
                        Location.builder().locationName("Birmingham").build(),
                        Location.builder().locationName("Leeds").build())
        );

    }

    @Test
    public void test1() {

        BookingService service = new BookingService(null, locationRepository, null, null, null);
        assertEquals(3L, StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        service.locations(),
                        Spliterator.ORDERED)
                , false).count());
    }
}
