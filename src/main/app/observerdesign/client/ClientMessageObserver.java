package main.app.observerdesign.client;

import java.io.DataOutputStream;
import java.io.IOException;

public class ClientMessageObserver implements ClientObserver {

	private DataOutputStream out;
	
	public ClientMessageObserver(DataOutputStream out)
	{
		this.out = out;
	}
	@Override
	public void notification(String message) {
		try {
			
			out.writeUTF(message);
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
