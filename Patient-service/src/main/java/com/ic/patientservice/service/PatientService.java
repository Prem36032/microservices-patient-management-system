package com.ic.patientservice.service;

import billing.BillingResponse;
import com.ic.patientservice.GlobalExceptionHandler.EmailAlreadyExistException;
import com.ic.patientservice.GlobalExceptionHandler.PatientNotFoundException;
import com.ic.patientservice.dto.PatientRequestDto;
import com.ic.patientservice.dto.PatientResponseDto;
import com.ic.patientservice.grpc.BillingServiceGrpcClient;
import com.ic.patientservice.mapper.PatientMapper;
import com.ic.patientservice.model.Patient;
import com.ic.patientservice.repository.PatientRepository;
import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository repository;
    private final BillingServiceGrpcClient grpcClient;

    public PatientService(PatientRepository repository, BillingServiceGrpcClient grpcClient) {
        this.repository = repository;
        this.grpcClient = grpcClient;
    }

    public List<PatientResponseDto> getAll(){
        List<Patient> patients = repository.findAll();
        BillingResponse response = grpcClient.CreateBillingAccount("12","12","122");
        log.info(String.valueOf(response));
        return patients.stream().map(PatientMapper::toDto).toList();
    }

    public PatientResponseDto addPatient(PatientRequestDto dto){
        if(repository.existsByEmail(dto.getEmail())){
            throw new EmailAlreadyExistException("Patient with this email already Exist "+ dto.getEmail());
        }
        Patient patient = repository.save(PatientMapper.toEntity(dto));
        log.info("Request before grpc {}",dto);
        BillingResponse res = grpcClient.CreateBillingAccount(patient.getId().toString(),patient.getName(), patient.getEmail());
        return PatientMapper.toDto(patient);
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDto dto){
        Patient patient = repository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient with this id doesn't exists"));
        if(repository.existsByEmailAndIdNot(dto.getEmail(), id)){
            throw new EmailAlreadyExistException("A patient with this email already exist");
        }
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));

        return PatientMapper.toDto(repository.save(patient));
    }

    public void deletePatient(UUID id){


        repository.deleteById(id);
    }


}
