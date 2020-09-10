package com.innotechnum.practice.controllers;

import com.innotechnum.practice.domain.Department;
import com.innotechnum.practice.domain.Education;
import com.innotechnum.practice.domain.Employee;
import com.innotechnum.practice.domain.Position;
import com.innotechnum.practice.repos.DepartmentRepo;
import com.innotechnum.practice.repos.EducationRepo;
import com.innotechnum.practice.repos.EmployeeRepo;
import com.innotechnum.practice.repos.PositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private EducationRepo educationRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private PositionRepo positionRepo;

    @GetMapping
    public String main(Model model) {
        Iterable<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @PostMapping
    public String add(@RequestParam String fullName,
                      @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
                      @RequestParam String educationName, @RequestParam String departmentName,
                      @RequestParam String positionName, @RequestParam String salary, @RequestParam String telephone,
                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfAppointment,
                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfDismissals,
                      Model model) {
        Education education = educationRepo.findByName(educationName);
        Department department = departmentRepo.findByName(departmentName);
        Position position = positionRepo.findByName(positionName);

        educationRepo.save(education);
        Employee employee = new Employee(fullName, birthDate, education, department, position,
                new BigDecimal(salary), telephone, dateOfAppointment, dateOfDismissals);
        employeeRepo.save(employee);

        Iterable<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);

        return "employees";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter,
                         Model model) {
        Iterable<Employee> employees;
        if (filter.trim().isEmpty()) {
            employees = employeeRepo.findAll();
        } else {
            employees = employeeRepo.findBySalaryAfter(new BigDecimal(filter));
        }
        model.addAttribute("employees", employees);

        return "employees";
    }

    @PostMapping("remove")
    public String remove(@RequestParam Long id, Model model) {
        employeeRepo.deleteById(id);

        Iterable<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @PostMapping("dismissals")
    public String dismissals(@RequestParam Long id,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfDismissals,
                             Model model) {
        Employee employee = employeeRepo.findById(id).get();
        if(!(dateOfDismissals == null)) {
            employee.setDateOfDismissals(dateOfDismissals);
        } else {
            employee.setDateOfDismissals(LocalDate.now());
        }
        employeeRepo.save(employee);

        Iterable<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @PostMapping("transfer")
    public String dismissals(@RequestParam Long id, @RequestParam String departmentName, Model model) {
        Employee employee = employeeRepo.findById(id).get();
        Department department = departmentRepo.findByName(departmentName);
        if(!(department == null) && !(employee.getDepartment().equals(department))) {
            employee.setDepartment(department);
            employeeRepo.save(employee);
        } else {
            model.addAttribute("message", "Введено некорректное название отдела");
        }

        Iterable<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @PostMapping("correction")
    public String correction(@RequestParam Long id, @RequestParam String fullName,
                             @RequestParam String educationName,
                             @RequestParam String positionName, @RequestParam String salary,
                             @RequestParam String telephone, Model model){
        Employee employee = employeeRepo.findById(id).get();
        if (!fullName.trim().isEmpty()) {
            employee.setFullName(fullName);
        }

        if (!educationName.trim().isEmpty()) {
            Education education = educationRepo.findByName(educationName);
            employee.setEducation(education);
        }

        if (!positionName.trim().isEmpty()) {
            Position position = positionRepo.findByName(positionName);
            employee.setPosition(position);
        }

        if (!salary.trim().isEmpty()) {
            try {
                employee.setSalary(new BigDecimal(salary));
            } catch(NumberFormatException e) {
                model.addAttribute("Некорректная зарплата");
            }
        }

        if (!telephone.trim().isEmpty()) {
            employee.setTelephone(telephone);
        }

        employeeRepo.save(employee);

        Iterable<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @PostMapping("filter2")
    public String filter2(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date1,
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date2,
                          @RequestParam String salary1, @RequestParam String salary2,
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate, Model model) {
        if (date1 == null) {
            date1 = LocalDate.of(1900, 1, 1);
        }

        if(date2 == null) {
            date2 = LocalDate.now();
        }

        if(salary1.trim().isEmpty()) {
            salary1 = "0";
        }

        if(salary2.trim().isEmpty()) {
            salary2 = "999999999";

        }

        if (birthDate == null) {
            birthDate = LocalDate.of(1900, 1, 1);
        }

        Iterable<Employee> employees = employeeRepo.findByDateOfAppointmentBetweenAndSalaryBetweenAndBirthDateAfter(date1,
                date2, new BigDecimal(salary1), new BigDecimal(salary2), birthDate);
        model.addAttribute("employees", employees);
        return "employees";
    }
}
