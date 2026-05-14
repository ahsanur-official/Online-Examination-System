package com.onlineexam.ui;

import com.onlineexam.database.FileManager;
import com.onlineexam.model.Student;
import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.CustomTextField;
import com.onlineexam.utils.UIConstants;
import java.awt.*;
import javax.swing.*;

/**
 * RegistrationForm - File-based student registration
 */
public class RegistrationForm extends JFrame {

    private CustomTextField txtName;
    private CustomTextField txtUsername;
    private CustomTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JLabel statusLabel;

    public RegistrationForm() {
        setTitle("Online Exam System - Registration");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720, 1040);
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));

        // Header
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, UIConstants.SUCCESS_COLOR,
                        getWidth(), getHeight(), new Color(39, 174, 96));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        headerPanel.setLayout(new GridBagLayout());
        headerPanel.setPreferredSize(new Dimension(720, 210));

        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(UIConstants.FONT_TITLE);
        titleLabel.setForeground(UIConstants.WHITE);

        JLabel subtitleLabel = new JLabel("Join the online exam portal");
        subtitleLabel.setFont(UIConstants.FONT_NORMAL);
        subtitleLabel.setForeground(new Color(255, 255, 255, 210));

        JLabel hintLabel = new JLabel("Create an account to take exams and track your progress");
        hintLabel.setFont(UIConstants.FONT_SMALL);
        hintLabel.setForeground(new Color(255, 255, 255, 185));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 4, 0);
        headerPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        headerPanel.add(subtitleLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(4, 0, 0, 0);
        headerPanel.add(hintLabel, gbc);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(UIConstants.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, UIConstants.BORDER_COLOR),
                BorderFactory.createEmptyBorder(36, 88, 36, 88)));

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel formTitle = new JLabel("Student Registration");
        formTitle.setFont(UIConstants.FONT_HEADING);
        formTitle.setForeground(UIConstants.PRIMARY_DARK);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 18, 0);
        formPanel.add(formTitle, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        // Full Name
        gbc.gridy = 1;
        formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridy = 2;
        txtName = new CustomTextField();
        txtName.setPreferredSize(new Dimension(360, 42));
        formPanel.add(txtName, gbc);

        // Username
        gbc.gridy = 3;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy = 4;
        txtUsername = new CustomTextField();
        txtUsername.setPreferredSize(new Dimension(360, 42));
        formPanel.add(txtUsername, gbc);

        // Email
        gbc.gridy = 5;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridy = 6;
        txtEmail = new CustomTextField();
        txtEmail.setPreferredSize(new Dimension(360, 42));
        formPanel.add(txtEmail, gbc);

        // Password
        gbc.gridy = 7;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy = 8;
        txtPassword = new JPasswordField();
        txtPassword.setFont(UIConstants.FONT_NORMAL);
        txtPassword.setPreferredSize(new Dimension(360, 42));
        formPanel.add(txtPassword, gbc);

        // Confirm Password
        gbc.gridy = 9;
        formPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridy = 10;
        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setFont(UIConstants.FONT_NORMAL);
        txtConfirmPassword.setPreferredSize(new Dimension(360, 42));
        formPanel.add(txtConfirmPassword, gbc);

        // Status
        gbc.gridy = 11;
        statusLabel = new JLabel();
        statusLabel.setFont(UIConstants.FONT_SMALL);
        formPanel.add(statusLabel, gbc);

        // Buttons
        gbc.gridy = 12;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 18, 0));
        buttonPanel.setBackground(UIConstants.WHITE);

        CustomButton btnRegister = new CustomButton("Register", UIConstants.SUCCESS_COLOR);
        btnRegister.setPreferredSize(new Dimension(170, 48));
        btnRegister.addActionListener(e -> register());
        buttonPanel.add(btnRegister);

        CustomButton btnBack = new CustomButton("Back", UIConstants.PRIMARY_COLOR);
        btnBack.setPreferredSize(new Dimension(170, 48));
        btnBack.addActionListener(e -> backToLogin());
        buttonPanel.add(btnBack);

        formPanel.add(buttonPanel, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void register() {
        String name = txtName.getText().trim();
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirm = new String(txtConfirmPassword.getPassword());

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill all fields!");
            statusLabel.setForeground(UIConstants.DANGER_COLOR);
            return;
        }

        if (!password.equals(confirm)) {
            statusLabel.setText("Passwords do not match!");
            statusLabel.setForeground(UIConstants.DANGER_COLOR);
            return;
        }

        if (password.length() < 4) {
            statusLabel.setText("Password must be at least 4 characters!");
            statusLabel.setForeground(UIConstants.DANGER_COLOR);
            return;
        }

        if (FileManager.studentExists(username)) {
            statusLabel.setText("Username already exists!");
            statusLabel.setForeground(UIConstants.DANGER_COLOR);
            return;
        }

        try {
            Student student = new Student(0, name, username, password, email);
            FileManager.addStudent(student);

            statusLabel.setText("Registration successful! Redirecting...");
            statusLabel.setForeground(UIConstants.SUCCESS_COLOR);

            Timer timer = new Timer(2000, e -> {
                new LoginForm().setVisible(true);
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setForeground(UIConstants.DANGER_COLOR);
            e.printStackTrace();
        }
    }

    private void backToLogin() {
        new LoginForm().setVisible(true);
        dispose();
    }
}
