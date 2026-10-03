package com.complaint;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

public class AdminDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ComplaintDAO dao;
    private Vector<Complaint> complaintsList;

    public AdminDashboard() {
        dao = new ComplaintDAO();
        setTitle("Admin Dashboard - Grievances");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("All Submitted Grievances", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Description", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        table = new JTable(tableModel);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshButton = new JButton("Refresh");
        JButton resolveButton = new JButton("Mark as Resolved");
        JButton deleteButton = new JButton("Delete");
        JButton exportButton = new JButton("Export Log");
        
        refreshButton.setMargin(new Insets(5, 10, 5, 10));
        resolveButton.setMargin(new Insets(5, 10, 5, 10));
        deleteButton.setMargin(new Insets(5, 10, 5, 10));
        exportButton.setMargin(new Insets(5, 10, 5, 10));

        refreshButton.addActionListener(e -> loadData());
        resolveButton.addActionListener(e -> resolveComplaint());
        deleteButton.addActionListener(e -> deleteComplaint());
        exportButton.addActionListener(e -> exportLog());

        buttonPanel.add(refreshButton);
        buttonPanel.add(resolveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        loadData();
    }

    private void loadData() {
        try {
            complaintsList = dao.getAllComplaints();
            tableModel.setRowCount(0);
            for (Complaint c : complaintsList) {
                tableModel.addRow(new Object[]{c.getId(), c.getTitle(), c.getDescription(), c.getStatus()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resolveComplaint() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String status = (String) tableModel.getValueAt(selectedRow, 3);
            if ("Resolved".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(this, "Complaint is already resolved.");
                return;
            }
            try {
                dao.markAsResolved(id);
                loadData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error updating status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a complaint to resolve.");
        }
    }

    private void deleteComplaint() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this complaint?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dao.deleteComplaint(id);
                    loadData();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting complaint: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a complaint to delete.");
        }
    }

    private void exportLog() {
        String filename = "Resolved_Complaints_" + LocalDate.now() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Resolved Complaints Log - " + LocalDate.now());
            writer.newLine();
            writer.write("====================================================");
            writer.newLine();

            if (complaintsList == null) {
                loadData();
            }

            int count = 0;
            for (Complaint c : complaintsList) {
                if ("Resolved".equalsIgnoreCase(c.getStatus())) {
                    writer.write("ID: " + c.getId() + " | Title: " + c.getTitle());
                    writer.newLine();
                    writer.write("Description: " + c.getDescription());
                    writer.newLine();
                    writer.write("----------------------------------------------------");
                    writer.newLine();
                    count++;
                }
            }
            writer.write("Total Resolved: " + count);
            JOptionPane.showMessageDialog(this, "Log exported successfully to " + filename, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
