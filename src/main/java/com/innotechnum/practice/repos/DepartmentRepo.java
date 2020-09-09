package com.innotechnum.practice.repos;

import com.innotechnum.practice.domain.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DepartmentRepo extends CrudRepository<Department, Long> {
    Department findByName(String name);
    List<Department> findByHigherDepartment(Department higherDepartment);
}
