package main.app.model;

public class Server {

	private String serverHostName;
	private String serverPeerName;
	private int port;
	
	public Server()
	{
		
	}

	public String getServerHostName() {
		return serverHostName;
	}

	public void setServerHostName(String serverHostName) {
		this.serverHostName = serverHostName;
	}

	public String getServerPeerName() {
		return serverPeerName;
	}

	public void setServerPeerName(String serverPeerName) {
		this.serverPeerName = serverPeerName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	
}
