package com.example.timetracker.service.impl;

import com.example.timetracker.annotation.TrackAsyncTime;
import com.example.timetracker.annotation.TrackTime;
import com.example.timetracker.service.TestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @TrackAsyncTime
    @Override
    public void test1(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @TrackAsyncTime
    @Override
    public List<Integer> test4(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return List.of(1,2,3);
    }

    @TrackTime
    @Override
    public void test3(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @TrackTime
    @Override
    public void test2(long time) {
//        try {
//            Thread.sleep(time);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        test3(time);
    }

}


