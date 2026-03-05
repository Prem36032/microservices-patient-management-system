package com.ic.patientservice.controller;

import com.ic.patientservice.dto.PatientRequestDto;
import com.ic.patientservice.dto.PatientResponseDto;
import com.ic.patientservice.dto.validationGroups.CreatePatientValidationGroup;
import com.ic.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<PatientResponseDto> addPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDto body){
        return ResponseEntity.ok(service.addPatient(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id, @RequestBody @Validated({Default.class}) PatientRequestDto dto){
        return ResponseEntity.ok(service.updatePatient(id,dto));
    }
}
