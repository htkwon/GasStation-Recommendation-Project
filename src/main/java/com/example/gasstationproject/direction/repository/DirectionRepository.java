package com.example.gasstationproject.direction.repository;

import com.example.gasstationproject.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction,Long> {
}
