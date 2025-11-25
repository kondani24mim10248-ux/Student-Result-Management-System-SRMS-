package com.vityarthi.srms.service;

import com.vityarthi.srms.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService {

    private final Map<Integer, Student> students = new HashMap<>();

    public boolean addStudent(Student student) {
        if (students.containsKey(student.getId())) {
            return false;
        }
        students.put(student.getId(), student);
        return true;
    }

    public Student getStudentById(int id) {
        return students.get(id);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public boolean updateStudent(int id, String newName, String newDept) {
        Student s = students.get(id);
        if (s == null) return false;
        s.setName(newName);
        s.setDepartment(newDept);
        return true;
    }

    public boolean deleteStudent(int id) {
        return students.remove(id) != null;
    }
}
