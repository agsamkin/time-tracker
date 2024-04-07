package com.example.timetracker.service.impl;

import com.example.timetracker.dto.MethodInfoDto;
import com.example.timetracker.dto.TimeMeasurementDto;
import com.example.timetracker.model.MethodInfo;
import com.example.timetracker.model.TimeMeasurement;
import com.example.timetracker.repository.TimeMeasurementRepository;
import com.example.timetracker.service.MethodInfoService;
import com.example.timetracker.service.TimeMeasurementService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class TimeMeasurementServiceImpl implements TimeMeasurementService {

    private final MethodInfoService methodInfoService;
    private final TimeMeasurementRepository timeMeasurementRepository;

    public static final int PAGE_DEFAULT = 0;
    public static final int PAGE_SIZE_DEFAULT = 10;

    public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;
    public static final String DEFAULT_SORT_PROPERTY = "createdAt";

    @Override
    public void saveExecutionTime(TimeMeasurement timeMeasurement) {
        MethodInfo methodInfo =
               methodInfoService.getMethodInfoBySignatureLongName(
                       timeMeasurement.getMethodInfo().getSignatureLongName())
                        .orElse(timeMeasurement.getMethodInfo());

        methodInfo.addTimeMeasurement(timeMeasurement);
        methodInfoService.saveMethodInfo(methodInfo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TimeMeasurementDto> getLogMethodExecutionTime(
            LocalDateTime from, LocalDateTime to,
            Optional<Integer> page, Optional<Integer> size, Optional<String> sort) {

        PageRequest of = PageRequest.of(
                page.isEmpty() ? PAGE_DEFAULT : page.get(),
                size.isEmpty() ? PAGE_SIZE_DEFAULT : size.get(),
                Sort.by(
                        sort.isEmpty() ? DEFAULT_SORT_DIRECTION :
                                Sort.Direction.valueOf(sort.get().toUpperCase(Locale.ROOT)), DEFAULT_SORT_PROPERTY));

        return timeMeasurementRepository.findAllByCreatedAtBetween(from, to, of)
                .stream()
                .map(this::mapToDto)
                .sorted(Comparator.comparing(TimeMeasurementDto::getCreatedAt))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Long> getTotalMethodExecutionTime(LocalDateTime from, LocalDateTime to) {
        return timeMeasurementRepository.findAllByCreatedAtBetween(from, to)
                .stream()
                .collect(Collectors.groupingBy(
                        e -> e.getMethodInfo().getSignatureShortName(),
                        Collectors.summingLong(TimeMeasurement::getExecutionTime)))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Double> getAverageMethodExecutionTime(LocalDateTime from, LocalDateTime to) {
        return timeMeasurementRepository.findAllByCreatedAtBetween(from, to)
                .stream()
                .collect(Collectors.groupingBy(
                        e -> e.getMethodInfo().getSignatureShortName(),
                        Collectors.averagingLong(TimeMeasurement::getExecutionTime)))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
    }

    private TimeMeasurementDto mapToDto(TimeMeasurement timeMeasurement) {
        return TimeMeasurementDto.builder()
                .methodInfo(mapToDto(timeMeasurement.getMethodInfo()))
                .executionTime(timeMeasurement.getExecutionTime())
                .createdAt(timeMeasurement.getCreatedAt()).build();
    }

    private MethodInfoDto mapToDto(MethodInfo methodInfo) {
        return MethodInfoDto.builder()
                .className(methodInfo.getClassName())
                .methodName(methodInfo.getMethodName())
                .signatureShortName(methodInfo.getSignatureShortName())
                .signatureLongName(methodInfo.getSignatureLongName()).build();
    }

}
