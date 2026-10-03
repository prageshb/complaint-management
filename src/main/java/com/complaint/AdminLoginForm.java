package com.complaint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AdminLoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private ComplaintDAO dao;

    public AdminLoginForm() {
        dao = new ComplaintDAO();
        setTitle("Admin Login");
        setSize(320, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel userPanel = new JPanel(new BorderLayout(5, 5));
        userPanel.add(new JLabel("Username:"), BorderLayout.NORTH);
        usernameField = new JTextField();
        userPanel.add(usernameField, BorderLayout.CENTER);
        
        JPanel passPanel = new JPanel(new BorderLayout(5, 5));
        passPanel.add(new JLabel("Password:"), BorderLayout.NORTH);
        passwordField = new JPasswordField();
        passPanel.add(passwordField, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        loginButton.setMargin(new Insets(5, 20, 5, 20));
        loginButton.addActionListener((ActionEvent e) -> attemptLogin());

        JPanel btnPanel = new JPanel();
        btnPanel.add(loginButton);

        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(btnPanel);

        add(panel);
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (dao.validateAdmin(username, password)) {
                this.dispose(); // Close login window
                new AdminDashboard().setVisible(true); // Open dashboard
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
