package com.complaint;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class UserForm extends JFrame {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private ComplaintDAO dao;

    public UserForm() {
        dao = new ComplaintDAO();
        setTitle("Submit a Complaint");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel headerLabel = new JLabel("File a New Grievance", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new BorderLayout(5, 10));
        
        JPanel titlePanel = new JPanel(new BorderLayout(5, 5));
        titlePanel.add(new JLabel("Title:"), BorderLayout.NORTH);
        titleField = new JTextField();
        titleField.setMargin(new Insets(2, 5, 2, 5));
        titlePanel.add(titleField, BorderLayout.CENTER);
        formPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel descPanel = new JPanel(new BorderLayout(5, 5));
        descPanel.add(new JLabel("Description:"), BorderLayout.NORTH);
        descriptionArea = new JTextArea(6, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        descPanel.add(scrollPane, BorderLayout.CENTER);
        formPanel.add(descPanel, BorderLayout.CENTER);
        
        panel.add(formPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit Complaint");
        submitButton.setMargin(new Insets(8, 20, 8, 20));
        submitButton.addActionListener((ActionEvent e) -> submitComplaint());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.add(submitButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void submitComplaint() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String trackingId = dao.addComplaint(title, description);
            
            Object[] options = {"Copy Tracking ID", "Close"};
            int choice = JOptionPane.showOptionDialog(this,
                    "Complaint submitted successfully!\nYour Tracking ID is: " + trackingId,
                    "Success",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);
                    
            if (choice == JOptionPane.YES_OPTION) {
                StringSelection stringSelection = new StringSelection(trackingId);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(this, "Tracking ID copied to clipboard!", "Copied", JOptionPane.INFORMATION_MESSAGE);
            }
            
            titleField.setText("");
            descriptionArea.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
