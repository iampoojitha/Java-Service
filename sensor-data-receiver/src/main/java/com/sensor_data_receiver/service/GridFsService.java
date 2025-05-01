package com.sensor_data_receiver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class GridFsService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> String storeStateDetailsAsMap(Map<String, T> stateDetails) throws IOException {
        String json = objectMapper.writeValueAsString(stateDetails);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());
        return gridFsTemplate.store(inputStream, "stateDetails.json", "application/json").toString();
    }

    public <T> Map<String, T> retrieveStateDetailsAsMap(String fileId, Class<T> valueType) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));
        if (gridFSFile == null) {
            return new HashMap<>();
        }

        InputStream inputStream = gridFsOperations.getResource(gridFSFile).getInputStream();
        return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, valueType));
    }
}