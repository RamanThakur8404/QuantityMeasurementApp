package com.app.measurementservice.repository;

import com.app.measurementservice.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(String operation);
    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);
    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT q FROM QuantityMeasurementEntity q " +
           "WHERE q.operation = :operation AND q.error = false")
    List<QuantityMeasurementEntity> findSuccessfulByOperation(
            @Param("operation") String operation);

    long countByOperationAndErrorFalse(String operation);
    List<QuantityMeasurementEntity> findByErrorTrue();
}
