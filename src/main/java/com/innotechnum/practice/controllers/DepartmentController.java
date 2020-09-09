package com.innotechnum.practice.controllers;


import com.innotechnum.practice.domain.Department;
import com.innotechnum.practice.domain.Employee;
import com.innotechnum.practice.repos.DepartmentRepo;
import com.innotechnum.practice.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping
    public String main(Model model) {
        Iterable<Department> departments = departmentRepo.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @PostMapping
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

    @PostMapping("filter")
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

    @PostMapping("remove")
    public String remove(@RequestParam Long id, Model model) {
        departmentRepo.deleteById(id);

        Iterable<Department> departments = departmentRepo.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @PostMapping("director")
    public String director(@RequestParam Long departmentId, @RequestParam Long directorId, Model model) {
        Employee director = employeeRepo.findById(directorId).get();
        Department department = departmentRepo.findById(departmentId).get();

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

    @PostMapping("higherDepartment")
    public String higherDepartment(@RequestParam Long departmentId, @RequestParam Long higherDepartmentId,
                                   Model model) {
        Department higherDepartment = departmentRepo.findById(departmentId).get();
        Department department = departmentRepo.findById(departmentId).get();

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

    @PostMapping("correction")
    public String correction(@RequestParam Long id, @RequestParam String name, @RequestParam String location,
                             Model model) {
        Department department = departmentRepo.findById(id).get();

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
