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
        mainFrame.setSize(350, 220);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Complaint Portal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        mainFrame.add(titleLabel, BorderLayout.NORTH);

        JButton userBtn = new JButton("User: Submit Complaint");
        JButton adminBtn = new JButton("Admin: Dashboard");
        
        // Give buttons a bit of breathing room without forcing sizes
        userBtn.setMargin(new Insets(8, 15, 8, 15));
        adminBtn.setMargin(new Insets(8, 15, 8, 15));

        userBtn.addActionListener(e -> new UserForm().setVisible(true));
        adminBtn.addActionListener(e -> new AdminLoginForm().setVisible(true));

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 40, 25, 40));
        panel.add(userBtn);
        panel.add(adminBtn);

        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
}
