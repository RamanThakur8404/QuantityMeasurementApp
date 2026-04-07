package com.app.adminservice.client;

import com.app.adminservice.dto.MeasurementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Feign client to communicate with Measurement Service.
 */
@FeignClient(name = "measurement-service")
public interface MeasurementServiceClient {

    @GetMapping("/api/v1/quantities/history/errored")
    List<MeasurementDTO> getErrorHistory();

    @GetMapping("/api/v1/quantities/history/operation/{operation}")
    List<MeasurementDTO> getHistoryByOperation(@PathVariable("operation") String operation);

    @GetMapping("/api/v1/quantities/history/type/{measurementType}")
    List<MeasurementDTO> getHistoryByMeasurementType(@PathVariable("measurementType") String measurementType);

    @GetMapping("/api/v1/quantities/count/{operation}")
    Long getOperationCount(@PathVariable("operation") String operation);
}
