package com.calerts.computer_alertsbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Scheduling
public class ComputerAlertsBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComputerAlertsBeApplication.class, args);
    }

}
