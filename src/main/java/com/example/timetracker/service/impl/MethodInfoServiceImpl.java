package com.example.timetracker.service.impl;

import com.example.timetracker.model.MethodInfo;
import com.example.timetracker.repository.MethodInfoRepository;
import com.example.timetracker.service.MethodInfoService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class MethodInfoServiceImpl implements MethodInfoService {

    private final MethodInfoRepository methodInfoRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<MethodInfo> getMethodInfoBySignatureLongName(String signatureLongName) {
        return methodInfoRepository.findBySignatureLongName(signatureLongName);
    }

    @Override
    public void saveMethodInfo(MethodInfo methodInfo) {
        methodInfoRepository.save(methodInfo);
    }

}
