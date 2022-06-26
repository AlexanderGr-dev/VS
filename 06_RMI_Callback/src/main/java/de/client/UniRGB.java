package de.client;

import de.lmu.Bericht;
import de.lmu.CallbackIF;
import de.lmu.FrüherkennungIF;
import de.lmu.Röntgenbild;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class UniRGB implements CallbackIF {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        Röntgenbild bild1 = new Röntgenbild(null,null,null);
        Registry r = LocateRegistry.getRegistry("localhost",1099);
        FrüherkennungIF erkennung_server = (FrüherkennungIF) r.lookup("analysieren");

        CallbackIF c = new UniRGB();
        CallbackIF callbackStub = (CallbackIF) UnicastRemoteObject.exportObject(c,0);
        erkennung_server.analysieren(bild1, callbackStub);
        System.out.println("Kann trotzdem weitermachen");
    }

    @Override
    public void onResult(Bericht erg) throws RemoteException {
        System.out.println(erg);
    }
}
