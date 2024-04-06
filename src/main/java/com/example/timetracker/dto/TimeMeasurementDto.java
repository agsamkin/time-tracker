package com.example.timetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TimeMeasurementDto {
    private MethodInfoDto methodInfo;
    private Long executionTime;
    private LocalDateTime createdAt;
}
