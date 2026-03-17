package com.example;

import com.example.controller.QuantityMeasurementController;
import com.example.dto.QuantityDTO;
import com.example.repository.QuantityMeasurementCacheRepository;
import com.example.service.IQuantityMeasurementService;
import com.example.service.QuantityMeasurementServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private IQuantityMeasurementService service;
    private QuantityMeasurementController controller;
    private QuantityMeasurementCacheRepository repository;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    // BASIC SERVICE OPERATION TESTS
    @Test
    void shouldAddTwoQuantities() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(5, "meter");
        QuantityDTO result = service.add(q1, q2);
        assertEquals(15, result.getValue());
    }

    @Test
    void shouldSubtractTwoQuantities() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(3, "meter");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(7, result.getValue());
    }

    @Test
    void shouldDivideTwoQuantities() {
        QuantityDTO q1 = new QuantityDTO(20, "meter");
        QuantityDTO q2 = new QuantityDTO(4, "meter");

        double result = service.divide(q1, q2);

        assertEquals(5, result);
    }

    @Test
    void shouldThrowExceptionForDivideByZero() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(0, "meter");

        assertThrows(RuntimeException.class, () -> service.divide(q1, q2));
    }

    @Test
    void shouldReturnTrueForEqualQuantities() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(10, "meter");

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void shouldReturnFalseForDifferentQuantities() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(12, "meter");

        assertFalse(service.compare(q1, q2));
    }

    // ADDITION TEST CASES
    @Test
    void additionWithZeroShouldReturnSameValue() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(0, "meter");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(10, result.getValue());
    }

    @Test
    void additionWithNegativeNumbers() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(-5, "meter");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(5, result.getValue());
    }

    @Test
    void additionLargeNumbers() {
        QuantityDTO q1 = new QuantityDTO(1000, "meter");
        QuantityDTO q2 = new QuantityDTO(2000, "meter");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(3000, result.getValue());
    }

    // SUBTRACTION TEST CASES
    @Test
    void subtractionWithZero() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(0, "meter");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(10, result.getValue());
    }

    @Test
    void subtractionNegativeResult() {
        QuantityDTO q1 = new QuantityDTO(5, "meter");
        QuantityDTO q2 = new QuantityDTO(10, "meter");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(-5, result.getValue());
    }

    @Test
    void subtractionLargeValues() {
        QuantityDTO q1 = new QuantityDTO(5000, "meter");
        QuantityDTO q2 = new QuantityDTO(2000, "meter");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(3000, result.getValue());
    }

    // DIVISION TEST CASES
    @Test
    void divisionWithOne() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(1, "meter");

        double result = service.divide(q1, q2);

        assertEquals(10, result);
    }

    @Test
    void divisionResultDecimal() {
        QuantityDTO q1 = new QuantityDTO(10, "meter");
        QuantityDTO q2 = new QuantityDTO(4, "meter");

        double result = service.divide(q1, q2);

        assertEquals(2.5, result);
    }

    @Test
    void divisionNegativeValues() {
        QuantityDTO q1 = new QuantityDTO(-10, "meter");
        QuantityDTO q2 = new QuantityDTO(2, "meter");

        double result = service.divide(q1, q2);

        assertEquals(-5, result);
    }

    // COMPARISON TEST CASES
    @Test
    void comparisonSameValues() {
        QuantityDTO q1 = new QuantityDTO(20, "meter");
        QuantityDTO q2 = new QuantityDTO(20, "meter");

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void comparisonDifferentValues() {
        QuantityDTO q1 = new QuantityDTO(20, "meter");
        QuantityDTO q2 = new QuantityDTO(30, "meter");

        assertFalse(service.compare(q1, q2));
    }

    @Test
    void comparisonWithZero() {
        QuantityDTO q1 = new QuantityDTO(0, "meter");
        QuantityDTO q2 = new QuantityDTO(0, "meter");

        assertTrue(service.compare(q1, q2));
    }

    // REPOSITORY TEST CASES
    @Test
    void repositoryShouldStoreAdditionOperation() {
        service.add(new QuantityDTO(5, "meter"), new QuantityDTO(5, "meter"));

        assertFalse(repository.findAll().isEmpty());
    }

    @Test
    void repositoryShouldStoreSubtractionOperation() {
        service.subtract(new QuantityDTO(10, "meter"), new QuantityDTO(3, "meter"));

        assertTrue(repository.findAll().size() > 0);
    }

    @Test
    void repositoryShouldStoreMultipleOperations() {
        service.add(new QuantityDTO(5, "meter"), new QuantityDTO(5, "meter"));
        service.subtract(new QuantityDTO(10, "meter"), new QuantityDTO(3, "meter"));
        service.divide(new QuantityDTO(10, "meter"), new QuantityDTO(2, "meter"));

        assertTrue(repository.findAll().size() >= 3);
    }

    // CONTROLLER FLOW TESTS
    @Test
    void controllerAdditionFlow() {
        QuantityDTO result = service.add(new QuantityDTO(8, "meter"), new QuantityDTO(2, "meter"));

        assertEquals(10, result.getValue());
    }

    @Test
    void controllerComparisonFlow() {
        boolean result = service.compare(new QuantityDTO(5, "meter"), new QuantityDTO(5, "meter"));

        assertTrue(result);
    }

    @Test
    void controllerDivisionFlow() {
        double result = service.divide(new QuantityDTO(20, "meter"), new QuantityDTO(4, "meter"));

        assertEquals(5, result);
    }

    // EDGE CASE TESTS
    @Test
    void additionWithDecimals() {
        QuantityDTO result = service.add(new QuantityDTO(2.5, "meter"), new QuantityDTO(2.5, "meter"));

        assertEquals(5.0, result.getValue());
    }

    @Test
    void subtractionWithDecimals() {
        QuantityDTO result = service.subtract(new QuantityDTO(5.5, "meter"), new QuantityDTO(2.5, "meter"));

        assertEquals(3.0, result.getValue());
    }

    @Test
    void divideDecimalValues() {
        double result = service.divide(new QuantityDTO(7.5, "meter"), new QuantityDTO(2.5, "meter"));

        assertEquals(3.0, result);
    }

    @Test
    void compareDecimalValues() {
        assertTrue(service.compare(new QuantityDTO(2.5, "meter"), new QuantityDTO(2.5, "meter")));
    }

    @Test
    void compareNegativeValues() {
        assertTrue(service.compare(new QuantityDTO(-5, "meter"), new QuantityDTO(-5, "meter")));
    }

    @Test
    void additionBothNegative() {
        QuantityDTO result = service.add(new QuantityDTO(-5, "meter"), new QuantityDTO(-5, "meter"));

        assertEquals(-10, result.getValue());
    }
}