package main.app.server.observerdesign;

import java.util.ArrayList;
import java.util.List;

public class ServerMessageSubject implements ServerSubject{

	List<ServerObserver> observers = new ArrayList<ServerObserver>();
	
	@Override
	public void addSubscriber(ServerObserver observer) 
	{
		observers.add(observer);
	}

	@Override
	public void removeSubscriber(ServerObserver observer)
	{
		observers.remove(observer);
	}

	@Override
	public void notifySubscriber(String message) 
	{
		observers.forEach(observer -> observer.notification(message));
	}
	
	public void sendMessage(String message)
	{
		notifySubscriber(message);
	}

}
