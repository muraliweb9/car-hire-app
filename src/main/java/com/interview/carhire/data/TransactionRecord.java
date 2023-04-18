package com.interview.carhire.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecord {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private TransactionType transactionType;

    private LocalDate transactionDate;

    private BigDecimal amount;


}
