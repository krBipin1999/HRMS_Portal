package com.Test;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;

@Path("/user")
public class UserService {
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String token = JwtUtil.generateToken(user.getUsername());
                String updateTokenQuery = "UPDATE users SET token = ? WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateTokenQuery);
                updateStmt.setString(1, token);
                updateStmt.setInt(2, rs.getInt("id"));
                updateStmt.executeUpdate();
                
                return Response.ok("{\"token\":\"" + token + "\"}").build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build();
        }
    }

    @PUT
    @Path("/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStatus(@HeaderParam("Authorization") String token, UserStatus status) {
        if (!JwtUtil.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "UPDATE status SET online = ? WHERE user_id = (SELECT id FROM users WHERE token = ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setBoolean(1, status.isOnline());
            stmt.setString(2, token);
            stmt.executeUpdate();
            return Response.ok().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build();
        }
    }
}

