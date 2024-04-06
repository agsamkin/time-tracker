package com.example.timetracker.exception;

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
public class ErrorMessage {
    private int statusCode;
    private String timeStamp;
    private String message;
    private String description;
}
