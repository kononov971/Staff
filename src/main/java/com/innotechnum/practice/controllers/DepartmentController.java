package com.innotechnum.practice.controllers;


import com.innotechnum.practice.domain.Department;
import com.innotechnum.practice.domain.Employee;
import com.innotechnum.practice.repos.DepartmentRepo;
import com.innotechnum.practice.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    public DepartmentRepo departmentRepo;
    private EmployeeRepo employeeRepo;

    @Autowired
    public DepartmentController(DepartmentRepo departmentRepo, EmployeeRepo employeeRepo) {
        this.departmentRepo = departmentRepo;
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('staff:read')")
    public Iterable<Department> get() {
        Iterable<Department> departments = departmentRepo.findAll();

        return departments;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('staff:write')")
    public Department getById(@PathVariable Long id) {
        Department department =
                departmentRepo.findById(id).filter(department1 -> department1.getId().equals(id)).orElse(null);

        return department;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('staff:write')")
    public Department add(@RequestParam String name, @RequestParam String location,
                      @RequestParam String directorName, @RequestParam String higherDepartmentName) {
        Employee director = employeeRepo.findByFullName(directorName);

        Department higherDepartment = departmentRepo.findByName(higherDepartmentName);

        Department department = new Department(name, location, director, higherDepartment);
        departmentRepo.save(department);

        return department;
    }

    @GetMapping("filter")
    @PreAuthorize("hasAuthority('staff:read')")
    public Iterable<Department> filter(@RequestParam String higherDepartmentName) {
        Iterable<Department> departments;
        if (!higherDepartmentName.trim().isEmpty()) {
            Department higherDepartment = departmentRepo.findByName(higherDepartmentName);
            departments = departmentRepo.findByHigherDepartment(higherDepartment);
        } else {
            departments = departmentRepo.findAll();
        }

        return departments;
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('staff:write')")
    public Iterable<Department> remove(@RequestParam Long id) {
        departmentRepo.deleteById(id);

        Iterable<Department> departments = departmentRepo.findAll();
        return departments;
    }

    @PutMapping("director")
    @PreAuthorize("hasAuthority('staff:write')")
    public String director(@RequestParam Long departmentId, @RequestParam Long directorId, Model model) {
        Employee director =
                employeeRepo.findById(directorId).filter(department1 -> department1.getId()
                        .equals(directorId)).orElse(null);
        Department department =
                departmentRepo.findById(departmentId).filter(department1 -> department1.getId()
                        .equals(departmentId)).orElse(null);

        if (director != null) {
          //  department.setDirector(director);
        } else {
            model.addAttribute("message", "некорректный id начальника");
        }

        departmentRepo.save(department);

        Iterable<Department> departments = departmentRepo.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @PutMapping("higherDepartment")
    @PreAuthorize("hasAuthority('staff:write')")
    public String higherDepartment(@RequestParam Long departmentId, @RequestParam Long higherDepartmentId,
                                   Model model) {
        Department higherDepartment =
                departmentRepo.findById(higherDepartmentId).filter(department1 -> department1.getId()
                        .equals(higherDepartmentId)).orElse(null);
        Department department =
                departmentRepo.findById(departmentId).filter(department1 -> department1.getId()
                        .equals(departmentId)).orElse(null);

        if (higherDepartment != null) {
            department.setHigherDepartment(higherDepartment);
        } else {
            model.addAttribute("message", "некорректный id вышестоящего отдела");
        }

        departmentRepo.save(department);

        Iterable<Department> departments = departmentRepo.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('staff:write')")
    public String correction(@RequestParam Long id, @RequestParam String name, @RequestParam String location,
                             Model model) {
        Department department =
                departmentRepo.findById(id).filter(department1 -> department1.getId().equals(id)).orElse(null);

        if (!name.trim().isEmpty()) {
            department.setName(name);
        }

        if (!location.trim().isEmpty()) {
            department.setLocation(location);
        }

        departmentRepo.save(department);

        Iterable<Department> departments = departmentRepo.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }

}
