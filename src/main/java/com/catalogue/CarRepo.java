package com.catalogue;

import com.catalogue.model.Car;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;

@RepositoryRestResource
public interface CarRepo extends CrudRepository<Car, Integer> {

}
