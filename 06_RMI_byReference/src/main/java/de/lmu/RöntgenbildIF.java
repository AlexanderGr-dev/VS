package de.lmu;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface RöntgenbildIF extends Remote {

    Date getDate() throws RemoteException;
}
