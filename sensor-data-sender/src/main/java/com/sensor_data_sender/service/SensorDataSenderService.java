package com.sensor_data_sender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.common_service.config.AppConfig;
import com.common_service.dto.SensorDataDto;

@Service
@RequiredArgsConstructor
public class SensorDataSenderService {
    @Autowired
    private KafkaTemplate<String, SensorDataDto> kafkaTemplate;

    public void sendData(SensorDataDto data) {
        kafkaTemplate.send(AppConfig.TOPIC, data);
    }
}
