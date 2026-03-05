package com.ic.patientservice.GlobalExceptionHandler;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
