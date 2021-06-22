package com.bridgelabz.employeepayrolljdbc.entity;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeeDetails {
    public int id;
    public String name;
    public double salary;
    public LocalDate startDate;
    public String gender;

    public EmployeeDetails(int id, String name, double salary, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }
    public EmployeeDetails(int id, String name, double salary, LocalDate startDate, String gender) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Employ{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDetails employ = (EmployeeDetails) o;
        return id == employ.id &&
                Double.compare(employ.salary, salary) == 0 && Objects.equals(name, employ.name) &&
                Objects.equals(startDate, employ.startDate);
    }
}
