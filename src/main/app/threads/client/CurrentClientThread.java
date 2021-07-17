package main.app.threads.client;

import java.util.Set;

/**
 * This Thread will maintain a list of current clients
 * @author zukaLover
 *
 */
public class CurrentClientThread implements Runnable {

	public CurrentClientThread()
	{
		
	}

	@Override
	public void run() 
	{
		while(true)
		{
			Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			
			for(Thread thread: threadSet)
			{
				
			}
		}
	}
}
