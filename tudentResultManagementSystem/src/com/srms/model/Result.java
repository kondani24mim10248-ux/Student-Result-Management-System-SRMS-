package com.vityarthi.srms.model;

public class Result {
    private int studentId;
    private double totalMarks;
    private double percentage;
    private String grade;
    private String status; // Pass / Fail

    public Result(int studentId, double totalMarks, double percentage, String grade, String status) {
        this.studentId = studentId;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.grade = grade;
        this.status = status;
    }

    public int getStudentId() {
        return studentId;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getGrade() {
        return grade;
    }

    public String getStatus() {
        return status;
    }
}
