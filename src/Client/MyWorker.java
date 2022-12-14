package Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyWorker extends SwingWorker<String, Integer> {
    String bookingResult = null;

    private final GUI gui;
    private String bttName;
    String eventList = "";

    public MyWorker(GUI gui, String bttName){
        this.gui = gui;
        this.bttName = bttName;
    }

    @Override
    protected String doInBackground(){
        // establish a connection by providing host and port number
        try (Socket socket = new Socket("localhost", 1234))
        {
            // writing to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // reading from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Close GUI if "exit"
            if(gui.tf_eventName.getText().equals("exit"))
                gui.dispose();

            else if(bttName.equals("book")) {
            // Booking request: "name + num"
	            String line = gui.tf_eventName.getText() + " " + gui.tf_eventSeats.getText();
	
	            // sending the user input to server
	            out.println(line);
	            out.flush();
	
	            // save server reply
	            bookingResult = "Server replied:\n"+ in.readLine();
            }
            
            else if(bttName.equals("eventList"))
            {
            	out.println("eventList");
                out.flush();
                eventList += in.readLine();
                eventList = eventList.replace("&", "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

    // Update UI values
    @Override
    protected void done(){
    	if(bttName.equals("book")) {
	        // Update result value
	        gui.ta_bookingReturn.setText(bookingResult);
	        // Enable button
	        gui.btt_book.setEnabled(true);
    	}
    	else if(bttName.equals("eventList")) {
    		gui.ta_eventList.setText(eventList);
            gui.btt_eventList.setEnabled(true);
    	}
    }
}
