package main.app.server.observerdesign;

import javax.swing.JTextArea;

public class ServerMessageObserver implements ServerObserver {

	private JTextArea textArea;
	
	public ServerMessageObserver(JTextArea textArea)
	{
		this.textArea = textArea;
	}
	
	@Override
	public void notification(String message) {		
		this.textArea.append(message);
	}

}
