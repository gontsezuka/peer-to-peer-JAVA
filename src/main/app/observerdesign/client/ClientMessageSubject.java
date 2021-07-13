package main.app.observerdesign.client;

import java.util.ArrayList;
import java.util.List;

public class ClientMessageSubject implements ClientSubject {

	private List<ClientObserver> observerList = new ArrayList<>();
	
	public ClientMessageSubject()
	{
		
	}
	
	@Override
	public void addSubscriber(ClientObserver observer) {
		observerList.add(observer);
	}

	@Override
	public void removeSubscriber(ClientObserver observer) {
		observerList.remove(observer);
	}

	@Override
	public void notifySubscriber(String message) {
		observerList.forEach(observer -> observer.notification(message));
	}
	
	public void sendMessage(String message)
	{
		notifySubscriber(message);
	}

}
