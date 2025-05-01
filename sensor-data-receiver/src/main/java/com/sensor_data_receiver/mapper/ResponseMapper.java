package com.sensor_data_receiver.mapper;


import com.sensor_data_receiver.dto.HistoricalStateDetails;
import com.sensor_data_receiver.dto.InstantaneousStateDetails;
import com.sensor_data_receiver.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.common_service.dto.SensorDataDto;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    SensorData mapToSensorData(SensorDataDto data);

    void updateFromDto(SensorDataDto dto, @MappingTarget SensorData data);

    void updateHistoricalStateDetailsFromDto(SensorDataDto dto, @MappingTarget HistoricalStateDetails data);

    void updateInstantaneousStateDetailsFromDto(SensorDataDto dto, @MappingTarget InstantaneousStateDetails data);

    HistoricalStateDetails mapToHistoricalStateDetails(SensorDataDto dto);

    InstantaneousStateDetails mapToInstantaneousStateDetails(SensorDataDto dto);
}

