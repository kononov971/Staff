package com.innotechnum.practice.domain;

import javax.persistence.*;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
   @ManyToOne
   @JoinColumn(name = "director_id", nullable = true)
    private Employee director;
    @ManyToOne
    @JoinColumn(name = "higher_department", nullable = true)
    private Department higherDepartment;

    public Department(String name, String location, Employee director, Department higherDepartment) {
        this.name = name;
        this.location = location;
        this.director = director;
        this.higherDepartment = higherDepartment;
    }

    public Department() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Employee getDirector() {
        return director;
    }

    public void setDirector(Employee director) {
        this.director = director;
    }

    public Department getHigherDepartment() {
        return higherDepartment;
    }

    public void setHigherDepartment(Department higherDepartment) {
        this.higherDepartment = higherDepartment;
    }

    @Override
    public String toString() {
        return name;
    }
}
