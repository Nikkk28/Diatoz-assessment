package com.diatoz.assesment.exceptions;

public class CourseFullException extends RuntimeException {
    public CourseFullException(String message) {
        super(message);
    }
}