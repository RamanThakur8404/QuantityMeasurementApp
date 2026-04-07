package com.app.measurementservice.model;

import com.app.measurementservice.unit.IMeasurable;

public class QuantityModel<U extends IMeasurable> {

    private final Double value;
    private final U unit;

    public QuantityModel(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }

    @Override
    public String toString() {
        return String.format("%s %s",
            Double.toString(value).replaceAll("\\.0+$", ""),
            unit.getUnitName());
    }
}
