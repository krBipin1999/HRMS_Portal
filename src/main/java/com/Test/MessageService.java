package com.Test;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import java.util.*;

@Path("/messages")
public class MessageService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMessage(@HeaderParam("Authorization") String token, Message message) {
        if (!JwtUtil.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO messages (sender_id, receiver_id, content) VALUES ((SELECT id FROM users WHERE token = ?), ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, token);
            stmt.setInt(2, message.getReceiverId());
            stmt.setString(3, message.getContent());
            stmt.executeUpdate();
            return Response.ok().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(@HeaderParam("Authorization") String token, @QueryParam("withUser") int withUser) {
        if (!JwtUtil.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<Message> messages = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM messages WHERE (sender_id = (SELECT id FROM users WHERE token = ?) AND receiver_id = ?) OR (sender_id = ? AND receiver_id = (SELECT id FROM users WHERE token = ?)) ORDER BY timestamp";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, token);
            stmt.setInt(2, withUser);
            stmt.setInt(3, withUser);
            stmt.setString(4, token);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messages.add(new Message(rs.getInt("sender_id"), rs.getInt("receiver_id"), rs.getString("content"), rs.getTimestamp("timestamp")));
            }
            return Response.ok(messages).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build();
        }
    }
}

