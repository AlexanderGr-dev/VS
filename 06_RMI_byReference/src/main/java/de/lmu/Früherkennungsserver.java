package de.lmu;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Früherkennungsserver implements FrüherkennungIF{

    @Override
    public Bericht analysieren(RöntgenbildIF bild) throws RemoteException {

        //Wird remote aufgerufen
        bild.getDate();

        Bericht b = new Bericht(null,"Gehirntumor","Chemo");
        return b;
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        FrüherkennungIF erkennung = new Früherkennungsserver();
        FrüherkennungIF stub = (FrüherkennungIF) UnicastRemoteObject.exportObject(erkennung,0);
        Registry r = LocateRegistry.createRegistry(1099);
        r.bind("analysieren",stub);
    }
}
