# UC14 – Temperature Measurement with Selective Arithmetic Support and IMeasurable Refactoring

## Description
UC14 extends the Quantity Measurement Application to support **Temperature measurements** alongside Length, Weight, and Volume.

Unlike other measurement categories, temperature has **selective arithmetic support**:

- ✔ Supports equality comparison
- ✔ Supports unit conversion
- ✖ Does NOT support addition, subtraction, or division of absolute temperatures

This use case refactors the `IMeasurable` interface to allow **optional arithmetic operations** using default methods and capability-based design.

---

## Objective
Return:
- Accurate temperature equality across units
- Correct temperature conversion formulas
- Meaningful exception handling for unsupported operations
- Maintain backward compatibility with UC1–UC13

---

## Supported Temperature Units
- CELSIUS (Base Unit)
- FAHRENHEIT
- KELVIN

---

## Key Design Enhancement

### Problem
Previous design assumed all measurement categories support arithmetic operations.

Temperature violates this assumption because:

```
100°C + 50°C → Not meaningful
100°C ÷ 50°C → Meaningless result
```

---

### Solution
Refactor `IMeasurable` interface to support **optional arithmetic capability**.

---

## IMeasurable Interface Refactoring

### Functional Interface
```java
@FunctionalInterface
public interface SupportsArithmetic {
    boolean isSupported();
}
```

### Default Lambda Support
```java
SupportsArithmetic supportsArithmetic = () -> true;
```

### Default Methods
```java
default boolean supportsArithmetic() {
    return supportsArithmetic.isSupported();
}

default void validateOperationSupport(String operation) {
    // overridden by TemperatureUnit
}
```

---

## TemperatureUnit Enum

### Characteristics
- Implements `IMeasurable`
- Uses lambda expressions for conversion
- Arithmetic support disabled

```java
SupportsArithmetic supportsArithmetic = () -> false;
```

### Conversion Formulas
- °F = (°C × 9/5) + 32
- °C = (°F − 32) × 5/9
- K = °C + 273.15

---

## Quantity Class Enhancements
- Arithmetic methods call:
```
unit.validateOperationSupport(operation)
```
before execution.

- Conversion method detects TemperatureUnit and applies non-linear conversion logic.

---

## Example Output

### Temperature Equality
```
new Quantity<>(0.0, CELSIUS)
.equals(new Quantity<>(32.0, FAHRENHEIT))
→ true
```

```
new Quantity<>(273.15, KELVIN)
.equals(new Quantity<>(0.0, CELSIUS))
→ true
```

---

### Temperature Conversion
```
100°C → 212°F
32°F → 0°C
0°C → 273.15K
-40°C → -40°F
```

---

### Unsupported Operations
```
new Quantity<>(100, CELSIUS).add(...)
→ UnsupportedOperationException

new Quantity<>(100, CELSIUS).divide(...)
→ UnsupportedOperationException
```

---

### Cross-Category Safety
```
Temperature ≠ Length
Temperature ≠ Weight
Temperature ≠ Volume
```

---

## Concepts Covered
- Interface Segregation Principle (ISP)
- Default Methods in Interfaces
- Functional Interfaces
- Lambda Expressions
- Capability-Based Design
- Non-Linear Unit Conversion
- Absolute vs Relative Temperature
- Polymorphic Error Handling
- SOLID Principles
- Generic Type Safety

---

## Key Improvements
- Arithmetic operations now optional
- Temperature integrates without breaking existing code
- Clear operation capability validation
- Improved API clarity
- Scalable for future measurement categories

---

## Postconditions
- Temperature supports equality and conversion
- Unsupported arithmetic throws `UnsupportedOperationException`
- UC1–UC13 tests pass unchanged
- Cross-category comparison prevented
- Design adheres to SOLID principles

---

## Test Cases

### Equality Tests
- `testTemperatureEquality_CelsiusToCelsius_SameValue()`
- `testTemperatureEquality_CelsiusToFahrenheit()`
- `testTemperatureEquality_FahrenheitToKelvin()`
- `testTemperatureEquality_SymmetricProperty()`
- `testTemperatureEquality_ReflexiveProperty()`

### Conversion Tests
- `testTemperatureConversion_CelsiusToFahrenheit()`
- `testTemperatureConversion_FahrenheitToCelsius()`
- `testTemperatureConversion_RoundTrip()`
- `testTemperatureConversion_ZeroValue()`
- `testTemperatureConversion_NegativeValues()`

### Unsupported Operations
- `testTemperatureUnsupportedOperation_Add()`
- `testTemperatureUnsupportedOperation_Subtract()`
- `testTemperatureUnsupportedOperation_Divide()`

### Type Safety
- `testTemperatureVsLengthIncompatibility()`
- `testTemperatureVsWeightIncompatibility()`
- `testTemperatureVsVolumeIncompatibility()`

### Interface Evolution
- `testIMeasurableInterface_BackwardCompatible()`
- `testTemperatureDefaultMethodInheritance()`

---

## Advantages
- Supports categories with different operational constraints
- No dummy implementations required
- Cleaner abstraction
- Compiler-safe generic design
- Easier future extension

---

## Future Extensions
- Relative temperature difference type
- Advanced thermodynamic units
- Capability-based compile-time constraints
- Strategy pattern for operation support