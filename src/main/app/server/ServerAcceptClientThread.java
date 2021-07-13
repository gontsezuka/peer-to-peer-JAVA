package main.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import main.app.config.Singleton;

/**
 * 
 * @author zukaLover
 * Description: This class handles each client request Separately
 *
 */
public class ServerAcceptClientThread implements Runnable {

	private Socket socket;
	private Singleton singleton;
	
	public ServerAcceptClientThread(Socket socket)
	{
		this.socket= socket;
		singleton= Singleton.getSingletonInstance();
	}
	
	@Override
	public void run() {
		System.out.println("New Client Connection has been established on server");
		try {
			
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			String nameOfClient = in.readUTF();
			Thread.currentThread().setName(nameOfClient);
			
			System.out.println("This server:"+singleton.getServerPeerName()+"will now receive messages from "+ nameOfClient);
			
			while(true)
			{
				System.out.println("Message Received: "+ in.readUTF());
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
