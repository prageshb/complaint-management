package com.complaint;

import java.sql.*;
import java.util.Vector;

public class ComplaintDAO {

    public String addComplaint(String title, String description) throws SQLException {
        String trackingId = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String sql = "INSERT INTO complaints (tracking_id, title, description, status) VALUES (?, ?, ?, 'Pending')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trackingId);
            stmt.setString(2, title);
            stmt.setString(3, description);
            stmt.executeUpdate();
        }
        return trackingId;
    }

    public Vector<Complaint> getAllComplaints() throws SQLException {
        Vector<Complaint> complaints = new Vector<>();
        String sql = "SELECT * FROM complaints";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                complaints.add(new Complaint(
                    rs.getInt("id"),
                    rs.getString("tracking_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("status")
                ));
            }
        }
        return complaints;
    }

    public void markAsResolved(int id) throws SQLException {
        String sql = "UPDATE complaints SET status = 'Resolved' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void deleteComplaint(int id) throws SQLException {
        String sql = "DELETE FROM complaints WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean validateAdmin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true if admin exists
            }
        }
    }

    public String getComplaintStatus(String trackingId) throws SQLException {
        String sql = "SELECT status FROM complaints WHERE tracking_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trackingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        }
        return null;
    }
}
