/**
 * @author Paulius Staisiunas
 */

package com.j2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class J2020Application {
	public static void main(String[] args){
		System.out.println("Running app now");
		SpringApplication.run(J2020Application.class, args);
	}
}
