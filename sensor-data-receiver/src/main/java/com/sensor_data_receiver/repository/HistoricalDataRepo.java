package com.sensor_data_receiver.repository;

import com.sensor_data_receiver.model.HistoricalData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HistoricalDataRepo extends MongoRepository<HistoricalData, String> {
    HistoricalData findByVinId(String vinId);

}
