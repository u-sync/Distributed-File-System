import java.rmi.RemoteException;
import java.io.*;
import java.rmi.*;
import java.util.List;
import java.lang.Exception;

public interface Service  extends Remote
{
        public boolean createFile(String file ) throws RemoteException, FileNotFoundException;
		public List<String> getStorage(String file ) throws RemoteException, FileNotFoundException ,IOException ;	
		public boolean put(String IP , String PORT , String path ) throws Exception;
		public List<String> list() throws Exception;

}

