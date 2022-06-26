package de.othr.vs.server;

import com.google.protobuf.Timestamp;
import de.othr.grpc.Bewertung;
import de.othr.grpc.Messwert;
import de.othr.grpc.MesswertServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.time.Instant;


public class MesswertService extends MesswertServiceGrpc.MesswertServiceImplBase {

    public void save(de.othr.grpc.Messwert request, StreamObserver<com.google.protobuf.Empty> responseObserver){
        System.out.println("Speichere Messwert: " + request.getLocation() + " " + request.getValue());
    }

    public StreamObserver<de.othr.grpc.Messwert> monitor(StreamObserver<de.othr.grpc.Bewertung> responseObserver) {

      return new Ueberwachungsvorgng(responseObserver);
    }
}

class Ueberwachungsvorgng implements StreamObserver<de.othr.grpc.Messwert> {

    private StreamObserver<de.othr.grpc.Bewertung> callback;
    private int messwertCounter = 0;

    public Ueberwachungsvorgng(StreamObserver<Bewertung> callback) {
        this.callback = callback;
    }

    @Override
    public void onNext(Messwert messwert) {
        System.out.println("Messwert empfangen: " +messwert);
        callback.onNext(de.othr.grpc.Bewertung.newBuilder().setAction("Schleuse um " + messwertCounter + " oeffnen").build());
        messwertCounter++;
        if(messwertCounter>=5){
            callback.onCompleted();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Fehler beim empfangen eines Messwerts");
    }

    @Override
    public void onCompleted() {
        System.out.println("Es kommen keine Messwerte mehr, Client hat beendet");
    }
}
