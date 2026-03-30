package com.app.quantitymeasurement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuantityDTO {

    // common interface for all units
    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    // length units
    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS;

        public String getUnitName() { return this.name(); }
        public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    // volume units
    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITRE, GALLON;

        public String getUnitName() { return this.name(); }
        public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    // weight units
    public enum WeightUnit implements IMeasurableUnit {
        KILOGRAM, GRAM, POUND;

        public String getUnitName() { return this.name(); }
        public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    // temperature units
    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;

        public String getUnitName() { return this.name(); }
        public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    // -------- fields --------

    @NotNull(message = "Value must not be null")
    private Double value;

    @NotEmpty(message = "Unit must not be empty")
    private String unit;

    @NotEmpty(message = "Measurement type must not be empty")
    @Pattern(
        regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
        message = "Invalid measurement type"
    )
    private String measurementType;

    // -------- constructors --------

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value = value;
        this.unit = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    // return primitive value safely
    public double getValue() {
        return value == null ? 0.0 : value;
    }

    // validate unit with measurement type
    @jakarta.validation.constraints.AssertTrue(
        message = "Unit must match measurement type"
    )
    public boolean isUnitValidForMeasurementType() {
        if (unit == null || measurementType == null) return true;

        try {
            switch (measurementType) {
                case "LengthUnit": LengthUnit.valueOf(unit); break;
                case "VolumeUnit": VolumeUnit.valueOf(unit); break;
                case "WeightUnit": WeightUnit.valueOf(unit); break;
                case "TemperatureUnit": TemperatureUnit.valueOf(unit); break;
                default: return false;
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // formatted output
    @Override
    public String toString() {
        return String.format("%s %s",
                Double.toString(value == null ? 0.0 : value).replaceAll("\\.0+$", ""),
                unit);
    }
}