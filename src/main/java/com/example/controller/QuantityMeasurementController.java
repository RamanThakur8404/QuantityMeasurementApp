package com.example.controller;
import com.example.dto.QuantityDTO;
import com.example.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performAddition() {

        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(5, "meter");

        QuantityDTO result = service.add(q1, q2);

        System.out.println("Addition Result: " + result.getValue());
    }

    public void performComparison() {

        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(10, "meter");

        boolean equal = service.compare(q1, q2);

        System.out.println("Are Equal: " + equal);
    }
}