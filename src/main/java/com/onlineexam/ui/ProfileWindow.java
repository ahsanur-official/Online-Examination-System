package com.onlineexam.ui;

import com.onlineexam.database.DBConnection;
import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.CustomTextField;
import com.onlineexam.utils.UIConstants;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

/**
 * ProfileWindow - User profile management and information
 */
public class ProfileWindow extends JFrame {

    private int studentId;
    private String studentName;
    private CustomTextField txtName;
    private CustomTextField txtUsername;
    private CustomTextField txtEmail;
    private JLabel statusLabel;

    public ProfileWindow(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        setTitle("My Profile");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIConstants.LIGHT_GREY);
        mainPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UIConstants.PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(600, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("User Profile Information");
        titleLabel.setFont(UIConstants.FONT_HEADING);
        titleLabel.setForeground(UIConstants.WHITE);
        headerPanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(UIConstants.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(UIConstants.FONT_NORMAL);
        nameLabel.setForeground(UIConstants.TEXT_COLOR);
        formPanel.add(nameLabel, gbc);

        gbc.gridy = 1;
        txtName = new CustomTextField(studentName);
        formPanel.add(txtName, gbc);

        // Username
        gbc.gridy = 2;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(UIConstants.FONT_NORMAL);
        userLabel.setForeground(UIConstants.TEXT_COLOR);
        formPanel.add(userLabel, gbc);

        gbc.gridy = 3;
        txtUsername = new CustomTextField();
        txtUsername.setEditable(false);
        formPanel.add(txtUsername, gbc);

        // Email
        gbc.gridy = 4;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(UIConstants.FONT_NORMAL);
        emailLabel.setForeground(UIConstants.TEXT_COLOR);
        formPanel.add(emailLabel, gbc);

        gbc.gridy = 5;
        txtEmail = new CustomTextField();
        formPanel.add(txtEmail, gbc);

        // Status label
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 0, 10, 0);
        statusLabel = new JLabel();
        statusLabel.setFont(UIConstants.FONT_SMALL);
        statusLabel.setForeground(UIConstants.SUCCESS_COLOR);
        formPanel.add(statusLabel, gbc);

        // Buttons
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 0, 5, 0);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(UIConstants.WHITE);

        CustomButton btnUpdate = new CustomButton("Update Profile", UIConstants.SUCCESS_COLOR);
        btnUpdate.addActionListener(e -> updateProfile());
        buttonPanel.add(btnUpdate);

        CustomButton btnClose = new CustomButton("Close", UIConstants.PRIMARY_COLOR);
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);

        formPanel.add(buttonPanel, gbc);

        // Load user data
        loadUserData();

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadUserData() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT username, email FROM student WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtUsername.setText(rs.getString(1));
                String email = rs.getString(2);
                if (email != null) {
                    txtEmail.setText(email);
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProfile() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();

        if (name.isEmpty()) {
            statusLabel.setForeground(UIConstants.DANGER_COLOR);
            statusLabel.setText("Name cannot be empty!");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE student SET name = ?, email = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email.isEmpty() ? null : email);
            ps.setInt(3, studentId);

            int result = ps.executeUpdate();
            if (result > 0) {
                statusLabel.setForeground(UIConstants.SUCCESS_COLOR);
                statusLabel.setText("Profile updated successfully!");
            }
            con.close();
        } catch (Exception e) {
            statusLabel.setForeground(UIConstants.DANGER_COLOR);
            statusLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
