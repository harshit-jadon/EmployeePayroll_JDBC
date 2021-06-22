package com.bridgelabz.employeepayrolljdbctest;

import com.bridgelabz.employeepayrolljdbc.entity.EmployeeDetails;
import com.bridgelabz.employeepayrolljdbc.service.EmployeeDB;
import com.bridgelabz.employeepayrolljdbc.service.EmployeePayroll;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EmployeeDBTest {
    @Test
    public void givenMySqlDatabase_WhenRetrieveDataFromDB_ShouldReturnListOfEmployees(){
        EmployeePayroll employeePayroll = new EmployeePayroll();
        List<EmployeeDetails> employeeDetailsList = employeePayroll.readData();
        Assert.assertEquals(3,employeeDetailsList.size());
    }
    @Test
    public void givenMySqlDatabase_WhenUpdateTheSalary_shouldReturnUpdatedSalary() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        List<EmployeeDetails> employeeDetailsList = employeePayroll.readData();
        employeePayroll.updateDB("Terisa",400000.00);
        Assert.assertEquals(3,employeeDetailsList.size());
    }
    @Test
    public void givenMySqlDatabase_WhenUpdateTheSalary_shouldReturnUpdatedSalaryByPreparedStatement() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        List<EmployeeDetails> employeeDetailsList = employeePayroll.readData();
        employeePayroll.updateDBPrepared("Terisa",500000.00);
        Assert.assertEquals(3,employeeDetailsList.size());
    }
    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
        EmployeeDB employeeDB = new EmployeeDB();
        List<EmployeeDetails> employList = employeeDB.readEmployeePayrollData();
        employeeDB.updateEmployeeSalary("Harshit", 456123.00);
        boolean result = employeeDB.checkEmployeePayrollSyncWithDb("Harshit");
        Assert.assertTrue(result);
    }
}
