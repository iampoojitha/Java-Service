package com.sensor_data_receiver.repository;

import com.sensor_data_receiver.model.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepo extends MongoRepository<SensorData, String> {
    SensorData findByVinIdAndSensor(String vinId, String sensor);
}
