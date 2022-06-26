package de.client;

import de.lmu.Bericht;
import de.lmu.FrüherkennungIF;
import de.lmu.Röntgenbild;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class UniRGB {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        Röntgenbild bild1 = new Röntgenbild(null,null,null);
        Registry r = LocateRegistry.getRegistry("localhost",1099);
        FrüherkennungIF erkennung_server = (FrüherkennungIF) r.lookup("analysieren");

        Bericht antwort = erkennung_server.analysieren(bild1);
        System.out.println(antwort);

    }
}
