package com.catalogue;

// Default imports through Springio
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ComponentScan("com.catalogue")
@EnableMongoRepositories("com.catalogue")
public class CatalogueApplication {
	public static void main(String[] args){
		SpringApplication.run(CatalogueApplication.class, args);
	}
}
