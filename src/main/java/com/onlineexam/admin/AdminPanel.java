package com.onlineexam.admin;

import com.onlineexam.database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminPanel extends JFrame {

    private JTextArea txtQuestion;
    private JTextField o1, o2, o3, o4, ans;

    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
    }

    private void init() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        JPanel form = new JPanel(new GridLayout(6, 2, 6, 6));
        txtQuestion = new JTextArea(3, 40);
        form.add(new JLabel("Question:"));
        form.add(new JScrollPane(txtQuestion));
        o1 = new JTextField();
        o2 = new JTextField();
        o3 = new JTextField();
        o4 = new JTextField();
        ans = new JTextField();
        form.add(new JLabel("Option1:"));
        form.add(o1);
        form.add(new JLabel("Option2:"));
        form.add(o2);
        form.add(new JLabel("Option3:"));
        form.add(o3);
        form.add(new JLabel("Option4:"));
        form.add(o4);
        form.add(new JLabel("Answer (exact text):"));
        form.add(ans);
        JButton btnAdd = new JButton("Add Question");
        btnAdd.addActionListener(e -> addQuestion());
        p.add(form, BorderLayout.CENTER);
        p.add(btnAdd, BorderLayout.SOUTH);
        add(p);
    }

    private void addQuestion() {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO question (question,option1,option2,option3,option4,answer) VALUES (?,?,?,?,?,?)");
            ps.setString(1, txtQuestion.getText().trim());
            ps.setString(2, o1.getText().trim());
            ps.setString(3, o2.getText().trim());
            ps.setString(4, o3.getText().trim());
            ps.setString(5, o4.getText().trim());
            ps.setString(6, ans.getText().trim());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Question added");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
