# UC11 – Refactoring Using Generic Quantity Measurement

## Description
Refactors the measurement system by replacing separate measurement classes (Feet, Inches, etc.) with a generic Quantity design to eliminate code duplication and improve maintainability.

This use case follows the DRY (Don't Repeat Yourself) principle by introducing a unified measurement model using a unit-based approach.

## Objective
Return:
- Accurate equality comparison across compatible units
- Support arithmetic operations using a single Quantity class
- Improve scalability and maintain clean architecture

## Implementation
- Generic class `QuantityMeasurement<U extends Unit>`
- Enum-based unit representation
- Base unit conversion strategy
- Immutable object design
- Centralized validation logic
- Overridden `equals()` using base-unit comparison
- Unified behavior for:
  - Addition
  - Subtraction
  - Division
  - Equality comparison

## Example

Input:  
1.0 feet and 12.0 inches  

Output:  
Equal: true  

Input:  
2.0 feet and 24.0 inches  

Output:  
Equal: true  

## Key Components
- `QuantityMeasurement` class
- `Unit` interface
- `LengthUnit` enum
- Conversion to base unit
- Validation handling

## Concepts Covered
- Generic Programming
- Enum Design
- DRY Principle
- Value-Based Equality
- Object Encapsulation
- Unit Conversion Strategy
- Clean Code Refactoring
- Immutable Objects

## Advantages
- Eliminates duplicate Feet and Inches classes
- Easier addition of new measurement units
- Centralized equality logic
- Improved maintainability
- Industry-standard design

## Test Cases
- `testEquality_SameUnit()`
- `testEquality_DifferentUnits()`
- `testAddition_SameUnit()`
- `testAddition_DifferentUnits()`
- `testNullValidation()`
- `testInvalidInput()`
- `testUnifiedBehavior()`

## Suggested Improvement
Extend the system to support:
- Weight measurements
- Temperature conversions
- Volume measurements
- Generic measurement operations