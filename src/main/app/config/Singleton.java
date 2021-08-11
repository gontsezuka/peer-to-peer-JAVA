package main.app.config;

import java.util.ArrayList;
import java.util.List;

import main.app.model.Server;


public class Singleton {

	private static Singleton instance;
	private String serverHostName;
	private int serverPortNumber;
	private String serverPeerName;
	private List<Server> serverList = new ArrayList<Server>();
	
	private Singleton()
	{
		
	}
	
	public static Singleton getSingletonInstance()
	{
		if(instance==null)
		{
			instance = new Singleton();
		}
		return instance;
	}

	public String getServerHostName() {
		return serverHostName;
	}

	public void setServerHostName(String serverHostName) {
		this.serverHostName = serverHostName;
	}

	public int getServerPortNumber() {
		return serverPortNumber;
	}

	public void setServerPortNumber(int serverPortNumber) {
		this.serverPortNumber = serverPortNumber;
	}

	public String getServerPeerName() {
		return serverPeerName;
	}

	public void setServerPeerName(String serverPeerName) {
		this.serverPeerName = serverPeerName;
	}
	
	public void addServer(Server server)
	{
		serverList.add(server);
	}
	
	public void removeServer(Server server)
	{
		serverList.remove(server);
	}
	
	public List<Server> getAllServers()
	{
		return serverList;
	}
	
	public Server getServerByName(String name)
	{
		String[] splitString = name.split(":");
		String peerName = splitString[0];
		int port = Integer.parseInt(splitString[1]);

		Server serverToReturn= null;
		for(Server server: serverList)
		{
			if(server.getServerPeerName().equals(peerName)&& server.getPort()==port)
			{
				serverToReturn = new Server();
				serverToReturn = server;
			}
		}
		return serverToReturn;
	}
}
