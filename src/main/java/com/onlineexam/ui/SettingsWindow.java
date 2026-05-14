package com.onlineexam.ui;

import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.UIConstants;
import javax.swing.*;

/**
 * SettingsWindow - User preferences and application settings
 */
public class SettingsWindow extends JFrame {

    private int studentId;

    public SettingsWindow(int studentId) {
        this.studentId = studentId;
        setTitle("Settings");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIConstants.LIGHT_GREY);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UIConstants.PRIMARY_COLOR);
        headerPanel.setMaximumSize(new java.awt.Dimension(600, 60));
        headerPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Settings & Preferences");
        titleLabel.setFont(UIConstants.FONT_HEADING);
        titleLabel.setForeground(UIConstants.WHITE);
        headerPanel.add(titleLabel);

        // Settings panel
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBackground(UIConstants.WHITE);
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Appearance settings
        JLabel appearanceLabel = new JLabel("Appearance");
        appearanceLabel.setFont(UIConstants.FONT_SUBHEADING);
        appearanceLabel.setForeground(UIConstants.TEXT_COLOR);
        settingsPanel.add(appearanceLabel);

        JCheckBox darkModeCheckBox = new JCheckBox("Enable Dark Mode");
        darkModeCheckBox.setBackground(UIConstants.WHITE);
        darkModeCheckBox.setFont(UIConstants.FONT_NORMAL);
        settingsPanel.add(darkModeCheckBox);

        settingsPanel.add(Box.createVerticalStrut(15));

        // Notification settings
        JLabel notificationLabel = new JLabel("Notifications");
        notificationLabel.setFont(UIConstants.FONT_SUBHEADING);
        notificationLabel.setForeground(UIConstants.TEXT_COLOR);
        settingsPanel.add(notificationLabel);

        JCheckBox emailNotification = new JCheckBox("Email Notifications");
        emailNotification.setBackground(UIConstants.WHITE);
        emailNotification.setFont(UIConstants.FONT_NORMAL);
        emailNotification.setSelected(true);
        settingsPanel.add(emailNotification);

        JCheckBox examReminders = new JCheckBox("Exam Reminders");
        examReminders.setBackground(UIConstants.WHITE);
        examReminders.setFont(UIConstants.FONT_NORMAL);
        examReminders.setSelected(true);
        settingsPanel.add(examReminders);

        settingsPanel.add(Box.createVerticalStrut(15));

        // Privacy settings
        JLabel privacyLabel = new JLabel("Privacy");
        privacyLabel.setFont(UIConstants.FONT_SUBHEADING);
        privacyLabel.setForeground(UIConstants.TEXT_COLOR);
        settingsPanel.add(privacyLabel);

        JCheckBox showResults = new JCheckBox("Show Results to Others");
        showResults.setBackground(UIConstants.WHITE);
        showResults.setFont(UIConstants.FONT_NORMAL);
        settingsPanel.add(showResults);

        settingsPanel.add(Box.createVerticalGlue());

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIConstants.WHITE);
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 0));

        CustomButton btnSave = new CustomButton("Save Settings", UIConstants.SUCCESS_COLOR);
        btnSave.addActionListener(e -> JOptionPane.showMessageDialog(this, "Settings saved!"));
        buttonPanel.add(btnSave);

        CustomButton btnClose = new CustomButton("Close", UIConstants.PRIMARY_COLOR);
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);

        settingsPanel.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(settingsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanel.add(headerPanel);
        mainPanel.add(scrollPane);

        add(mainPanel);
    }
}
