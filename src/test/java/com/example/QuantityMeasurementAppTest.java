package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {

	@Test
	void testEquality_SameValue() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet feet2 = new QuantityMeasurementApp.Feet(1.0);
		assertTrue(feet1.equals(feet2),"1.0 ft should be equal to 1.0 ft");
	}
	
	@Test
	void estEquality_DifferentValue() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet feet2 = new QuantityMeasurementApp.Feet(2.0);
		assertFalse(feet1.equals(feet2),"1.0 ft should not be equal to 2.0 ft");
	}
	
	@Test
	void testEquality_NullComparison() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
		assertFalse(feet1.equals(null), "Value should not be equal to null");
	}

	@Test
	void testEquality_NonNumericInput() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
		String nonNumeric = "xyz";
		assertFalse(feet1.equals(nonNumeric), "Value should not be equal to non-numeric input");
	}

	@Test
	void testEquality_SameReference() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
		assertTrue(feet1.equals(feet1), "Object should be equal to itself");
	}
}
