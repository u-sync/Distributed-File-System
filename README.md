# Distributed-File-System

Implementation of TCP/IP & Java RMI based DFS  
=============================================

The code attempts to implement Distributed File System:

> Abhishek Negi, Manish Thakur

List of files
-------------
1. Command.java       (Interface for creating file in DFS).
2. Get.java           (Get file from DFS(Storage Server Hosting File) and store in Current Directory).        
3. NamingServer.java  (NameServer for DFS).
4. Put.java           (Put a file in DFS).
5. Registration.java  (Interface for registering a StorageServer to NameServer).
6. Services.java      (Client Interface to create,get storageserver of a file and list files in DFS).
7. list.java          (Show files in DFS).


Usage
-----
To Start NameServer :

    java NamimgServer [IP-NameServer] [Port-NameServer(to use for RMI communication)]

To Start StorageServer on any PC in the network :

    java StorageServer [Own-Ip] [PORT(for tcp)] [Port(for RMI)] [IP NameServer] [RMI port In NameServer]


To Get , Put and List file in DFS: 
    
    java Get  [File-Name] [IP-NameServer] [RMI port In NameServer]
    java Put  [File-Name] [IP-NameServer] [RMI port In NameServer] [Port(for TCP)] [Own-IP]
    java list [File-Name] [IP-NameServer] [RMI port In NameServer]


