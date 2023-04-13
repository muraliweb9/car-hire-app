package com.interview.carhire.repository;


import com.interview.carhire.data.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, String> {

    Optional<Car> findById(String id);

}