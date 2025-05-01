package com.sensor_data_receiver.controller;

import com.sensor_data_receiver.dto.VehicleResponse;
import com.sensor_data_receiver.service.SensorDataReceiver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.common_service.config.AppConfig;

import java.io.IOException;

@RestController
@RequestMapping(AppConfig.API_RECEIVE)
public class ReceiverRoute {

    private final SensorDataReceiver sensorDataReceiver;

    public ReceiverRoute(SensorDataReceiver sensorDataReceiver) {
        this.sensorDataReceiver = sensorDataReceiver;
    }

    @GetMapping(AppConfig.GET_DATA)
    public VehicleResponse getSensorDataById(@PathVariable String id) throws IOException {
        return sensorDataReceiver.getVehicleDataByVinId(id);
    }
}