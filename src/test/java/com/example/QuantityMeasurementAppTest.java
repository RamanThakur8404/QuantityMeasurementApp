package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.QuantityMeasurementApp.Feet;
import com.example.QuantityMeasurementApp.Inches;

public class QuantityMeasurementAppTest {

	@Test
	void testFeetEquality_SameValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);
		assertTrue(feet1.equals(feet2));
		
	}
	
	@Test
	void testFeetEquality_DifferentValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(2.0);
		assertFalse(feet1.equals(feet2));
	}
	
	@Test
	void testFeetEquality_NullComparison() {
		Feet feet1 = new Feet(1.0);
		assertFalse(feet1.equals(null));
	}

	@Test
	void testFeetEquality_DifferentClass() {
		Feet feet1 = new Feet(1.0);
		String other = "notFeet";
		assertFalse(feet1.equals(other));
	}

	@Test
	void testFeetEquality_SameReference() {
		Feet feet1 = new Feet(1.0);
		assertTrue(feet1.equals(feet1));
	}
	
	@Test
	void testInchesEquality_SameValue() {
		Inches inch1 = new Inches(1.0);
		Inches inch2 = new Inches(1.0);
		assertTrue(inch1.equals(inch2));
	}
	
	@Test
	void testInchesEquality_DifferentValue() {
		Inches inches1 = new Inches(1.0);
		Inches inches2 = new Inches(2.0);
		assertFalse(inches1.equals(inches2));
	}
	
	@Test
	void testInchesEquality_NullComparison() {
		Inches inches1 = new Inches(1.0);
		assertFalse(inches1.equals(null));
	}
	
	@Test
	void testInchesEquality_DifferentClass() {
		Inches inches1 = new Inches(1.0);
		String nonNumeric = "notInches";
		assertFalse(inches1.equals(nonNumeric));
	}

	@Test
	void testInchesEquality_SameReference() {
		Inches inches1 = new Inches(1.0);
		assertTrue(inches1.equals(inches1));
	}
}
