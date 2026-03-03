package com.ic.patientservice.controller;

import com.ic.patientservice.dto.PatientRequestDto;
import com.ic.patientservice.dto.PatientResponseDto;
import com.ic.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService service;

    public PatientController(PatientService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> addPatient(@Valid @RequestBody PatientRequestDto body){
        return ResponseEntity.ok(service.addPatient(body));
    }
}
