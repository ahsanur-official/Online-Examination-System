package com.onlineexam.model;

import java.util.Date;
import java.sql.Timestamp;

public class Result {
    private int id;
    private int studentId;
    private int score;
    private Date examDate;
    private int totalQuestions;

    public Result() {
    }

    public Result(int id, int studentId, int score, int totalQuestions, String examDate) {
        this.id = id;
        this.studentId = studentId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.examDate = Timestamp.valueOf(examDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
