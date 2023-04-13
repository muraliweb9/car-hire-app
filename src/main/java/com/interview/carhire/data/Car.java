package com.interview.carhire.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    private String id;

    private String registrationPlate;

    private String make;

    private String model;

    private String type;

    private String capacity;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
