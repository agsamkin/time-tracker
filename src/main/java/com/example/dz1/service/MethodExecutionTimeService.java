package com.example.dz1.service;

import com.example.dz1.dto.MethodExecutionTimeDto;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface MethodExecutionTimeService {
    void saveExecutionTime(MethodExecutionTimeDto measurementDto);
    void saveExecutionTimeAsync(MethodExecutionTimeDto measurementDto);

    List<MethodExecutionTimeDto> getLogMethodExecutionTime(Date from, Date to, PageRequest of);
    Map<String, Long> getTotalMethodExecutionTime(Date from, Date to);
    Map<String, Double> getAverageMethodExecutionTime(Date from, Date to);
}

