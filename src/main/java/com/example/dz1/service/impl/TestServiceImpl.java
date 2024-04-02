package com.example.dz1.service.impl;

import com.example.dz1.annotation.TrackAsyncTime;
import com.example.dz1.annotation.TrackTime;
import com.example.dz1.service.TestService;
import org.springframework.stereotype.Service;

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

    @TrackTime
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


