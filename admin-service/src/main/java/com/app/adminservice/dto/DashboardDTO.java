package com.app.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Aggregated dashboard statistics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

    private long totalUsers;
    private long totalErrors;
    private Map<String, Long> operationCounts;
}
