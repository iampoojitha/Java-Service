package com.sensor_data_receiver.dto;

import com.sensor_data_receiver.model.HistoricalData;
import com.sensor_data_receiver.model.InstantaneousData;
import lombok.Data;

import java.util.Map;

@Data
public class VehicleResponse {
    private Map<String, HistoricalStateDetails> historicalData;
    private Map<String, InstantaneousStateDetails> instantaneousData;
}
