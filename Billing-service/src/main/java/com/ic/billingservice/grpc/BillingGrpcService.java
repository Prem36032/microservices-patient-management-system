package com.ic.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest request, StreamObserver<BillingResponse> responseObserver ) {
        log.info("Got the Create Billing request {}", request.toString());

        BillingResponse res = BillingResponse.newBuilder().setAccountId("111").setStatus("Active").build();

        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
