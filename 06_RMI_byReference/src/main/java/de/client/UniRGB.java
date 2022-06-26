package de.client;

import de.lmu.Bericht;
import de.lmu.FrüherkennungIF;
import de.lmu.Röntgenbild;
import de.lmu.RöntgenbildIF;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class UniRGB {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        Röntgenbild bild1 = new Röntgenbild(null,null,null);
        Registry r = LocateRegistry.getRegistry("localhost",1099);
        FrüherkennungIF erkennung_server = (FrüherkennungIF) r.lookup("analysieren");

        RöntgenbildIF clientStub = (RöntgenbildIF) UnicastRemoteObject.exportObject(bild1,0);
        Bericht antwort = erkennung_server.analysieren(clientStub);
        System.out.println(antwort);

    }
}
