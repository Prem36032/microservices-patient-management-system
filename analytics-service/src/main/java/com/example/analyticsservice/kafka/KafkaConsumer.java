package com.example.analyticsservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

import java.util.Arrays;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics")
    public void consumeEvent(byte[] message){
        try{
            PatientEvent event = PatientEvent.parseFrom(message);
            log.info("Event Received {} : {} : {}",event.getPatientId(),event.getName(),event.getEmail());
        } catch (Exception e) {
            log.error("Error in deserializing message {}",e.getMessage());
        }

    }
}
