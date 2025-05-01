package com.sensor_data_receiver.dto;

import lombok.Data;
import com.common_service.constants.InstantaneousSensors;

@Data
public class InstantaneousStateDetails {
    private InstantaneousSensors sensor;
    private String reading;
    private String timestamp;
}
