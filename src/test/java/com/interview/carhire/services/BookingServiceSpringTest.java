package com.interview.carhire.services;

import com.interview.carhire.data.Booking;
import com.interview.carhire.repository.BookingRepository;
import com.interview.carhire.repository.CarRepository;
import com.interview.carhire.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookingService.class)
public class BookingServiceSpringTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private LocationRepository locationRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private MaintenanceServiceProxy maintenanceServiceProxy;

    @Autowired
    BookingService bookingService;

    @Test
    public void getCarShouldReturnCarDetails() throws Exception {
        when(bookingRepository.findAll()).thenReturn(
                Arrays.asList(Booking.builder().id("100").build(),
                        Booking.builder().id("200").build(),
                        Booking.builder().id("300").build(),
                        Booking.builder().id("400").build()));

        //MvcResult res =
                mvc.perform(MockMvcRequestBuilders.get("/carhire/allBookings")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("100"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value("300"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].id").value("400"));

        //System.out.println(res);


//        mvc.perform(MockMvcRequestBuilders
//                        .get("/employees")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
    }
}
