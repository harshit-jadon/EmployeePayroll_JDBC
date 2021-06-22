package com.bridgelabz.employeepayrolljdbc.service;

import com.bridgelabz.employeepayrolljdbc.entity.EmployeeDetails;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeeDB {
    private List<EmployeeDetails> employeeDBList;
    private  EmployeePayroll employeePayroll;

    public EmployeeDB() {
        employeePayroll = EmployeePayroll.getInstance();
    }
    public EmployeeDB(List<EmployeeDetails> employeeDBList) {
        this();
        this.employeeDBList = employeeDBList;
    }
    public List<EmployeeDetails> readEmployeePayrollData() {
        this.employeeDBList = employeePayroll.readData();
        return employeeDBList;
    }
    public boolean checkEmployeePayrollSyncWithDb(String name) {
        List<EmployeeDetails> employeeDetailsList = employeePayroll.getEmployeeData(name);
        return employeeDetailsList.get(0).equals(getEmployeePayrollData(name));
    }

    public void updateEmployeeSalary(String name, double salary) {
        int result = employeePayroll.updateDB(name, salary);
        if (result == 0) return;
        EmployeeDetails employeeDetails = this.getEmployeePayrollData(name);
        if (employeeDetails != null) employeeDetails.salary = salary;
    }

    private EmployeeDetails getEmployeePayrollData(String name) {
        return this.employeeDBList.stream()
                .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
                .findFirst()
                .orElse(null);
    }
    public List<EmployeeDetails> getEmployListInGivenDateRange(LocalDate startDate, LocalDate endDate) {
        return employeePayroll.getEmployeeDataForDateRange(startDate, endDate);
    }

    public Map<String, Double> readAverageSalaryByGender() {
        return employeePayroll.getAverageSalaryByGender();
    }

    public void addEmployeeToPayroll(String name, double salary, LocalDate startDate, String gender) throws SQLException {
        employeeDBList = this.readEmployeePayrollData();
        int id = employeePayroll.addNewEmployeeToDB(name,salary,startDate,gender);
        if(id != -1)
            this.employeeDBList.add(new EmployeeDetails(id,name,salary,startDate,gender));
    }
}
