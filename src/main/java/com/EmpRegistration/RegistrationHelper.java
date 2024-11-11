package com.EmpRegistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Chatting.Chatting.DBConnector;

public class RegistrationHelper {

    public static String saveRegistration(String name, String number, String email, String password) {
        Connection con = DBConnector.getConnection();
        try {
            String queryCheckExistence = "SELECT number, email FROM employeedata WHERE number = ? OR email = ?";
            PreparedStatement pst = con.prepareStatement(queryCheckExistence);
            pst.setString(1, number);
            pst.setString(2, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {             
                return "exists";
            }
            String queryInsert = "INSERT INTO employeedata (name, number, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(queryInsert);
            ps.setString(1, name);
            ps.setString(2, number);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.executeUpdate();
            return "insert";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception Occurred in Add Data Section");
            System.out.println("Error: " + e.getMessage());
            return "exception";
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
