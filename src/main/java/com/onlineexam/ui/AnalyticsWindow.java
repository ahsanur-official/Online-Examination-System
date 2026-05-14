package com.onlineexam.ui;

import com.onlineexam.database.FileManager;
import com.onlineexam.model.Result;
import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.StatisticsCard;
import com.onlineexam.utils.UIConstants;
import java.awt.*;
import javax.swing.*;

/**
 * AnalyticsWindow - Detailed performance analytics and statistics
 */
public class AnalyticsWindow extends JFrame {

    private final int studentId;
    private final String studentName;

    public AnalyticsWindow(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        setTitle("Performance Analytics - " + studentName);
        setSize(900, 700);
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
        headerPanel.setPreferredSize(new Dimension(900, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Performance Analytics & Insights");
        titleLabel.setFont(UIConstants.FONT_HEADING);
        titleLabel.setForeground(UIConstants.WHITE);
        headerPanel.add(titleLabel);

        // Statistics panel
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(UIConstants.LIGHT_GREY);
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Get analytics data
        int totalExams = FileManager.getStudentExamCount(studentId);
        int avgScore = (int) Math.round(FileManager.getStudentAverageScore(studentId));
        int highestScore = FileManager.getStudentHighestScore(studentId);
        int lowestScore = FileManager.getStudentLowestScore(studentId);
        int passingExams = 0;

        for (Result result : FileManager.getStudentResults(studentId)) {
            if (result.getScore() >= 5) {
                passingExams++;
            }
        }
        int failingExams = totalExams - passingExams;

        // Create cards
        StatisticsCard card1 = new StatisticsCard("Total Exams", String.valueOf(totalExams),
                UIConstants.PRIMARY_COLOR, UIConstants.PRIMARY_COLOR);
        statsPanel.add(card1);

        StatisticsCard card2 = new StatisticsCard("Average Score", String.valueOf(avgScore) + "%",
                UIConstants.SUCCESS_COLOR, UIConstants.SUCCESS_COLOR);
        statsPanel.add(card2);

        StatisticsCard card3 = new StatisticsCard("Highest Score", String.valueOf(highestScore) + "%",
                UIConstants.INFO_COLOR, UIConstants.INFO_COLOR);
        statsPanel.add(card3);

        StatisticsCard card4 = new StatisticsCard("Lowest Score", String.valueOf(lowestScore) + "%",
                UIConstants.WARNING_COLOR, UIConstants.WARNING_COLOR);
        statsPanel.add(card4);

        StatisticsCard card5 = new StatisticsCard("Passing", String.valueOf(passingExams),
                new Color(46, 204, 113), new Color(46, 204, 113));
        statsPanel.add(card5);

        StatisticsCard card6 = new StatisticsCard("Failing", String.valueOf(failingExams),
                UIConstants.DANGER_COLOR, UIConstants.DANGER_COLOR);
        statsPanel.add(card6);

        // Performance indicators
        JPanel indicatorsPanel = createPerformanceIndicators(avgScore, passingExams, totalExams);

        // Bottom button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(UIConstants.LIGHT_GREY);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        CustomButton btnClose = new CustomButton("Close", UIConstants.PRIMARY_COLOR);
        btnClose.addActionListener(e -> dispose());
        bottomPanel.add(btnClose);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        mainPanel.add(indicatorsPanel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        add(mainPanel);
    }

    private JPanel createPerformanceIndicators(int avgScore, int passingExams, int totalExams) {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Overall score bar
        JLabel label1 = new JLabel("Overall Performance Score: " + avgScore + "%");
        label1.setFont(UIConstants.FONT_NORMAL);
        label1.setForeground(UIConstants.TEXT_COLOR);
        panel.add(label1);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(avgScore);
        progressBar.setStringPainted(true);
        progressBar.setForeground(getColorForScore(avgScore));
        panel.add(Box.createVerticalStrut(5));
        panel.add(progressBar);

        // Recommendation
        JLabel recommendationLabel = new JLabel(getRecommendation(avgScore, totalExams));
        recommendationLabel.setFont(UIConstants.FONT_NORMAL);
        recommendationLabel.setForeground(getColorForScore(avgScore));
        panel.add(Box.createVerticalStrut(15));
        panel.add(recommendationLabel);

        return panel;
    }

    private Color getColorForScore(int score) {
        if (score >= 80) {
            return UIConstants.SUCCESS_COLOR;
        }
        if (score >= 60) {
            return UIConstants.WARNING_COLOR;
        }
        return UIConstants.DANGER_COLOR;
    }

    private String getRecommendation(int avgScore, int totalExams) {
        String recommendation = "Recommendation: ";
        if (avgScore >= 80) {
            recommendation += "Excellent! You're performing very well. Keep up the great work!";
        } else if (avgScore >= 60) {
            recommendation += "Good performance. Practice more challenging questions to improve.";
        } else if (avgScore >= 40) {
            recommendation += "You need improvement. Focus on weak areas and practice regularly.";
        } else {
            recommendation += "Please consult with your instructor for additional help and study guidance.";
        }

        if (totalExams == 0) {
            recommendation = "No exams taken yet. Start an exam to see your analytics!";
        }

        return recommendation;
    }
}
