package com.example.dz1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MethodExecutionTimeDto {
    private Long executionTime;
    private String className;
    private String methodName;
    private String methodArgs;
    private Date createdAt;
}
