package Server;

public class MainServer {

	public static void main(String[] args) 
	{
		ThreadPooledServer server = new ThreadPooledServer();
		new Thread(server).start();
		
		try { Thread.sleep(120*1000); }
		catch (InterruptedException e) { e.printStackTrace(); }

		System.out.println("-----------------------------------");
		System.out.println("Stopping Server");
		server.stop();
	}

}
