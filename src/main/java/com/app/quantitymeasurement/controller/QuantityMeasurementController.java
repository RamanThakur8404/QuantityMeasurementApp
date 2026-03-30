package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity operations")
public class QuantityMeasurementController {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementController.class.getName());

    @Autowired
    private IQuantityMeasurementService quantityMeasurementService;

    // ---------- POST APIs ----------

    @PostMapping("/compare")
    @Operation(summary = "Compare quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(
            @Valid @RequestBody QuantityInputDTO quantityInputDTO) {

        logger.info("POST /compare"); // log request

        return ResponseEntity.ok(
                quantityMeasurementService.compare(
                        quantityInputDTO.getThisQuantityDTO(),
                        quantityInputDTO.getThatQuantityDTO()
                ));
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert quantity")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(
            @Valid @RequestBody QuantityInputDTO quantityInputDTO) {

        logger.info("POST /convert");

        return ResponseEntity.ok(
                quantityMeasurementService.convert(
                        quantityInputDTO.getThisQuantityDTO(),
                        quantityInputDTO.getThatQuantityDTO()
                ));
    }

    @PostMapping("/add")
    @Operation(summary = "Add quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(
            @Valid @RequestBody QuantityInputDTO quantityInputDTO) {

        logger.info("POST /add");

        // choose method based on target unit
        QuantityMeasurementDTO result =
                (quantityInputDTO.getTargetUnitDTO() != null)
                        ? quantityMeasurementService.add(
                                quantityInputDTO.getThisQuantityDTO(),
                                quantityInputDTO.getThatQuantityDTO(),
                                quantityInputDTO.getTargetUnitDTO())
                        : quantityMeasurementService.add(
                                quantityInputDTO.getThisQuantityDTO(),
                                quantityInputDTO.getThatQuantityDTO());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(
            @Valid @RequestBody QuantityInputDTO quantityInputDTO) {

        logger.info("POST /subtract");

        // choose method based on target unit
        QuantityMeasurementDTO result =
                (quantityInputDTO.getTargetUnitDTO() != null)
                        ? quantityMeasurementService.subtract(
                                quantityInputDTO.getThisQuantityDTO(),
                                quantityInputDTO.getThatQuantityDTO(),
                                quantityInputDTO.getTargetUnitDTO())
                        : quantityMeasurementService.subtract(
                                quantityInputDTO.getThisQuantityDTO(),
                                quantityInputDTO.getThatQuantityDTO());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(
            @Valid @RequestBody QuantityInputDTO quantityInputDTO) {

        logger.info("POST /divide");

        return ResponseEntity.ok(
                quantityMeasurementService.divide(
                        quantityInputDTO.getThisQuantityDTO(),
                        quantityInputDTO.getThatQuantityDTO()
                ));
    }

    // ---------- GET APIs ----------

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get history by operation")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(
            @Parameter(description = "Operation type")
            @PathVariable String operation) {

        logger.info("GET /history/operation/" + operation);

        return ResponseEntity.ok(
                quantityMeasurementService.getHistoryByOperation(operation));
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get history by type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMeasurementHistory(
            @Parameter(description = "Measurement type")
            @PathVariable String measurementType) {

        logger.info("GET /history/type/" + measurementType);

        return ResponseEntity.ok(
                quantityMeasurementService.getHistoryByMeasurementType(measurementType));
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get error history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {

        logger.info("GET /history/errored");

        return ResponseEntity.ok(
                quantityMeasurementService.getErrorHistory());
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get operation count")
    public ResponseEntity<Long> getOperationCount(
            @Parameter(description = "Operation type")
            @PathVariable String operation) {

        logger.info("GET /count/" + operation);

        return ResponseEntity.ok(
                quantityMeasurementService.getOperationCount(operation));
    }
}