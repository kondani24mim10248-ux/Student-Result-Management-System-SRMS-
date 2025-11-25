package com.vityarthi.srms.util;

public class InputValidator {

    public static boolean isValidMark(double marks, double maxMarks) {
        return marks >= 0 && marks <= maxMarks;
    }
}

