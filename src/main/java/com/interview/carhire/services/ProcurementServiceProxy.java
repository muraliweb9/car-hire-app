package com.interview.carhire.services;

import com.interview.carhire.data.TransactionRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;
import java.util.List;

@FeignClient(value = "car-procurement-app")
public interface ProcurementServiceProxy {

    @GetMapping("/procurement/transactionrecords")
    Iterator<TransactionRecord> transactionrecords();

    @GetMapping("/procurement/transactionrecords/{carId}")
    List<TransactionRecord> transactionrecordsFor(@PathVariable String carId);
}
