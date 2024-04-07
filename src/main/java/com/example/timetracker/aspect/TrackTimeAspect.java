package com.example.timetracker.aspect;

import com.example.timetracker.model.MethodInfo;
import com.example.timetracker.model.TimeMeasurement;
import com.example.timetracker.service.MethodInfoService;
import com.example.timetracker.service.TimeMeasurementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class TrackTimeAspect {

    private final TimeMeasurementService timeMeasurementService;

    @Pointcut("@annotation(com.example.timetracker.annotation.TrackTime)")
    public void trackTimePointcut() {
    }

    @Pointcut("@annotation(com.example.timetracker.annotation.TrackAsyncTime)")
    public void trackAsyncTimePointcut() {
    }

    @Around("trackTimePointcut()")
    public Object aroundTrackTimeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.nanoTime();
        Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long end = System.nanoTime();

        long executionTime = end - start;

        Signature signature = proceedingJoinPoint.getSignature();

        MethodInfo methodInfo = MethodInfo.builder()
                .className(signature.getDeclaringTypeName())
                .methodName(signature.getName())
                .signatureShortName(signature.toShortString())
                .signatureLongName(signature.toLongString()).build();

        TimeMeasurement timeMeasurement = TimeMeasurement.builder()
                .methodInfo(methodInfo).executionTime(executionTime).build();

        try {
            timeMeasurementService.saveExecutionTime(timeMeasurement);
        } catch (Throwable e) {
            log.error("The time measurement could not be saved due to:", e);
        }

        return result;
    }

    @Around("trackAsyncTimePointcut()")
    public Object aroundTrackAsyncTimeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var handler = new CompletableFutureHandler();

        CompletableFuture.supplyAsync(() -> {
            try {
                handler.setResult(aroundTrackTimeAdvice(proceedingJoinPoint));
            } catch (Throwable e) {
                handler.setException(e);
            }
            return handler;
        }).get();

        if (Objects.nonNull(handler.getException())) {
            throw handler.getException();
        } else {
            return handler.getResult();
        }
    }

}
