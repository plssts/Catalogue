package com.catalogue.controller;

import com.catalogue.CarRepo;
import com.catalogue.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class CarController {
    @Autowired
    private CarRepo repository;

    @GetMapping("/cars")
    public @ResponseBody Iterable<Car> readCars(){
        return repository.findAll();
    }

    @PostMapping("/cars")
    public @ResponseBody String createCar(@RequestBody Map<String, String> body){
        Car temp = new Car(body.get("make"), body.get("model"), body.get("year"), body.get("price"));
        repository.save(temp);
        return "Car saved";
    }

    @PutMapping("/cars/{id}")
    public @ResponseBody String putCar(@RequestBody Car car, @PathVariable("id") Integer id){
        Optional<Car> temp = repository.findById(id);
        Car updated = temp.get();
        updated.setMake(car.getMake());
        updated.setModel(car.getModel());
        updated.setYear(car.getYear());
        updated.setPrice(car.getPrice());
        repository.save(updated);
        return "Car updated";
    }

    @DeleteMapping("/cars/{id}")
    public @ResponseBody String deleteCar(@PathVariable("id") Integer id){
        repository.deleteById(id);
        return "Car removed";
    }
}
