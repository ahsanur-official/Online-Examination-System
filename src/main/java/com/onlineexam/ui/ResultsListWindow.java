package com.onlineexam.ui;

import com.onlineexam.database.DBConnection;
import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.UIConstants;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * ResultsListWindow - Display all exam results in a table
 */
public class ResultsListWindow extends JFrame {

    private int studentId;
    private JTable resultsTable;

    public ResultsListWindow(int studentId) {
        this.studentId = studentId;
        setTitle("My Exam Results");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        init();
    }

    private void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIConstants.WHITE);
        mainPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UIConstants.PRIMARY_COLOR);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setPreferredSize(new Dimension(800, 50));

        JLabel titleLabel = new JLabel("Your Exam Results");
        titleLabel.setFont(UIConstants.FONT_HEADING);
        titleLabel.setForeground(UIConstants.WHITE);
        headerPanel.add(titleLabel);

        // Results table
        String[] columns = {"Exam ID", "Score", "Total Questions", "Percentage", "Date", "Grade"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultsTable = new JTable(model);
        resultsTable.setFont(UIConstants.FONT_NORMAL);
        resultsTable.setRowHeight(25);
        resultsTable.setBackground(UIConstants.WHITE);
        resultsTable.setGridColor(UIConstants.BORDER_COLOR);

        loadResults(model);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bottom button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(UIConstants.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        CustomButton btnClose = new CustomButton("Close", UIConstants.PRIMARY_COLOR);
        btnClose.addActionListener(e -> dispose());
        bottomPanel.add(btnClose);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadResults(DefaultTableModel model) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT id, score, total_questions, exam_date FROM result WHERE student_id = ? ORDER BY exam_date DESC";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int examId = rs.getInt(1);
                int score = rs.getInt(2);
                int total = rs.getInt(3);
                Date examDate = rs.getTimestamp(4);

                double percentage = (score * 100.0) / total;
                String grade = percentage >= 90 ? "A" : percentage >= 75 ? "B" : percentage >= 50 ? "C" : "D";

                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(examDate);

                model.addRow(new Object[]{examId, score, total, String.format("%.2f%%", percentage), dateStr, grade});
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading results: " + e.getMessage());
        }
    }
}
