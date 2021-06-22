package com.bridgelabz.employeepayrolljdbctest;

import com.bridgelabz.employeepayrolljdbc.entity.EmployeeDetails;
import com.bridgelabz.employeepayrolljdbc.service.EmployeeDB;
import com.bridgelabz.employeepayrolljdbc.service.EmployeePayroll;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeeDB employeeDB = new EmployeeDB();
        LocalDate startDate = LocalDate.of(2019, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeeDetails> employList = employeeDB.getEmployListInGivenDateRange(startDate, endDate);
        Assert.assertEquals(2, employList.size());
    }
    @Test
    public void givenPayrollDataWhenAverageSalaryRetrievedByGenderShouldReturnProperValue() {
        EmployeeDB employeeDB = new EmployeeDB();
        Map<String, Double> averageSalaryByGender = employeeDB.readAverageSalaryByGender();
        Assert.assertTrue(averageSalaryByGender.get("M").equals(2000000.00) &&
                averageSalaryByGender.get("F").equals(400000.00));
    }
    @Test
    public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() throws SQLException {
        EmployeeDB employeeDB = new EmployeeDB();
        List<EmployeeDetails> employList = employeeDB.readEmployeePayrollData();
        employeeDB.addEmployeeToPayroll("Mark",5000000, LocalDate.now(),"M");
        boolean result = employeeDB.checkEmployeePayrollSyncWithDb("Mark");
        Assert.assertTrue(result);

    }
}
