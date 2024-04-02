package com.example.dz1.aspect;

import com.example.dz1.dto.MethodExecutionTimeDto;
import com.example.dz1.service.MethodExecutionTimeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class TrackAsyncTimeAspect {

    private final MethodExecutionTimeService methodExecutionTimeService;

    @Pointcut("@annotation(com.example.dz1.annotation.TrackAsyncTime)")
    public void trackAsyncTimePointcut() {
    }

    @Around("trackAsyncTimePointcut()")
    public Object trackAsyncTimeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
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
        methodExecutionTimeService.saveExecutionTimeAsync(methodExecutionTimeDto);

        return result;
    }

}
