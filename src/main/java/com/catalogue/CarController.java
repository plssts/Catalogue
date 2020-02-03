package com.catalogue;

import com.catalogue.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CarController {

    @Autowired
    private CarRepo repository;

    @GetMapping("/cars")
    public @ResponseBody Iterable<Car> readCars() {
        return repository.findAll();
    }

    @PostMapping("/cars")
    /*public @ResponseBody String createCar(@RequestParam String make,
                                          @RequestParam String model,
                                          @RequestParam String year,
                                          @RequestParam Float price){*/
    public @ResponseBody String createCar(@RequestBody Map<String, String> body){
        Car temp = new Car(body.get("make"), body.get("model"), body.get("year"), body.get("price"));
        repository.save(temp);
        return "Saved";
    }
}
