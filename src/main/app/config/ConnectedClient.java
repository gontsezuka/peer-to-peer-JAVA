package main.app.config;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author zukaLover
 * Description: This singleton class will maintain a list of all the connected clients(Peers) on server
 *
 */
public class ConnectedClient {

	private static ConnectedClient connectedClientInstance;
	private List<String> currentConnectedClients = new ArrayList<>();
	
	private ConnectedClient()
	{
		
	}
	
	public static ConnectedClient getConnectedClientInstance()
	{
		if(connectedClientInstance == null)
		{
			connectedClientInstance = new ConnectedClient();
		}
		
		return connectedClientInstance;
	}
	
	public synchronized void addConnectedClient(String client)
	{
		currentConnectedClients.add(client);
	}
	
	public synchronized void deleteConnectedClient(String client)
	{
		currentConnectedClients.remove(client);
	}
	
	public synchronized List<String> getAllClients()
	{
		return currentConnectedClients;
	}
	
}
