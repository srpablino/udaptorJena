package io.udaptor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataPortabilityApplication {

    public static final Logger logger = LoggerFactory.getLogger(DataPortabilityApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DataPortabilityApplication.class, args);
    }
}
