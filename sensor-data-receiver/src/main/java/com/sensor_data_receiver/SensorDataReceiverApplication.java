package com.sensor_data_receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.sensor_data_receiver.service")
@SpringBootApplication
public class SensorDataReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorDataReceiverApplication.class, args);
	}

}
