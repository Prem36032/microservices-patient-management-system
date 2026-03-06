package com.ic.patientservice.controller;

import com.ic.patientservice.dto.PatientRequestDto;
import com.ic.patientservice.dto.PatientResponseDto;
import com.ic.patientservice.dto.validationGroups.CreatePatientValidationGroup;
import com.ic.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patient")
@Tag(name = "Patient Management", description = "Endpoints for Managing Patients")
public class PatientController {
    private final PatientService service;

    public PatientController(PatientService service){
        this.service = service;
    }

    @GetMapping
    @Operation(description = "For Retrieving all Patients")
    public ResponseEntity<List<PatientResponseDto>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    @Operation(description = "Add new Patient")
    public ResponseEntity<PatientResponseDto> addPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDto body){
        return ResponseEntity.ok(service.addPatient(body));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update existing patient")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id, @RequestBody @Validated({Default.class}) PatientRequestDto dto){
        return ResponseEntity.ok(service.updatePatient(id,dto));
    }
    @DeleteMapping("/{id}")
    @Operation(description = "Delete a patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        service.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
