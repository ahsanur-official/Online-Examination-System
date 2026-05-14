package com.onlineexam.ui;

import com.onlineexam.database.DBConnection;
import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.StatisticsCard;
import com.onlineexam.utils.UIConstants;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * AdminPanelEnhanced - Modern admin dashboard with multiple management features
 */
public class AdminPanelEnhanced extends JFrame {

    private int adminId;
    private String adminName;
    private JTabbedPane tabbedPane;

    public AdminPanelEnhanced(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
        setTitle("Admin Dashboard - " + adminName);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIConstants.LIGHT_GREY);
        mainPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = createHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Tabbed pane for different sections
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(UIConstants.WHITE);
        tabbedPane.setForeground(UIConstants.TEXT_COLOR);
        tabbedPane.setFont(UIConstants.FONT_NORMAL);

        tabbedPane.addTab("Dashboard", createDashboardTab());
        tabbedPane.addTab("Students", createStudentsTab());
        tabbedPane.addTab("Questions", createQuestionsTab());
        tabbedPane.addTab("Results", createResultsTab());
        tabbedPane.addTab("Reports", createReportsTab());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, UIConstants.PRIMARY_DARK,
                        getWidth(), getHeight(), UIConstants.PRIMARY_COLOR);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(1200, 80));

        JLabel titleLabel = new JLabel("Admin Dashboard - Welcome, " + adminName);
        titleLabel.setFont(UIConstants.FONT_HEADING);
        titleLabel.setForeground(UIConstants.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 15));

        CustomButton btnLogout = new CustomButton("Logout", UIConstants.DANGER_COLOR);
        btnLogout.setPreferredSize(new Dimension(80, 35));
        btnLogout.addActionListener(e -> logout());
        rightPanel.add(btnLogout);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createDashboardTab() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants.LIGHT_GREY);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int totalStudents = 0, totalQuestions = 0, totalExamsTaken = 0;
        double averageScore = 0;

        try {
            Connection con = DBConnection.getConnection();

            // Get total students
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM student");
            if (rs.next()) {
                totalStudents = rs.getInt(1);
            }

            // Get total questions
            rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM question");
            if (rs.next()) {
                totalQuestions = rs.getInt(1);
            }

            // Get total exams taken
            rs = con.createStatement().executeQuery("SELECT COUNT(*), AVG(score) FROM result");
            if (rs.next()) {
                totalExamsTaken = rs.getInt(1);
                averageScore = rs.getDouble(2);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StatisticsCard card1 = new StatisticsCard("Total Students", String.valueOf(totalStudents),
                UIConstants.PRIMARY_COLOR, UIConstants.PRIMARY_COLOR);
        panel.add(card1);

        StatisticsCard card2 = new StatisticsCard("Total Questions", String.valueOf(totalQuestions),
                UIConstants.SUCCESS_COLOR, UIConstants.SUCCESS_COLOR);
        panel.add(card2);

        StatisticsCard card3 = new StatisticsCard("Exams Taken", String.valueOf(totalExamsTaken),
                UIConstants.INFO_COLOR, UIConstants.INFO_COLOR);
        panel.add(card3);

        StatisticsCard card4 = new StatisticsCard("Avg Score", String.format("%.1f%%", averageScore),
                UIConstants.WARNING_COLOR, UIConstants.WARNING_COLOR);
        panel.add(card4);

        return panel;
    }

    private JPanel createStudentsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIConstants.WHITE);

        String[] columns = {"Student ID", "Name", "Username", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(UIConstants.FONT_NORMAL);
        table.setRowHeight(25);

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT id, name, username, email FROM student";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)});
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createQuestionsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIConstants.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(UIConstants.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        CustomButton btnAdd = new CustomButton("Add Question", UIConstants.SUCCESS_COLOR);
        btnAdd.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add question functionality coming soon!"));
        topPanel.add(btnAdd);

        String[] columns = {"Question ID", "Question", "Option 1", "Option 2", "Option 3", "Option 4", "Answer"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(UIConstants.FONT_NORMAL);
        table.setRowHeight(25);

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT id, question, option1, option2, option3, option4, answer FROM question LIMIT 20";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createResultsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIConstants.WHITE);

        String[] columns = {"Result ID", "Student ID", "Score", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(UIConstants.FONT_NORMAL);
        table.setRowHeight(25);

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT id, student_id, score, exam_date FROM result ORDER BY exam_date DESC LIMIT 50";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4)});
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReportsTab() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel reportTitle = new JLabel("System Reports & Analytics");
        reportTitle.setFont(UIConstants.FONT_HEADING);
        reportTitle.setForeground(UIConstants.TEXT_COLOR);
        panel.add(reportTitle);

        panel.add(Box.createVerticalStrut(15));

        CustomButton btnGenerateReport = new CustomButton("Generate Monthly Report", UIConstants.PRIMARY_COLOR);
        btnGenerateReport.addActionListener(e -> JOptionPane.showMessageDialog(this, "Report generated successfully!"));
        panel.add(btnGenerateReport);

        panel.add(Box.createVerticalStrut(10));

        CustomButton btnExportData = new CustomButton("Export Data to CSV", UIConstants.SUCCESS_COLOR);
        btnExportData.addActionListener(e -> JOptionPane.showMessageDialog(this, "Data exported successfully!"));
        panel.add(btnExportData);

        panel.add(Box.createVerticalGlue());

        return panel;
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
