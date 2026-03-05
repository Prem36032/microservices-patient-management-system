package com.ic.patientservice.GlobalExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>  handleValidationExceptions(MethodArgumentNotValidException ex){
        log.error("e: ", ex);
        HashMap<String,String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err-> errors.put(err.getField(),err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyExistException(EmailAlreadyExistException e){
        HashMap<String,String> error = new HashMap<>();

        error.put("Message", e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);

    }
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<HashMap<String,String>> handlePatientNotFoundException(PatientNotFoundException e){
        HashMap<String,String> errors = new HashMap<>();
        errors.put("Message", e.getMessage());
        return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
    }

}
