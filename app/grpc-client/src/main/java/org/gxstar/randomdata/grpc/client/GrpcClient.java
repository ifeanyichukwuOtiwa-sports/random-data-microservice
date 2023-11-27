package org.gxstar.randomdata.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


@Getter
@Slf4j
public class GrpcClient {
    private final ManagedChannel channel;


    protected GrpcClient(final String name, final Integer port) {
        this.channel = ManagedChannelBuilder.forAddress(name, port)
                .keepAliveTime(30, TimeUnit.SECONDS)
                .usePlaintext()
                .build();
    }

    public void shutdownChannel() {
        if (channel != null && !channel.isShutdown()) {
            log.info("Shutting down grpc client channel");
            channel.shutdown();
            try {
                if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.info("forcefully shutting down grpc client channel");
                    channel.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("failed to shut down grpc client", e);
            }
        }
    }
}
