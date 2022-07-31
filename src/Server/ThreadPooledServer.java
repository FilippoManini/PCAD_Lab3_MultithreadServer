package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer implements Runnable 
{
	protected int serverPort = 1234;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread= null;
	protected Eventi e;
	ExecutorService executor = Executors.newCachedThreadPool();
	
	public ThreadPooledServer(){
		this.e = new Eventi();
	}
	
	public ThreadPooledServer(int port){
		this.serverPort = port;
		this.e = new Eventi();
	}
	
	public void run()
	{
		synchronized(this){
			this.runningThread = Thread.currentThread(); 
		}
		
		try{ this.serverSocket = new ServerSocket(this.serverPort); }
		catch (IOException e) { throw new RuntimeException("Cannot open port "+serverPort, e); }
		
		System.out.println("Server is Running!\n");

		// Events creation
		for(int i=0; i<10; i++)
		{
			e.create("Event"+i, 10);
		}
		System.out.println("Server: events created\n");
		
		while(!isStopped())
		{
			 Socket clientSocket;
			 try
			 {
				 clientSocket = this.serverSocket.accept();
				 System.out.println("Connection Accepted");
			 }
			 catch (IOException e) 
			 {
				 if(isStopped()) {
					 break;
				 }
				 throw new RuntimeException("Error accepting client connection", e);
			 }
			 executor.execute(new WorkerRunnable(clientSocket, e));
		}

		// When it is stopped we no longer accept requests but we finish the current ones
		executor.shutdown();

		System.out.println("Server Stopped.");

		// Print final events state
		System.out.println("\n----------[Event List]--------------");
		System.out.println(e.printEventList());

		// Close event booking
		e.close();
	}
	
	
	private synchronized boolean isStopped() {
		 return this.isStopped;	 
	}
		 
	public synchronized void stop()
	{
		this.isStopped = true;
		
		try { this.serverSocket.close(); } 
		catch (IOException e) { throw new RuntimeException("Error closing server", e); }
	}
}