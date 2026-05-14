package com.onlineexam.ui;

import com.onlineexam.database.FileManager;
import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.StatisticsCard;
import com.onlineexam.utils.UIConstants;
import java.awt.*;
import javax.swing.*;

/**
 * Enhanced Dashboard - Modern student dashboard with multiple features
 */
public class Dashboard extends JFrame {

    private final int studentId;
    private final String studentName;

    public Dashboard(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        setTitle("Student Dashboard - " + studentName);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIConstants.LIGHT_GREY);
        mainPanel.setLayout(new BorderLayout());

        // Top header
        JPanel headerPanel = createHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content area
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(UIConstants.LIGHT_GREY);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Statistics section
        JPanel statsPanel = createStatisticsPanel();
        contentPanel.add(statsPanel, BorderLayout.NORTH);

        // Action buttons
        JPanel actionsPanel = createActionsPanel();
        contentPanel.add(actionsPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeader() {
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
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(1000, 80));

        JLabel titleLabel = new JLabel("Welcome, " + studentName + "!");
        titleLabel.setFont(UIConstants.FONT_HEADING);
        titleLabel.setForeground(UIConstants.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 15));

        CustomButton btnProfile = new CustomButton("Profile", UIConstants.SECONDARY_COLOR);
        btnProfile.setPreferredSize(new Dimension(80, 35));
        btnProfile.addActionListener(e -> showProfile());
        rightPanel.add(btnProfile);

        CustomButton btnSettings = new CustomButton("Settings", UIConstants.WARNING_COLOR);
        btnSettings.setPreferredSize(new Dimension(80, 35));
        btnSettings.addActionListener(e -> showSettings());
        rightPanel.add(btnSettings);

        CustomButton btnLogout = new CustomButton("Logout", UIConstants.DANGER_COLOR);
        btnLogout.setPreferredSize(new Dimension(80, 35));
        btnLogout.addActionListener(e -> logout());
        rightPanel.add(btnLogout);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createStatisticsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(UIConstants.LIGHT_GREY);
        statsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // Get statistics from database
        int totalExams = FileManager.getStudentExamCount(studentId);
        int averageScore = (int) Math.round(FileManager.getStudentAverageScore(studentId));
        int highestScore = FileManager.getStudentHighestScore(studentId);

        // Create statistic cards
        StatisticsCard card1 = new StatisticsCard("Total Exams", String.valueOf(totalExams),
                UIConstants.PRIMARY_COLOR, UIConstants.PRIMARY_COLOR);
        statsPanel.add(card1);

        StatisticsCard card2 = new StatisticsCard("Average Score", String.valueOf(averageScore) + "%",
                UIConstants.SUCCESS_COLOR, UIConstants.SUCCESS_COLOR);
        statsPanel.add(card2);

        StatisticsCard card3 = new StatisticsCard("Highest Score", String.valueOf(highestScore) + "%",
                UIConstants.INFO_COLOR, UIConstants.INFO_COLOR);
        statsPanel.add(card3);

        StatisticsCard card4 = new StatisticsCard("Study Progress", "75%",
                UIConstants.WARNING_COLOR, UIConstants.WARNING_COLOR);
        statsPanel.add(card4);

        return statsPanel;
    }

    private JPanel createActionsPanel() {
        JPanel actionsPanel = new JPanel();
        actionsPanel.setBackground(UIConstants.LIGHT_GREY);
        actionsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel actionsTitle = new JLabel("Available Actions");
        actionsTitle.setFont(UIConstants.FONT_HEADING);
        actionsTitle.setForeground(UIConstants.TEXT_COLOR);
        actionsPanel.add(actionsTitle, gbc);

        // Button grid
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;

        CustomButton btnStartExam = createActionButton("Start Exam", UIConstants.SUCCESS_COLOR);
        btnStartExam.addActionListener(e -> startExam());
        actionsPanel.add(btnStartExam, gbc);

        gbc.gridx = 1;
        CustomButton btnViewResults = createActionButton("View Results", UIConstants.PRIMARY_COLOR);
        btnViewResults.addActionListener(e -> viewResults());
        actionsPanel.add(btnViewResults, gbc);

        gbc.gridx = 2;
        CustomButton btnAnalytics = createActionButton("Analytics", UIConstants.INFO_COLOR);
        btnAnalytics.addActionListener(e -> showAnalytics());
        actionsPanel.add(btnAnalytics, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        CustomButton btnPractice = createActionButton("Practice Mode", UIConstants.WARNING_COLOR);
        btnPractice.addActionListener(e -> JOptionPane.showMessageDialog(this, "Practice mode coming soon!"));
        actionsPanel.add(btnPractice, gbc);

        gbc.gridx = 1;
        CustomButton btnStudyMaterials = createActionButton("Study Materials", UIConstants.SECONDARY_COLOR);
        btnStudyMaterials.addActionListener(e -> JOptionPane.showMessageDialog(this, "Study materials coming soon!"));
        actionsPanel.add(btnStudyMaterials, gbc);

        gbc.gridx = 2;
        CustomButton btnHelp = createActionButton("Help & FAQ", UIConstants.INFO_COLOR);
        btnHelp.addActionListener(e -> showHelp());
        actionsPanel.add(btnHelp, gbc);

        return actionsPanel;
    }

    private CustomButton createActionButton(String text, Color color) {
        CustomButton btn = new CustomButton(text, color);
        btn.setPreferredSize(new Dimension(200, 60));
        btn.setFont(UIConstants.FONT_SUBHEADING);
        return btn;
    }

    private void startExam() {
        new ExamWindow(studentId).setVisible(true);
        dispose();
    }

    private void viewResults() {
        new ResultsListWindow(studentId).setVisible(true);
    }

    private void showAnalytics() {
        new AnalyticsWindow(studentId, studentName).setVisible(true);
    }

    private void showProfile() {
        new ProfileWindow(studentId, studentName).setVisible(true);
    }

    private void showSettings() {
        new SettingsWindow(studentId).setVisible(true);
    }

    private void showHelp() {
        new HelpWindow().setVisible(true);
    }

    private void logout() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?",
                "Logout", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            new LoginForm().setVisible(true);
            dispose();
        }
    }
}
