package com.onlineexam.ui;

import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.UIConstants;
import java.awt.*;
import javax.swing.*;

/**
 * Enhanced ResultWindow - Beautiful result display with performance metrics
 */
public class ResultWindow extends JFrame {

    private int studentId;
    private int score;
    private int total;

    public ResultWindow(int studentId, int score, int total) {
        this.studentId = studentId;
        this.score = score;
        this.total = total;
        setTitle("Exam Results");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIConstants.LIGHT_GREY);
        mainPanel.setLayout(new BorderLayout());

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
        headerPanel.setPreferredSize(new Dimension(700, 80));

        JLabel titleLabel = new JLabel("Exam Completed!");
        titleLabel.setFont(UIConstants.FONT_TITLE);
        titleLabel.setForeground(UIConstants.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 0, 15, 0);
        headerPanel.add(titleLabel, gbc);

        // Results panel
        JPanel resultsPanel = new JPanel();
        resultsPanel.setBackground(UIConstants.WHITE);
        resultsPanel.setLayout(new GridBagLayout());
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Score display
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel scoreLabel = new JLabel("Your Score:");
        scoreLabel.setFont(UIConstants.FONT_SUBHEADING);
        scoreLabel.setForeground(UIConstants.TEXT_COLOR);
        resultsPanel.add(scoreLabel, gbc);

        gbc.gridy = 1;
        JLabel scoreValue = new JLabel(score + " / " + total);
        scoreValue.setFont(new Font("Arial", Font.BOLD, 48));
        scoreValue.setForeground(UIConstants.PRIMARY_COLOR);
        scoreValue.setHorizontalAlignment(SwingConstants.CENTER);
        resultsPanel.add(scoreValue, gbc);

        // Percentage
        double percentage = (score * 100.0) / total;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        JLabel percentLabel = new JLabel("Percentage:");
        percentLabel.setFont(UIConstants.FONT_SUBHEADING);
        percentLabel.setForeground(UIConstants.TEXT_COLOR);
        resultsPanel.add(percentLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 10, 0);
        JLabel percentValue = new JLabel(String.format("%.2f%%", percentage));
        percentValue.setFont(new Font("Arial", Font.BOLD, 32));
        percentValue.setForeground(getColorForPercentage(percentage));
        percentValue.setHorizontalAlignment(SwingConstants.CENTER);
        resultsPanel.add(percentValue, gbc);

        // Grade
        String grade = percentage >= 90 ? "A" : percentage >= 75 ? "B" : percentage >= 50 ? "C" : "D";
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 0, 10, 0);
        JLabel gradeLabel = new JLabel("Grade:");
        gradeLabel.setFont(UIConstants.FONT_SUBHEADING);
        gradeLabel.setForeground(UIConstants.TEXT_COLOR);
        resultsPanel.add(gradeLabel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 10, 0);
        JLabel gradeValue = new JLabel(grade);
        gradeValue.setFont(new Font("Arial", Font.BOLD, 36));
        gradeValue.setForeground(getColorForGrade(grade));
        gradeValue.setHorizontalAlignment(SwingConstants.CENTER);
        resultsPanel.add(gradeValue, gbc);

        // Statistics
        gbc.gridy = 6;
        gbc.insets = new Insets(25, 0, 5, 0);
        JLabel statsLabel = new JLabel("Statistics:");
        statsLabel.setFont(UIConstants.FONT_SUBHEADING);
        statsLabel.setForeground(UIConstants.TEXT_COLOR);
        resultsPanel.add(statsLabel, gbc);

        int wrong = total - score;
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 0, 5, 0);
        JLabel correctLabel = new JLabel("Correct Answers: " + score);
        correctLabel.setFont(UIConstants.FONT_NORMAL);
        correctLabel.setForeground(UIConstants.SUCCESS_COLOR);
        resultsPanel.add(correctLabel, gbc);

        gbc.gridy = 8;
        JLabel wrongLabel = new JLabel("Wrong Answers: " + wrong);
        wrongLabel.setFont(UIConstants.FONT_NORMAL);
        wrongLabel.setForeground(UIConstants.DANGER_COLOR);
        resultsPanel.add(wrongLabel, gbc);

        gbc.gridy = 9;
        JLabel totalLabel = new JLabel("Total Questions: " + total);
        totalLabel.setFont(UIConstants.FONT_NORMAL);
        totalLabel.setForeground(UIConstants.INFO_COLOR);
        resultsPanel.add(totalLabel, gbc);

        // Feedback
        gbc.gridy = 10;
        gbc.insets = new Insets(20, 0, 20, 0);
        JLabel feedbackLabel = new JLabel(getFeedback(percentage));
        feedbackLabel.setFont(UIConstants.FONT_NORMAL);
        feedbackLabel.setForeground(getColorForPercentage(percentage));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultsPanel.add(feedbackLabel, gbc);

        // Buttons
        gbc.gridy = 11;
        gbc.insets = new Insets(10, 0, 0, 0);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIConstants.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));

        CustomButton btnDashboard = new CustomButton("Back to Dashboard", UIConstants.PRIMARY_COLOR);
        btnDashboard.addActionListener(e -> backToDashboard());
        buttonPanel.add(btnDashboard);

        CustomButton btnRetry = new CustomButton("Retake Exam", UIConstants.WARNING_COLOR);
        btnRetry.addActionListener(e -> retakeExam());
        buttonPanel.add(btnRetry);

        resultsPanel.add(buttonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private Color getColorForPercentage(double percentage) {
        if (percentage >= 80) {
            return UIConstants.SUCCESS_COLOR;
        }
        if (percentage >= 60) {
            return UIConstants.WARNING_COLOR;
        }
        return UIConstants.DANGER_COLOR;
    }

    private Color getColorForGrade(String grade) {
        switch (grade) {
            case "A":
                return UIConstants.SUCCESS_COLOR;
            case "B":
                return new Color(52, 152, 219);
            case "C":
                return UIConstants.WARNING_COLOR;
            default:
                return UIConstants.DANGER_COLOR;
        }
    }

    private String getFeedback(double percentage) {
        if (percentage >= 90) {
            return "Excellent! Outstanding performance! Keep up the amazing work!";
        } else if (percentage >= 75) {
            return "Great job! You performed very well. A little more practice will help you excel!";
        } else if (percentage >= 50) {
            return "Good effort! Practice regularly to improve your performance.";
        } else {
            return "Don't give up! Review the material and try again. You will improve!";
        }
    }

    private void backToDashboard() {
        dispose();
        // Create new dashboard - would need student info from session
    }

    private void retakeExam() {
        dispose();
        // Start new exam
    }
}
