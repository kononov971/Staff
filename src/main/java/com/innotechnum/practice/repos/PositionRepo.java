package com.innotechnum.practice.repos;

import com.innotechnum.practice.domain.Position;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionRepo extends CrudRepository<Position, Long> {
    Position findByName(String name);
}
