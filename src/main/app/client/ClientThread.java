package main.app.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import main.app.config.Singleton;
import main.app.model.Server;
import main.app.observerdesign.client.ClientMessageObserver;
import main.app.observerdesign.client.ClientMessageSubject;

/**
 * 
 * @author zukaLover
 * Description: This class defines the client thread which we will use to connect to another Peer we would like to send 
 * Messages to.
 *
 */
public class ClientThread implements Runnable {
	private Server server;
	private Singleton singleton;
	private ClientMessageSubject subject;
	private Boolean threadProcess;
	
	public ClientThread(Server server, ClientMessageSubject subject)
	{
		this.server = server;
		this.subject = subject;
		threadProcess = true;
		singleton = Singleton.getSingletonInstance();
	}
	@Override
	public void run() 
	{
		try {
			Socket socket = new Socket(server.getServerHostName(),server.getPort());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			//The client will send it's name to server to tell it its them
			out.writeUTF(singleton.getServerPeerName()+":"+singleton.getServerPortNumber());
			ClientMessageObserver observer = new ClientMessageObserver(out);
			this.subject.addSubscriber(observer);
			//The client Thread must contain name of server
			Thread.currentThread().setName(server.getServerPeerName()+":"+server.getPort());
			while(threadProcess)
			{
				if(Thread.interrupted())
				{	
					threadProcess=false;
					out.close();
					socket.close();
					System.out.println("THREAD EXITED SUCCESSUFULLY");
					
				}
				//The OBSERVER must send a notification from here
				//Once a subject has changed
				//So the observer must receive a data output stream reference
				//It will use it to send out data to corresponding server
			}	
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
