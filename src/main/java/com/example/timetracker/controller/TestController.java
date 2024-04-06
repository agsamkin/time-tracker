package com.example.timetracker.controller;

import com.example.timetracker.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.timetracker.controller.TestController.TEST_CONTROLLER_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping("${base-url}" + TEST_CONTROLLER_PATH)
public class TestController {
    public static final String TEST_CONTROLLER_PATH = "/test";

    private final TestService testService;

    @PostMapping
    public String create(@RequestParam("async") boolean async, @RequestParam("time") long time)  {
        if (async) {
            testService.test4(time);
        } else {
            testService.test3(time);
        }

        return "OK";
    }

}
