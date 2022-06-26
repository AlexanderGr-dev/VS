package de.othr.vs.client;

import de.othr.grpc.MesswertServiceGrpc;
import de.othr.vs.server.BewertungObserver;
import de.othr.vs.server.MesswertServer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class Client {

    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(MesswertServer.SERVER_GRPC_HOST,
                        MesswertServer.SERVER_GRPC_PORT)
                .usePlaintext()
                .build();

        // Stub generieren (je nach Anwendungsfall stub, blocking stub oder future stub)
        MesswertServiceGrpc.MesswertServiceStub stub = MesswertServiceGrpc.newStub(channel);

        // Messages generieren und Service aufrufen
        de.othr.grpc.Messwert value = de.othr.grpc.Messwert.newBuilder().setValue(3.2).setLocation("Wasserstand").build();
        StreamObserver<de.othr.grpc.Messwert> messageStreamObserver = stub.monitor(new BewertungObserver());

        messageStreamObserver.onNext(value);
        messageStreamObserver.onNext(value);
        messageStreamObserver.onNext(value);
        messageStreamObserver.onNext(value);
        messageStreamObserver.onNext(value);
        messageStreamObserver.onNext(value);
        messageStreamObserver.onNext(value);

        //System.out.println(response);

        // Client noch nicht beenden, Callbacks vom Server (via StreamObserver::onNext, ...)
        // werden sonst nicht mehr empfangen
        channel.awaitTermination(30L, TimeUnit.SECONDS);
    }
}
