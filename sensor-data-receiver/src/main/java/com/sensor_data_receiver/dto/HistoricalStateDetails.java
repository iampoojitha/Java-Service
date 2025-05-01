
package com.sensor_data_receiver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.common_service.constants.HistoricalSensors;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalStateDetails {
    private HistoricalSensors sensor;
    private String reading;
    private String timestamp;

}
