package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;

import java.util.List;

// service interface for quantity operations
public interface IQuantityMeasurementService {

    // compare quantities
    QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    // convert quantity
    QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    // add quantities
    QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    // add with target unit
    QuantityMeasurementDTO add(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO);

    // subtract quantities
    QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    // subtract with target unit
    QuantityMeasurementDTO subtract(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO);

    // divide quantities
    QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

    // history by operation
    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);

    // history by measurement type
    List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);

    // count successful operations
    long getOperationCount(String operation);

    // get error history
    List<QuantityMeasurementDTO> getErrorHistory();
}