package com.sensor_data_receiver.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "sensor_data")
public class SensorData {

    @Id
    private String id;
    private String vinId;
    private String sensor;
    private String reading;
    private String timestamp;

    @Version
    private Long version;
}