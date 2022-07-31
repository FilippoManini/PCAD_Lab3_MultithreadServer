package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class WorkerRunnable implements Runnable 
{
	protected Socket clientSocket;
	protected Eventi e;
	
	public WorkerRunnable(Socket clientSocket, Eventi e) 
	{
		this.clientSocket = clientSocket;
		this.e = e;
	}
	
	@Override
	public void run() 
	{
		PrintWriter out;
        BufferedReader in;

        try {
	        // get the outputstream of client
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	
	        // get the inputstream of client
	        in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));

	        String line;
	        while ((line = in.readLine()) != null)
	        {
				// Client want to stop connection
				if(line.contains("exit")) {
					System.out.println("Task Stopped.");
					return;
				}

				// Send the available events list if needed
				if(line.contains("eventList")) {
					out.println(e.formatEventList());
					System.out.printf(" Sent from the client: %s\n", line);
					continue;
				}

				// writing the received message from client
				StringTokenizer st = new StringTokenizer(line, " ");

				// Booking
				var name = st.nextToken();
				var nSeat = Integer.parseInt(st.nextToken());
				var bookingReply = e.book(name, nSeat);

				System.out.printf(" Sent from the client: %s\n", line);
				out.println(bookingReply);
	        }
        }       
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}

}
