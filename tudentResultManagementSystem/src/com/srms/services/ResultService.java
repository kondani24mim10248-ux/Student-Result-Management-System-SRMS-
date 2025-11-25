package com.vityarthi.srms.service;

import com.vityarthi.srms.model.MarkEntry;
import com.vityarthi.srms.model.Result;
import com.vityarthi.srms.model.Student;
import com.vityarthi.srms.model.Subject;
import com.vityarthi.srms.util.GradeCalculator;

import java.util.*;

public class ResultService {

    private final StudentService studentService;
    private final SubjectService subjectService;

    // studentId -> list of marks
    private final Map<Integer, List<MarkEntry>> marksByStudent = new HashMap<>();

    public ResultService(StudentService studentService, SubjectService subjectService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    public void addOrUpdateMark(int studentId, String subjectCode, double marks) {
        List<MarkEntry> entries = marksByStudent.computeIfAbsent(studentId, k -> new ArrayList<>());

        for (MarkEntry entry : entries) {
            if (entry.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                entry.setMarksObtained(marks);
                return;
            }
        }

        entries.add(new MarkEntry(studentId, subjectCode, marks));
    }

    public Result calculateResultForStudent(int studentId) {
        List<MarkEntry> entries = marksByStudent.get(studentId);
        if (entries == null || entries.isEmpty()) {
            return null;
        }

        double totalObtained = 0;
        double totalMax = 0;

        for (MarkEntry entry : entries) {
            Subject subject = subjectService.getSubjectByCode(entry.getSubjectCode());
            if (subject != null) {
                totalObtained += entry.getMarksObtained();
                totalMax += subject.getMaxMarks();
            }
        }

        if (totalMax == 0) return null;

        double percentage = (totalObtained / totalMax) * 100.0;
        String grade = GradeCalculator.calculateGrade(percentage);
        String status = percentage >= 40 ? "Pass" : "Fail";

        return new Result(studentId, totalObtained, percentage, grade, status);
    }

    public List<Result> calculateAllResults() {
        List<Result> results = new ArrayList<>();
        for (Integer studentId : marksByStudent.keySet()) {
            Result r = calculateResultForStudent(studentId);
            if (r != null) {
                results.add(r);
            }
        }
        return results;
    }

    public void printDetailedResult(int studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        List<MarkEntry> entries = marksByStudent.get(studentId);
        if (entries == null || entries.isEmpty()) {
            System.out.println("No marks entered for this student.");
            return;
        }

        System.out.println("---------------------------------------");
        System.out.println("Student: " + student.getName() + " (ID: " + student.getId() + ")");
        System.out.println("Department: " + student.getDepartment());
        System.out.println("---------------------------------------");
        System.out.printf("%-10s %-20s %-10s %-10s%n", "Code", "Subject", "Marks", "Max");

        double totalObtained = 0;
        double totalMax = 0;

        for (MarkEntry entry : entries) {
            Subject subject = subjectService.getSubjectByCode(entry.getSubjectCode());
            if (subject != null) {
                System.out.printf("%-10s %-20s %-10.2f %-10.2f%n",
                        subject.getCode(),
                        subject.getName(),
                        entry.getMarksObtained(),
                        subject.getMaxMarks());
                totalObtained += entry.getMarksObtained();
                totalMax += subject.getMaxMarks();
            }
        }

        double percentage = (totalObtained / totalMax) * 100.0;
        String grade = GradeCalculator.calculateGrade(percentage);
        String status = percentage >= 40 ? "Pass" : "Fail";

        System.out.println("---------------------------------------");
        System.out.printf("Total Marks: %.2f / %.2f%n", totalObtained, totalMax);
        System.out.printf("Percentage : %.2f%%%n", percentage);
        System.out.println("Grade      : " + grade);
        System.out.println("Status     : " + status);
        System.out.println("---------------------------------------");
    }
}
