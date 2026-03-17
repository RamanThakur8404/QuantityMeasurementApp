package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import java.util.List;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void saveMeasurement(String measurementType,
                                String operationType,
                                double value) {

        service.saveMeasurement(measurementType, operationType, value);
    }

    public List<QuantityMeasurementEntity> getAllMeasurements() {

        return service.getAllMeasurements();
    }

    public int getTotalMeasurementsCount() {

        return service.getTotalMeasurementsCount();
    }

    public void deleteAllMeasurements() {

        service.deleteAllMeasurements();
    }
}