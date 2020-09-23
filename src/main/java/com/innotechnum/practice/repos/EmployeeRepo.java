package com.innotechnum.practice.repos;

import com.innotechnum.practice.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;



public interface EmployeeRepo extends CrudRepository<Employee, Long> {

    Employee findByFullName(String fullName);
    List<Employee> findByBirthDateBetween(LocalDate date1, LocalDate date2);
    List<Employee> findBySalaryAfter(BigDecimal salary);
    List<Employee> findByDateOfAppointmentBetweenAndSalaryBetweenAndBirthDateAfter(LocalDate date1, LocalDate date2,
                                                                           BigDecimal salary1, BigDecimal salary2,
                                                                           LocalDate birthDate);
    @Query("SELECT e FROM Employee e WHERE ((e.dateOfAppointment BETWEEN ?1 AND ?2) AND (e.salary BETWEEN " +
            "?3 AND ?4) AND e.birthDate > ?5)")
    List<Employee> findByDateOfAppointmentBetweenAndSalaryBetweenAndBirthDateAfter2(LocalDate date1, LocalDate date2,
                                                                                    BigDecimal salary1, BigDecimal salary2,
                                                                                    LocalDate birthDate);

//            (@Param("date1") LocalDate date1,
//                                                                                    @Param("date2") LocalDate date2,
//                                                                                    @Param("salary1") BigDecimal salary1,
//                                                                                    @Param("salary2") BigDecimal salary2,
//                                                                                    @Param("birthDate") LocalDate birthDate);

}
