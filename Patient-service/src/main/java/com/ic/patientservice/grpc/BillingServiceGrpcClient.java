package com.ic.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {

    BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient (@Value("${billing.service.address:localhost}") String serverAddress, @Value("${billing.service.port:9001}") int serverPort){
        log.info("this nigga running on {}:{}",serverAddress,serverPort );
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,serverPort).usePlaintext().build();
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse CreateBillingAccount(String patientId, String name,String email){
        BillingRequest request = BillingRequest.newBuilder().setName(name).setEmail(email).setPatientId(patientId).build();
        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received Response {}",response);
        return response;
    }
}
