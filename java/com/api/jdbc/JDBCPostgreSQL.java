package com.api.jdbc;

import com.model.Task;

import java.sql.*;
import java.util.ArrayList;

public class JDBCPostgreSQL {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/javaCourse";
    static final String USER = "postgres";
    static final String PASS = "root";
    Connection connection = null;


    private static JDBCPostgreSQL INSTANCE;

    private JDBCPostgreSQL(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
    }

    public static synchronized JDBCPostgreSQL getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JDBCPostgreSQL();
        }
        return INSTANCE;
    }

    public void saveTask(Task task){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into tasks(task) values ('" + task.getTask() + "')");
        } catch (Exception ex){
            System.out.println("Connection failed in save");
            ex.printStackTrace();
        }
    }

    public void deleteTask(Task task){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from tasks where task = '" + task.getTask() + "'");
        } catch (Exception ex){
            System.out.println("Connection failed in delete");
            ex.printStackTrace();
        }
    }

    public ArrayList<String> getAll(){
        ArrayList<String> allTasks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tasks");
            while(resultSet.next()){
                String task = resultSet.getString(2);
                allTasks.add(task);
            }
        } catch (Exception ex){
            System.out.println("Connection failed in getAll");
            ex.printStackTrace();
        }
        return allTasks;
    }
}
