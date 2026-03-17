package com.example.app;
import com.example.controller.QuantityMeasurementController;
import com.example.repository.QuantityMeasurementCacheRepository;
import com.example.service.IQuantityMeasurementService;
import com.example.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityMeasurementCacheRepository repository =
                QuantityMeasurementCacheRepository.getInstance();

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        controller.performAddition();
        controller.performComparison();
    }
}