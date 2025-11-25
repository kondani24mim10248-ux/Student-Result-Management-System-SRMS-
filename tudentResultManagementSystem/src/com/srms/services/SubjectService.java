package com.vityarthi.srms.service;

import com.vityarthi.srms.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectService {

    private final Map<String, Subject> subjects = new HashMap<>();

    public boolean addSubject(Subject subject) {
        if (subjects.containsKey(subject.getCode())) {
            return false;
        }
        subjects.put(subject.getCode(), subject);
        return true;
    }

    public Subject getSubjectByCode(String code) {
        return subjects.get(code);
    }

    public List<Subject> getAllSubjects() {
        return new ArrayList<>(subjects.values());
    }
}
