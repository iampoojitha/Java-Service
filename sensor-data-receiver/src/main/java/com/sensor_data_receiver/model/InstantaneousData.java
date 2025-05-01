package com.sensor_data_receiver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "instantaneous_data")
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "vinId_sensorName_idx", def = "{'vinId': 1, 'sensorName': 1}", unique = true)
public class InstantaneousData {
    @Id
    private String id;
    private String vinId;
    private String fileId;

    @Version
    private Long version;
}
