package com.app.measurementservice.controller;

import lombok.extern.slf4j.Slf4j;

import com.app.measurementservice.dto.request.QuantityInputDTO;
import com.app.measurementservice.dto.request.QuantityMeasurementDTO;
import com.app.measurementservice.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "Quantity operations APIs")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService quantityMeasurementService;

    public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
        this.quantityMeasurementService = quantityMeasurementService;
    }

    @PostMapping("/compare")
    @Operation(summary = "Compare quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(
            @Valid @RequestBody QuantityInputDTO dto) {
        log.info("POST /compare");
        return ResponseEntity.ok(
                quantityMeasurementService.compare(
                        dto.getThisQuantityDTO(),
                        dto.getThatQuantityDTO()
                ));
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert quantity")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(
            @Valid @RequestBody QuantityInputDTO dto) {
        log.info("POST /convert");
        return ResponseEntity.ok(
                quantityMeasurementService.convert(
                        dto.getThisQuantityDTO(),
                        dto.getThatQuantityDTO()
                ));
    }

    @PostMapping("/add")
    @Operation(summary = "Add quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(
            @Valid @RequestBody QuantityInputDTO dto) {
        log.info("POST /add");

        QuantityMeasurementDTO result = (dto.getTargetUnitDTO() != null)
                ? quantityMeasurementService.add(
                        dto.getThisQuantityDTO(),
                        dto.getThatQuantityDTO(),
                        dto.getTargetUnitDTO())
                : quantityMeasurementService.add(
                        dto.getThisQuantityDTO(),
                        dto.getThatQuantityDTO());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(
            @Valid @RequestBody QuantityInputDTO dto) {
        log.info("POST /subtract");

        QuantityMeasurementDTO result = (dto.getTargetUnitDTO() != null)
                ? quantityMeasurementService.subtract(
                        dto.getThisQuantityDTO(),
                        dto.getThatQuantityDTO(),
                        dto.getTargetUnitDTO())
                : quantityMeasurementService.subtract(
                        dto.getThisQuantityDTO(),
                        dto.getThatQuantityDTO());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(
            @Valid @RequestBody QuantityInputDTO dto) {
        log.info("POST /divide");
        return ResponseEntity.ok(
                quantityMeasurementService.divide(
                        dto.getThisQuantityDTO(),
                        dto.getThatQuantityDTO()
                ));
    }

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get history by operation")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(
            @PathVariable String operation) {
        log.info("GET /history/operation/{}", operation);
        return ResponseEntity.ok(
                quantityMeasurementService.getHistoryByOperation(operation));
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get history by type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMeasurementHistory(
            @PathVariable String measurementType) {
        log.info("GET /history/type/{}", measurementType);
        return ResponseEntity.ok(
                quantityMeasurementService.getHistoryByMeasurementType(measurementType));
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get error history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        log.info("GET /history/errored");
        return ResponseEntity.ok(
                quantityMeasurementService.getErrorHistory());
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get operation count")
    public ResponseEntity<Long> getOperationCount(
            @PathVariable String operation) {
        log.info("GET /count/{}", operation);
        return ResponseEntity.ok(
                quantityMeasurementService.getOperationCount(operation));
    }
}
