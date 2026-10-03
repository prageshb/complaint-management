package com.complaint;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            showMainMenu();
        });
    }

    private static void showMainMenu() {
        JFrame mainFrame = new JFrame("Complaint Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(350, 260);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Complaint Portal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        mainFrame.add(titleLabel, BorderLayout.NORTH);

        JButton userBtn = new JButton("Submit Complaint");
        JButton trackBtn = new JButton("Track Progress");
        JButton adminBtn = new JButton("Admin Dashboard");
        
        userBtn.setMargin(new Insets(8, 15, 8, 15));
        trackBtn.setMargin(new Insets(8, 15, 8, 15));
        adminBtn.setMargin(new Insets(8, 15, 8, 15));

        userBtn.addActionListener(e -> new UserForm().setVisible(true));
        trackBtn.addActionListener(e -> trackProgress(mainFrame));
        adminBtn.addActionListener(e -> new AdminLoginForm().setVisible(true));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 40, 25, 40));
        panel.add(userBtn);
        panel.add(trackBtn);
        panel.add(adminBtn);

        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private static void trackProgress(JFrame parent) {
        String trackingId = JOptionPane.showInputDialog(parent, "Enter your Tracking ID:", "Track Progress", JOptionPane.QUESTION_MESSAGE);
        if (trackingId != null && !trackingId.trim().isEmpty()) {
            try {
                ComplaintDAO dao = new ComplaintDAO();
                String status = dao.getComplaintStatus(trackingId.trim());
                if (status != null) {
                    JOptionPane.showMessageDialog(parent, "Status for Tracking ID '" + trackingId + "':\n" + status, "Complaint Status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parent, "Complaint not found for Tracking ID: " + trackingId, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
