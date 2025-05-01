package com.sensor_data_sender.controller;

import com.sensor_data_sender.service.SensorDataSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.common_service.config.AppConfig;
import com.common_service.dto.SensorDataDto;

@RestController
@RequestMapping(AppConfig.API_SEND)
@RequiredArgsConstructor
@Slf4j
public class SensorDataRoute {
    private final SensorDataSenderService sensorDataSenderService;

    @PostMapping(AppConfig.API_SEND_DATA)
    public void sendSensorData(@RequestBody SensorDataDto sensorDataRequest) {
        log.info("Data sent to kafka");
        sensorDataSenderService.sendData(sensorDataRequest);
    }
}
