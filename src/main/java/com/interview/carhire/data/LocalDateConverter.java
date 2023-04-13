package com.interview.carhire.data;


import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Needed to convert the date from String in format "yyyy-MM-dd" in application.yml
 * to the Pojo in LocalDate
 * application.yml -> startDate: 2023-03-20
 * Pojo Booking.java -> private LocalDate startDate;
 */
@Component
@ConfigurationPropertiesBinding
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if (source == null) {
            return null;
        }
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
