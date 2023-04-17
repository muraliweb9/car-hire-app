package com.interview.carhire.services;

import com.interview.carhire.data.MaintenanceRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class MaintenanceServiceProxy {

    @Value("${api.car-maintenance-app.baseurl}")
    private String apiBaseUrl;

    @Autowired
    RestTemplate restTemplate;

    public MaintenanceRecord getMaintenanceRecord(String carId)
    {
        log.info("Looking up in car-maintenance-app for maintenance record for carId {}", carId);
        MaintenanceRecord maintenanceRecord
                = restTemplate.getForObject(apiBaseUrl + "/maintenance/maintenancerecords/" + carId, MaintenanceRecord.class);

        if (maintenanceRecord != null) {
            log.info("Found maintenance record for carId {}", carId);
        }
        return maintenanceRecord;
    }
}
