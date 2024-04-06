package com.example.timetracker.service;

import com.example.timetracker.dto.TimeMeasurementDto;
import com.example.timetracker.model.TimeMeasurement;

import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TimeMeasurementService {
    void saveExecutionTime(TimeMeasurement measurement);

    List<TimeMeasurementDto> getLogMethodExecutionTime(
            LocalDateTime from, LocalDateTime to,
            Optional<Integer> page, Optional<Integer> size, Optional<String> sort);

    Map<String, Long> getTotalMethodExecutionTime(LocalDateTime from, LocalDateTime to);
    Map<String, Double> getAverageMethodExecutionTime(LocalDateTime from, LocalDateTime to);
}

