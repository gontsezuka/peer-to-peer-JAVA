package main.app.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import main.app.config.Singleton;
/**
 * 
 * @author zukaLover
 * Description: Server Thread that will continuously listen for connections.
 * Once a connection is established it will  forward them to a client handler thread assigned to each client
 */
public class ServerThread implements Runnable{

	private Singleton singleton;
	
	public ServerThread()
	{
		singleton = Singleton.getSingletonInstance();
	}
	
	
	@Override
	public void run() {
		
		System.out.println("Server-Name: "+singleton.getServerPeerName()+" has started on port: "+singleton.getServerPortNumber());
		try {
			ServerSocket serverSocket = new ServerSocket(singleton.getServerPortNumber());
			while(true)
			{
				Socket socket = serverSocket.accept();
				new Thread(new ServerAcceptClientThread(socket)).start();
			}
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
