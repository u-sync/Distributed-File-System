import java.rmi.RemoteException;
import java.rmi.*;
import java.io.IOException;

public interface Command extends Remote  
{
	
	public boolean create (String file) throws RemoteException, IOException;

}

