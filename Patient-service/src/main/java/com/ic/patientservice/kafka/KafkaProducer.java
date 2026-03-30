package com.ic.patientservice.kafka;

import com.ic.patientservice.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;



    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder().setName(patient.getName()).setEmail(patient.getEmail()).setPatientId(
                patient.getId().toString()).setEventType("PATIENT_CREATED").build();

        try {
            kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e) {
            log.error("Error in sending Patient Event {}", event);
        }
    }
}
