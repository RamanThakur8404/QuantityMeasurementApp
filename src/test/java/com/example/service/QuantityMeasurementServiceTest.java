package com.example.service;

import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QuantityMeasurementServiceTest {

    private IQuantityMeasurementService service;

    @Before
    public void setup() {

        service = new QuantityMeasurementServiceImpl(
                new QuantityMeasurementCacheRepository());
    }

    @Test
    public void testSaveMeasurement() {

        service.saveMeasurement("LENGTH", "COMPARE", 10);

        Assert.assertEquals(1,
                service.getTotalMeasurementsCount());
    }

    @Test
    public void testDeleteAll() {

        service.saveMeasurement("WEIGHT", "ADD", 20);

        service.deleteAllMeasurements();

        Assert.assertEquals(0,
                service.getTotalMeasurementsCount());
    }
}