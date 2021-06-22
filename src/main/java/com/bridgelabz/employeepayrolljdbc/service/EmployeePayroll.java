package com.bridgelabz.employeepayrolljdbc.service;

import com.bridgelabz.employeepayrolljdbc.entity.EmployeeDetails;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePayroll {
    private PreparedStatement preparedStatementName;

    private Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/employee_payroll?useSSL=false";
        String userName = "root";
        String userPassword = "Harshit@31";
        Connection connection = DriverManager.getConnection(dbUrl, userName, userPassword);
        System.out.println("..........Connection Successful..........");
        return connection;
    }
    public List<EmployeeDetails> readData() {
        List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM employee_details";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeeDetailsList.add(new EmployeeDetails(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeDetailsList;
    }

    public int updateDB(String name, double salary) {
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = String.format("UPDATE employee_details SET Salary = %.2f WHERE Name = '%s';", salary, name);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void updateDBPrepared(String name, double salary) {
        try (Connection connection = this.getConnection()) {
            String sql = String.format("UPDATE employee_details SET Salary = %.2f WHERE Name = '%s';", salary, name);
            PreparedStatement preparedStatementUpdate = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void readDBNamePreparedStatement(){
        try (Connection connection = this.getConnection()){
            String sql = "SELECT * FROM employee_details WHERE name = ?";
             preparedStatementName = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static EmployeePayroll getInstance(){
        EmployeePayroll employeePayroll = null;
        employeePayroll = new EmployeePayroll();
        return  employeePayroll;
    }
    public List<EmployeeDetails> getEmployeeData(String name) {
        List<EmployeeDetails> employeeDetailsList = null;
        if(this.preparedStatementName == null)
            this.readDBNamePreparedStatement();
        try{
            preparedStatementName.setString(1,name);
            ResultSet resultSet = preparedStatementName.executeQuery();
            employeeDetailsList = this.getEmployeeData(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return employeeDetailsList;
    }
    public List<EmployeeDetails> getEmployeeData(ResultSet resultSet) {
        List<EmployeeDetails> employeePayrollList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeeDetails(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    public List<EmployeeDetails> getEmployeeDataForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT * FROM employee_details WHERE start BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getEmployeeDataUsingDB(sql);
    }
    private List<EmployeeDetails> getEmployeeDataUsingDB(String sql) {
        List<EmployeeDetails> employeeDetails = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeeDetails = this.getEmployeeData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeDetails;
    }

    public Map<String, Double> getAverageSalaryByGender() {
        String sql = "SELECT gender, AVG(salary) as average_salary FROM employee_details GROUP BY gender;";
        Map<String, Double> genderToAverageSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("average_salary");
                genderToAverageSalaryMap.put(gender, salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToAverageSalaryMap;
    }
}


