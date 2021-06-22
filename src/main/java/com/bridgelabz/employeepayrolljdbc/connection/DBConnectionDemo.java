package com.bridgelabz.employeepayrolljdbc.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class DBConnectionDemo {
    private static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while(driverList.hasMoreElements()){
            System.out.println(driverList.nextElement().getClass().getName());
        }
    }
    public static void main(String[] args){
    String dbUrl = "jdbc:mysql://localhost:3306/employee_payroll?useSSL= false";
    String dbUserName = "root";
    String dbPassword = "Harshit@31";
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver Loaded");
    } catch (ClassNotFoundException e) {
        throw new IllegalStateException("Cannot find the driver in the classpath", e);
    }
    try{
        Connection connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        System.out.println(".........Connection is successful........" + connection);
    }catch(Exception e){
        e.printStackTrace();
    }
    listDrivers();
}

}
