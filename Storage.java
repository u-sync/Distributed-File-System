import java.io.*;
import java.rmi.*;
import java.rmi.RemoteException;
import java.io.IOException;

public interface Storage  extends Remote 
{
	public byte[] read( ) throws RemoteException;
	public void read(String path ) throws IOException , RemoteException;
	public boolean create (String file) throws RemoteException, IOException;
	public void write(String IP , String PORT , String  path ) throws UnknownHostException, IOException;
	
}

