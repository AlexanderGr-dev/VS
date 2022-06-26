package de.lmu;

import java.rmi.RemoteException;

public class Analysiere implements Runnable{

    Röntgenbild bild;
    CallbackIF callback;

    public Analysiere(Röntgenbild bild, CallbackIF callback) {
        this.bild = bild;
        this.callback = callback;
    }

    @Override
    public void run() {
        Bericht b = new Bericht(null,"Gehirntumor","Chemo");

        try {
            Thread.sleep(5000);
            this.callback.onResult(b);
        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
