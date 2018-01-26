package com.application;

import com.application.model.Ship;
import com.application.services.ShipService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {
    public static final String REST_SERVICE_PREFIX = "/rest/";
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

        Ship ship = context.getBean(ShipService.class).getShip();
        System.out.println(ship);
    }
}