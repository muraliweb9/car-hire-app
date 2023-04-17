package com.interview.carhire.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRecord {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private LocalDate motDate;

    private LocalDate serviceDate;

    private LocalDate taxedDate;


}
