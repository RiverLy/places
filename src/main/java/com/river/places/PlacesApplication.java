package com.river.places;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PlacesApplication {

    public static void main(String[] args) {
	SpringApplication.run(PlacesApplication.class, args);
    }

}
