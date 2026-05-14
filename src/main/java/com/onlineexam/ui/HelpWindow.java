package com.onlineexam.ui;

import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.UIConstants;
import javax.swing.*;

/**
 * HelpWindow - Help, FAQ, and support information
 */
public class HelpWindow extends JFrame {

    public HelpWindow() {
        setTitle("Help & FAQ");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIConstants.LIGHT_GREY);
        mainPanel.setLayout(new java.awt.BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UIConstants.PRIMARY_COLOR);
        headerPanel.setPreferredSize(new java.awt.Dimension(800, 60));
        headerPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Help & FAQ");
        titleLabel.setFont(UIConstants.FONT_HEADING);
        titleLabel.setForeground(UIConstants.WHITE);
        headerPanel.add(titleLabel);

        // Help content
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(UIConstants.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Add FAQ items
        addFAQItem(contentPanel, "How do I start an exam?",
                "Click on 'Start Exam' from the dashboard. Select the exam you want to take and click 'Begin'."
                + " You will have a limited time to complete the exam.");

        addFAQItem(contentPanel, "How much time do I have for each exam?",
                "The time limit for each exam is displayed when you start it. Most exams are 60 minutes long."
                + " The timer will count down, and you will receive a warning when time is running out.");

        addFAQItem(contentPanel, "Can I save my progress?",
                "Your answers are automatically saved as you proceed through the exam. If your connection drops,"
                + " your answers up to that point will be saved and you can resume from where you left off.");

        addFAQItem(contentPanel, "How are my results calculated?",
                "Your score is calculated as: (Correct Answers / Total Questions) × 100. "
                + "Grades are assigned based on your percentage: A (90-100%), B (75-89%), C (50-74%), D (Below 50%).");

        addFAQItem(contentPanel, "How do I view my past results?",
                "Click on 'View Results' from the dashboard to see all your previous exam results."
                + " You can also check 'Analytics' for detailed performance insights.");

        addFAQItem(contentPanel, "What should I do if I encounter technical issues?",
                "If you experience any technical problems, please contact the support team immediately."
                + " Try refreshing the page or restarting the application. If issues persist, contact support.");

        addFAQItem(contentPanel, "Can I retake an exam?",
                "Yes, you can retake any exam as many times as you want. Your latest score will be recorded,"
                + " but all previous attempts will remain visible in your results history.");

        addFAQItem(contentPanel, "How do I update my profile information?",
                "Click on 'Profile' from the dashboard to view and edit your personal information."
                + " Make the necessary changes and click 'Update Profile' to save your changes.");

        contentPanel.add(Box.createVerticalGlue());

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIConstants.WHITE);
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 15));

        CustomButton btnClose = new CustomButton("Close", UIConstants.PRIMARY_COLOR);
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
        mainPanel.add(scrollPane, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addFAQItem(JPanel parent, String question, String answer) {
        JLabel questionLabel = new JLabel(question);
        questionLabel.setFont(UIConstants.FONT_SUBHEADING);
        questionLabel.setForeground(UIConstants.PRIMARY_COLOR);
        parent.add(questionLabel);

        JLabel answerLabel = new JLabel("<html><p style='width: 700px;'>" + answer + "</p></html>");
        answerLabel.setFont(UIConstants.FONT_NORMAL);
        answerLabel.setForeground(UIConstants.TEXT_COLOR);
        parent.add(answerLabel);

        parent.add(Box.createVerticalStrut(15));
    }
}
