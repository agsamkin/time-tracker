package com.example.timetracker.repository;

import com.example.timetracker.model.MethodInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MethodInfoRepository extends JpaRepository<MethodInfo, Long> {
    Optional<MethodInfo> findBySignatureLongName(String signatureLongName);
}
