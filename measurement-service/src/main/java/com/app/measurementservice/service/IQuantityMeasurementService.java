package com.app.measurementservice.service;

import com.app.measurementservice.dto.response.QuantityDTO;
import com.app.measurementservice.dto.request.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

    QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

    QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);
    List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);
    long getOperationCount(String operation);
    List<QuantityMeasurementDTO> getErrorHistory();
}
