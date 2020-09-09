package com.innotechnum.practice.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private LocalDate birthDate;
    @ManyToOne
    @JoinColumn(name = "education_id", nullable = false)
    private Education education;
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;
    private BigDecimal salary;
    private String telephone;
    private LocalDate dateOfAppointment;
    private LocalDate dateOfDismissals;

    public Employee(String fullName, LocalDate birthDate, Education education,
                    Department department, Position position, BigDecimal salary, String telephone,
                    LocalDate dateOfAppointment, LocalDate dateOfDismissals) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.education = education;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.telephone = telephone;
        this.dateOfAppointment = dateOfAppointment;
        this.dateOfDismissals = dateOfDismissals;
    }

    public Employee() {}

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(LocalDate dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public LocalDate getDateOfDismissals() {
        return dateOfDismissals;
    }

    public void setDateOfDismissals(LocalDate dateOfDismissals) {
        this.dateOfDismissals = dateOfDismissals;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
