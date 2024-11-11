package com.empLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Chatting.Chatting.DBConnector;

public class LoginHelper {
    public static boolean getData(String email, String password) {
        String query = "SELECT email, password FROM employeedata WHERE email = ? AND password = ?";
        try (Connection con = DBConnector.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred. Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false; 
    }

    public static void main(String[] args) {
        System.out.println(getData("krbipin1999@gmail.com", "1236"));
    }
}
