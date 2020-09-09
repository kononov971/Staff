package com.innotechnum.practice.repos;

import com.innotechnum.practice.domain.Education;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EducationRepo extends CrudRepository<Education, Long> {
    Education findByName(String name);


}
