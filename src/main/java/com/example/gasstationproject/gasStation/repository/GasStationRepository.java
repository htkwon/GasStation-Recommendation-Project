package com.example.gasstationproject.gasStation.repository;

import com.example.gasstationproject.gasStation.entity.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GasStationRepository extends JpaRepository<GasStation,Long> {
}
