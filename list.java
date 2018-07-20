/* Gets File From the Storage server hosting file ... and saves that file as the same name in current directory 
*/
import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Set;
import java.lang.Exception;


public class list  {
	private Registry namingserver;
	private Service service_stub;
	private String IP_NAMING_SERVER;
	private int PORT_NAMING_SERVER;

	public list(String IP , String PORT ) throws RemoteException , NotBoundException {
		this.IP_NAMING_SERVER = IP ;
		this.PORT_NAMING_SERVER = Integer.parseInt(PORT);

		Registry namingserver = LocateRegistry.getRegistry(IP_NAMING_SERVER, PORT_NAMING_SERVER);
		service_stub = (Service)namingserver.lookup("NamingServer");
	}
	public void run () throws Exception
	{
		List <String> files = service_stub.list(); // get storge server hosting "path" file 
		for (String file : files)
			System.out.println(file);
	}
	public static void main (String args[] )throws  Exception {
		//new Get().run(args[0]);
		if (args.length < 2){ 
			System.err.println( "Bad usage  " + " IP address of naming server port of naming server ");
			System.exit(1);
		}
		list object = new list(args[0], args[1]); //2nd arg   ip of naming server
		object.run(); // 1st   arg file
		System.exit(1);
	}

}


