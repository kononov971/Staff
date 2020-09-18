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

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentRepo departmentRepo;
    private EmployeeRepo employeeRepo;

    @Autowired
    public DepartmentController(DepartmentRepo departmentRepo, EmployeeRepo employeeRepo) {
        this.departmentRepo = departmentRepo;
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('staff:read')")
    public String main(Model model) {
        Iterable<Department> departments = departmentRepo.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('staff:write')")
    public String getById(@PathVariable Long id, Model model) {
        Department department =
                departmentRepo.findById(id).filter(department1 -> department1.getId().equals(id)).orElse(null);

        model.addAttribute("departments", Arrays.asList(department));

        return "departments";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('staff:write')")
    public String add(@RequestParam String name, @RequestParam String location,
                      @RequestParam String directorName, @RequestParam String higherDepartmentName, Model model) {
        Employee director = employeeRepo.findByFullName(directorName);

        Department higherDepartment = departmentRepo.findByName(higherDepartmentName);

        Department department = new Department(name, location, director, higherDepartment);
        departmentRepo.save(department);

        Iterable<Department> departments = departmentRepo.findAll();
        model.addAttribute("departments", departments);

        return "departments";
    }

    @GetMapping("filter")
    @PreAuthorize("hasAuthority('staff:read')")
    public String filter(@RequestParam String higherDepartmentName, Model model) {
        Iterable<Department> departments;
        if (!higherDepartmentName.trim().isEmpty()) {
            Department higherDepartment = departmentRepo.findByName(higherDepartmentName);
            departments = departmentRepo.findByHigherDepartment(higherDepartment);
        } else {
            departments = departmentRepo.findAll();
        }
        model.addAttribute("departments", departments);

        return "departments";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('staff:write')")
    public String remove(@RequestParam Long id, Model model) {
        departmentRepo.deleteById(id);

        Iterable<Department> departments = departmentRepo.findAll();
        model.addAttribute("departments", departments);
        return "departments";
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
            department.setDirector(director);
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
