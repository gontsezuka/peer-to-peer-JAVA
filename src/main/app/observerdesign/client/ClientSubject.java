package main.app.observerdesign.client;

public interface ClientSubject {

	public void addSubscriber(ClientObserver observer);
	public void removeSubscriber(ClientObserver observer);
	public void notifySubscriber(String message);
}
