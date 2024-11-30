package com.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Chatting.Chatting.DBConnector;

public class CheckoutDAO {

    public static void saveData(CheckoutData data) throws Exception {
        try (Connection connection = DBConnector.getConnection()) {
            String query = "INSERT INTO checkout_data (item_name, quantity, price, total_amount) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (CartItem item : data.getItems()) {
                preparedStatement.setString(1, item.getName());
                preparedStatement.setInt(2, item.getQuantity());
                preparedStatement.setDouble(3, item.getPrice());
                preparedStatement.setDouble(4, data.getTotalAmount());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }
}

