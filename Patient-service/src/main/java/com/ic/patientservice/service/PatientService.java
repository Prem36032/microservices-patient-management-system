package com.ic.patientservice.service;

import com.ic.patientservice.dto.PatientResponseDto;
import com.ic.patientservice.mapper.PatientMapper;
import com.ic.patientservice.model.Patient;
import com.ic.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<PatientResponseDto> getAll(){
        List<Patient> patients = repository.findAll();
        return patients.stream().map(PatientMapper::toDto).toList();
    }

}
