package com.example.dz1.aspect;

import com.example.dz1.dto.MethodExecutionTimeDto;
import com.example.dz1.service.MethodExecutionTimeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class TrackTimeAspect {

    private final MethodExecutionTimeService methodExecutionTimeService;

    @Pointcut("@annotation(com.example.dz1.annotation.TrackTime)")
    public void trackTimePointcut() {
    }

    @Around("trackTimePointcut()")
    public Object trackTimeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long end = System.currentTimeMillis();

        long executionTime = end - start;

        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] methodArgs = proceedingJoinPoint.getArgs();

        MethodExecutionTimeDto methodExecutionTimeDto = MethodExecutionTimeDto.builder()
                .executionTime(executionTime)
                .className(className)
                .methodName(methodName)
                .methodArgs(Arrays.toString(methodArgs)).build();

        methodExecutionTimeService.saveExecutionTime(methodExecutionTimeDto);

        return result;
    }

}
