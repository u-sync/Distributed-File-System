import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;
import java.rmi.registry.Registry;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.lang.Exception;


public class NamingServer extends UnicastRemoteObject implements Registration , Service {

	private Map <String , List <Storage> > Replicaloc;
	private Map <Storage ,List <String> >  Replica;
	private Set <Storage> StorageServers;

	public NamingServer () throws RemoteException {
		Replicaloc = new HashMap <String , List<Storage> >();
		Replica = new HashMap <Storage, List<String>> ();
		StorageServers = new HashSet <Storage> ();
	}

	public synchronized void start(String PORT) throws RemoteException  {

		Registry r = LocateRegistry.createRegistry(Integer.parseInt(PORT));		
		r.rebind("NamingServer", new NamingServer()); // bind remote obj with name 
	}
	public String[] register (String IP_STORAGE_SERVER , int PORT_STORAGE_SERVER , String [] files, Storage command_stub ) throws RemoteException, NotBoundException{	


		StorageServers.add(command_stub); //check if server is active 

		System.out.println("Storage server : "+  IP_STORAGE_SERVER + " "+PORT_STORAGE_SERVER+ " connected");
		for (String file : files) {
			if (Replicaloc.get(file) == null ){
				List<Storage> temp = new ArrayList<Storage>();
				temp.add(command_stub);
				Replicaloc.put(file , temp);
			}
			else
				Replicaloc.get(file).add(command_stub);
		}
		if ( Replica.get(command_stub) == null){
			List<String> temp = new ArrayList<String>();
			temp.add(new String(IP_STORAGE_SERVER));
			temp.add(new String(PORT_STORAGE_SERVER + "") );
			Replica.put( command_stub,  temp);
		}
		//System.out.println(Replicaloc.keySet());
		return new String[2];
	}
	public boolean createFile(String file ) throws RemoteException, FileNotFoundException{

		return true ;	
	}
	public List<String> getStorage(String file ) throws RemoteException, FileNotFoundException , IOException {

		Storage  lucky_replicaserver = Replicaloc.get(file).get(new Random().nextInt(Replicaloc.get(file).size()));
		System.out.println("Lucky Replicaserver " + Replica.get(lucky_replicaserver));

		lucky_replicaserver.read(file); // start read thread at server 
		return Replica.get(lucky_replicaserver);
	}	
	public boolean put(String IP , String PORT , String path )throws Exception {
			System.out.println("Senfing file to : ");

			//if (Replicaloc.get(path) == null){
					System.out.println("File "+ path + "not exist" + " storing file ");

					for (Storage stub : StorageServers){
		
						stub.write(IP , PORT , path);

					}
					return true;
			//}
			//else 
			//		return false;

	}
	public List <String> list() throws Exception {
		return new ArrayList( Replicaloc.keySet()) ;
	}
	public static void main (String args[] ) throws RemoteException  , NotBoundException , UnknownHostException, IOException {
		if (args.length < 2){
			System.err.println( "Bad usage:   " + "ITS OWN IP Address  Port oto use > 1100");
			System.exit(1);
		}
		System.setProperty("java.rmi.server.hostname",args[0]);//ip  
		new NamingServer().start(args[1]);
		System.out.println("\nListening Incoming  Connections on :: " + args[1]);
	}

}
