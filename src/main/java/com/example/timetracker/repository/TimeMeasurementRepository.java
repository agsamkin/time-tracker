package com.example.timetracker.repository;

import com.example.timetracker.model.TimeMeasurement;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeMeasurementRepository extends JpaRepository<TimeMeasurement, Long> {
    @EntityGraph(attributePaths = "methodInfo")
    List<TimeMeasurement> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @EntityGraph(attributePaths = "methodInfo")
    List<TimeMeasurement> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to, PageRequest of);
}
