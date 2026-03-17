package com.example.service;
import com.example.dto.QuantityDTO;

public interface IQuantityMeasurementService {

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2);

    double divide(QuantityDTO q1, QuantityDTO q2);

    boolean compare(QuantityDTO q1, QuantityDTO q2);
}