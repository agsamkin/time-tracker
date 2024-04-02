package com.example.dz1.service.impl;

import com.example.dz1.dto.MethodExecutionTimeDto;
import com.example.dz1.model.MethodExecutionTime;
import com.example.dz1.repository.MethodExecutionTimeRepository;
import com.example.dz1.service.MethodExecutionTimeService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class MethodExecutionTimeServiceImpl implements MethodExecutionTimeService {

    private final MethodExecutionTimeRepository methodExecutionTimeRepository;

    @Override
    public void saveExecutionTime(MethodExecutionTimeDto methodExecutionTimeDto) {
        MethodExecutionTime methodExecutionTime = MethodExecutionTime.builder()
                .executionTime(methodExecutionTimeDto.getExecutionTime())
                .className(methodExecutionTimeDto.getClassName())
                .methodName(methodExecutionTimeDto.getMethodName())
                .methodArgs(methodExecutionTimeDto.getMethodArgs()).build();
        methodExecutionTimeRepository.save(methodExecutionTime);
    }

    @Async
    @Override
    public void saveExecutionTimeAsync(MethodExecutionTimeDto methodExecutionTimeDto) {
        MethodExecutionTime methodExecutionTime = MethodExecutionTime.builder()
                .executionTime(methodExecutionTimeDto.getExecutionTime())
                .className(methodExecutionTimeDto.getClassName())
                .methodName(methodExecutionTimeDto.getMethodName())
                .methodArgs(methodExecutionTimeDto.getMethodArgs()).build();
        methodExecutionTimeRepository.save(methodExecutionTime);
    }

    @Override
    public List<MethodExecutionTimeDto> getLogMethodExecutionTime(Date from, Date to, PageRequest of) {
        return methodExecutionTimeRepository.findAllByCreatedAtBetween(from, to, of)
                .stream()
                .map(
                        e -> MethodExecutionTimeDto.builder()
                                .className(e.getClassName())
                                .methodName(e.getMethodName())
                                .methodArgs(e.getMethodArgs())
                                .executionTime(e.getExecutionTime())
                                .createdAt(e.getCreatedAt()).build()
                )
                .sorted(Comparator.comparing(MethodExecutionTimeDto::getCreatedAt))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> getTotalMethodExecutionTime(Date from, Date to) {
        return methodExecutionTimeRepository.findAllByCreatedAtBetween(from, to)
                .stream()
                .collect(
                        Collectors.groupingBy(
                                e -> e.getMethodName(), Collectors.summingLong(e -> e.getExecutionTime())
                        )
                )
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
    }

    @Override
    public Map<String, Double> getAverageMethodExecutionTime(Date from, Date to) {
        return methodExecutionTimeRepository.findAllByCreatedAtBetween(from, to)
                .stream()
                .collect(
                        Collectors.groupingBy(
                                e -> e.getMethodName(), Collectors.averagingLong(e -> e.getExecutionTime())
                        )
                )
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
    }

}
