package com.example.timetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MethodInfoDto {
    private String className;
    private String methodName;
    private String signatureShortName;
    private String signatureLongName;
}
