package com.lopes.workhours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkHoursApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkHoursApplication.class, args);
    }

    // TODO Add internationalization (EN, PT, FR, NL)
    // TODO Add charts filters
    // TODO Review charts - Create native queries

}
