package de.lmu;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrüherkennungIF extends Remote {

    void analysieren(Röntgenbild bild, CallbackIF referenz) throws RemoteException;
}
