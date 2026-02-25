# UC13 – Centralized Arithmetic Logic to Enforce DRY in Quantity Operations

## Description
UC13 refactors arithmetic operations (`add`, `subtract`, `divide`) introduced in UC12 to eliminate code duplication and enforce the **DRY (Don't Repeat Yourself)** principle.

Instead of repeating validation, unit conversion, and normalization logic across multiple arithmetic methods, a centralized private helper method is introduced.

All public APIs remain unchanged while internal implementation becomes cleaner, scalable, and maintainable.

---

## Objective
Return:
- Same arithmetic behavior as UC12
- Centralized validation and conversion logic
- Consistent error handling across operations
- Reduced duplication and improved maintainability

---

## Problems in UC12 Implementation
- Duplicate validation logic across arithmetic methods
- Repeated base-unit conversion logic
- Increased maintenance complexity
- Reduced readability
- Inconsistent error handling risks
- Difficult scalability for new operations

---

## Refactoring Strategy

### Step 1 – Analyze Common Logic
Common logic identified:
- Null operand validation
- Unit category compatibility check
- Numeric finiteness validation
- Base-unit conversion
- Arithmetic computation
- Target unit handling

---

### Step 2 – ArithmeticOperation Enum
Introduce private enum:

- `ADD`
- `SUBTRACT`
- `DIVIDE`
- `MULTIPLY` (future extension)

Each enum constant performs computation using:
- Abstract method implementation **OR**
- Lambda expression using `DoubleBinaryOperator`

---

### Step 3 – Centralized Validation Helper

```
private void validateArithmeticOperands(
    Quantity<U> other,
    U targetUnit,
    boolean targetUnitRequired
)
```

Validates:
- Null operand
- Unit compatibility
- Numeric finiteness
- Target unit correctness

---

### Step 4 – Core Arithmetic Helper

```
private double performBaseArithmetic(
    Quantity<U> other,
    ArithmeticOperation operation
)
```

Responsibilities:
- Convert operands to base unit
- Execute arithmetic operation
- Return base-unit result

---

### Step 5 – Refactor Public Methods

#### Addition
- `add(other)`
- `add(other, targetUnit)`

#### Subtraction
- `subtract(other)`
- `subtract(other, targetUnit)`

#### Division
- `divide(other)` → returns scalar value

All delegate computation to centralized helper.

---

### Step 6 – Backward Compatibility
- Public APIs unchanged
- UC12 behavior preserved
- Existing test cases pass without modification

---

## Example Outputs

### Addition
```
new Quantity<>(1.0, FEET).add(new Quantity<>(12.0, INCHES))
→ Quantity(2.0, FEET)
```

### Subtraction
```
new Quantity<>(10.0, FEET).subtract(new Quantity<>(6.0, INCHES))
→ Quantity(9.5, FEET)
```

### Division
```
new Quantity<>(24.0, INCHES).divide(new Quantity<>(2.0, FEET))
→ 1.0
```

---

## Error Handling (Unified)
```
add(null) → IllegalArgumentException
cross-category operation → IllegalArgumentException
divide by zero → ArithmeticException
```

---

## Concepts Covered
- DRY Principle Enforcement
- Method Extraction Refactoring
- Enum-Based Operation Dispatch
- Lambda Expressions
- Functional Interfaces
- Centralized Validation Strategy
- Separation of Concerns
- Consistent Error Handling
- Scalable Architecture Design
- Refactoring Without Behavioral Change

---

## Key Improvements
- Validation logic written once
- Conversion logic centralized
- Cleaner public methods
- Reduced maintenance effort
- Easier future extension (Multiply, Modulo)

---

## Test Cases

### Delegation Tests
- `testRefactoring_Add_DelegatesViaHelper()`
- `testRefactoring_Subtract_DelegatesViaHelper()`
- `testRefactoring_Divide_DelegatesViaHelper()`

### Validation Consistency
- `testValidation_NullOperand_ConsistentAcrossOperations()`
- `testValidation_CrossCategory_ConsistentAcrossOperations()`
- `testValidation_FiniteValue_ConsistentAcrossOperations()`

### Enum Operation Tests
- `testArithmeticOperation_Add_EnumComputation()`
- `testArithmeticOperation_Subtract_EnumComputation()`
- `testArithmeticOperation_Divide_EnumComputation()`

### Backward Compatibility
- `testAdd_UC12_BehaviorPreserved()`
- `testSubtract_UC12_BehaviorPreserved()`
- `testDivide_UC12_BehaviorPreserved()`

### Rounding Behavior
- `testRounding_AddSubtract_TwoDecimalPlaces()`
- `testRounding_Divide_NoRounding()`

### Immutability Tests
- `testImmutability_AfterAdd_ViaCentralizedHelper()`
- `testImmutability_AfterSubtract_ViaCentralizedHelper()`
- `testImmutability_AfterDivide_ViaCentralizedHelper()`

### DRY Enforcement
- `testCodeDuplication_ValidationLogic_Eliminated()`
- `testCodeDuplication_ConversionLogic_Eliminated()`

---

## Postconditions
- All arithmetic operations use centralized helper logic
- Code duplication removed
- Consistent validation and error handling
- UC12 tests pass without changes
- System ready for future arithmetic extensions

---

## Advantages
- Improved maintainability
- Reduced bug risk
- Cleaner architecture
- Scalable design
- Easier testing and debugging

---

## Future Extension
New operations can be added by simply extending the enum:

```
MULTIPLY,
MODULO
```

without modifying validation or conversion logic.