package com.vityarthi.srms;

import com.vityarthi.srms.model.Result;
import com.vityarthi.srms.model.Student;
import com.vityarthi.srms.model.Subject;
import com.vityarthi.srms.service.ResultService;
import com.vityarthi.srms.service.StudentService;
import com.vityarthi.srms.service.SubjectService;
import com.vityarthi.srms.util.InputValidator;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final StudentService studentService = new StudentService();
    private static final SubjectService subjectService = new SubjectService();
    private static final ResultService resultService = new ResultService(studentService, subjectService);

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> listStudents();
                case 3 -> addSubject();
                case 4 -> listSubjects();
                case 5 -> enterMarks();
                case 6 -> viewStudentResult();
                case 7 -> viewAllResults();
                case 8 -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=======================================");
        System.out.println("   Student Result Management System");
        System.out.println("=======================================");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Add Subject");
        System.out.println("4. View All Subjects");
        System.out.println("5. Enter/Update Marks");
        System.out.println("6. View Result of a Student");
        System.out.println("7. View All Results");
        System.out.println("8. Exit");
        System.out.println("=======================================");
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void addStudent() {
        System.out.println("--- Add Student ---");
        int id = readInt("Enter student ID: ");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter department/section: ");
        String dept = scanner.nextLine();

        Student student = new Student(id, name, dept);
        boolean added = studentService.addStudent(student);
        if (added) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Student with this ID already exists.");
        }
    }

    private static void listStudents() {
        System.out.println("--- All Students ---");
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void addSubject() {
        System.out.println("--- Add Subject ---");
        System.out.print("Enter subject code: ");
        String code = scanner.nextLine().trim();
        System.out.print("Enter subject name: ");
        String name = scanner.nextLine();
        double maxMarks = readDouble("Enter maximum marks: ");

        Subject subject = new Subject(code, name, maxMarks);
        boolean added = subjectService.addSubject(subject);
        if (added) {
            System.out.println("Subject added successfully.");
        } else {
            System.out.println("Subject with this code already exists.");
        }
    }

    private static void listSubjects() {
        System.out.println("--- All Subjects ---");
        List<Subject> subjects = subjectService.getAllSubjects();
        if (subjects.isEmpty()) {
            System.out.println("No subjects found.");
            return;
        }

        for (Subject s : subjects) {
            System.out.println(s);
        }
    }

    private static void enterMarks() {
        System.out.println("--- Enter/Update Marks ---");
        int studentId = readInt("Enter student ID: ");

        if (studentService.getStudentById(studentId) == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter subject code: ");
        String subjectCode = scanner.nextLine().trim();

        Subject subject = subjectService.getSubjectByCode(subjectCode);
        if (subject == null) {
            System.out.println("Subject not found.");
            return;
        }

        double marks = readDouble("Enter marks obtained (0 - " + subject.getMaxMarks() + "): ");

        if (!InputValidator.isValidMark(marks, subject.getMaxMarks())) {
            System.out.println("Invalid marks. Must be between 0 and " + subject.getMaxMarks());
            return;
        }

        resultService.addOrUpdateMark(studentId, subjectCode, marks);
        System.out.println("Marks saved successfully.");
    }

    private static void viewStudentResult() {
        System.out.println("--- View Student Result ---");
        int studentId = readInt("Enter student ID: ");

        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        Result result = resultService.calculateResultForStudent(studentId);
        if (result == null) {
            System.out.println("No marks found for this student.");
            return;
        }

        System.out.println("Result for: " + student.getName() + " (ID: " + student.getId() + ")");
        resultService.printDetailedResult(studentId);
    }

    private static void viewAllResults() {
        System.out.println("--- All Results ---");
        List<Result> results = resultService.calculateAllResults();
        if (results.isEmpty()) {
            System.out.println("No results available.");
            return;
        }

        System.out.printf("%-10s %-20s %-10s %-10s %-10s%n",
                "ID", "Name", "Total", "Percent", "Grade");
        for (Result r : results) {
            Student s = studentService.getStudentById(r.getStudentId());
            if (s != null) {
                System.out.printf("%-10d %-20s %-10.2f %-10.2f %-10s%n",
                        s.getId(),
                        s.getName(),
                        r.getTotalMarks(),
                        r.getPercentage(),
                        r.getGrade());
            }
        }
    }
}
