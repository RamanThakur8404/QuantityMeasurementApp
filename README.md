# UC12 – Unified Validation and Error Handling

## Description
Enhances the refactored Quantity Measurement system by introducing unified validation and consistent error handling across all measurement operations.

This use case ensures that invalid inputs, null values, and unsupported operations are handled uniformly throughout the application.

## Objective
Return:
- Valid results for all measurement operations
- Consistent exceptions for invalid scenarios
- Unified behavior across add, subtract, divide, and equality operations

## Implementation
- Centralized validation inside `QuantityMeasurement` class
- Validation checks include:
  - Null quantity validation
  - Null unit validation
  - Non-numeric values (NaN / Infinite)
- Standardized exception handling using `IllegalArgumentException`
- Shared validation logic reused across all operations
- Improved robustness and reliability

## Example

Input:  
Add null quantity  

Output:  
IllegalArgumentException thrown  

Input:  
Invalid numeric value (NaN)  

Output:  
IllegalArgumentException thrown  

## Key Components
- `QuantityMeasurement` class
- Validation methods
- Exception handling strategy
- Unified operation behavior

## Concepts Covered
- Defensive Programming
- Input Validation
- Exception Handling
- Null Safety
- Clean Code Principles
- Robust System Design

## Advantages
- Prevents inconsistent error handling
- Reduces duplicate validation code
- Improves application reliability
- Easier debugging and maintenance
- Industry-standard validation design

## Test Cases
- `testAdd_NullQuantity()`
- `testSubtract_NullQuantity()`
- `testDivide_NullQuantity()`
- `testInvalidUnit()`
- `testNaNValueValidation()`
- `testUnifiedBehaviorValidation()`

## Suggested Improvement
Extend validation framework to include:
- Custom exception hierarchy
- Logging support
- Input sanitization layer
- Global error handling mechanism