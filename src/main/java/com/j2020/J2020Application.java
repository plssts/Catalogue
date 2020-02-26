/**
 * @author Paulius Staisiunas
 */

package com.j2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;

@SpringBootApplication//(exclude = ActiveMQAutoConfiguration.class)
public class J2020Application {
    public static void main(String[] args) {
        SpringApplication.run(J2020Application.class, args);
    }
}
