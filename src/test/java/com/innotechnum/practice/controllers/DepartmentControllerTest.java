package com.innotechnum.practice.controllers;

import com.innotechnum.practice.domain.Department;
import com.innotechnum.practice.repos.DepartmentRepo;
import com.innotechnum.practice.repos.EmployeeRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.mockito.Mockito.*;

public class DepartmentControllerTest {

    DepartmentRepo departmentRepo = mock(DepartmentRepo.class);
    EmployeeRepo employeeRepo = mock(EmployeeRepo.class);

    DepartmentController departmentController = new DepartmentController(departmentRepo, employeeRepo);


    @Test
    void get() {
       Department department1 = mock(Department.class);
       Department department2 = mock(Department.class);

        doReturn(Arrays.asList(department1, department2)).when(departmentRepo).findAll();
        List<Department> departments = StreamSupport.stream(departmentController.get().spliterator(), false)
                .collect(Collectors.toList());

        Assert.assertTrue(departments.contains(department1));
        Assert.assertTrue(departments.contains(department2));

        verify(departmentRepo, times(1)).findAll();
        verifyNoMoreInteractions(departmentRepo);
    }

    @Test
    void getById() {
        Department department = mock(Department.class);
        doReturn(Optional.of(department)).when(departmentRepo).findById(0L);

        Assert.assertEquals(department, departmentController.getById(0L));
        Assert.assertNotEquals(Optional.of(department), departmentController.getById(2L));

        verify(departmentRepo, times(2)).findById(any());
        verify(departmentRepo, times(1)).findById(0L);
        verify(departmentRepo, times(1)).findById(2L);
        verifyNoMoreInteractions(departmentRepo);
    }

    @Test
    void add() {
        departmentController.add("Бухгалтерия", "г.Москва Гагарина 7", "Петр Петров", "Отчетность");
        verify(employeeRepo, times(1)).findByFullName("Петр Петров");
        verify(departmentRepo, times(1)).findByName("Отчетность");
        verify(departmentRepo, times(1)).save(any());
        verifyNoMoreInteractions(departmentRepo);
        verifyNoMoreInteractions(employeeRepo);
    }

    @Test
    void filter() {
        Department department1 = new Department("Бухгалтерия", "Москва", null, null);
        Department department2 = new Department("Маркетинг", "Севастополь", null, department1);

        List<Department> departments = Arrays.asList(department1, department2);

        doReturn(department1).when(departmentRepo).findByName("Бухгалтерия");
        doReturn(Arrays.asList(department2)).when(departmentRepo).findByHigherDepartment(department1);

        List<Department> filterDepartments =
                StreamSupport.stream(departmentController.filter("Бухгалтерия").spliterator(), false)
                .collect(Collectors.toList());

        Assert.assertFalse(filterDepartments.contains(department1));
        Assert.assertTrue(filterDepartments.contains(department2));

        verify(departmentRepo, times(1)).findByName("Бухгалтерия");
        verify(departmentRepo, times(1)).findByHigherDepartment(department1);
        verify(departmentRepo, never()).findAll();
        verifyNoMoreInteractions(departmentRepo);

        departmentController.filter("");

        verify(departmentRepo, never()).findByName("");
        verify(departmentRepo, times(1)).findByHigherDepartment(any());
        verify(departmentRepo, times(1)).findAll();
        verifyNoMoreInteractions(departmentRepo);
    }
}

