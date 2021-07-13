package main.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import main.app.config.Singleton;
import main.app.server.observerdesign.ServerMessageObserver;
import main.app.server.observerdesign.ServerMessageSubject;

/**
 * 
 * @author zukaLover
 * Description: This class handles each client request Separately
 *
 */
public class ServerAcceptClientThread implements Runnable {

	private Socket socket;
	private Singleton singleton;
	private ServerMessageObserver observer;
	
	public ServerAcceptClientThread(Socket socket,ServerMessageObserver observer)
	{
		this.socket= socket;
		this.observer = observer;
		singleton= Singleton.getSingletonInstance();
	}
	
	@Override
	public void run() {
		System.out.println("New Client Connection has been established on server");
		try {
			
			ServerMessageSubject subject = new ServerMessageSubject();
			subject.addSubscriber(observer);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			String nameOfClient = in.readUTF();
			Thread.currentThread().setName(nameOfClient);
			
			System.out.println("This server:\""+singleton.getServerPeerName()+"\" will now receive messages from "+ nameOfClient);
			
			while(true)
			{
				//System.out.println("Message Received: "+ in.readUTF());
				subject.sendMessage(Thread.currentThread().getName()+": "+in.readUTF()+"\n");
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
