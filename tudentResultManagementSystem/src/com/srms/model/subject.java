package com.vityarthi.srms.model;

public class Subject {
    private String code;
    private String name;
    private double maxMarks;

    public Subject(String code, String name, double maxMarks) {
        this.code = code;
        this.name = name;
        this.maxMarks = maxMarks;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }

    @Override
    public String toString() {
        return "Subject{" +
               "code='" + code + '\'' +
               ", name='" + name + '\'' +
               ", maxMarks=" + maxMarks +
               '}';
    }
}
