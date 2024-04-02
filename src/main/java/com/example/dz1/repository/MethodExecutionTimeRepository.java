package com.example.dz1.repository;

import com.example.dz1.model.MethodExecutionTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MethodExecutionTimeRepository extends JpaRepository<MethodExecutionTime, Long> {
    List<MethodExecutionTime> findAllByCreatedAtBetween(Date from, Date to);
    List<MethodExecutionTime> findAllByCreatedAtBetween(Date from, Date to, PageRequest of);
}
