package com.common_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDataDto {
    private String vinId;
    private String sensor;
    private String reading;
    private String timestamp;
}
