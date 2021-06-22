package com.bridgelabz.employeepayrolljdbctest;

import com.bridgelabz.employeepayrolljdbc.entity.EmployeeDetails;
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
}
