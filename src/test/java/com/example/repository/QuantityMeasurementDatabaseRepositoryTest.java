package com.example.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class QuantityMeasurementDatabaseRepositoryTest {

    private IQuantityMeasurementRepository repository;

    @Before
    public void setup() {

        ConnectionPool pool = new ConnectionPool();

        repository = new QuantityMeasurementDatabaseRepository(pool);

        repository.deleteAll();
    }

    @Test
    public void testSaveAndFetch() {

        QuantityMeasurementEntity e = new QuantityMeasurementEntity();

        e.setMeasurementType("LENGTH");
        e.setOperationType("COMPARE");
        e.setValue(10);

        repository.save(e);

        List<QuantityMeasurementEntity> list =
                repository.getAllMeasurements();

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testCount() {

        QuantityMeasurementEntity e = new QuantityMeasurementEntity();

        e.setMeasurementType("WEIGHT");
        e.setOperationType("ADD");
        e.setValue(20);

        repository.save(e);

        Assert.assertEquals(1, repository.getTotalCount());
    }

    @Test
    public void testDeleteAll() {

        QuantityMeasurementEntity e = new QuantityMeasurementEntity();

        e.setMeasurementType("TEMP");
        e.setOperationType("SUB");
        e.setValue(5);

        repository.save(e);

        repository.deleteAll();

        Assert.assertEquals(0, repository.getTotalCount());
    }
}