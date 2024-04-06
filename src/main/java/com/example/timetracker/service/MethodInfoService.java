package com.example.timetracker.service;

import com.example.timetracker.model.MethodInfo;

import java.util.Optional;

public interface MethodInfoService {
    Optional<MethodInfo> getMethodInfoBySignatureLongName(String signatureLongName);

    void saveMethodInfo(MethodInfo methodInfo);
}
