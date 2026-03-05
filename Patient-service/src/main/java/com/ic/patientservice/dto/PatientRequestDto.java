package com.ic.patientservice.dto;

import com.ic.patientservice.dto.validationGroups.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequestDto {

    @NotBlank(message = "Name can't be empty")
    @Size(min = 2)
    private String name;

    @NotBlank(message = "Email can't be empty")
    @Email( message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Address Can't be empty")
    private String address;

    @NotBlank( groups = {CreatePatientValidationGroup.class}, message = "registeredDate Can't be empty")
    private String registeredDate;

    @NotBlank(message = "Date of birth can't be empty")
    private String dateOfBirth;
}
