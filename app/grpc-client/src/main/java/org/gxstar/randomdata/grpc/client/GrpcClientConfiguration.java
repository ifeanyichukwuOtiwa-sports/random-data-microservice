package org.gxstar.randomdata.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.gxstar.randomdatatwo.api.IndividualServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class GrpcClientConfiguration {
    @Value("${grpc.client.host:localhost}")
    private String name;
    @Value("${grpc.client.port:6565}")
    private int port;
    @Bean
    public IndividualServiceGrpc.IndividualServiceBlockingStub individualServerStub(final GrpcClient client) {
        return IndividualServiceGrpc.newBlockingStub(client.getChannel());
    }


    @Bean(destroyMethod = "shutdownChannel")
    public GrpcClient channel() {
        return new GrpcClient(name, port);
    }
}
