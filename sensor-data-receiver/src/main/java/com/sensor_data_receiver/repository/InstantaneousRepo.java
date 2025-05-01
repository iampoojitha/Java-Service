package com.sensor_data_receiver.repository;

import com.sensor_data_receiver.model.InstantaneousData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstantaneousRepo extends MongoRepository<InstantaneousData, String> {
    InstantaneousData findByVinId(String vinId);
}
