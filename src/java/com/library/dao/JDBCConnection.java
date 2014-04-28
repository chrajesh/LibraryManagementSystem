/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author bharathi
 */
public class JDBCConnection {

    private static JDBCConnection instance = null;
    private static Connection con = null;
    private static Statement stmt = null;

    public static void establishConnection() {
        try {
            System.out.println("establish connection called");
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "123456");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JDBCConnection getInstance() {
        try {
            if (instance != null) {
                return instance;
            } else {
                establishConnection();
                instance = new JDBCConnection();
                return instance;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            System.out.println("Query........." + query);
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public Integer executeUpdate(String query) {
        Integer counter = 0;
        try {
            System.out.println("Query........." + query);
            counter = stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counter;
    }
}
