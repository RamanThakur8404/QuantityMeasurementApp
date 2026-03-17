package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.util.List;

public interface IQuantityMeasurementService {

    void saveMeasurement(String measurementType,
                         String operationType,
                         double value);

    List<QuantityMeasurementEntity> getAllMeasurements();

    int getTotalMeasurementsCount();

    void deleteAllMeasurements();
}