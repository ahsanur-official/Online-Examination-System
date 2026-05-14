package com.onlineexam.ui;

import com.onlineexam.database.FileManager;
import com.onlineexam.model.Question;
import com.onlineexam.model.Result;
import com.onlineexam.utils.CustomButton;
import com.onlineexam.utils.UIConstants;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

/**
 * Enhanced ExamWindow - Modern exam interface with timer and progress tracking
 */
public class ExamWindow extends JFrame {

    private int studentId;
    private List<Question> questions = new ArrayList<>();
    private int[] answers;
    private int index = 0;
    private JLabel lblQuestion;
    private JRadioButton[] opts = new JRadioButton[4];
    private ButtonGroup group = new ButtonGroup();
    private int score = 0;
    private int timeLeft = 60 * 5; // 5 minutes
    private JLabel timerLabel;
    private JProgressBar progressBar;
    private JLabel progressLabel;
    private Timer timer;

    public ExamWindow(int studentId) {
        this.studentId = studentId;
        setTitle("Online Exam System");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        loadQuestions();
        init();
    }

    private void loadQuestions() {
        questions = FileManager.getRandomQuestions(10);
        answers = new int[questions.size()];
        Arrays.fill(answers, -1);
    }

    private void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIConstants.LIGHT_GREY);
        mainPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = createHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(UIConstants.WHITE);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Question panel
        JPanel questionPanel = new JPanel();
        questionPanel.setBackground(UIConstants.WHITE);
        questionPanel.setLayout(new BorderLayout());

        lblQuestion = new JLabel();
        lblQuestion.setFont(UIConstants.FONT_HEADING);
        lblQuestion.setForeground(UIConstants.TEXT_COLOR);
        lblQuestion.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        questionPanel.add(lblQuestion, BorderLayout.NORTH);

        // Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBackground(UIConstants.WHITE);
        optionsPanel.setLayout(new GridLayout(4, 1, 0, 10));

        for (int i = 0; i < 4; i++) {
            opts[i] = createStyledRadioButton();
            group.add(opts[i]);
            optionsPanel.add(opts[i]);
        }

        questionPanel.add(optionsPanel, BorderLayout.CENTER);
        contentPanel.add(questionPanel, BorderLayout.CENTER);

        // Navigation buttons
        JPanel navPanel = new JPanel();
        navPanel.setBackground(UIConstants.WHITE);
        navPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        CustomButton btnPrev = new CustomButton("Previous", UIConstants.SECONDARY_COLOR);
        btnPrev.setPreferredSize(new Dimension(100, 35));
        btnPrev.addActionListener(e -> prev());
        navPanel.add(btnPrev);

        CustomButton btnNext = new CustomButton("Next", UIConstants.PRIMARY_COLOR);
        btnNext.setPreferredSize(new Dimension(100, 35));
        btnNext.addActionListener(e -> next());
        navPanel.add(btnNext);

        CustomButton btnSubmit = new CustomButton("Submit Exam", UIConstants.SUCCESS_COLOR);
        btnSubmit.setPreferredSize(new Dimension(120, 35));
        btnSubmit.addActionListener(e -> submitExam());
        navPanel.add(btnSubmit);

        contentPanel.add(navPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        if (!questions.isEmpty()) {
            showQuestion(0);
            startTimer();
        }
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
        headerPanel.setPreferredSize(new Dimension(900, 120));

        // Left side
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));

        JLabel examTitle = new JLabel("Online Examination");
        examTitle.setFont(UIConstants.FONT_HEADING);
        examTitle.setForeground(UIConstants.WHITE);
        leftPanel.add(examTitle);

        progressLabel = new JLabel("Question 1 of " + questions.size());
        progressLabel.setFont(UIConstants.FONT_NORMAL);
        progressLabel.setForeground(new Color(255, 255, 255, 200));
        leftPanel.add(progressLabel);

        // Progress bar
        progressBar = new JProgressBar(0, questions.size());
        progressBar.setForeground(UIConstants.SUCCESS_COLOR);
        progressBar.setBackground(new Color(255, 255, 255, 50));
        progressBar.setBorder(BorderFactory.createEmptyBorder());
        progressBar.setPreferredSize(new Dimension(200, 8));
        leftPanel.add(progressBar);

        // Right side - Timer
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel timerTitleLabel = new JLabel("Time Remaining:");
        timerTitleLabel.setFont(UIConstants.FONT_NORMAL);
        timerTitleLabel.setForeground(new Color(255, 255, 255, 200));
        rightPanel.add(timerTitleLabel);

        timerLabel = new JLabel("05:00");
        timerLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        timerLabel.setForeground(UIConstants.WHITE);
        rightPanel.add(timerLabel);

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JRadioButton createStyledRadioButton() {
        JRadioButton radio = new JRadioButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw background
                Color bgColor = isSelected() ? UIConstants.PRIMARY_COLOR : UIConstants.LIGHT_GREY;
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);

                // Draw text
                g2.setColor(isSelected() ? UIConstants.WHITE : UIConstants.TEXT_COLOR);
                g2.setFont(UIConstants.FONT_NORMAL);
                int x = 40;
                int y = (getHeight() + g2.getFontMetrics().getAscent()) / 2 - 2;
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };
        radio.setPreferredSize(new Dimension(600, 45));
        radio.setOpaque(false);
        radio.setFocusPainted(false);
        radio.setBorderPainted(false);
        radio.setFont(UIConstants.FONT_NORMAL);
        return radio;
    }

    private void showQuestion(int idx) {
        if (idx < 0 || idx >= questions.size()) {
            return;
        }
        index = idx;
        group.clearSelection();

        Question q = questions.get(idx);
        lblQuestion.setText((idx + 1) + ". " + q.getQuestion());
        opts[0].setText(q.getOption1());
        opts[1].setText(q.getOption2());
        opts[2].setText(q.getOption3());
        opts[3].setText(q.getOption4());

        if (answers[idx] >= 0 && answers[idx] < opts.length) {
            opts[answers[idx]].setSelected(true);
        }

        // Update progress
        progressBar.setValue(idx + 1);
        progressLabel.setText("Question " + (idx + 1) + " of " + questions.size());

        repaint();
    }

    private void next() {
        saveAnswer();
        if (index + 1 < questions.size()) {
            showQuestion(index + 1);
        }
    }

    private void prev() {
        if (index - 1 >= 0) {
            showQuestion(index - 1);
        }
    }

    private void saveAnswer() {
        answers[index] = -1;
        for (int i = 0; i < 4; i++) {
            if (opts[i].isSelected()) {
                answers[index] = i;
                break;
            }
        }
    }

    private void startTimer() {
        timer = new Timer(1000, ev -> {
            timeLeft--;
            int m = timeLeft / 60;
            int s = timeLeft % 60;
            timerLabel.setText(String.format("%02d:%02d", m, s));

            if (timeLeft <= 60) {
                timerLabel.setForeground(UIConstants.DANGER_COLOR);
            }

            if (timeLeft <= 0) {
                timer.stop();
                submitExam();
            }
        });
        timer.start();
    }

    private void submitExam() {
        saveAnswer();

        // Calculate score
        int correctCount = 0;
        for (int i = 0; i < questions.size(); i++) {
            int selectedIndex = answers[i];
            if (selectedIndex == questions.get(i).getCorrectAnswer() - 1) {
                correctCount++;
            }
        }

        // Save result to CSV file storage
        try {
            FileManager.addResult(new Result(0, studentId, correctCount, questions.size(),
                    java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (timer != null) {
            timer.stop();
        }

        dispose();
        new ResultWindow(studentId, correctCount, questions.size()).setVisible(true);
    }
}
