package com.app.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mirrors QuantityMeasurementDTO from Measurement Service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {

    private Double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private Double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    private String operation;

    private String resultString;
    private Double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    private String errorMessage;
    private boolean error;
}
