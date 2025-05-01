package com.sensor_data_receiver;

import com.sensor_data_receiver.dto.HistoricalStateDetails;
import com.sensor_data_receiver.dto.InstantaneousStateDetails;
import com.sensor_data_receiver.mapper.ResponseMapper;
import com.sensor_data_receiver.model.SensorData;
import com.sensor_data_receiver.repository.HistoricalDataRepo;
import com.sensor_data_receiver.repository.InstantaneousRepo;
import com.sensor_data_receiver.repository.SensorDataRepo;
import com.sensor_data_receiver.service.GridFsService;
import com.sensor_data_receiver.service.SensorDataReceiver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.common_service.dto.SensorDataDto;

import java.io.IOException;
import java.util.Map;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Testcontainers
@Slf4j
public class SensorDataReceiverTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void setMongoUri(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private HistoricalDataRepo historicalDataRepo;

    @Autowired
    private SensorDataReceiver sensorDataReceiver;

    @Autowired
    private InstantaneousRepo instantaneousRepo;

    @Autowired
    private SensorDataRepo sensorDataRepo;

    @Autowired
    private GridFsService gridFsService;

    @Autowired
    private ResponseMapper responseMapper;

    @BeforeEach
    void cleanDB() {
        historicalDataRepo.deleteAll();
    }

    @Test
    public void saveAndRetrieveHistoricalData() throws IOException {
        SensorDataDto data = new SensorDataDto();
        data.setVinId("1HGCM82633A123456");
        data.setSensor("BATTERY");
        data.setReading("LOW");
        data.setTimestamp("2025-04-25-07:30:00");

        sensorDataReceiver.receiveData(data);

        var historicalData = historicalDataRepo.findByVinId("1HGCM82633A123456");

        Map<String, HistoricalStateDetails> stateDetails = gridFsService.retrieveStateDetailsAsMap(historicalData.getFileId(), HistoricalStateDetails.class);

        assertFalse(historicalData.getFileId().isBlank());
        HistoricalStateDetails result = stateDetails.get("BATTERY");
        assertEquals("BATTERY", result.getSensor().name());
        assertEquals("LOW",result.getReading());
    }

    @Test
    public void saveAndRetrieveInstantaneousData() throws IOException {
        SensorDataDto data = new SensorDataDto();
        data.setVinId("1HGCM82633A123456");
        data.setSensor("TEMPERATURE");
        data.setReading("LOW");
        data.setTimestamp("2025-04-25-07:30:00");

        sensorDataReceiver.receiveData(data);

        var instantaneousData = instantaneousRepo.findByVinId("1HGCM82633A123456");

        Map<String, InstantaneousStateDetails> stateDetails = gridFsService.retrieveStateDetailsAsMap(instantaneousData.getFileId(), InstantaneousStateDetails.class);

        assertFalse(instantaneousData.getFileId().isBlank());
        InstantaneousStateDetails result = stateDetails.get("TEMPERATURE");
        assertEquals("TEMPERATURE", result.getSensor().name());
        assertEquals("LOW",result.getReading());
    }

    @Test
    public void testOptimisticLocking() throws IOException {
        String testVin = "1HGCM82633A123456";
        String testSensor = "BATTERY";

        SensorDataDto dto = new SensorDataDto();
        dto.setVinId(testVin);
        dto.setSensor(testSensor);
        dto.setReading("LOW");
        dto.setTimestamp("2025-04-25-07:30:00");

        sensorDataReceiver.receiveData(dto);

        SensorData data1 = sensorDataRepo.findByVinIdAndSensor(testVin, testSensor);
        SensorData data2 = sensorDataRepo.findByVinIdAndSensor(testVin, testSensor);

        data1.setReading("NORMAL");
        data2.setReading("FULLY_CHARGED");

        sensorDataRepo.save(data1);
        log.info("First save successful with version: " + data1.getVersion());

        try {
            sensorDataRepo.save(data2);
            fail("Expected OptimisticLockingFailureException but no exception was thrown");
        } catch (OptimisticLockingFailureException e) {
            log.info("Got expected OptimisticLockingFailureException: " + e.getMessage());
        }

        SensorData finalData = sensorDataRepo.findByVinIdAndSensor(testVin, testSensor);
        assertEquals("NORMAL", finalData.getReading());
        assertEquals(data1.getVersion(), finalData.getVersion());
    }
}
