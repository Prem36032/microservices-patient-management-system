package com.ic.patientservice.mapper;
import com.ic.patientservice.dto.PatientRequestDto;
import com.ic.patientservice.dto.PatientResponseDto;
import com.ic.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
     public static PatientResponseDto toDto(Patient p){
         PatientResponseDto dto = new PatientResponseDto();
         dto.setAddress(p.getAddress());
         dto.setEmail(p.getEmail());
         dto.setName(p.getName());
         dto.setRegisteredDate(p.getRegisteredDate().toString());
         return dto;
     }

     public static Patient toEntity(PatientRequestDto dto){
         Patient patient = new Patient();

         patient.setName(dto.getName());
         patient.setAddress(dto.getAddress());
         patient.setEmail(dto.getEmail());
         patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
         patient.setRegisteredDate(LocalDate.parse(dto.getRegisteredDate()));
         return patient;
     }

}
