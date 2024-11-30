package com.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Chatting.Chatting.DBConnector;

@WebServlet("/saveCart")
public class CartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read the cart data from the request body
        String cartData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        // Parse the cart data (assumes it's in JSON format)
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, CartItem> cart = objectMapper.readValue(cartData, new TypeReference<Map<String, CartItem>>(){});

            // Save cart data to the database
            boolean isSaved = saveCartToDatabase(cart);

            // Send response back to the client
            if (isSaved) {
                response.getWriter().write("{\"success\": true}");
            } else {
                response.getWriter().write("{\"success\": false}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"Error saving cart data.\"}");
        }
    }

    private boolean saveCartToDatabase(Map<String, CartItem> cart) {
        // Example of saving cart data to a database (JDBC)
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection =DBConnector.getConnection();

            String sql = "INSERT INTO cart_items (name, price, quantity) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            for (CartItem item : cart.values()) {
                preparedStatement.setString(1, item.getName());
                preparedStatement.setDouble(2, item.getPrice());
                preparedStatement.setInt(3, item.getQuantity());
                preparedStatement.addBatch();
            }

            int[] result = preparedStatement.executeBatch();
            return result.length > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // CartItem class representing an individual cart item
    public static class CartItem {
        private String name;
        private double price;
        private int quantity;

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}

