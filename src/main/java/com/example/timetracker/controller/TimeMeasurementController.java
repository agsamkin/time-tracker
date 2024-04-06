package com.example.timetracker.controller;

import com.example.timetracker.dto.TimeMeasurementDto;
import com.example.timetracker.service.TimeMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.timetracker.controller.TimeMeasurementController.TIME_MEASUREMENT_CONTROLLER_PATH;

@Tag(name = "time-measurement-controller", description = "Time measurement controller")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("${base-url}" + TIME_MEASUREMENT_CONTROLLER_PATH)
public class TimeMeasurementController {

    public static final String TIME_MEASUREMENT_CONTROLLER_PATH = "/time-measurement";

    private final TimeMeasurementService timeMeasurementService;

    @Operation(summary = "Get log method execution time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/log")
    public List<TimeMeasurementDto> getLogMethodExecutionTime(
            @RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value ="to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(value = "page", required = false) Optional<
                    @Min(value = 0, message = "The page parameter cannot be negative") Integer> page,
            @RequestParam(value = "size", required = false) Optional<
                    @Min(value = 1, message = "The page size parameter must not be less than one") Integer> size,
            @RequestParam(value = "sort", required = false) Optional<
                    @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE,
                            message = "The sort parameter can take one of two values: asc or desc") String> sort) {

        return timeMeasurementService.getLogMethodExecutionTime(from, to, page, size, sort);
    }

    @Operation(summary = "Get total method execution time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/total")
    public Map<String, Long> getTotalMethodExecutionTime(
            @RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        return timeMeasurementService.getTotalMethodExecutionTime(from, to);
    }

    @Operation(summary = "Get average method execution time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/average")
    public Map<String, Double> getAverageMethodExecutionTime(
            @RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value ="to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        return timeMeasurementService.getAverageMethodExecutionTime(from, to);
    }

}
