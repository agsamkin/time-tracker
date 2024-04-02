package com.example.dz1.controller;

import com.example.dz1.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.dz1.controller.TestController.TEST_CONTROLLER_PATH;


@RequiredArgsConstructor
@RestController
@RequestMapping("${base-url}" + TEST_CONTROLLER_PATH)
public class TestController {
    public static final String TEST_CONTROLLER_PATH = "/test";

    private final TestService testService;

    @PostMapping
    public String create(@RequestParam("async") boolean async, @RequestParam("time") long time)  {
        if (async) {
            testService.test1(time);
        } else {
            testService.test2(time);
        }
        return "OK";
    }

}
