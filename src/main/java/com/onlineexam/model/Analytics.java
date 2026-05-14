package com.onlineexam.model;

/**
 * Analytics - Model for storing student analytics data
 */
public class Analytics {

    private int studentId;
    private int totalExamsTaken;
    private int averageScore;
    private int highestScore;
    private int lowestScore;
    private double passingPercentage;
    private String favoriteSubject;
    private int totalStudyHours;

    public Analytics() {
    }

    public Analytics(int studentId, int totalExamsTaken, int averageScore,
            int highestScore, int lowestScore, double passingPercentage) {
        this.studentId = studentId;
        this.totalExamsTaken = totalExamsTaken;
        this.averageScore = averageScore;
        this.highestScore = highestScore;
        this.lowestScore = lowestScore;
        this.passingPercentage = passingPercentage;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTotalExamsTaken() {
        return totalExamsTaken;
    }

    public void setTotalExamsTaken(int totalExamsTaken) {
        this.totalExamsTaken = totalExamsTaken;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(int lowestScore) {
        this.lowestScore = lowestScore;
    }

    public double getPassingPercentage() {
        return passingPercentage;
    }

    public void setPassingPercentage(double passingPercentage) {
        this.passingPercentage = passingPercentage;
    }

    public String getFavoriteSubject() {
        return favoriteSubject;
    }

    public void setFavoriteSubject(String favoriteSubject) {
        this.favoriteSubject = favoriteSubject;
    }

    public int getTotalStudyHours() {
        return totalStudyHours;
    }

    public void setTotalStudyHours(int totalStudyHours) {
        this.totalStudyHours = totalStudyHours;
    }
}
