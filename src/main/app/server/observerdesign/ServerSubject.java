package main.app.server.observerdesign;

public interface ServerSubject {

	public void addSubscriber(ServerObserver observer);
	public void removeSubscriber(ServerObserver observer);
	public void notifySubscriber(String message);
}
