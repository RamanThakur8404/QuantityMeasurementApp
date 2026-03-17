package com.example.service;
import com.example.dto.QuantityDTO;
import com.example.entity.QuantityMeasurementEntity;
import com.example.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        double result = q1.getValue() + q2.getValue();

        repository.save(new QuantityMeasurementEntity(
                "Addition",
                q1.getValue(),
                q2.getValue(),
                result
        ));

        return new QuantityDTO(result, q1.getUnit());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {

        double result = q1.getValue() - q2.getValue();

        repository.save(new QuantityMeasurementEntity(
                "Subtraction",
                q1.getValue(),
                q2.getValue(),
                result
        ));

        return new QuantityDTO(result, q1.getUnit());
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {

        if (q2.getValue() == 0)
            throw new RuntimeException("Division by zero");

        double result = q1.getValue() / q2.getValue();

        repository.save(new QuantityMeasurementEntity(
                "Division",
                q1.getValue(),
                q2.getValue(),
                result
        ));

        return result;
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        boolean result = q1.getValue() == q2.getValue();

        repository.save(new QuantityMeasurementEntity(
                "Comparison",
                q1.getValue(),
                q2.getValue(),
                result ? 1 : 0
        ));

        return result;
    }
}