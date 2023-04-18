package com.interview.carhire.services;

import com.interview.carhire.data.TransactionRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;
import java.util.List;

/**
 * @FeignClient(value = "car-procurement-app")
 *
 * requires an app like this registered with Eureka
 *
 * spring:
 *   application:
 *     name: car-procurement-app
 *
 * This app is then discovered on Eureka and a GET request like this created.
 *
 * http://car-procurement-app/procurement/transactionrecords
 *
 * On the car-procurement-app there should be  REST endpoint like below:
 *
 * @RestController
 * @RequestMapping("/procurement")
 *
 * @GetMapping("transactionrecords")
 * public Iterator<TransactionRecord> transactionrecords() {
 *  return transactionRecordRepository.findAll().iterator();
 * }
 */
@FeignClient(value = "car-procurement-app")
public interface ProcurementServiceProxy {

    @GetMapping("/procurement/transactionrecords")
    Iterator<TransactionRecord> transactionrecords();

    @GetMapping("/procurement/transactionrecords/{carId}")
    List<TransactionRecord> transactionrecordsFor(@PathVariable String carId);
}
