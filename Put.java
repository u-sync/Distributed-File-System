import java.rmi.registry.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.io.File;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.Inet4Address;
import java.lang.Exception;

public class Put  {
	private static ServerSocket ssock ;
	private static Socket socket;

	private Registry namingserver; ////
	private Service service_stub;
	private String IP_NAMING_SERVER;
	private int PORT_NAMING_SERVER;

	public Put(String IP , String PORT, int Server_Port ) throws Exception {
		this.IP_NAMING_SERVER = IP ;
		this.PORT_NAMING_SERVER = Integer.parseInt(PORT);

		ssock = new ServerSocket(Server_Port);

		Registry namingserver = LocateRegistry.getRegistry(IP_NAMING_SERVER, PORT_NAMING_SERVER);

		service_stub = (Service)namingserver.lookup("NamingServer");
	}
	private void transfer(String args[] ) throws Exception {

		//if ( service_stub.put(args[4], args[3] , args[0]) == false  ){ ///write file 
		//		System.out.println("File exist ");
		//	System.exit(1);
		//}
		String path = args[0];

		new Thread (new Runnable (){
			public void run(){
				System.out.println(" listening ...");
			try{
			Socket socket = ssock.accept();

			String anim = "|/-\\";
				File file = new File(path);
				FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis); 
				OutputStream os = socket.getOutputStream();
				byte[] contents;
				long fileLength = file.length(); 
				long current = 0;
				long start = System.nanoTime();
				while(current!=fileLength){ 
					int size = 10000;
					if(fileLength - current >= size)
						current += size;    
					else{ 
						size = (int)(fileLength - current); 
						current = fileLength;
					} 
					contents = new byte[size]; 
					bis.read(contents, 0, size); 
					os.write(contents);
					int x = (int)((current * 100)/fileLength) ;

					String data = "\r" + anim.charAt(x % anim.length()) + " " + x + "%" + "Sent" ;
					System.out.write(data.getBytes());

				}   
				os.flush(); 
				System.out.println("File sent succesfully!");
				}catch(Exception e){ e.printStackTrace();}
			}

		}).start(); 

		service_stub.put(args[4], args[3] , args[0]);

		//run(args[0]);

	}
	public static void main(String[] args) throws Exception {
		if (args.length < 5){ 
			System.err.println( "Bad usage  " + "file | IP address of naming server | port of naming server | port for tcp  |its own ip "); //contact naming serv to put file 
			System.exit(1);
		}
		Put object = new Put(args[1], args[2], Integer.parseInt(args[3])); //2nd arg   ip of naming server
		object.transfer(args);
		
	}
}

