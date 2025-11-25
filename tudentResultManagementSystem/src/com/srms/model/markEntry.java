package com.vityarthi.srms.model;

public class MarkEntry {
    private int studentId;
    private String subjectCode;
    private double marksObtained;

    public MarkEntry(int studentId, String subjectCode, double marksObtained) {
        this.studentId = studentId;
        this.subjectCode = subjectCode;
        this.marksObtained = marksObtained;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(double marksObtained) {
        this.marksObtained = marksObtained;
    }
}
