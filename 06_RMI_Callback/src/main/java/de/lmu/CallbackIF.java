package de.lmu;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackIF extends Remote {

    void onResult(Bericht erg) throws RemoteException;
}
