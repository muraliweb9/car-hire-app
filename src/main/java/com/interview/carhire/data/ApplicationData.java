package com.interview.carhire.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Helper class to load data to prime the application in memory database
 * Uses @ConfigurationParameters to load the data from application.yml
 */
@Component
@ConfigurationProperties(prefix = "application-data")
@Data
public class ApplicationData {
    private List<Car> cars;

    private List<Booking> bookings;

    private List<Location> locations;
}
