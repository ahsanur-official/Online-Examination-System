package com.onlineexam.ui;

import com.onlineexam.database.FileManager;
import com.onlineexam.model.Student;
import com.onlineexam.model.Admin;
import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.CustomTextField;
import com.onlineexam.utils.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
/**
 * LoginForm - Modern file-based authentication interface
 */
public class LoginForm extends JFrame {

    private CustomTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> roleBox;
    private JCheckBox showPassword;
    private CustomButton btnLogin;
    private JLabel statusLabel;

    public LoginForm() {
        setTitle("Online Exam System - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(560, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(UIConstants.LIGHT_GREY);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Header panel with gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, UIConstants.PRIMARY_COLOR,
                        getWidth(), getHeight(), UIConstants.PRIMARY_DARK);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        headerPanel.setLayout(new GridBagLayout());
        headerPanel.setPreferredSize(new Dimension(560, 150));

        JLabel titleLabel = new JLabel("Online Exam System");
        titleLabel.setFont(UIConstants.FONT_TITLE);
        titleLabel.setForeground(UIConstants.WHITE);

        JLabel subtitleLabel = new JLabel("Student & Admin Portal");
        subtitleLabel.setFont(UIConstants.FONT_NORMAL);
        subtitleLabel.setForeground(new Color(255, 255, 255, 210));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 4, 0);
        headerPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        headerPanel.add(subtitleLabel, gbc);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(UIConstants.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(35, 55, 35, 55));

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel formTitle = new JLabel("Sign in to continue");
        formTitle.setFont(UIConstants.FONT_HEADING);
        formTitle.setForeground(UIConstants.PRIMARY_DARK);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 14, 0);
        formPanel.add(formTitle, gbc);

        gbc.insets = new Insets(8, 0, 8, 0);

        // Username
        gbc.gridy = 1;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy = 2;
        txtUsername = new CustomTextField();
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy = 3;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy = 4;
        txtPassword = new JPasswordField();
        txtPassword.setFont(UIConstants.FONT_NORMAL);
        txtPassword.setPreferredSize(UIConstants.TEXT_FIELD_SIZE);
        formPanel.add(txtPassword, gbc);

        showPassword = new JCheckBox("Show");
        showPassword.setBackground(UIConstants.WHITE);
        showPassword.setForeground(UIConstants.TEXT_COLOR);
        showPassword.addActionListener(e -> 
            txtPassword.setEchoChar(showPassword.isSelected() ? (char) 0 : '\u2022'));
        gbc.gridy = 5;
        formPanel.add(showPassword, gbc);

        // Role
        gbc.gridy = 6;
        formPanel.add(new JLabel("Login As:"), gbc);
        gbc.gridy = 7;
        roleBox = new JComboBox<>(new String[]{"Student", "Admin"});
        roleBox.setFont(UIConstants.FONT_NORMAL);
        formPanel.add(roleBox, gbc);

        // Status label
        gbc.gridy = 8;
        statusLabel = new JLabel();
        statusLabel.setFont(UIConstants.FONT_SMALL);
        statusLabel.setForeground(UIConstants.DANGER_COLOR);
        formPanel.add(statusLabel, gbc);

        // Buttons
        gbc.gridy = 9;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        buttonPanel.setBackground(UIConstants.WHITE);

        btnLogin = new CustomButton("Login", UIConstants.PRIMARY_COLOR);
        btnLogin.addActionListener(e -> login());
        buttonPanel.add(btnLogin);

        CustomButton btnCancel = new CustomButton("Exit", UIConstants.DANGER_COLOR);
        btnCancel.addActionListener(e -> System.exit(0));
        buttonPanel.add(btnCancel);

        formPanel.add(buttonPanel, gbc);

        // Register link
        gbc.gridy = 10;
        gbc.insets = new Insets(18, 0, 0, 0);
        CustomButton btnRegister = new CustomButton("Register", UIConstants.SUCCESS_COLOR);
        btnRegister.setPreferredSize(new Dimension(200, 38));
        btnRegister.addActionListener(e -> openRegistration());
        formPanel.add(btnRegister, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill all fields!");
            return;
        }

        try {
            if ("Student".equals(role)) {
                Student student = FileManager.getStudent(username, password);
                if (student != null) {
                    new Dashboard(student.getId(), student.getName()).setVisible(true);
                    dispose();
                } else {
                    statusLabel.setText("Invalid credentials!");
                    txtPassword.setText("");
                }
            } else {
                Admin admin = FileManager.getAdmin(username, password);
                if (admin != null) {
                    new AdminPanelEnhanced(admin.getId(), admin.getName()).setVisible(true);
                    dispose();
                } else {
                    statusLabel.setText("Invalid credentials!");
                    txtPassword.setText("");
                }
            }
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openRegistration() {
        new RegistrationForm().setVisible(true);
        dispose();
    }
}
