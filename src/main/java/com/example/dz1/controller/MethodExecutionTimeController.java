package com.example.dz1.controller;


import com.example.dz1.dto.MethodExecutionTimeDto;
import com.example.dz1.service.MethodExecutionTimeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.example.dz1.controller.MethodExecutionTimeController.METHOD_EXECUTION_TIME_CONTROLLER_PATH;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("${base-url}" + METHOD_EXECUTION_TIME_CONTROLLER_PATH)
public class MethodExecutionTimeController {

    public static final String METHOD_EXECUTION_TIME_CONTROLLER_PATH = "/statistics";

    public static final int PAGE_DEFAULT = 0;
    public static final int PAGE_SIZE_DEFAULT = 10;

    public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;
    public static final String DEFAULT_SORT_PROPERTY = "createdAt";

    private final MethodExecutionTimeService methodExecutionTimeService;

    @GetMapping("/log")
    public List<MethodExecutionTimeDto> getAllMethodExecutionTime(
            @RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam(value ="to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @RequestParam(value = "page", required = false) Optional<Integer> page,
            @RequestParam(value = "size", required = false) Optional<Integer> size,
            @RequestParam(value = "sort", required = false) Optional<
                    @Pattern(regexp = "asc|desc",
                    flags = Pattern.Flag.CASE_INSENSITIVE,
                    message = "The sort parameter can take one of two values: asc or desc") String> sort) {

        PageRequest of = PageRequest.of(
                page.isEmpty() ? PAGE_DEFAULT : page.get(),
                size.isEmpty() ? PAGE_SIZE_DEFAULT : size.get(),
                Sort.by(
                        sort.isEmpty() ? DEFAULT_SORT_DIRECTION : Sort.Direction.valueOf(sort.get().toUpperCase(Locale.ROOT)),
                        DEFAULT_SORT_PROPERTY));
        return methodExecutionTimeService.getLogMethodExecutionTime(from, to, of);
    }

    @GetMapping("/total")
    public Map<String, Long> getTotalMethodExecutionTime(
            @RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam(value = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to) {

        return methodExecutionTimeService.getTotalMethodExecutionTime(from, to);
    }

    @GetMapping("/average")
    public Map<String, Double> getAverageMethodExecutionTime(
            @RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam(value ="to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to) {

        return methodExecutionTimeService.getAverageMethodExecutionTime(from, to);
    }

}
