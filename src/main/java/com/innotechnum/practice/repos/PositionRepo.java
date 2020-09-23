package com.innotechnum.practice.repos;

import com.innotechnum.practice.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionRepo extends JpaRepository<Position, Long> {
    Position findByName(String name);
}
