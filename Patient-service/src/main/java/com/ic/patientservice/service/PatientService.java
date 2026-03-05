package com.ic.patientservice.service;

import com.ic.patientservice.GlobalExceptionHandler.EmailAlreadyExistException;
import com.ic.patientservice.GlobalExceptionHandler.PatientNotFoundException;
import com.ic.patientservice.dto.PatientRequestDto;
import com.ic.patientservice.dto.PatientResponseDto;
import com.ic.patientservice.mapper.PatientMapper;
import com.ic.patientservice.model.Patient;
import com.ic.patientservice.repository.PatientRepository;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<PatientResponseDto> getAll(){
        List<Patient> patients = repository.findAll();
        return patients.stream().map(PatientMapper::toDto).toList();
    }

    public PatientResponseDto addPatient(PatientRequestDto dto){
        if(repository.existsByEmail(dto.getEmail())){
            throw new EmailAlreadyExistException("Patient with this email already Exist "+ dto.getEmail());
        }
        Patient patient = repository.save(PatientMapper.toEntity(dto));
        return PatientMapper.toDto(patient);
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDto dto){
        Patient patient = repository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient with this id doesn't exists"));
        if(repository.existsByEmail(dto.getEmail())){
            throw new EmailAlreadyExistException("A patient with this email already exist");
        }
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));

        return PatientMapper.toDto(repository.save(patient));
    }


}
