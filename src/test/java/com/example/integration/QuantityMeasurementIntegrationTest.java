package com.example.integration;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.repository.*;
import com.app.quantitymeasurement.service.*;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementController controller;

    @Before
    public void setup() {

        ConnectionPool pool = new ConnectionPool();

        IQuantityMeasurementRepository repo =
                new QuantityMeasurementDatabaseRepository(pool);

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repo);

        controller = new QuantityMeasurementController(service);

        controller.deleteAllMeasurements();
    }

    @Test
    public void testEndToEndFlow() {

        controller.saveMeasurement("LENGTH", "COMPARE", 10);

        int count = controller.getTotalMeasurementsCount();

        Assert.assertEquals(1, count);
    }
}