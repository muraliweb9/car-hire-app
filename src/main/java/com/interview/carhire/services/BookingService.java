package com.interview.carhire.services;

import com.interview.carhire.data.Booking;
import com.interview.carhire.data.BookingStatus;
import com.interview.carhire.data.Car;
import com.interview.carhire.data.Location;
import com.interview.carhire.repository.BookingRepository;
import com.interview.carhire.repository.CarRepository;
import com.interview.carhire.repository.LocationRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.interview.carhire.services.DataUtils.isBookingInside;
import static com.interview.carhire.services.DataUtils.parse;
import static com.interview.carhire.services.ValidationUtils.validateAvailable;

/**
 * Main REST service class
 */
@RestController
@RequestMapping("/carhire")
@Validated
public class BookingService {

    private Semaphore bookingsSemaphore = new Semaphore(1);
    private CarRepository carRepository;

    private LocationRepository locationRepository;

    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(CarRepository carRepository, LocationRepository locationRepository,
                          BookingRepository bookingRepository) {
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("cars")
    public Iterator<Car> cars() {
        return carRepository.findAll().iterator();
    }

    @GetMapping("locations")
    public Iterator<Location> locations() {
        return locationRepository.findAll().iterator();
    }

    @GetMapping("bookedBookings")
    public Iterator<Booking> bookedBookings() {
        return bookingsByStatus(BookingStatus.BOOKED).iterator();
    }

    @GetMapping("selectedBookings")
    public Iterator<Booking> selectedBookings() {
        return bookingsByStatus(BookingStatus.SELECTED).iterator();
    }

    @GetMapping("allBookings")
    public Iterator<Booking> allBookings() {
        return bookingRepository.findAll().iterator();
    }

    @GetMapping("available")
    public @ResponseBody Iterator<Car> available(
            @RequestParam String location,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        // Validate the parameters
        validateAvailable(location, startDate, endDate);

        // Find all the cars
        Iterable<Car> cars = carRepository.findAll();
        // Find all the bookings
        Iterable<Booking> bookings = bookingRepository.findAll();

        // Find already booked cars
        List<Car> bookedCarsForTimeSlot = StreamSupport
                .stream(bookings.spliterator(), false)
                .filter(b -> isBookingInside(parse(startDate), parse(endDate), b))
                .map(b -> b.getCar())
                .collect(Collectors.toList());

        // Remove cars not at location and already booked
        Iterator<Car> availableAtLocationAndNotBooked = StreamSupport
                .stream(cars.spliterator(), false)
                .filter(c -> location.equals(c.getLocation().getLocationName()) //Check location
                        && !bookedCarsForTimeSlot.contains(c)) // Check whether already booked
                .collect(Collectors.toList())
                .iterator();

        // Return the available at the location
        return availableAtLocationAndNotBooked;

    }

    @PostMapping("selectBooking")
    @SneakyThrows
    public Booking selectBooking(@RequestBody Booking booking) {
        Location location = booking.getLocation();
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();
        Car car = booking.getCar();

        try {
            System.out.println(Thread.currentThread().getName() + " aquiring semaphore");
            bookingsSemaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " acquired semaphore");

            List<Car> availableAtLocationForPeriod = StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(
                                    available(location.getLocationName(), startDate.toString(), endDate.toString()),
                                    Spliterator.ORDERED)
                            , false)
                    .collect(Collectors.toList());
            if (availableAtLocationForPeriod.contains(car)) {
                Integer newBookingId = getMaxBookingId() + 1;
                booking.setId(newBookingId.toString());
                booking.setStatus(BookingStatus.SELECTED);
                Booking selectedBooking = bookingRepository.save(booking);
                return selectedBooking;
            } else {
                throw new BookingServiceException("Requested booking cannot be selected - vehicle not available");
            }
        }
        finally {
            //Thread.sleep(5000L);
            System.out.println(Thread.currentThread().getName() + " releasing semaphore");
            bookingsSemaphore.release();
            System.out.println(Thread.currentThread().getName() + " released semaphore");

        }
    }

    @PutMapping("bookBooking")
    @SneakyThrows
    public Booking bookBooking(@RequestBody Booking booking) {
        String bookingId = booking.getId();
        Optional<Booking> selectedBookingOpt = bookingRepository.findById(bookingId);

        try {

            bookingsSemaphore.acquire();

            if (selectedBookingOpt.isPresent()) {
                Booking selectedBooking = selectedBookingOpt.get();
                if (selectedBooking.getStatus() == BookingStatus.SELECTED) {
                    selectedBooking.setStatus(BookingStatus.BOOKED);
                    return bookingRepository.save(selectedBooking);
                } else {
                    throw new BookingServiceException("Requested booking cannot be booked - booking not SELECTED");
                }
            } else {
                throw new BookingServiceException("Requested booking cannot be booked - booking not found");
                //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        finally {
            bookingsSemaphore.release();
        }

    }

    private RestErrorMessage errMessage(String msg) {
        return RestErrorMessage.builder().errorMessage(msg).build();
    }

    private List<Booking> bookingsByStatus(BookingStatus bookingStatus) {
        return StreamSupport.stream(bookingRepository.findAll().spliterator(), false)
                .filter(b -> b.getStatus() == bookingStatus)
                .collect(Collectors.toList());
    }

    private Integer getMaxBookingId() {

        Integer maxBookingId = StreamSupport
                .stream(bookingRepository.findAll().spliterator(), false)
                .sorted((b1, b2) -> b1.getId().compareTo(b2.getId()))
                .mapToInt((b) -> Integer.valueOf(b.getId()))
                .max()
                .getAsInt();
        return maxBookingId;

    }


}

