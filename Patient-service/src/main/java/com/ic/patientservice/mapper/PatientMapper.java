package com.ic.patientservice.mapper;
import com.ic.patientservice.dto.PatientResponseDto;
import com.ic.patientservice.model.Patient;

public class PatientMapper {
     public static PatientResponseDto toDto(Patient p){
         PatientResponseDto dto = new PatientResponseDto();
         dto.setAddress(p.getAddress());
         dto.setEmail(p.getEmail());
         dto.setName(p.getName());
         dto.setRegisteredDate(p.getRegisteredDate().toString());
         return dto;
     }

}
