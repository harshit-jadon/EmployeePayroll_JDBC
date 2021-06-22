package com.bridgelabz.employeepayrolljdbc.service;

import com.bridgelabz.employeepayrolljdbc.entity.EmployeeDetails;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayroll {
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
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM employee_details";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeeDetailsList.add(new EmployeeDetails(id,name,salary,startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeDetailsList;
    }
    public void updateDB(String name, double salary){
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            String sql = String.format("UPDATE employee_details SET Salary = %.2f WHERE Name = '%s';",salary,name);
            statement.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println("Welcome to Employee Payroll using JDBC to Get Database From MYSQL");
    }

}

