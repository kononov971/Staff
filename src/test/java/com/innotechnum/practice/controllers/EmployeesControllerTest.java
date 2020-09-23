package com.innotechnum.practice.controllers;

import com.innotechnum.practice.domain.Department;
import com.innotechnum.practice.domain.Education;
import com.innotechnum.practice.domain.Employee;
import com.innotechnum.practice.domain.Position;
import com.innotechnum.practice.repos.DepartmentRepo;
import com.innotechnum.practice.repos.EducationRepo;
import com.innotechnum.practice.repos.EmployeeRepo;
import com.innotechnum.practice.repos.PositionRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EmployeesControllerTest {

    private EmployeeRepo employeeRepo = mock(EmployeeRepo.class);

    private DepartmentRepo departmentRepo = mock(DepartmentRepo.class);

    private EducationRepo educationRepo = mock(EducationRepo.class);

    private PositionRepo positionRepo = mock(PositionRepo.class);

    private Model model = mock(Model.class);

    private EmployeesController employeesController = new EmployeesController(employeeRepo, educationRepo,
            departmentRepo, positionRepo);

    @Test
    void add() {

        employeesController.add("Petr Petrov", LocalDate.of(1991, 11, 6),
                "Высшее", "ИТ", "Менеджер", "30000", "89997776655", LocalDate.of(2019, 9, 02),
                null, model);

        verify(educationRepo, times(1)).findByName("Высшее");
        verify(departmentRepo, times(1)).findByName("ИТ");
        verify(positionRepo, times(1)).findByName("Менеджер");
        verify(employeeRepo, times(1)).save(any());
        verify(employeeRepo, times(1)).findAll();
        verifyNoMoreInteractions(educationRepo);
        verifyNoMoreInteractions(departmentRepo);
        verifyNoMoreInteractions(positionRepo);
        verifyNoMoreInteractions(employeeRepo);
    }

    @Test
    void remove() {
        employeesController.remove(1L, model);

        verify(employeeRepo, times(1)).deleteById(1L);
        verifyNoMoreInteractions(educationRepo);
    }

    @Test
    void correction() {
        Employee employee = mock(Employee.class);
        Education education = mock(Education.class);
        Position position = mock(Position.class);

        doReturn(Optional.of(employee)).when(employeeRepo).findById(0L);
        doReturn(education).when(educationRepo).findByName(any());
        doReturn(position).when(positionRepo).findByName(any());

        employeesController.correction(0L, "Ivan Petrov", "Высшее",
                "Программист", "40000", "89996547654", model);

        verify(employeeRepo, times(1)).findById(0L);
        verify(employee, times(1)).getId();
        verify(employee, times(1)).setFullName("Ivan Petrov");
        verify(educationRepo, times(1)).findByName("Высшее");
        verify(employee, times(1)).setEducation(education);
        verify(positionRepo, times(1)).findByName("Программист");
        verify(employee, times(1)).setPosition(position);
        verify(employee, times(1)).setSalary(new BigDecimal("40000"));
        verify(employee, times(1)).setTelephone("89996547654");
        verify(employeeRepo, times(1)).save(employee);
        verify(employeeRepo, times(1)).findAll();
        verifyNoMoreInteractions(employeeRepo);
        verifyNoMoreInteractions(educationRepo);
        verifyNoMoreInteractions(positionRepo);
        verifyNoMoreInteractions(employee);
        reset(employeeRepo);
        reset(educationRepo);
        reset(positionRepo);
        reset(employee);

        doReturn(Optional.of(employee)).when(employeeRepo).findById(0L);

        employeesController.correction(0L, "", "",
                "", "", "89993215467", model);

        verify(employeeRepo, times(1)).findById(0L);
        verify(employee, times(1)).getId();
        verify(employee, times(1)).setTelephone("89993215467");
        verify(employeeRepo, times(1)).save(employee);
        verify(employeeRepo, times(1)).findAll();
        verifyNoMoreInteractions(employeeRepo);
        verifyNoInteractions(educationRepo);
        verifyNoInteractions(positionRepo);
        verifyNoMoreInteractions(employee);
    }
}