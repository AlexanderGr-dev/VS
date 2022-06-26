package de.lmu;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrüherkennungIF extends Remote {

    Bericht analysieren(RöntgenbildIF bild) throws RemoteException;
}
