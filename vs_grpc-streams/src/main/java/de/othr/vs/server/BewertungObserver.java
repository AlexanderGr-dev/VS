package de.othr.vs.server;

import de.othr.grpc.Bewertung;
import io.grpc.stub.StreamObserver;

public class BewertungObserver implements StreamObserver<de.othr.grpc.Bewertung> {

    @Override
    public void onNext(Bewertung bewertung) {
        System.out.println("Received: " + bewertung.toString());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("RPC call error: " + throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("RPC call completed");
    }
}
