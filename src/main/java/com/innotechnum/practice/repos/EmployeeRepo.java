package com.innotechnum.practice.repos;

import com.innotechnum.practice.domain.Employee;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepo extends CrudRepository<Employee, Long> {

    Employee findByFullName(String fullName);
    List<Employee> findByBirthDateBetween(LocalDate date1, LocalDate date2);
    List<Employee> findBySalaryAfter(BigDecimal salary);

}
