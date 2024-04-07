package com.example.timetracker.aspect;

import com.example.timetracker.service.TimeMeasurementService;
import com.example.timetracker.service.impl.TimeMeasurementServiceImpl;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class TrackTimeAspectTest {

    @Test
    void aroundTrackTimeAdvice_whenProceedIsOkAndSaveExecutionTimeIsOk() throws Throwable {
        TimeMeasurementService timeMeasurementService = mock(TimeMeasurementServiceImpl.class);
        doNothing().when(timeMeasurementService).saveExecutionTime(any());

        ProceedingJoinPoint proceedingJoinPoint = mock(MethodInvocationProceedingJoinPoint.class);
        when(proceedingJoinPoint.getSignature()).thenReturn(getSignature());
        doReturn(null).when(proceedingJoinPoint).proceed(proceedingJoinPoint.getArgs());

        TrackTimeAspect trackTimeAspect = new TrackTimeAspect(timeMeasurementService);
        Object result = trackTimeAspect.aroundTrackTimeAdvice(proceedingJoinPoint);

        assertThat(result).isNull();
    }

    @Test
    void aroundTrackTimeAdvice_whenProceedThrowsException() throws Throwable {
        ProceedingJoinPoint proceedingJoinPoint = mock(MethodInvocationProceedingJoinPoint.class);
        when(proceedingJoinPoint.proceed(any())).thenThrow(new Throwable("Proceeding exception"));

        TimeMeasurementService timeMeasurementService = mock(TimeMeasurementServiceImpl.class);
        TrackTimeAspect trackTimeAspect = new TrackTimeAspect(timeMeasurementService);

        assertThatThrownBy(() ->
            trackTimeAspect.aroundTrackTimeAdvice(proceedingJoinPoint))
                .isInstanceOf(Throwable.class).hasMessage("Proceeding exception");

    }

    @Test
    void aroundTrackTimeAdvice_whenSaveExecutionTimeThrowsException() throws Throwable {
        TimeMeasurementService timeMeasurementService = mock(TimeMeasurementServiceImpl.class);
        doThrow(new RuntimeException("Save execution time exception")).when(timeMeasurementService).saveExecutionTime(any());

        ProceedingJoinPoint proceedingJoinPoint = mock(MethodInvocationProceedingJoinPoint.class);
        when(proceedingJoinPoint.getSignature()).thenReturn(getSignature());
        doReturn(null).when(proceedingJoinPoint).proceed(proceedingJoinPoint.getArgs());

        TrackTimeAspect trackTimeAspect = new TrackTimeAspect(timeMeasurementService);
        Object result = trackTimeAspect.aroundTrackTimeAdvice(proceedingJoinPoint);

        assertThat(result).isNull();
    }

    @Test
    void aroundTrackAsyncTimeAdvice_whenProceedIsOkAndSaveExecutionTimeIsOk() throws Throwable {
        TimeMeasurementService timeMeasurementService = mock(TimeMeasurementServiceImpl.class);
        doNothing().when(timeMeasurementService).saveExecutionTime(any());

        ProceedingJoinPoint proceedingJoinPoint = mock(MethodInvocationProceedingJoinPoint.class);
        when(proceedingJoinPoint.getSignature()).thenReturn(getSignature());
        doReturn(null).when(proceedingJoinPoint).proceed(proceedingJoinPoint.getArgs());

        TrackTimeAspect trackTimeAspect = new TrackTimeAspect(timeMeasurementService);
        Object result = trackTimeAspect.aroundTrackAsyncTimeAdvice(proceedingJoinPoint);

        assertThat(result).isNull();
    }

    @Test
    void aroundTrackAsyncTimeAdvice_whenProceedThrowsException() throws Throwable {
        ProceedingJoinPoint proceedingJoinPoint = mock(MethodInvocationProceedingJoinPoint.class);
        when(proceedingJoinPoint.proceed(any())).thenThrow(new Throwable("Proceeding exception"));

        TimeMeasurementService timeMeasurementService = mock(TimeMeasurementServiceImpl.class);
        TrackTimeAspect trackTimeAspect = new TrackTimeAspect(timeMeasurementService);

        assertThatThrownBy(() ->
                trackTimeAspect.aroundTrackAsyncTimeAdvice(proceedingJoinPoint))
                .isInstanceOf(Throwable.class).hasMessage("Proceeding exception");

    }

    @Test
    void aroundTrackAsyncTimeAdvice_whenSaveExecutionTimeThrowsException() throws Throwable {
        TimeMeasurementService timeMeasurementService = mock(TimeMeasurementServiceImpl.class);
        doThrow(new RuntimeException("Save execution time exception")).when(timeMeasurementService).saveExecutionTime(any());

        ProceedingJoinPoint proceedingJoinPoint = mock(MethodInvocationProceedingJoinPoint.class);
        when(proceedingJoinPoint.getSignature()).thenReturn(getSignature());
        doReturn(null).when(proceedingJoinPoint).proceed(proceedingJoinPoint.getArgs());

        TrackTimeAspect trackTimeAspect = new TrackTimeAspect(timeMeasurementService);
        Object result = trackTimeAspect.aroundTrackAsyncTimeAdvice(proceedingJoinPoint);

        assertThat(result).isNull();
    }

    private Signature getSignature() {
        return new Signature() {
            @Override
            public String toShortString() {
                return "DefaultFooBarService.foo()";
            }

            @Override
            public String toLongString() {
                return "public void com.example.timetracker.service.impl.DefaultFooBarService.foo()";
            }

            @Override
            public String getName() {
                return "foo";
            }

            @Override
            public int getModifiers() {
                return 1;
            }

            @Override
            public Class getDeclaringType() {
                return null;
            }

            @Override
            public String getDeclaringTypeName() {
                return "com.example.timetracker.service.impl.DefaultFooBarService";
            }
        };
    }

}