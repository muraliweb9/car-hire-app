package com.interview.carhire.services;

import com.interview.carhire.data.MaintenanceRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class MaintenanceServiceProxy {

    /**
     * Defined in this service's (i.e. car-hire-app) application.yml
     api:
        car-maintenance-app:
        # This is the spring.application.name of the car-maintenance-app as registered with Eureka
            baseurl: http://car-maintenance-app
     */
    @Value("${api.car-maintenance-app.baseurl}")
    // http://car-maintenance-app
    private String apiBaseUrl;

    // Defined in com.interview.carhire.services.AppConfig
    // RestTemplate is in maintenance mode use WebClient instead
    @Autowired
    private RestTemplate restTemplate;

    // Defined in com.interview.carhire.services.AppConfig
    @Autowired
    private WebClient.Builder webClient;

    // RestTemplate implementation
    public MaintenanceRecord getMaintenanceRecord(String carId) {
        log.info("Looking up in car-maintenance-app for maintenance record for carId {} using RestTemplate", carId);
        // Constructs
        // http://car-maintenance-app/maintenance/maintenancerecords/2
        // Through Eureka http://car-maintenance-app maps to the car-maintenance-app
        // This endpoint (/maintenance/maintenancerecords/2) is then hit
        String uri = apiBaseUrl + "/maintenance/maintenancerecords/" + carId;
        MaintenanceRecord maintenanceRecord
                = restTemplate.getForObject(uri, MaintenanceRecord.class);

        if (maintenanceRecord != null) {
            log.info("Found maintenance record for carId {}", carId);
        }
        return maintenanceRecord;
    }

    // WebClient implementation
    public MaintenanceRecord getMaintenanceRecordTry(String carId) {
        log.info("Looking up in car-maintenance-app for maintenance record for carId {} using WebClient", carId);
        // Constructs
        // http://car-maintenance-app/maintenance/maintenancerecordsTry/2
        // Through Eureka http://car-maintenance-app maps to the car-maintenance-app
        // This endpoint (/maintenance/maintenancerecordsTry/2) is then hit
        String uri = apiBaseUrl + "/maintenance/maintenancerecordsTry/" + carId;

        MaintenanceRecord maintenanceRecord = webClient.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(MaintenanceRecord.class)
                .block();

        if (maintenanceRecord != null) {
            log.info("Found maintenance record for carId {}", carId);
        }
        return maintenanceRecord;
    }
}
