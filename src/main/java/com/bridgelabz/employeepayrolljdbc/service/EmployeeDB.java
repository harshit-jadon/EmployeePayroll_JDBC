package com.bridgelabz.employeepayrolljdbc.service;

import com.bridgelabz.employeepayrolljdbc.entity.EmployeeDetails;

import java.util.List;

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
}
