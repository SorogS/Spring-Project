package com.sorog.carrent.repository;

import com.sorog.carrent.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Override
    Car save(Car car);


    List<Car> findAll();

    Car getById(Long id);
}
